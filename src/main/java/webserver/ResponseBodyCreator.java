package webserver;

public interface ResponseBodyCreator {
    Response create(Request request);
}
