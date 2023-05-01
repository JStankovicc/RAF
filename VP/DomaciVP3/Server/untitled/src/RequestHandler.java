import HTTP.HttpMethod;
import HTTP.HttpRequest;
import HTTP.HttpResponse;
import HTTP.MakeRequest;

import java.io.*;

public class RequestHandler {

    public HttpResponse handleRequest(HttpRequest request) throws Exception {
        if (request.isMethod(HttpMethod.GET)) {
            return handleGet(request);
        } else if (request.isMethod(HttpMethod.POST)) {
            return handlePost(request);
        } else {
            return handleNotFound("Method nije pronadjen");
        }
    }

    protected HttpResponse handleNotFound(String message) throws Exception {

        HttpResponse response = new HttpResponse(404);
        response.startBody();
        response.append("<html>" +
                "<head><title>ERROR</title></head>" +
                "<body>" +
                message +
                "</h2></center></body></html>");
        return response;
    }

    protected HttpResponse handleGet(HttpRequest request) throws Exception {

        if (request.getPath().equals("/quotes") || request.getPath().equals("/")) {
            HttpResponse response = new HttpResponse(200);
            response.append("Content-Type: text/html\r\n");
            response.startBody();
            getQuotes(response);
            return response;
        }

        return handleNotFound("Putanja nije pronadjena");
    }

    private void getQuotes(HttpResponse response) throws IOException {
        String quoteOfTheDay;
        try {
            quoteOfTheDay = MakeRequest.makeGetRequestForJSON("localhost", 8888, "/qotd");
        } catch (Exception e) {
            quoteOfTheDay = "Citat dana nije pronadjen.";
        }
        response.append("<html><head><title>Quotes</title></head><body>");
        response.append("<form method=\"POST\" action=\"save-quote\" accept-charset=\"utf-8\">");
        response.append("<label>Author: </label><input name=\"author\" type=\"text\"><br><br>");
        response.append("<label>Quote: </label><input name=\"quote\" type=\"text\"><br><br>");
        response.append("<button>Submit</button>");
        response.append("</form>");
        response.append("<h1>Quote of the day:</h1><br>");
        response.append(quoteOfTheDay + "<br><br>");
        response.append("<h1>Saved Quotes:</h1><br>");
        for (Quote quote : Main.ListOfQuotes) {
            response.append(quote.getQuote() + "<br>");
        }
        response.append("<h1></h1><br>");
        response.append("<body></html>");
    }

    protected HttpResponse handlePost(HttpRequest request) throws Exception {
        if (request.getPath().equals("/save-quote")) {
            HttpResponse response = new HttpResponse(303);
            response.append("Location: http://localhost/quotes\r\n");
            response.startBody();
            postSaveQuote(request);
            return response;
        }
        return handleNotFound("Path not found");
    }

    private void postSaveQuote(HttpRequest request) throws IOException {
        Main.ListOfQuotes.add(new Quote(request.getParamValue("author"), request.getParamValue("quote")));
    }

}
