
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class ServletMapping {
    private final long lastModifiedTime;
    private final String webxml;
    private HashMap<String, String> nameClazzMap = new HashMap<>();
    private HashMap<String, String> urlNameMap = new HashMap<>();

    private static ServletMapping instance;
    public static ServletMapping getInstance() {
        return instance;
    }
    static {
        try {
            instance = new ServletMapping();
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
            e.printStackTrace();
        }
    }

    private ServletMapping() throws IOException, ParserConfigurationException, org.xml.sax.SAXException {
        webxml = "D:\\project\\webserver\\src\\web.xml";
        lastModifiedTime = new File(webxml).lastModified();
        parse(webxml);
    }

    public String update(String url) throws IOException, SAXException, ParserConfigurationException {
        if (new File(webxml).lastModified() != lastModifiedTime) {
            parse(webxml);
        }

        String servletName = urlNameMap.get(url);
        String servlet = nameClazzMap.get(servletName);
        return servlet;
    }

    public void parse(String webxml) throws ParserConfigurationException, IOException, org.xml.sax.SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(webxml);

        Element root = doc.getDocumentElement();

        find(root);
    }

    public void find(Element root)  {
        NodeList children = root.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeName().equals("servlet-mapping")) {
                findMapping(child, "url-pattern", "servlet-name");
            } else if (child.getNodeName().equals("servlet")) {
                findMapping(child, "servlet-name", "servlet-class");
            }
        }
    }

    private void findMapping(Node servletMapping, String keyTag, String valueTag) {
        NodeList children = servletMapping.getChildNodes();
        String key = "";
        String value = "";
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeName().equals(keyTag)) {
                key = child.getTextContent();
            } else if (child.getNodeName().equals(valueTag)) {
                value = child.getTextContent();
            }
        }

        // 可能可以用工厂模式
        if (!key.equals("")) {
            if (servletMapping.getNodeName().equals("servlet-mapping")) {
                urlNameMap.put(key, value);

            } else if (servletMapping.getNodeName().equals("servlet")) {
                nameClazzMap.put(key, value);
            }
        }
    }


    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        ServletMapping servletMapping = ServletMapping.getInstance();
        System.out.println(servletMapping.update("/log"));
    }
}

