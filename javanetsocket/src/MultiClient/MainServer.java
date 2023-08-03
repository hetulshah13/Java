package MultiClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class MainServer {
    public static final String HOST = "127.0.0.1";
    public static final int SERVER_PORT = 7777;

    public static void main(String[] args) throws IOException {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(SERVER_PORT);
            System.out.println("SERVER IS ACTIVE ON " + ss.getLocalPort());
            //ServerThread[] st;
            //st = new ServerThread[50];
            //Arrays.fill(st,null);
            while(true){
                //for(int i=0;i<50;i++){
                    //if(st[i]==null){
                        Socket s=null;
                        s=ss.accept();
                        ServerThread st=new ServerThread(s);
                        st.start();
                        /*st[i]=new ServerThread(s);
                        st[i].start();
                        for(int j=0;j<50;j++)if(st[j]!=null)if(!(st[j].isAlive()))st[j]=null;*/
                    //}
                //}
            }
            // System.out.println("SOCKET CLOSED");
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
