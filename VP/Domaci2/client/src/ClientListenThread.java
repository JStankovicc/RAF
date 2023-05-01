import java.io.BufferedReader;
import java.io.IOException;

public class ClientListenThread implements Runnable{
    public ClientListenThread() {}

    @Override
    public void run() {

        //SLUSA PORUKE I ISPISUJE IH U KONZOLU
        while (true) {

            try {

                String msg = ClientMain.in.readLine();
                System.out.println(msg);

            } catch (IOException e) {

                System.out.println("Greska prilikom komunikacije sa serverom.");
                break;

            }
        }
    }
}
