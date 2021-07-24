package ru.geekbrains.june.chat.server;

import java.sql.*;
import java.text.SimpleDateFormat;

public class JdbcApp {
    private static Connection connection;
    private static Statement stmt;

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try {
            connect();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    public static void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:C:\\sqlite\\windblwg_chat.s3db");
        stmt = connection.createStatement();
    }

    public static void disconnect() {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addMessage(String username, String message) throws SQLException {
        String queryAdd = "INSERT INTO Messages_log (Alias, DT, Message, Error) VALUES (?, ?, ?, NULL);";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            PreparedStatement ps = connection.prepareStatement(queryAdd);
            ps.setString(1, username);
            ps.setString(2, dateFormat.format(timestamp));
            ps.setString(3, message);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setActive(String username) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM USERS WHERE ALIAS = ?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        String queryAdd = "INSERT INTO Users (Alias, isActive, FD, TD, Par1, Par2) VALUES (?, ?, ?, NULL, NULL, NULL);";
    //    String logAdd = "INSERT INTO Messages_log (Alias, DT, Message, Error) VALUES (?, ?, 'Entered in the chat' , NULL);";
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                PreparedStatement ps1 = connection.prepareStatement(queryAdd);
        //  PreparedStatement ps2 = connection.prepareStatement(logAdd);
                ps1.setString(1, username);
         //       ps2.setString(1, username);
                ps1.setBoolean(2, true);
                ps1.setString(3, dateFormat.format(timestamp));
          //      ps2.setString(2, dateFormat.format(timestamp));
                ps1.execute();
           //     ps2.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static void setInactive(String username) throws SQLException {
        String queryDel = "UPDATE Users SET isActive=false, TD=? WHERE ALIAS = ?";
     //   String logAdd = "INSERT INTO Messages_log (Alias, DT, Message, Error) VALUES (?, ?, 'Entered in the chat' , NULL);";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            PreparedStatement ps1 = connection.prepareStatement(queryDel);
     //       PreparedStatement ps2 = connection.prepareStatement(logAdd);
            ps1.setString(1, dateFormat.format(timestamp));
     //       ps2.setString(1, String.valueOf(username));
            ps1.setString(2, String.valueOf(username));
     //       ps2.setString(2, dateFormat.format(timestamp));
            ps1.executeUpdate();
      //      ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int isFree(String username) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM USERS WHERE ALIAS = ?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        int count = 0;

        while(rs.next()){
            count++;
        }
        System.out.println(count);
        return count;
    }

    public static String getLastMessages(int count) throws SQLException {
        String mess = "";
        String[] lastMess = new String[count];
        int i;

        try{
            String lastMessages = "SELECT * FROM (SELECT * FROM Messages_log ORDER BY id DESC LIMIT ?) t ORDER BY id";
            PreparedStatement ps = connection.prepareStatement(lastMessages);
            ps.setInt(1, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                    System.out.println(rs.getString("MESSAGE"));
                    mess = mess+rs.getString("MESSAGE")+"\n";
                }
                return mess;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mess;
    }
}
