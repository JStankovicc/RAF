import HTTP.HttpRequest;
import HTTP.HttpResponse;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable{

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private RequestHandler requestHandler;

    public ServerThread(Socket socket) {
        this.socket = socket;
        this.requestHandler = new RequestHandler();
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        } catch (IOException e) {
            System.out.println("Nije uspesno kreirana konekcija sa klijentom.");
        }
    }

    public void run() {
        try {
            //PRIMA SE ZAHTEV
            HttpRequest request = new HttpRequest(this.in);

            //ZAHTEV SE OBRADJUJE
            HttpResponse response = requestHandler.handleRequest(request);

            //SALJE SE ODGOVOR
            out.println(response.toString());

            //OSLOBADJAJU SE RESURSI
            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("Doslo je do greske u unosu klijenta.");
        } catch (Exception e) {
            System.out.println("Doslo je do greske prilikom komunikacije sa klijentom.");
            e.printStackTrace();
        }
    }
}
