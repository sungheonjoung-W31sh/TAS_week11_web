import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VulnWebServer {
    private static final Logger logger = LogManager.getLogger(VulnWebServer.class);

    static String PAGE = "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Search</title></head><body>"
        + "<h2>Search</h2>"
        + "<form method='POST' action='/search'>"
        + "<input type='text' name='query' size='60' placeholder='Enter search query' />"
        + "<button type='submit'>Search</button>"
        + "</form></body></html>";

    public static void main(String[] args) throws Exception {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new HttpHandler() {
            public void handle(HttpExchange ex) throws IOException {
                byte[] page = PAGE.getBytes("UTF-8");
                ex.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                ex.sendResponseHeaders(200, page.length);
                OutputStream os = ex.getResponseBody();
                os.write(page);
                os.close();
            }
        });

        server.createContext("/search", new HttpHandler() {
            public void handle(HttpExchange ex) throws IOException {
                BufferedReader br = new BufferedReader(new InputStreamReader(ex.getRequestBody(), "UTF-8"));
                StringBuilder body = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) body.append(line);
                br.close();

                String query = "";
                for (String param : body.toString().split("&")) {
                    if (param.startsWith("query=")) {
                        query = URLDecoder.decode(param.substring(6), "UTF-8");
                    }
                }

                System.out.println("[*] Search query: " + query);
                logger.error("Search query: {}", query);

                String resp = "<html><body><h3>Result for: " + query + "</h3></body></html>";
                byte[] respBytes = resp.getBytes("UTF-8");
                ex.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                ex.sendResponseHeaders(200, respBytes.length);
                OutputStream os = ex.getResponseBody();
                os.write(respBytes);
                os.close();
            }
        });

        server.setExecutor(null);
        server.start();
        System.out.println("[*] Vulnerable web server started on port 8080");
    }
}