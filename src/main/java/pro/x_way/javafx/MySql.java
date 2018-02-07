package pro.x_way.javafx;

import pro.x_way.javafx.Model.Message;

import java.sql.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MySql {
    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/tabs?useSSL=false&UseUnicode=true&characterEncoding=UTF-8";
    private static final String user = "root";
    private static final String password = "";
    public static final String INSERT_INTO = "INSERT INTO all_tabs (title, message, create_date) VALUES (?, ?, ?);";
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS all_tabs (id INT PRIMARY KEY auto_increment, title VARCHAR (50) NOT NULL , message VARCHAR(100) NOT NULL, create_date DATE NOT NULL);";

    public static boolean addMessage(String title, String message) {
        AtomicBoolean tmp = new AtomicBoolean(false);

        new Thread(() -> {
            Connection con = null;
            Statement stmt = null;
            try {
                con = DriverManager.getConnection(url, user, password);
                stmt = con.createStatement();

                stmt.executeUpdate(CREATE_TABLE);
                PreparedStatement preparedStatement = con.prepareStatement(INSERT_INTO);
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, message);
                preparedStatement.setDate(3, new Date(System.currentTimeMillis()));
                preparedStatement.executeUpdate();
                tmp.set(true);
            } catch (SQLException sqlEx) {
                tmp.set(false);
                sqlEx.printStackTrace();
            } finally {
                try {
                    con.close();
                    stmt.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }

            }
        }).start();
        return tmp.get();
    }

    public static void getMessage(String textUpdate) {
        new Thread(() -> {
            Connection con = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {
                con = DriverManager.getConnection(url, user, password);
                stmt = con.createStatement();

                stmt.executeUpdate(CREATE_TABLE);

                if (textUpdate == null) {
                    rs = stmt.executeQuery("select * from all_tabs;");
                } else {
                    rs = stmt.executeQuery("select * from all_tabs WHERE title = '" + textUpdate + "';");
                }

                while (rs.next()) {
                    Message message = new Message(rs.getInt(1), rs.getString(2),
                            rs.getString(3), rs.getDate(4));
                    Listener.getInstance().getAbq().put(message);
                }
            } catch (SQLException | InterruptedException sqlEx) {
                sqlEx.printStackTrace();
            } finally {
                try {
                    con.close();
                    stmt.close();
                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }

            }
        }).start();
    }

}
