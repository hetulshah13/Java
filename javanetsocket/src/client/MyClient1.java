package client;

import server.MyServer1;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class MyClient1 {
    public static void main(String[] args) throws IOException {
        Socket s = null;
        try {
            s = new Socket(MyServer1.HOST,MyServer1.SERVER_PORT);
            System.out.println("SOCKET IS CONNECTED");
            OutputStream os = s.getOutputStream();
            PrintStream ps = new PrintStream(os);
            ps.println("Hello");
            ps.close();
            os.close();
        } catch (IOException e) {
            System.err.println("Exception occurred: " + e.getMessage());
        } finally {
            if (s != null) {
                s.close();
                System.out.println("SOCKET IS CLOSED");
            }
        }
    }
}
