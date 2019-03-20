import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class Request {
    private String host;
    private String method;
    private String url;
    private String path;
    private String cookie;
    private String Content_length;
    private String status;
    private HashMap<String, String> headers = new HashMap<>();
    private String body;
    private HashMap<String, String> parameters = new HashMap<>();


    public static void main(String[] args) throws IOException {
        File f = new File("D:\\project\\webserver\\post.txt");

    }

    public Request(InputStream requestData) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(requestData));
        char[] rawData = new char[1024];
        reader.read(rawData);


        String requestMessage = new String(rawData);
        parse(requestMessage);
        System.out.println("解析 request 成功");
    }

    private void parse(String requestMessage) {
        String[] messageLines = requestMessage.split("\\n");
        if (messageLines.length >= 2) {
            setStatus(messageLines[0]);
            setHeaders(messageLines);
        } else {
            System.out.println("不符合 http 协议规范");
        }
    }

    private void setHeaders(String[] messageLines) {
        for (int i = 1; i < messageLines.length; i++) {

        }
    }

    public void setStatus(String statusLine) {
        String[] status = statusLine.split("\\s");
        if (status.length == 3) {
            setMethod(status[0]);
            setPath(status[1]);
        } else {
            System.out.println("状态行格式错误");
        }

        this.status = statusLine;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        if (!method.equals("GET") && !method.equals("POST")) {
            System.out.println(method);
            System.out.println("http 方法不存在");
        }
        this.method = method;
    }

    // path /examples?name=zjt&value=123
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        String[] path_param = path.split("\\?");
        if (path_param.length == 2) {
            setParameters(path_param[1]);
        }
        this.path = path_param[0];
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    // 以键值对的方式添加头信息到 headers
    public void addHeader(String header) {
        String[] key_value = header.split("\\:");
        if (key_value.length >= 2) {
            String key = key_value[0].trim();

            String value = "";
            for (int i = 1; i < key_value.length; i++) {
                value += key_value[i].trim();
            }

            if (key != null && value != null) {
                headers.put(key, value);
            }
        } else {
            System.out.println("头信息错误");
        }
    }

    public String getParameter(String key) {
        return this.parameters.get(key);
    }

    // 将 url 参数 name=zjt&value=234  以键值对的形式添加到 parameters 中。
    public void setParameters(String parameters) {
        String[] paramList = parameters.split("\\&");
        for (String p : paramList) {
            String[] key_value = p.split("\\=");
            if (key_value != null && key_value.length == 2) {
                this.parameters.put(key_value[0].trim(), key_value[1]);
            } else {
                System.out.println("参数设置错误");
                return;
            }
        }
    }

    public String getUrl() {
        this.url = getHost() + this.path;
        return this.url;
    }

    public String getHost() {
        return this.headers.get("Host");
    }

    public String getCookie() {
        return this.headers.get("Cookie");
    }

    public String getContent_length() {
//        System.out.println(parameters.get("Content-Length"));
        return this.headers.get("Content-Length");
    }

    public void setContent_length(String content_length) {
        Content_length = content_length;
    }

    public String getStatus() {
        return status;
    }
}

