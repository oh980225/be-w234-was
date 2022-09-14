package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, UTF_8));
            DataOutputStream dos = new DataOutputStream(out);

            var request = new Request(new RequestStartLine(br.readLine()));
            logger.debug("Request path : {}", URLDecoder.decode(request.getUrl().getPath(), UTF_8));

            var response = RequestExecutor.execute(request);

            writeResponseToOutputStream(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeResponseToOutputStream(DataOutputStream dos, Response response) {
        try {
            dos.writeBytes(response.getProtocol().getName() + " "
                    + response.getStatusCode().getCode() + " "
                    + response.getStatusCode().getMessage() + "\r\n"
                    + "Content-Type: " + response.getContentType().getDetail() + "\r\n"
                    + "Content-Length: " + response.getContentLength() + "\r\n");
            dos.writeBytes("\r\n");
            dos.write(response.getBody(), 0, response.getContentLength());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
