package webserver;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        if (logger.isDebugEnabled()) {
            logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
        }

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, UTF_8));
            DataOutputStream dos = new DataOutputStream(out);

            var response = RequestExecutor.execute(getRequest(br));

            writeResponseToOutputStream(dos, response);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private Request getRequest(BufferedReader br) throws IOException {
        String readLine = br.readLine();

        var requestStartLine = new RequestStartLine(readLine);
        var requestHeader = getRequestHeader(br);
        var requestBody = getRequestBody(br, requestStartLine, requestHeader);

        return new Request(requestStartLine, requestHeader, requestBody);
    }

    private Map<String, String> getRequestBody(BufferedReader br, RequestStartLine requestStartLine, RequestHeader requestHeader) throws IOException {
        Map<String, String> requestBody = new HashMap<>();

        if (hasRequestBody(requestStartLine)) {
            String bodyContent = IOUtils.readData(br, requestHeader.getContentLength());
            requestBody = HttpRequestUtils.parseQueryString(URLDecoder.decode(bodyContent, UTF_8));
        }

        return requestBody;
    }

    private boolean hasRequestBody(RequestStartLine requestStartLine) {
        return requestStartLine.getMethod() == HttpMethod.POST;
    }

    private RequestHeader getRequestHeader(BufferedReader br) throws IOException {
        var requestHeader = new RequestHeader();
        String readLine;

        while (true) {
            readLine = br.readLine();

            if (Strings.isNullOrEmpty(readLine)) {
                break;
            }

            var splitLine = readLine.split(": ");
            requestHeader.putContent(splitLine[0], splitLine[1]);
        }

        return requestHeader;
    }

    private void writeResponseToOutputStream(DataOutputStream dos, Response response) throws IOException {
        writeReponseStatusLine(dos, response);

        writeResponseHeader(dos, response);

        writeResponseBody(dos, response);
    }

    private void writeReponseStatusLine(DataOutputStream dos, Response response) throws IOException {
        dos.writeBytes(response.getStatusLine().toString() + "\r\n");
    }

    private void writeResponseHeader(DataOutputStream dos, Response response) throws IOException {
        if (response.getHeader().isPresent()) {
            dos.writeBytes(response.getHeader().get().toString());
        }
    }

    private void writeResponseBody(DataOutputStream dos, Response response) throws IOException {
        if (response.getBody().isPresent() && response.getHeader().isPresent()) {
            dos.writeBytes("\r\n");
            dos.write(response.getBody().get(),
                    0,
                    Integer.parseInt(response.getHeader().get().getContents().get(ResponseHeaderOption.CONTENT_LENGTH)));
        }
    }
}
