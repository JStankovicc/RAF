package HTTP;

import HTTP.HttpMethod;
import HTTP.HttpPostParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class HttpRequest {


    private String method;
    private String path;
    private ArrayList<HttpPostParam> postParams;

    public HttpRequest(BufferedReader in) {
        try {
            //CITAMO PRVU LINIJU
            String line = in.readLine();

            //KREIRAMO STRING TOKENIZER ZA PRVU LINIJU ZAHTEVA
            StringTokenizer stringTokenizer = new StringTokenizer(line);

            //IZVLACIMO METODU I PUTANJU
            this.method = stringTokenizer.nextToken();
            this.path = stringTokenizer.nextToken();

            if (this.isMethod(HttpMethod.POST)) {
                this.handlePost(in);
            }

        } catch (IOException e) {
            System.out.println("Greska prilikom obrade zahteva");
        }
    }

    public String getParamValue(String paramName) {
        for (HttpPostParam postParam : this.postParams) {
            if (postParam.getName().equals(paramName)) {
                return postParam.getValue();
            }
        }
        return null;
    }
    private void handlePost(BufferedReader in) throws IOException {

        String line;

        this.postParams = new ArrayList<>();


        //PRESKACEMO SVE DO BODYja ZAHTEVA
        do {
            line = in.readLine();
        } while (!line.trim().equals(""));

        StringBuilder content = new StringBuilder();

        while (in.ready()) {
            content.append((char) in.read());
        }


        String urlDecodedContent = URLDecoder.decode(content.toString(), String.valueOf(StandardCharsets.UTF_8));

        String[] fields = urlDecodedContent.split("&");

        for (String field : fields) {
            String[] params = field.split("=");
            postParams.add(new HttpPostParam(params[0], params[1]));
        }

    }

    public boolean isMethod(HttpMethod method) {
        return this.method.equals(method.toString());
    }
    public String getPath() {
        return path;
    }



}
