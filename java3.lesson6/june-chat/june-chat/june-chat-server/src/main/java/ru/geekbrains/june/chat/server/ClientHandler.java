package ru.geekbrains.june.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

import ru.geekbrains.june.chat.server.Server.*;

public class ClientHandler extends JdbcApp {
    private Server server;
    private Socket socket;
    private String username;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientHandler(Server server, Socket socket) {
        ExecutorService service = Executors.newFixedThreadPool(3);
        service.submit(() -> {
            try {
                this.server = server;
                this.socket = socket;
                this.in = new DataInputStream(socket.getInputStream());
                this.out = new DataOutputStream(socket.getOutputStream());
                new Thread(() -> {
                    try {
                        while (true) {
                            String inputMessage = in.readUTF();
                            System.out.println(inputMessage);
                            if (inputMessage.startsWith("/auth ")) {
                                username = inputMessage.split("\\s+", 2)[1];
                                System.out.println(this.username);
                                if (server.subscribe(this, username)) {
                                    sendMessage("/authok");
                                    break;
                                } else {
                                    sendMessage("/authNok");
                                    System.out.println(" NOK ");
                                }
                            } else {
                                sendMessage("SERVER: Вам необходимо авторизоваться");
                            }
                        }
                        while (true) {
                            String inputMessage = in.readUTF();
                            if (inputMessage.startsWith("//")) {
                                continue;
                            } else if (inputMessage.startsWith("/hist")) {
                                String mess = getLastMessages(100);
                                sendMessage(mess);
                            } else if (inputMessage.startsWith("/off ")) {
                                System.out.println(inputMessage);
                                username = inputMessage.split("\\s+", 2)[1];
                                server.unsubscribe(this, username);
                                System.out.println(username + " отключился ");
                            }
                            server.broadcastMessage(username + ": " + inputMessage);
                        }
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                service.shutdown();
            }
        });
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
