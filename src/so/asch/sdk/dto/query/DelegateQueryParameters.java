package so.asch.sdk.dto.query;

public class DelegateQueryParameters extends QueryParameters {
    public String getAddress() {
        return address;
    }

    public DelegateQueryParameters setAddress(String address) {
        this.address = address;
        return this;
    }

    private String address;
}
