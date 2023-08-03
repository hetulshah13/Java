package MultiClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread{
    public static Socket s= null;
    ServerThread(Socket ss1){
        s=ss1;
    }
    @Override
    public void run() {

        InetSocketAddress remote = (InetSocketAddress) s.getRemoteSocketAddress();
        String remoteAddr = remote.getHostName() + ":" + remote.getPort();
        System.out.println(remoteAddr + " IS CONNECTED ON " + s.getLocalPort());
        InputStream is = null;
        try {
            is = s.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(is);
        OutputStream os = null;
        try {
            os = s.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintStream ps = new PrintStream(os);
        while (sc.hasNext()) {
            String msg = sc.nextLine();
            System.out.println(remoteAddr + "> " + msg);
            ps.println(msg.toUpperCase());
        }
        ps.close();
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sc.close();
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
