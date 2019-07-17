package so.asch.sdk.security;

import java.security.SecureRandom;

import io.github.novacrypto.bip39.MnemonicValidator;
import io.github.novacrypto.bip39.Words;
import io.github.novacrypto.bip39.wordlists.English;
import io.github.novacrypto.bip39.MnemonicGenerator;

public class Bip39 {

    public static String generateMnemonicCode() {
        StringBuilder sb = new StringBuilder();
        byte[] entropy = new byte[Words.TWELVE.byteLength()];
        new SecureRandom().nextBytes(entropy);
        new MnemonicGenerator(English.INSTANCE).createMnemonic(entropy, sb::append);
        return sb.toString();
    }

    public static boolean isValidMnemonicCode(String mnemonicCode){
        if (mnemonicCode == null || mnemonicCode.equals(""))
            return false;

        try{
            MnemonicValidator.ofWordList(English.INSTANCE).validate(mnemonicCode);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }
}
