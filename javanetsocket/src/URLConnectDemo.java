import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class URLConnectDemo {
    public static void saveStream(String mURL, String ofile) throws Exception {

        URL url = new URL(mURL);
        URLConnection myconn = url.openConnection();
        myconn.setRequestProperty("User-Agent","User-Agent: Mozilla/5.0 (Windows NT 6.1; rv:7.0.1) Gecko/20100101 Firefox/7.0.1");
        BufferedImage image = ImageIO.read(url);
        ImageIO.write(image, "jpg", new File(ofile));
    }

    public static void main(String[] args) throws Exception {
        saveStream("http://images.anandtech.com/doci/5434/X79%20Extreme9Box_575px.jpg",
                "C:\\Users\\Hetul Shah\\Desktop\\saved.jpg");
    }
    public static void main1(String[] args) throws IOException {
        String uri = "http://docs.oracle.com:8969/javase/7/docs/api/java/net/URLConnection.html?subject=method_summary";
        URL url = new URL(uri);
        System.out.println(" > " +url.getPort());
        System.out.println(" > " +url.getProtocol());
        System.out.println(" > " +url.getHost());
        System.out.println(" > " +url.getQuery());
        System.out.println(" > " +url.getPath());
        System.out.println(" > " +url.getAuthority());
        System.out.println(" > " +url.getFile());
        System.out.println(" > " +url.getUserInfo());
        System.out.println(" > " +url.getDefaultPort());
        System.out.println("---------------------------------------------------------");
        uri = "https://docs.oracle.com/javase/7/docs/api/java/net/URLConnection.html#method_summary";
        uri = "https://images.unsplash.com/photo-1579353977828-2a4eab540b9a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8c2FtcGxlfGVufDB8fDB8fA%3D%3D&w=1000&q=80";
        url = new URL(uri);
        URLConnection conn = url.openConnection();
        conn.connect();
        InputStream is = conn.getInputStream();
        Scanner sc = new Scanner(is);
        File file = new File("C:\\Users\\Hetul Shah\\Desktop", "info.jpg");
        FileOutputStream fos = new FileOutputStream(file);
        PrintStream ps = new PrintStream(fos);
        /*while (sc.hasNext()) {
            String line = sc.nextLine();
            System.out.println(line);
            ps.println(line);
        }*/
        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        is.close();
        fos.write(bytes);
        ps.close();
        fos.close();
        sc.close();
        System.out.println("---------------------------------------------------------");
        System.out.println(" Content type > " + conn.getContentType());
        System.out.println(" Length > " + conn.getContentLength());
        for (Map.Entry<String, List<String>> entry : conn.getHeaderFields().entrySet()) {
            System.out.println("HEADER > " + entry.getKey() + " = " + entry.getValue());
        }
        System.out.println("---------------------------------------------------------");
    }
}
