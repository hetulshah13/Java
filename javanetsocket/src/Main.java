import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;

public class Main {

    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket();
        SocketAddress saddr = new InetSocketAddress(0);
        ss.bind(saddr);
        msg("Server port: " + ss.getLocalPort());
        msg("Server is active: " + ss.isBound());
        InetAddress inet = ss.getInetAddress();
        msg("Inet: " + inet);
        ss.setSoTimeout(10000);
        ss.setReceiveBufferSize(102400);
        msg("Default buffer size: " + ss.getReceiveBufferSize());
        System.out.println("Server is waiting for new client");
        ss.accept();

        ss.close();
        System.out.println("Server is closed");
    }

    private static void msg(String s) {
        System.out.println(s);
    }
}
