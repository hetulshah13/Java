package Search;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static Socket s = null;
    public Client() throws IOException {
        s = new Socket(Server.HOST, Server.SERVER_PORT);
        System.out.println("SOCKET IS CONNECTED");
    }
    public static List main(String s1) throws IOException {
        List<String> li;
        try {
            OutputStream os = s.getOutputStream();
            PrintStream ps = new PrintStream(os);
            InputStream is = s.getInputStream();
            Scanner sc = new Scanner(is);
            Scanner in = new Scanner(System.in);
                System.out.print("> ");
                //String line = in.nextLine();
                ps.println(s1);
                String reply = sc.nextLine();
                reply=reply.replace("[","");
                reply=reply.replace("]","");
                String a1[]=reply.split(",");
                 li= Arrays.asList(a1);
            //in.close();
            //sc.close();
            //is.close();
            //ps.close();
            //os.close();
                return li;
                //System.out.println("REPLY> " + reply);

        } catch (IOException e) {
            System.err.println("Exception occurred: " + e.getMessage());
        } finally {
            if (s != null) {
                //s.close();
                //System.out.println("SOCKET IS CLOSED");
            }
        }
        li=new ArrayList<>();
        return li;
    }
}
