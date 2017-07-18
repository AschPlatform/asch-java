package so.asch.sdk;

import so.asch.sdk.dbc.Argument;
import so.asch.sdk.dbc.ContractException;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by eagle on 17-7-14.
 */
public enum TransactionType {
    Transfer(0,"transfer"),
    Signature(1, "signature"),
    Delegate(2, "delegate"),
    Vote(3, "vote"),
    MultiSignature(4, "multiSignature"),
    Dapp(5, "dapp"),
    InTransfer(6,"inTransfer"),
    OutTransfer(7,"outTransfer"),
    Store(8, "store"),

    UIAIssuer(9, "UIA_ISSUER"),
    UIAAssert(10, "UIA_ASSET"),
    UIAFlags(11, "UIA_FLAGS"),
    UIA_ACL(12, "UIA_ACL"),
    UIAIssue(13, "UIA_ISSUE"),
    UIATransfer(14, "UIA_TRANSFER:");

    private int code;
    private String name;

    public int getCode(){
        return this.code;
    }
    public String getName(){
        return this.name;
    }

    private static final TransactionType[] allTransactionTypes = TransactionType.values().clone();
    static{
        Arrays.sort(allTransactionTypes, Comparator.comparing(TransactionType::getCode));
    }

    private TransactionType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static TransactionType FromCode(int code) throws ContractException {
        Argument.require(code >=Transfer.getCode() && code<= UIATransfer.getCode(),
                String.format("invalid code %d", code));

        return allTransactionTypes[code];
    }

}
