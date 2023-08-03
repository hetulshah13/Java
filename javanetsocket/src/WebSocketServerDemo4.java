

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class WebSocketServerDemo4 {
    public static final String IP_ADDRESS = "localhost";
    public static final String SOCKET_KEY_APPENDER = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    public static final int PORT = 25000;
    private static boolean firstCall = false, lineEndFlag = false;

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket();
        InetSocketAddress inetSocket = new InetSocketAddress(IP_ADDRESS, PORT);
        serverSocket.bind(inetSocket);
        msg("Server is open");
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        PrintStream printStream = new PrintStream(socket.getOutputStream());
        msg("Socket is connected and ready to receive text");
        if (handshake(scanner, printStream)) {
            msg("Handshake is good");
            do {
                StringBuffer sb = new StringBuffer();
                buildReceivedString(sb, inputStream);
                String value = sb.toString();
                if ("quit".equalsIgnoreCase(value)) break;
//                double val = Math.random() * 100;
//                String value = String.valueOf(val);
//                msg(value + "\n");
//                if (val < 1) break;
                sendBytes(value.getBytes(), printStream);
                Thread.sleep(50L);
            } while (scanner.hasNext());
        }
        scanner.close();
        printStream.close();
        msg("Communication lines are closed");
        socket.close();
        msg("Socket is closed");
        serverSocket.close();
        msg("Server is closed\n\n");
    }

    private static void sendBytes(byte[] nstr, PrintStream printStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(129);
        baos.write(nstr.length);
        baos.write(nstr);
        baos.flush();
        baos.close();
        printStream.write(baos.toByteArray(), 0, baos.size());
        printStream.flush();
    }

    protected static void buildReceivedString(StringBuffer sb, InputStream inputStream) throws IOException {
        String tmp;
        boolean hasGsonImpl = false;
        do {
            tmp = receive(inputStream);
            if (tmp == null)
                break;
            if (hasGsonImpl) {
                if (tmp.endsWith("\"}")) {
                    sb.append(tmp);
                    if (isJson(sb.toString()))
                        break;
                } else {
                    sb.append(tmp);
                }
            } else if (tmp.startsWith("{\"")) {
                sb.append(tmp);
                hasGsonImpl = true;
            } else {
                sb.append(tmp);
            }
        } while (tmp.length() >= 120);
    }

    private static boolean isJson(String str) {
        try {
            if (str != null && !str.isEmpty() && str.charAt(0) == '{' && str.charAt(str.length() - 1) == '}') {
                return true;
            }
            return false;
        } catch (Exception jsex) {
            jsex.printStackTrace(System.err);
            return false;
        }
    }
    private static byte[] readBytes(int numOfBytes, InputStream inputStream) throws IOException {
        byte[] b = new byte[numOfBytes];
        inputStream.read(b);
        return b;
    }

    private static String receive(InputStream inputStream) throws IOException {
        boolean skipBit = true;
        byte[] bb = null;
        if (!firstCall) {
            firstCall = true;
            bb = readBytes(1, inputStream);
            String firstBit = String.format("%02X ", new Object[] { Byte.valueOf(bb[0]) });
            skipBit = firstBit.trim().equals("81");
        }
        if (lineEndFlag)
            try {
                byte[] arrayOfByte = readBytes(1, inputStream);
            } catch (Exception ex) {
                System.out.println("Unknown chars found");
                ex.printStackTrace();
            }
        byte[] buf = null;
        if (skipBit) {
            buf = readBytes(1, inputStream);
        } else {
            buf = new byte[1];
            buf[0] = bb[0];
        }
        int payloadSize = getSizeOfPayload(buf[0]);
        lineEndFlag = true;
        if (payloadSize > 0) {
            buf = readBytes(4 + payloadSize, inputStream);
            buf = unMask(Arrays.copyOfRange(buf, 0, 4), Arrays.copyOfRange(buf, 4, buf.length));
            String message = new String(buf);
            return message;
        }
        return null;
    }

    private static int getSizeOfPayload(byte b) {
        return (b & 0xFF) - 128;
    }

    protected static byte[] unMask(byte[] mask, byte[] data) {
        for (int i = 0; i < data.length; i++)
            data[i] = (byte) (data[i] ^ mask[i % mask.length]);
        return data;
    }

    private static void msg(String str) {
        System.out.print("\n" + str);
    }

    private static boolean handshake(Scanner scanner, PrintStream printStream) throws Exception {
        HashMap<String, String> keys = new HashMap<>();
        String str;
        while (!(str = scanner.nextLine()).equals("")) {
            String[] s = str.split(": ");
            if (s.length == 2)
                keys.put(s[0], s[1]);
        }
        if (keys.size() > 0) {
            if (keys.get("Sec-WebSocket-Key") != null) {
                try {
                    String respKey = String.valueOf(keys.get("Sec-WebSocket-Key")) + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
                    String hash = DatatypeConverter.printBase64Binary(MessageDigest.getInstance("SHA-1").digest(respKey.getBytes()));
                    String str1 = "HTTP/1.1 101 Switching Protocols\r\nUpgrade: websocket\r\nConnection: Upgrade\r\nSec-WebSocket-Accept: " +

                            hash + "\r\n" + "\r\n";
                    printStream.print(str1);
                    printStream.flush();
                    msg("Connected with WebSocket");
                    return true;
                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                    msg("Connection failed due to bad algorithm");
                    return false;
                }
            }
            String str2 = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("res/index.html");
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            is.close();
            str2 = str2 + new String(bytes);
            printStream.println(str2);
            printStream.flush();
            msg("Connected without WebSocket");
        } else {
            msg("Connection failed as no header details found");
        }
        return false;
    }
}

