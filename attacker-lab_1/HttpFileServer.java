import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;

public class HttpFileServer {
    public static void main(String[] args) throws Exception {
        int port = 8888;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new HttpHandler() {
            public void handle(HttpExchange ex) throws IOException {
                String path = ex.getRequestURI().getPath().substring(1);
                System.out.println("[+] HTTP request for: " + path);
                File f = new File(path);
                if (f.exists() && !f.isDirectory()) {
                    byte[] data = Files.readAllBytes(f.toPath());
                    ex.sendResponseHeaders(200, data.length);
                    OutputStream os = ex.getResponseBody();
                    os.write(data);
                    os.close();
                } else {
                    ex.sendResponseHeaders(404, -1);
                }
            }
        });
        server.setExecutor(null);
        server.start();
        System.out.println("[*] HTTP server started on port " + port);
    }
}