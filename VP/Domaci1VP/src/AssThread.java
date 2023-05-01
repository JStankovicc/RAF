import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static java.lang.Thread.sleep;

public class AssThread implements Runnable{

    private CyclicBarrier cbStart;
    private StudentThread studentThread;

    public AssThread(CyclicBarrier cbStart){
        this.cbStart = cbStart;
    }

    @Override
    public void run() {


        //ASISTENT I PROFESOR POCINJU DA SLUSAJU ODBRANE U ISTO VREME

        try {
            cbStart.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        //SVE DOK TRAJE 5 SEKUNDI

        while (true){

            try {

                //ASISTENT UZIMA SLEDECEG STUDENTA

                studentThread = Main.studentsAss.take();

                studentThread.setProfOrAss(studentThread.getProfOrAss() + " " + Thread.currentThread().getId());

                //ASISTENT SLUSA ODBRANU

                Thread.sleep((long) (studentThread.getTtc()*1000));

                //STUDENT DOBIJA OCENU

                studentThread.setScore(new Random().nextInt(5,11));
                Main.sumScores.addAndGet(studentThread.getScore());

                //STUDENT ZAVRSAVA ODBRANU

                Main.studentsFinished.add(studentThread);
                studentThread = null;

            } catch (InterruptedException e) {

                //UKOLIKO JE STUDENT PREKINUT U ODBRANI ISPISUJE SE KOJI JE TO STUDENT

                if(!studentThread.equals(null)){
                    System.out.println("Student " + studentThread.getId() + " je prekinut u odbrani kod asistenta.");
                }

                throw new RuntimeException(e);

            }

        }
    }
}
