package client;

import server.MyServer2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class MyClient2 {
    public static void main(String[] args) throws IOException {
        Socket s = null;
        try {
            s = new Socket(MyServer2.HOST, MyServer2.SERVER_PORT);
            System.out.println("SOCKET IS CONNECTED");
            OutputStream os = s.getOutputStream();
            PrintStream ps = new PrintStream(os);
            InputStream is = s.getInputStream();
            Scanner sc = new Scanner(is);
            Scanner in = new Scanner(System.in);
            do {
                System.out.print("> ");
                String line = in.nextLine();
                ps.println(line);
                if(line.equalsIgnoreCase("exit"))break;
                String reply = sc.nextLine();
                System.out.println("REPLY> " + reply);
            }while (true);
            in.close();
            sc.close();
            is.close();
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
