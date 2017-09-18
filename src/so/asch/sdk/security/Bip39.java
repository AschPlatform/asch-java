package so.asch.sdk.security;


import java.lang.SecurityException;
import java.util.Arrays;
import java.util.List;

public class Bip39 {

    public static String generateMnemonicCode(byte[] entropy) throws SecurityException{
        try {
            String[] words = new String[0];//MnemonicCode.INSTANCE.toMnemonic(entropy).toArray(new String[0]);

            return String.join(" ", words);
        }
        catch (Exception ex){
            throw new SecurityException("generate mnemonic code failed", ex);
        }
    }

    public static boolean isValidMnemonicCode(String mnemonicCode){

        if (mnemonicCode == null || mnemonicCode.equals(""))
            return false;

        List<String> words = Arrays.asList(mnemonicCode.split(" "));
        try{
            //MnemonicCode.INSTANCE.check(words);
            return false;
        }
        catch (Exception ex){
            return false;
        }
    }
}
