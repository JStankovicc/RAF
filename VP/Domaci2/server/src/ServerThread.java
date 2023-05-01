import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable{

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String user;

    public ServerThread(Socket socket){
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        }catch (IOException e){
            ServerMain.serverThreads.remove(ServerThread.this);
            System.out.println("Konekcija nije uspela.");
        }
    }

    @Override
    public void run() {

        // UNOSENJE KORISNICKOG IMENA

        sendText("Unesite korisnicko ime:");
        try {
            while (true){
                user = in.readLine();
                if(checkUsername(user)){
                    break;
                }
                sendText("Korisnicko ime " + user + " je zauzeto, unesite drugo korisnicko ime:");
            }

            ServerMain.usernames.add(user);

            //POZDRAV NOVOM KORISNIKU I OBAVESTENJE POSTOJECIM

            sendText("Pozdrav " + user + "!");
            sendToAllExceptCurrent(user + " je usao u chat!");
            System.out.println(user + " je pristupio chat-u!");

            //SLANJE ISTORIJE PORUKA NOVOM KORISNIKU

            sendText("ISTORIJA PORUKA:");
            sendHistory();

        } catch (Exception e) {
            System.out.println("Nije moguce korisnika sa korisnickim imenom " + user);
        }

        while (true){
            try {

                //CITANJE NOVIH PORUKA

                String msg = in.readLine();

                //NE OBRADJUJU SE PRAZNE PORUKE

                if (msg.isBlank()) continue;

                Message message = new Message(user,msg);

                //PORUKA SE DODAJE U ISTORIJU

                message.addToHistory();

                //SVI OSTALI KORISNICI DOBIJAJU PORUKU

                sendToAllExceptCurrent(message);

            } catch (Exception e) {
                try {
                    if (socket != null) socket.close();
                    if (in != null) in.close();
                    if (out != null) out.close();

                }catch (Exception ex){
                    System.out.println("Doslo je do greske prilikom prekidanja konekcije sa korisnikom " + user);
                }finally {
                    System.out.println("Konekcija sa userom " + user + " se prekinula.");
                    break;
                }
            }
        }
    }

    //SLANJE TEKSTA TRENUTNOM KORISNIKU
    public void sendText(String message){
        out.println(message);
    }

    //SLANJE PORUKE TRENUTNOM KORISNIKU
    public void sendMessage(Message message){
        out.println(message.getMessageForSending());
    }

    //SLANJE POSLEDNJIH 100 PORUKA KORISNIKU
    public void sendHistory(){
        for (Message m : ServerMain.messages){
            sendMessage(m);
        }
    }

    //SLANJE PORUKE SVIM KORISNICIMA
    public void sendToAll(Message message){
        for(ServerThread serverThread : ServerMain.serverThreads){
            serverThread.sendMessage(message);
        }
    }

    //SLANJE TEKSTA SVIM KORISNICIMA
    public void sendToAll(String text){
        for (ServerThread serverThread : ServerMain.serverThreads){
                serverThread.sendText(text);
        }
    }

    //SLANJE PORUKE SVIM KORISNICIMA SEM TRENUTNOG
    public void sendToAllExceptCurrent(Message message){
        for (ServerThread serverThread : ServerMain.serverThreads){

            //AKO JE ULOGOVAN I NIJE SADASNJI KORISNIK

            if(serverThread.getUser() == null || serverThread.getUser().equals(user)) continue;

            serverThread.sendMessage(message);

        }
    }

    //SLANJE TEKSTA SVIM KORISNICIMA SEM TRENUTNOM
    public void sendToAllExceptCurrent(String text){
        for (ServerThread serverThread : ServerMain.serverThreads){
            if(serverThread.getUser() == null || serverThread.getUser().equals(user)) continue;
            serverThread.sendText(text);
        }
    }

    //PROVERAVA DA LI JE USERNAME SLOBODAN
    public boolean checkUsername(String username) {
        if(ServerMain.usernames.contains(username)) return false;
        return true;
    }

    public String getUser(){
        return user;
    }
}
