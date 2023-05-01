package HTTP;

import java.io.*;
import java.net.Socket;

public class MakeRequest {

    public static <T> T makeGetRequestForJSON(String host, int port, String path) throws IOException {

        Socket socket = new Socket(host, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        out.println("GET " + path + " HTTP/1.1\r\nHost: " + host + "\r\n\r\n");

        String line;

        do {
            line = in.readLine();
        } while (!line.trim().equals(""));

        StringBuilder payload = new StringBuilder();

        while (in.ready()) {
            payload.append((char) in.read());
        }

        T output = (T) payload.toString();

        return output;
    }

}