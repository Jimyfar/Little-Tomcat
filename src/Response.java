import java.io.*;
import java.util.Date;

public class Response {
    private StringBuilder statusLine = new StringBuilder();
    private StringBuilder responseHeaders = new StringBuilder();
    private StringBuilder responseBody = new StringBuilder();

    public static final String CRLF = "\r\n";
    public static final String BLANK = " ";
    public static final String HTTPVERSION = "HTTP/1.1";
    public static final String OKSTATUS = "OK";
    public static final String NOTFOUNDSTATUS = "Not Found";

    private BufferedWriter writer;

    public Response(OutputStream out) {
        writer = new BufferedWriter(new OutputStreamWriter(out));
        setStatusLine();
    }

    public void write(String content) throws IOException {

        setResponseBody(content);
        setResponseHeaders(content.getBytes().length);

        StringBuilder response = new StringBuilder();
        response.append(statusLine)
                .append(responseHeaders).append(CRLF)
                .append(responseBody);

        writer.write(response.toString());

        System.out.println("响应内容组合成功");
    }

    public void setStatusLine() {
        setStatusLine(200);
    }

    public void setStatusLine(int statusNum) {
        if (statusNum == 200) {
            statusLine = new StringBuilder(HTTPVERSION + BLANK + statusNum + BLANK + OKSTATUS + CRLF);
        } else if (statusNum == 404){
            statusLine = new StringBuilder(HTTPVERSION + BLANK + statusNum + BLANK + NOTFOUNDSTATUS + CRLF);
        }
    }

    private void setResponseHeaders(int contentLength) {
        responseHeaders.append("Date: ").append(new Date()).append(CRLF);
        responseHeaders.append("Server: ").append("Tomcat").append(CRLF);
        responseHeaders.append("Content-Length: ").append(contentLength).append(CRLF);
        responseHeaders.append("Content-Type: ").append("text/html").append(CRLF);
    }

    private void setResponseBody(String responseBody) {
        this.responseBody = new StringBuilder(responseBody).append(CRLF);
    }
}
