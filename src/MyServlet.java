import java.io.IOException;

public abstract class MyServlet {
    public void doGet(Request request, Response response) throws IOException {

    }

    public void doPost(Request request, Response response){

    }

    public void service(Request request, Response response) throws IOException {
        doGet(request, response);
    }
}
