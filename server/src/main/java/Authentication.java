import at.favre.lib.crypto.bcrypt.BCrypt;
import ru.gb.AuthRequest;
import ru.gb.ChangeNickRequest;
import ru.gb.ChangePasswordRequest;
import ru.gb.RegRequest;

import java.sql.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Authentication {
    private Connection connection;
    private Statement stmt;
    private CopyOnWriteArrayList<String> clients = new CopyOnWriteArrayList<>();

    Authentication() {
        try {
            connectDB();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void connectDB() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:users.db");
        stmt = connection.createStatement();
        Server.getLogger().info("Database connected");
    }


    public boolean registration(RegRequest regData) {

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT login FROM users WHERE login = ?;");
            ps.setString(1, regData.getLogin());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return false;
            }
            PreparedStatement ps2 = connection.prepareStatement("INSERT INTO users (nick, login, password)  VALUES (?,?,?);");
            ps2.setString(1, regData.getNick());
            ps2.setString(2, regData.getLogin());
            ps2.setString(3, BCrypt.withDefaults().hashToString(12, regData.getPassword().toCharArray()));
            ps2.executeUpdate();
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean login(AuthRequest authData) {

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT login, password FROM users WHERE login = ?;");
            ps.setString(1, authData.getLogin());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BCrypt.Result result = BCrypt.verifyer().verify(authData.getPassword().toCharArray(), rs.getString(2));
                if (result.verified) {
                    return true;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changePassword(ChangePasswordRequest changePasswordRequest) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT nick FROM users WHERE nick = ?;");
            ps.setString(1, changePasswordRequest.getNick());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PreparedStatement ps1 = connection.prepareStatement("UPDATE users SET password = ? WHERE nick = ?;");
                ps1.setString(1, BCrypt.withDefaults().hashToString(12, changePasswordRequest.getPassword().toCharArray()));
                ps1.setString(2, changePasswordRequest.getNick());
                ps1.executeUpdate();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public String getLogin(AuthRequest authRequest) {
        return authRequest.getLogin();
    }
    public String getNick (String login){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT nick FROM users WHERE login = ?;");
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString("nick"));
                return rs.getString("nick");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public CopyOnWriteArrayList<String> getClients() {
        return clients;
    }

    public boolean changeNick(ChangeNickRequest nickRequest) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT nick FROM users WHERE nick = ?;");
            ps.setString(1, nickRequest.getNick());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PreparedStatement ps1 = connection.prepareStatement("UPDATE users SET nick = ? WHERE nick = ?;");
                ps1.setString(1, nickRequest.getNewNick());
                ps1.setString(2, nickRequest.getNick());
                ps1.executeUpdate();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}