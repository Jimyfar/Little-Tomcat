import java.io.IOException;

public class LoginServlet extends MyServlet{
    @Override
    public void doGet(Request request, Response response) throws IOException {
        response.write("<h1>login Servlet!</h1>");
    }
}
