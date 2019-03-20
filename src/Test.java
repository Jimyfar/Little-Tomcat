import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;

public class Test {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8081);
        Socket client = server.accept();
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        char[] rawData = new char[1024];
        reader.read(rawData);

        System.out.println(client.getOutputStream());
        OutputStream out = client.getOutputStream();
        new BufferedWriter(new OutputStreamWriter(out)).write("");
        System.out.println(new String(rawData));


    }
}
