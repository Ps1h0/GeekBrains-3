import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Network {

    private static final int PORT = 8189;
    private DataInputStream in;
    private DataOutputStream out;
    private static Network instance;
    private Socket socket;

    public static Network getInstance() {
        if (instance == null){
            instance = new Network();
        }
        return instance;
    }

    private Network(){
        try{
            socket = new Socket("localhost", PORT);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        }catch (Exception e){
            System.err.println("Problem with server on port: " + PORT);
        }
    }

    public String readMessage() throws IOException {
        return in.readUTF();
    }

    public void writeMessage(String message) throws IOException {
        out.writeUTF(message);
        out.flush();
    }

    public void close() throws IOException {
        out.close();
        in.close();
        socket.close();
    }
}