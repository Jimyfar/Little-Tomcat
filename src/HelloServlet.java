import java.io.IOException;

public class HelloServlet extends MyServlet{
    @Override
    public void doGet(Request request, Response response) throws IOException {
        response.write("<h1>Hello Servlet!</h1>");
    }
}
