package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MyServer3 {
    public static final String HOST = "2405:201:2016:e059:680b:8818:9fb8:9084";
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
            Scanner in = new Scanner(System.in);
            while (sc.hasNext()) {
                String msg = sc.nextLine();
                System.out.println(remoteAddr + "> " + msg);
                System.out.print("Reply-");
                String reply=in.nextLine();
                ps.println(reply);
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
