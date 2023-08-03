package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MyServer1 {
    public static final String HOST = "127.0.0.1";
    public static final int SERVER_PORT = 7777;

    public static void main(String[] args) throws IOException {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(SERVER_PORT);
            System.out.println("SERVER IS ACTIVE ON " + ss.getLocalPort());
            Socket s = ss.accept();
            InetSocketAddress remote = (InetSocketAddress) s.getRemoteSocketAddress();
            String remoteAddr = remote.getHostName() + ":" + remote.getPort();
            System.out.println(remoteAddr + " IS CONNECTED ON " + s.getLocalPort());
            InputStream is = s.getInputStream();
            Scanner sc = new Scanner(is);
            while (sc.hasNext()) {
                String msg = sc.nextLine();
                System.out.println(remoteAddr + "> " + msg);
            }
            sc.close();
            is.close();
            s.close();
            System.out.println("SOCKET CLOSED");
        } catch (IOException e) {
            System.err.println("Exception occurred: " + e.getMessage());
        } finally {
            if (ss != null) {
                ss.close();
                System.out.println("SERVER CLOSED");
            }
        }
    }
}
