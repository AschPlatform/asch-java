package so.asch.sdk;

public enum  PeerState {
    Ban(0),
    Normal(1),
    Reset(2),
    Unknow(3);

    PeerState(int code) {
        this.code = code;
    }

    private int code;

    public int getCode() {
        return this.code;
    }
}
