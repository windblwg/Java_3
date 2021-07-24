package ru.geekbrains.june.chat.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Controller<filename> {
    public Button nickPanel;
    @FXML
    TextArea chatArea;

    @FXML
    TextField messageField, usernameField, authArea;

    @FXML
    HBox authPanel, msgPanel;

    private Stage stage;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String userName;
    private File filePath;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean checkMessage(){
        if(messageField.getText().length() != 0) return true;
        return false;
    }

    public void sendMessage() {
        if (checkMessage()) {
            try {
                out.writeUTF(messageField.getText());
                messageField.clear();
                messageField.requestFocus();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void tryToAuth() {
        connect();
        try {
            out.writeUTF("/auth " + usernameField.getText());
            userName=usernameField.getText();
            usernameField.clear();
        } catch (IOException e) {
            showError("Невозможно отправить запрос авторизации на сервер");
        }
        nickPanel.setText(userName);
    }

    public void connect() {
        if (socket != null && !socket.isClosed()) {
            return;
        }
        try {
            socket = new Socket("localhost", 8189);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Thread readThread = new Thread(() -> {
                try {
                    while (true) {
                        String inputMessage = in.readUTF();
                        if (inputMessage.equals("/authok")) {
                            chatArea.clear();
                            msgPanel.setVisible(true);
                            msgPanel.setManaged(true);
                            authPanel.setVisible(false);
                            authPanel.setManaged(false);
                            chatArea.setText(" Пользователь " + userName+" вошёл в чат \n");
                            getMessages();
                            createOwnLog();
                            break;
                        }
                        else if (inputMessage.equals("/authNok")){
                            chatArea.appendText(" Логин "+ userName + " уже занят! Введите другой" + "\n");
                        }
                    }
                    while (true) {
                        String inputMessage = in.readUTF();
                        chatArea.appendText(inputMessage + "\n");
                        writeLog(inputMessage + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            readThread.start();
        } catch (IOException e) {
            showError("Невозможно подключиться к серверу");
        }
    }

    private void createOwnLog() throws IOException {
        String path = userName+"_log.txt";
        filePath = new File(path);
        if(filePath.createNewFile()){
            System.out.println("файл создан в корневой директории проекта");
        }else System.out.println("файл уже существует в корневой директории проекта");
        System.out.println(filePath);
    }

    public void disconnect(){
        stage.setOnCloseRequest(we -> disconnect());
        try{
            this.out.writeUTF("/off " + userName);
            System.out.println( userName + " вышел ");
            this.stage.close();
            this.socket.close();
            this.in.close();
            this.out.close();
        }  catch(    IOException    e){
            System.out.println("Disconnected");
        }
    }

    public void getMessages() {
        try {
            out.writeUTF("/hist");
        } catch (IOException e) {
            showError("Невозможно отправить запрос авторизации на сервер");
        }
     //   chatArea.appendText();
    }
    public void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message, ButtonType.OK).showAndWait();
    }

    public void onEnterAuth(ActionEvent actionEvent){
        tryToAuth();
    }

    public void onEnterSend(ActionEvent actionEvent){
        sendMessage();
    }

    public void onClose(ActionEvent actionEvent){
        disconnect();
    }

    public void writeLog(String s){
        try(FileWriter writer = new FileWriter(filePath,true))
        {
            writer.write(s);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }


}
