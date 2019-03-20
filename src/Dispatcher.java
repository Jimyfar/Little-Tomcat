import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Dispatcher {
    private Request request;
    private Response response;
    private String servletName;

    public Dispatcher(Request request, Response response) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IOException, IllegalAccessException, ParserConfigurationException, SAXException {
        this.request = request;
        this.response = response;
        servletName = ServletMapping.getInstance().update(request.getPath());
        System.out.println("获取到 Servlet： " + servletName);

    }

    // 派发 request 和 response 给对应的 Servlet
    public void dispatch() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        if (servletName == null) {
            response.setStatusLine(404);
            response.write("<div> 404 <div>");
            return;
        }

        Constructor c = Class.forName(servletName).getConstructor();
        MyServlet servlet = (MyServlet) c.newInstance();
        System.out.println("开始执行 service");
        servlet.service(request, response);
    }


}
