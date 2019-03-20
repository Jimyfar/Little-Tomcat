import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ParserConfigurationException, SAXException {
        Server server = new Server(8888);
        server.start();
    }

    // 初始化 ServerSocket
    public Server(int port){
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("服务器初始化失败，端口号是" + port);
            e.printStackTrace();
        }
    }

    // 接收客户端信息 生成 request 实例
    public void start() throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ParserConfigurationException, SAXException {
        Socket client = null;
        try {
            client = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Request request = new Request(client.getInputStream());
        OutputStream out = client.getOutputStream();
        Response response = new Response(out);
        new Dispatcher(request, response).dispatch();

    }

}