import java.util.Random;

import static java.lang.Thread.sleep;

public class StudentThread implements Runnable{

    private int id;
    private String profOrAss;
    private float ttc;
    private float arrival;
    private int score;

    public StudentThread(int id){

        //BRZINA ODBRANE I VREME DOLASKA SE GENERISU PRI KREIRANJU

        this.id = id;
        this.arrival = 1 - new Random().nextFloat(0,1);
        this.ttc = (float) (Math.random() * 0.5f + 0.5f);

    }

    @Override
    public void run() {

        try {

            //STUDENT KASNI NA ODBRANU ODREDJENO VREME

            Thread.sleep((long) arrival * 1000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //STUDENT DOLAZI NA ODBRANU I NASUMICNO (50% sanse) JE RASPOREDJEN KOD PROFESORA ILI ASISTENTA
        if(new Random().nextBoolean()){

            profOrAss = "Profesor";
            Main.studentsProf.add(this);

        }else {
            profOrAss = "Asistent";
            Main.studentsAss.add(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfOrAss() {
        return profOrAss;
    }

    public void setProfOrAss(String profOrAss) {
        this.profOrAss = profOrAss;
    }

    public float getTtc() {
        return ttc;
    }

    public void setTtc(float ttc) {
        this.ttc = ttc;
    }

    public float getArrival() {
        return arrival;
    }

    public void setArrival(float arrival) {
        this.arrival = arrival;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
