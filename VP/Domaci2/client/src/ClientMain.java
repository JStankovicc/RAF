import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientMain {

    public static final int PORT = 9999;
    public static BufferedReader in;
    public static PrintWriter out;
    public static void main(String[] args) {

        Socket socket = null;

        try {

            socket = new Socket("localhost", PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            Scanner scanner = new Scanner(System.in);

            //THREAD KOJI SLUSA PORUKE RADI PARALELNO SA MAIN THREADOM KOJI SLUZI ZA SLANJE PORUKA
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(new ClientListenThread());

            while (true){
                out.println(scanner.nextLine());
            }

        } catch (IOException e) {
            System.out.println("Konekcija sa serverom nije uspela.");
        } finally {
            try {
                if (socket != null) socket.close();
                if (in != null) in.close();
                if (out != null) out.close();
            }catch (Exception e){
                System.out.println("Nije moguce osloboditi resurse.");
            }
        }

    }
}