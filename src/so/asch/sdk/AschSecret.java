package so.asch.sdk;

public interface AschSecret {

    enum SecondSecretState{
        Required,
        Needless,
        Unknow
    }

    String getPublicKey();
    String getSecondPublicKey();
    boolean isSecretPresented();
    boolean isSecondSecretPresented();
    SecondSecretState getSecondSecretState();
    byte[] sign(byte[] content);
    byte[] secondSign(byte[] content);
}
