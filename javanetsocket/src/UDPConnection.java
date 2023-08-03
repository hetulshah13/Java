import java.io.IOException;
import java.io.StringReader;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class UDPConnection {
    private static final int RECEIVER_PORT = 8991;
    private static final int MAX_SIZE = 24;
    private static final byte DEFAULT_BYTE = 0;

    public static void main(String[] args)  throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("S-Sender, R-Receiver: > ");
        char ch = sc.next().charAt(0);
        switch (ch) {
            case 'S': case 's' :
                senderApp();
                break;
            case 'R': case 'r' :
                receiverApp();
                break;
            default:
                System.err.println("Bad choice.");
        }
        sc.close();
    }

    private static void senderApp() throws IOException {
        DatagramSocket socket = new DatagramSocket();
        Scanner sc = new Scanner(System.in);
        try {
            do {
                System.out.print("> ");
                String line = sc.nextLine();
                int length = line.length();
                byte[] bytes = new byte[MAX_SIZE];
                StringReader reader = new StringReader(line);
                do {
                    Arrays.fill(bytes, DEFAULT_BYTE);
                    char[] chArray = new char[MAX_SIZE];
                    int ret = reader.read(chArray, 0, MAX_SIZE);
                    if (ret <= 0) break;
                    String msg = new String(chArray);
                    msg.getBytes(0, ret, bytes, 0);
                    DatagramPacket packet = new DatagramPacket(bytes, ret, InetAddress.getLocalHost(), RECEIVER_PORT);
                    socket.send(packet);
                } while (true);
                if (line.equalsIgnoreCase("quit")) break;
            }while (sc.hasNext());
        } finally {
            socket.disconnect();
            System.out.println("UDP Sender disconnected.");
            sc.close();
        }
    }
    private static void receiverApp() throws IOException {
        DatagramSocket socket = new DatagramSocket(RECEIVER_PORT, InetAddress.getLocalHost());
        try {
            byte[] bytes = new byte[MAX_SIZE];
            do {
                Arrays.fill(bytes, DEFAULT_BYTE);
                DatagramPacket packet = new DatagramPacket(bytes,0, MAX_SIZE);
                socket.receive(packet);
                String senderAddress = packet.getAddress().getHostName();
                int senderPort = packet.getPort();
                String line = new String(packet.getData(),0, packet.getLength());
                System.out.println(senderAddress + ":" + senderPort + ":\\> " + line);
                if (line.equalsIgnoreCase("quit")) break;
            } while (true);
        } finally {
            socket.disconnect();
            System.out.println("UDP Receiver disconnected.");
        }
    }
}
