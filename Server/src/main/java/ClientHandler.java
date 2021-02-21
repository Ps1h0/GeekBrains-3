import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalTime;

public class ClientHandler implements Runnable{

    private final Socket socket;
    private final Server server;
    private DataInputStream in;
    private DataOutputStream out;
    private final boolean running;
    private String nickName;
    private static int cnt = 0;

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        running = true;
        cnt++;
        nickName = "user" + cnt;
    }

    public String getNickName() {
        return nickName;
    }

    @Override
    public void run() {
        try{
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            System.out.println("[DEBUG] client start processing");
            while (running){
                String message = in.readUTF();
                if (message.startsWith("/")){
                    if (message.equals("/quit")){
                        out.writeUTF(message);
                    }
                    if (message.equals("/time")){
                        server.getTime();
                    }
                    if (message.startsWith("/changenick ")){
                        String newNick = message.substring(message.indexOf(" ") + 1);
                        server.changeNick(this, newNick);
                    }
                    if (message.startsWith("/w ")){
                        String[] tokens = message.split("\\s");
                        String nick = tokens[1];
                        String msg = message.substring(4 + nick.length());
                        server.sendPrivateMessage(this, nick, msg);
                    }
                    continue;
                }
                server.broadcastMessage(nickName + ": " + message);
                System.out.println("[DEBUG] message from client: " + message);
            }
        } catch (IOException ioException) {
            System.err.println("Handled connection was broken");
            server.removeClient(this);
        }

    }

    public void sendMessage(String message) throws IOException {
        LocalTime time = LocalTime.now();
        out.writeUTF("[" + time + "] " + message);
        out.flush();

    }
}