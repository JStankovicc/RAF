import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {
    public static List<Message> messages = Collections.synchronizedList(new ArrayList<Message>());
    public static List<String> usernames = Collections.synchronizedList(new ArrayList<String>());
    public static List<ServerThread> serverThreads = Collections.synchronizedList(new ArrayList<ServerThread>());
    private static int PORT = 9999;
    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("SERVER JE POKRENUT");

            //PRIMAMO SVE KONEKCIJE

            while (true){

                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket);

                //DODAJEMO U LISTU THREADOVA

                serverThreads.add(serverThread);

                //POKRECEMO THREAD

                executorService.submit(serverThread);

            }

        } catch (IOException e) {
            System.out.println("Nesto nije u redu. Aplikacija se prekida.");
        }finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.out.println("Nije moguce oslobadjanje resursa.");
                }
            }
        }
    }
}