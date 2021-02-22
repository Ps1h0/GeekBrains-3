import java.sql.*;

public class DAO {

    private static Connection connection;

    public void addUser(String nickName){
        connection = getConnection();
        try(PreparedStatement ps = connection.prepareStatement("insert into users(Nickname) values(?)")) {
            ps.setString(1, nickName);
            ps.executeUpdate();
        } catch (SQLException e) {
            closeConnection();
        }
    }

    public void deleteUser(String nickName) {
        connection = getConnection();
        try (PreparedStatement ps = connection.prepareStatement("delete from users where Nickname = ?")) {
            ps.setString(1, nickName);
            ps.executeUpdate();
        } catch (SQLException e){
            closeConnection();
        }
    }

    public void updateUser(String oldNickname, String newNickname) {
        connection = getConnection();
        try(PreparedStatement ps = connection.prepareStatement("update users set Nickname = ? where Nickname = ?")) {
            ps.setString(1, newNickname);
            ps.setString(2, oldNickname);
            ps.executeUpdate();
        } catch (SQLException e){
            closeConnection();
        }
    }

    public Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Server/database.db");
        } catch (ClassNotFoundException | SQLException e) {
            closeConnection();
        }
        return connection;
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
