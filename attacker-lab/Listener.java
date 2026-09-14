import java.io.*;
import java.net.*;

public class Listener {
    public static void main(String[] args) throws Exception {
        int port = 9999;
        ServerSocket server = new ServerSocket(port);
        System.out.println("[*] Listening on port " + port + " ...");
        while (true) {
            Socket s = server.accept();
            System.out.println("[+] Connection received from " + s.getInetAddress());
            s.close();
        }
    }
}