import java.sql.*;

public class DAO {

    public void addUser(String nickName){
        try(Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement("insert into users(Nickname) values(?)")) {
            ps.setString(1, nickName);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String nickName) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("delete from users where Nickname = ?")) {
            ps.setString(1, nickName);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateUser(String oldNickname, String newNickname) {
        try(Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("update users set Nickname = ? where Nickname = ?")) {
            ps.setString(1, newNickname);
            ps.setString(2, oldNickname);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Server/database.db");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
