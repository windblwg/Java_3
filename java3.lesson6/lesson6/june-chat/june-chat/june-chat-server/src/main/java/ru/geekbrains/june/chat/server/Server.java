package ru.geekbrains.june.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.june.chat.server.JdbcApp.*;

public class Server extends JdbcApp {
    private List <ClientHandler> clients;
    private JdbcApp base = new JdbcApp();

    public Server() {
        try {
            this.clients = new ArrayList<>();
            ServerSocket serverSocket = new ServerSocket(8189);
            System.out.println("Сервер запущен. Ожидаем подключение клиентов..");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Подключился новый клиент");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean subscribe(ClientHandler c, String username) {
        try {
            if (base.isFree(username) == 0) {
                try {
                    base.setActive(username);
                    clients.add(c);
                    this.broadcastMessages(" Пользователь " + username + " вошёл в чат ");
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            } else return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

        public synchronized void unsubscribe (ClientHandler c, String username){
        try{
            base.setInactive(username);
            clients.remove(c);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        this.broadcastMessages(" Пользователь " + username + " вышел из чата ");
        }

        public synchronized void broadcastMessage (String message){
            String username = message.substring(0, message.indexOf(':'));
            for (ClientHandler c : clients) {
                try {
                    base.addMessage(username, message);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                c.sendMessage(message);
            }
        }

    public synchronized void broadcastMessages (String message){
        for (ClientHandler c : clients) {
            c.sendMessage(message);
        }
    }
}


