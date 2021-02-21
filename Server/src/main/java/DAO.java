import java.sql.*;

public class DAO {

    private static Connection connection;

    public void addUser(String nickName) throws SQLException, ClassNotFoundException {
        getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into users(Nickname) values('" + nickName + "')");
        preparedStatement.executeUpdate();
    }

    public void deleteUser(String nickName) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from users where Nickname = '" + nickName + "'");
        preparedStatement.executeUpdate();
    }

    public void updateUser(String oldNickname, String newNickname) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = connection.prepareStatement("update users set Nickname = '" + newNickname + "' where Nickname = '" + oldNickname + "'");
        preparedStatement.executeUpdate();
    }

    public void getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Server/database.db");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
