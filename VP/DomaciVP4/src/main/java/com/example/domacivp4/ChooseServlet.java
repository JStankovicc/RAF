package com.example.domacivp4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@WebServlet(name = "chooseservlet",value = "/chooseFood")
public class ChooseServlet extends HttpServlet {

    private CopyOnWriteArrayList<String> jelaPonedeljak = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<String> jelaUtorak = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<String> jelaSreda = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<String> jelaCetvrtak = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<String> jelaPetak = new CopyOnWriteArrayList<>();
    private String message;

    private Info info = Info.getInstance();

    public ChooseServlet() {
        System.out.println("ChooseServlet kreiran");
        message = "";
    }

    public void init(){}

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        getJelaPonedeljak();
        getJelaUtorak();
        getJelaSreda();
        getJelaCetvrtak();
        getJelaPetak();

        out.println("<html><body>");

        if(message!="") {
            out.println(message);
            message = "";
        }
        out.println("<h1>Choose your food</h1>");
        out.println("<p>Odaberite vas rucak:</p>");
        out.println("<label for=\"ponedeljak\">Ponedeljak:</label>");
        out.println("<br><select name=\"ponedeljak\" id=\"ponedeljak\" form=\"form\">");

        for(String jelo : jelaPonedeljak){
            out.println("<option value=\"" + jelo + "\">" + jelo + "</option>");
        }

        out.println("</select>");



        out.println("<label for=\"utorak\">Utorak:</label>");
        out.println("<br><select name=\"utorak\" id=\"utorak\" form=\"form\">");

        for(String jelo : jelaUtorak){
            out.println("<option value=\"" + jelo + "\">" + jelo + "</option>");
        }

        out.println("</select>");



        out.println("<label for=\"sreda\">Sreda:</label>");
        out.println("<br><select name=\"sreda\" id=\"sreda\" form=\"form\">");

        for(String jelo : jelaSreda){
            out.println("<option value=\"" + jelo + "\">" + jelo + "</option>");
        }

        out.println("</select>");



        out.println("<label for=\"cetvrtak\">Cetvrtak:</label>");
        out.println("<br><select name=\"cetvrtak\" id=\"cetvrtak\" form=\"form\">");

        for(String jelo : jelaCetvrtak){
            out.println("<option value=\"" + jelo + "\">" + jelo + "</option>");
        }

        out.println("</select>");



        out.println("<label for=\"petak\">Petak:</label>");
        out.println("<br><select name=\"petak\" id=\"petak\" form=\"form\">");

        for(String jelo : jelaPetak){
            out.println("<option value=\"" + jelo + "\">" + jelo + "</option>");
        }

        out.println("</select>");


        out.println("<br><form method=\"POST\" action=\"/chooseFood\" id=\"form\">\n" +
                "  <input type=\"submit\">\n" +
                "</form>\n");
        out.println("</body></html>");

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{


        HttpSession session = req.getSession();



        if(session.getAttribute("key") == null || (Integer)session.getAttribute("key") != info.getKey()) {

            synchronized (this) {
                info.dodajPon(req.getParameter("ponedeljak"));
                info.dodajUto(req.getParameter("utorak"));
                info.dodajSre(req.getParameter("sreda"));
                info.dodajCet(req.getParameter("cetvrtak"));
                info.dodajPet(req.getParameter("petak"));


                session.setAttribute("key",info.getKey());
            }
        } else {
            message = " Vec je naruceno ";
        }

        resp.sendRedirect("chooseFood");

    }



    public void getJelaPonedeljak(){
        try (BufferedReader br = new BufferedReader(new FileReader("E:\\DOCS\\Fakultet\\RAF\\VP\\DomaciVP4\\src\\main\\java\\com\\example\\domacivp4\\ponedeljak.txt"))) {
            jelaPonedeljak.removeAll(jelaPonedeljak);
            String line;
            while ((line = br.readLine()) != null) {
                jelaPonedeljak.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getJelaUtorak(){
        try (BufferedReader br = new BufferedReader(new FileReader("E:\\DOCS\\Fakultet\\RAF\\VP\\DomaciVP4\\src\\main\\java\\com\\example\\domacivp4\\utorak.txt"))) {
            jelaUtorak.removeAll(jelaUtorak);
            String line;
            while ((line = br.readLine()) != null) {
                jelaUtorak.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getJelaSreda(){
        try (BufferedReader br = new BufferedReader(new FileReader("E:\\DOCS\\Fakultet\\RAF\\VP\\DomaciVP4\\src\\main\\java\\com\\example\\domacivp4\\sreda.txt"))) {
            jelaSreda.removeAll(jelaSreda);
            String line;
            while ((line = br.readLine()) != null) {
                jelaSreda.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getJelaCetvrtak(){
        try (BufferedReader br = new BufferedReader(new FileReader("E:\\DOCS\\Fakultet\\RAF\\VP\\DomaciVP4\\src\\main\\java\\com\\example\\domacivp4\\cetvrtak.txt"))) {
            jelaCetvrtak.removeAll(jelaCetvrtak);
            String line;
            while ((line = br.readLine()) != null) {
                jelaCetvrtak.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getJelaPetak(){
        try (BufferedReader br = new BufferedReader(new FileReader("E:\\DOCS\\Fakultet\\RAF\\VP\\DomaciVP4\\src\\main\\java\\com\\example\\domacivp4\\petak.txt"))) {
            jelaPetak.removeAll(jelaPetak);
            String line;
            while ((line = br.readLine()) != null) {
                jelaPetak.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
