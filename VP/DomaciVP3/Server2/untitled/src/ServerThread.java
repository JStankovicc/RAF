import HTTP.HttpRequest;
import HTTP.HttpResponse;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private RequestHandler requestHandler;

    public ServerThread(Socket socket) {
        this.socket = socket;
        this.requestHandler = new RequestHandler();
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            HttpRequest request = new HttpRequest(this.in);
            HttpResponse response = requestHandler.handleRequest(request);
            out.println(response.toString());
            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
