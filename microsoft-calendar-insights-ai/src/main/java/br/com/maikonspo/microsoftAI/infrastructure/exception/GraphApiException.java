package br.com.maikonspo.microsoftAI.infrastructure.exception;

public class GraphApiException extends RuntimeException {
    private final int status;
    private final String responseBody;

    public GraphApiException(int status, String responseBody, String message) {
        super(message);
        this.status = status;
        this.responseBody = responseBody;
    }
    public GraphApiException(int status, String responseBody, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.responseBody = responseBody;
    }
    public int getStatus() { return status; }
    public String getResponseBody() { return responseBody; }
}
