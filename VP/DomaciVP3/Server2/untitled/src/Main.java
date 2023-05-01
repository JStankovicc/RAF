import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static final int PORT = 8888;

    public static final QOTD qotd = new QOTD();

    public static void main(String[] args) {

        try {
            ServerSocket ss = new ServerSocket(PORT);
            while (true) {
                Socket sock = ss.accept();
                new Thread(new ServerThread(sock)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}