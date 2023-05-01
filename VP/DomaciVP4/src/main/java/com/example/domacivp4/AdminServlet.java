package com.example.domacivp4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "adminServlet",value = "/admin")
public class AdminServlet extends HttpServlet {

    private Info info = Info.getInstance();
    private List<String> lista;
    private Map<String , Integer> mapa;
    public AdminServlet() {
        System.out.println("AdminServlet kreiran");
    }

    public void init(){}

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        String password = request.getParameter("password");

        if(checkPassword(password)){
            getInfo();

            out.println("<h1>Ponedeljak:</h1>");
            out.println("<ul>");
            lista = info.getPonede();
            mapa = info.getPonedeMap();
            for (String s : lista) {
                out.println("<li>" + s + " : " + mapa.get(s) + "</li>");
            }
            out.println("</ul>");

            out.println("<h1>Utorak:</h1>");
            out.println("<ul>");
            lista = info.getUtor();
            mapa = info.getUtorMap();
            for (String s : lista) {
                out.println("<li>" + s + " : " + mapa.get(s) + "</li>");
            }
            out.println("</ul>");


            out.println("<h1>Sreda:</h1>");
            out.println("<ul>");
            lista = info.getSred();
            mapa = info.getSredMap();
            for (String s : lista) {
                out.println("<li>" + s + " : " + mapa.get(s) + "</li>");
            }
            out.println("</ul>");


            out.println("<h1>Cetvrtak:</h1>");
            out.println("<ul>");
            lista = info.getCetv();
            mapa = info.getCetvMap();
            for (String s : lista) {
                out.println("<li>" + s + " : " + mapa.get(s) + "</li>");
            }
            out.println("</ul>");


            out.println("<h1>Petak:</h1>");
            out.println("<ul>");
            lista = info.getPeta();
            mapa = info.getpetaMap();
            for (String s : lista) {
                out.println("<li>" + s + " : " + mapa.get(s) + "</li>");
            }
            out.println("</ul>");

            out.println("<form action=\"/admin\" method=\"post\">\n" +
                    "  <button type=\"submit\">Admin</button>\n" +
                    "</form>");
        }

    }

    public boolean checkPassword(String password){
        try (BufferedReader br = new BufferedReader(new FileReader("E:\\DOCS\\Fakultet\\RAF\\VP\\DomaciVP4\\src\\main\\java\\com\\example\\domacivp4\\password.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.equals(password)) return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        info.printMapSize();
        info.setKey(info.getKey()+1);
        info.restartMap();

        info.printMapSize();
        response.sendRedirect("/chooseFood");
    }

    private void getInfo(){
        lista = info.getIzabranaJela();
        mapa = info.getIzabranaJelaMap();
    }
}
