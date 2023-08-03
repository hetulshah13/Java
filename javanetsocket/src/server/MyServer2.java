package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MyServer2 {
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
            OutputStream os = s.getOutputStream();
            PrintStream ps = new PrintStream(os);
            while (sc.hasNext()) {
                String msg = sc.nextLine();
                System.out.println(remoteAddr + "> " + msg);
                ps.println(msg.toUpperCase());
            }
            ps.close();
            os.close();
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
