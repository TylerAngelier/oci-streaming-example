package dev.trangelier.oci.ocistreaming.model;

public class ConsumerInbox {
    private String consumerId;
    private String requestId;
    private String payload;

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "ConsumerInbox{" +
                "consumerId='" + consumerId + '\'' +
                ", requestId='" + requestId + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}
