package HTTP;

public class HttpResponse {

    private StringBuilder response;

    public HttpResponse(int status) throws Exception {
        this.response = new StringBuilder();

        response.append("HTTP/1.1 " + status + " " + getStatusText(status));
        response.append("\r\n");
    }
    private String getStatusText(int status) throws Exception {
        switch (status) {
            case 200:
                return "OK";
            case 303:
                return "See Other";
            case 404:
                return "Not Found";
        }
        throw new Exception("Status text not found");
    }

    @Override
    public String toString() {
        return response.toString();
    }

    public void append(String s) {
        response.append(s);
    }

    public void startBody() {
        response.append("\r\n");
    }


}
