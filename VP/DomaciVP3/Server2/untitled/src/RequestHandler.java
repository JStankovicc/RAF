import HTTP.HttpMethod;
import HTTP.HttpRequest;
import HTTP.HttpResponse;

public class RequestHandler {

    private Quote qotd;

    public HttpResponse handleRequest(HttpRequest request) throws Exception {
        if (request.isMethod(HttpMethod.GET)) {
            return handleGet(request);
        } else if (request.isMethod(HttpMethod.POST)) {
            return handlePost(request);
        } else {
            return handleNotFound("Method nije pronadjen");
        }
    }
    protected HttpResponse handleGet(HttpRequest request) throws Exception {
        if (request.getPath().equals("/qotd")) {
            HttpResponse response = new HttpResponse(200);
            response.append("Content-Type: application/json\r\n");
            response.startBody();
            if (qotd == null) {
                qotd = Main.qotd.getQOTD();
            }
            response.append(qotd.getQuote());
            return response;
        }
        return handleNotFound("Path not found");
    }

    protected HttpResponse handlePost(HttpRequest request) throws Exception {
        //DODAVANJE QOTD
        return handleNotFound("Metod nije implementiran");
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

}
