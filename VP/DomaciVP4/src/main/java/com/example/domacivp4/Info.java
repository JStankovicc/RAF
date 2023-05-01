package com.example.domacivp4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Info {

    private Map<String,Integer> izabranaJela;

    private Map<String , Integer> mapaPon;
    private Map<String , Integer> mapaUto;
    private Map<String , Integer> mapaSre;
    private Map<String , Integer> mapaCet;
    private Map<String , Integer> mapaPet;

    private int key;

    private static Info instance = null;

    private Info() {
        mapaPon = new HashMap<>();
        mapaUto = new HashMap<>();
        mapaSre = new HashMap<>();
        mapaCet = new HashMap<>();
        mapaPet = new HashMap<>();

        izabranaJela = new HashMap<>();
        key = 0;
    }

    public static synchronized Info getInstance() {
        if (instance == null) {
            instance = new Info();
        }
        return instance;
    }

    public void printMapSize(){
        System.out.println(izabranaJela.size());
    }

    public List<String> getIzabranaJela() {
        List<String> list = new ArrayList<>(izabranaJela.keySet());
        return list;
    }

    public Map<String , Integer> getIzabranaJelaMap(){
        return izabranaJela;
    }

    public List<String> getPonede() {
        List<String> list = new ArrayList<>(mapaPon.keySet());
        return list;
    }

    public Map<String , Integer> getPonedeMap(){
        return mapaPon;
    }

    public List<String> getUtor() {
        List<String> list = new ArrayList<>(mapaUto.keySet());
        return list;
    }

    public Map<String , Integer> getUtorMap(){
        return mapaUto;
    }

    public List<String> getSred() {
        List<String> list = new ArrayList<>(mapaSre.keySet());
        return list;
    }

    public Map<String , Integer> getSredMap(){
        return mapaSre;
    }

    public List<String> getCetv() {
        List<String> list = new ArrayList<>(mapaCet.keySet());
        return list;
    }

    public Map<String , Integer> getCetvMap(){
        return mapaCet;
    }

    public List<String> getPeta() {
        List<String> list = new ArrayList<>(mapaPet.keySet());
        return list;
    }

    public Map<String , Integer> getpetaMap(){
        return mapaPet;
    }

    public void dodajIzabranoJelo(String string){
        int count = izabranaJela.getOrDefault(string, 0);
        izabranaJela.put(string, count + 1);
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void restartMap(){
        izabranaJela.clear();
    }

    public void dodajPon(String string){
        int count = mapaPon.getOrDefault(string, 0);
        mapaPon.put(string, count + 1);
    }
    public void dodajUto(String string){
        int count = mapaUto.getOrDefault(string, 0);
        mapaUto.put(string, count + 1);
    }
    public void dodajSre(String string){
        int count = mapaSre.getOrDefault(string, 0);
        mapaSre.put(string, count + 1);
    }
    public void dodajCet(String string){
        int count = mapaCet.getOrDefault(string, 0);
        mapaCet.put(string, count + 1);
    }
    public void dodajPet(String string){
        int count = mapaPet.getOrDefault(string, 0);
        mapaPet.put(string, count + 1);
    }

}

