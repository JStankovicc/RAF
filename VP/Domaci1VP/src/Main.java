import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static CyclicBarrier cbStart = new CyclicBarrier(3);
    public static CyclicBarrier cbProfWaiting = new CyclicBarrier(2);
    public static Semaphore semaphoreProfTTC = new Semaphore(1,true);
    public static BlockingQueue<StudentThread> studentsProf = new LinkedBlockingQueue<>();
    public static BlockingQueue<StudentThread> studentsAss = new LinkedBlockingQueue<>();
    public static AtomicInteger sumScores = new AtomicInteger(0);
    public static ArrayList<StudentThread> studentsFinished = new ArrayList<StudentThread>();
    public static void main(String[] args) {

        //UZIMA SE BROJ STUDENATA OD KORISNIKA

        System.out.println("Unesite broj studenata:");
        int N = new Scanner(System.in).nextInt();

        //ExecutorSevice ZA POKRETANJE NITI

        ExecutorService executorService = Executors.newFixedThreadPool(N+3);

        //KREIRANJE THREADOVA ZA PROFESORA I ASISTENTA

        executorService.submit(new AssThread(cbStart));
        executorService.submit(new ProfThread(cbStart, cbProfWaiting, semaphoreProfTTC));
        executorService.submit(new ProfThread(cbStart, cbProfWaiting, semaphoreProfTTC));

        //KREIRANJE THREADOVA STUDENATA

        for (int i = 0; i < N; i++) {
            executorService.submit(new StudentThread(i));
        }

        //ODBRANE TRAJU 5 SEKUNDI

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //ODBRANE SE PREKIDAJU NAKON 5 SEKUNDI

        executorService.shutdownNow();

        //ISPISUJU SE REZULTATI

        for(StudentThread student : studentsFinished){
            System.out.println("Thread: " + student.getId()
                                + " Arrival: " + student.getArrival()
                                + " Prof: " + student.getProfOrAss()
                                + " TTC: " + student.getTtc()
                                + " Score: " + student.getScore());
        }

        System.out.println("Prosecna ocena: " + sumScores.floatValue()/studentsFinished.size());

    }
}