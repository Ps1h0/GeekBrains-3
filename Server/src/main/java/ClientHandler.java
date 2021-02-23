import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
            readFromFile("Client/src/main/resources/history" + nickName + ".txt");
            server.broadcastMessage("User " + nickName + " connected");
            while (running){
                String message = in.readUTF();
                if (message.startsWith("/")){
                    if (message.equals("/quit")){
                        server.broadcastMessage("User " + nickName + " disconnected");
                        out.writeUTF(message);
                    }
                    if (message.equals("/time")){
                        server.getTime();
                    }
                    if (message.startsWith("/changenick ")){
                        String newNick = message.substring(message.indexOf(" ") + 1);
                        if(!server.isNickBusy(newNick))
                            server.changeNick(this, newNick);
                        else
                            sendMessage("Nickname is busy");
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
        String msg = "[" + time + "]" + message;
        out.writeUTF(msg);
        out.flush();
        writeToFile(msg);
    }

    private void writeToFile(String message){
        File file = new File("Client/src/main/resources/history" + nickName + ".txt");
        try(BufferedWriter fw = new BufferedWriter(new FileWriter(file, true))) {
            fw.write(message + "\n");
            fw.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void readFromFile(String filepath) {
        File file = new File(filepath);
        try(ReversedLinesFileReader reader = new ReversedLinesFileReader(file, Charset.defaultCharset())){
            List<String> list = reader.readLines(100);
            for (int i = list.size() - 1; i >= 0; i--){
                out.writeUTF(list.get(i));
                out.flush();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}