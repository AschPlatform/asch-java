package so.asch.sdk;

public enum ContentEncoding {
    Raw("raw"),
    Hex("hex"),
    Base64("base64");

    ContentEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getEncoding() {
        return encoding;
    }

    private String encoding;
}
