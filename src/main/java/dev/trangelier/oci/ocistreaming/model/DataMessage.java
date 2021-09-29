package dev.trangelier.oci.ocistreaming.model;

public class DataMessage {
    private String id;
    private String requestId;
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DataMessage{" +
                "id=" + id +
                ", requestId=" + requestId +
                ", message='" + message + '\'' +
                '}';
    }
}
