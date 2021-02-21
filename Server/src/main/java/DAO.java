import java.sql.*;

public class DAO {

    private static Connection connection;

    public void addUser(String nickName) throws SQLException{
        Statement statement = connection.createStatement();
        statement.executeUpdate("insert into users(Nickname) values('" + nickName + "')");
        statement.close();
    }

    public void deleteUser(String nickName) throws SQLException, ClassNotFoundException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from users where Nickname = '" + nickName + "'");
        statement.close();
    }

    public void updateUser(String oldNickname, String newNickname) throws SQLException, ClassNotFoundException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("update users set Nickname = '" + newNickname + "' where Nickname = '" + oldNickname + "'");
        statement.close();
    }

    public void getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Server/database.db");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
