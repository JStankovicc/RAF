import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {

    //KREIRAMO LISTU CITATA
    public static CopyOnWriteArrayList<Quote> ListOfQuotes = new CopyOnWriteArrayList<>();

    //PORT
    public static final int PORT = 80;
    public static void main(String[] args) {

        try {

            //KREIRAMO SOCKET ZA SVAKU NOVU KONEKCIJU

            ServerSocket ss = new ServerSocket(PORT);
            while (true) {
                Socket socket = ss.accept();
                new Thread(new ServerThread(socket)).start();
            }

        } catch (IOException e) {
            System.out.println("Server je prekinuo sa radom.");
        }
    }
}