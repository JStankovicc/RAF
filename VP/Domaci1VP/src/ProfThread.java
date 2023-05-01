import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class ProfThread implements Runnable{

    private CyclicBarrier cbStart;
    private CyclicBarrier cbStudents;
    private Semaphore semaphoreTTC;
    private StudentThread studentThread;

    public ProfThread(CyclicBarrier cbStart, CyclicBarrier cbStudents, Semaphore semaphoreTTC){
        this.cbStart = cbStart;
        this.cbStudents = cbStudents;
        this.semaphoreTTC = semaphoreTTC;
    }
    @Override
    public void run() {

        //PROFESOR I ASISTENT ISTOVREMENO POCINJU SA ODBRANOM

        try {
            cbStart.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        //SVE DOK ODBRANA TRAJE

        while (true){
            try{

                //PROFESOR UZIMA STUDENTA OD ONIH KOJI CEKAJU KOD NJEGA

                studentThread = Main.studentsProf.take();

                //PROFESOR NECE POCETI DA SLUSA ODBRANE DOK GOD NE BUDE MOGAO DA SLUSA DVE ISTOVREMENO

                cbStudents.await();

                studentThread.setProfOrAss(studentThread.getProfOrAss() + " " + Thread.currentThread().getId());

                //PROFESOR PRVO SLUSA ODBRANU JEDNOG PA DRUGOG STUDENTA

                semaphoreTTC.acquire();
                Thread.sleep((long) (studentThread.getTtc() * 1000));
                semaphoreTTC.release();

                //STUDENT DOBIJA OCENU

                studentThread.setScore(new Random().nextInt(5,11));
                Main.sumScores.addAndGet(studentThread.getScore());

                //STUDENT ZAVRSAVA SA ODBRANOM

                Main.studentsFinished.add(studentThread);
                studentThread = null;

            } catch (InterruptedException e) {

                //ISPISUJE SE KOJI STUDENT JE PREKINUT U ODBRANI AKO JE ODBRANA PREKINUTA

                if(!studentThread.equals(null)){
                    System.out.println("Student " + studentThread.getId() + " je prekinut u odbrani kod profesora.");
                }

                throw new RuntimeException(e);

            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
