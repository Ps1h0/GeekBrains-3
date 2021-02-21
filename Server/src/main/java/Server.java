import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Server {

    private static final int DEFAULT_PORT = 8189;
    private final DAO dataBaseService = new DAO();
    private final ConcurrentLinkedDeque<ClientHandler> clients;


    public Server(int port) throws SQLException, ClassNotFoundException {
        dataBaseService.getConnection();
        clients = new ConcurrentLinkedDeque<>();
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("[DEBUG] server starter on port: " + port);
            while (true){
                Socket socket = server.accept();
                System.out.println("[DEBUG] client accepted");
                ClientHandler handler = new ClientHandler(socket, this);
                addClient(handler);
                new Thread(handler).start();
            }
        } catch (Exception e){
            System.err.println("Server was broken");
        }
        dataBaseService.closeConnection();
    }

    public void addClient(ClientHandler clientHandler) throws SQLException, ClassNotFoundException {
        clients.add(clientHandler);
        String name = clientHandler.getNickName();
        dataBaseService.addUser(name);
        System.out.println("[DEBUG] client added to broadcast queue");
    }

    public void removeClient(ClientHandler clientHandler){
        clients.remove(clientHandler);
        String name = clientHandler.getNickName();
        try {
            dataBaseService.deleteUser(name);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("[DEBUG] client removed from broadcast queue");
    }

    public void broadcastMessage(String message) throws IOException {
        for (ClientHandler client : clients){
            client.sendMessage(message);
        }
    }

    public void sendPrivateMessage(ClientHandler from, String nickName, String message) throws IOException {
        for (ClientHandler client : clients){
            if (client.getNickName().equals(nickName)){
                client.sendMessage("from " + from.getNickName() + ": " + message);
                from.sendMessage("to " + nickName + ": " + message);
                return;
            }
        }
        from.sendMessage("User " + nickName + " does not exist");
    }

    public void getTime() throws IOException {
        LocalTime time = LocalTime.now();
        broadcastMessage(time.toString());

    }

    public void changeNick(ClientHandler clientHandler, String newNick) throws IOException {
        for(ClientHandler client : clients){
            if (client.getNickName().equals(clientHandler.getNickName())){
                broadcastMessage(clientHandler.getNickName() + " changed nickname to " + newNick);
                try {
                    dataBaseService.updateUser(clientHandler.getNickName(), newNick);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                client.setNickName(newNick);
            }
        }
    }

    public boolean isNickBusy(String newNick){
        for (ClientHandler client : clients){
            if(client.getNickName().equals(newNick)) return true;
        }
        return false;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        int port = -1;
        if (args != null && args.length ==1){
            port = Integer.parseInt(args[0]);
        }
        if (port == -1){
            port = DEFAULT_PORT;
        }
        new Server(port);
    }
}