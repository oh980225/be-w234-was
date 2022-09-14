package webserver;

public interface ResponseBodyCreator {
    byte[] create(Request request);
}
