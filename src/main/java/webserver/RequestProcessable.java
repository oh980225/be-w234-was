package webserver;

public interface RequestProcessable {
    Response process(Request request);
}
