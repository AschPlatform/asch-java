package so.asch.sdk;

import so.asch.sdk.dbc.Argument;
import so.asch.sdk.dbc.ContractException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eagle on 17-7-14.
 */
public enum TransactionType {
    //named SEND in asch
    Transfer(0,"transfer"),
    Signature(1, "setSignature"),
    Delegate(2, "delegate"),
    Vote(3, "vote"),
    MultiSignature(4, "setMultiSignature"),
    Dapp(5, "dapp"),
    InTransfer(6,"inTransfer"),
    OutTransfer(7,"outTransfer"),
    Store(8, "store"),

    UIAIssuer(9, "UIA_ISSUER"),
    UIAAsset(10, "UIA_ASSET"),
    UIAFlags(11, "UIA_FLAGS"),
    UIA_ACL(12, "UIA_ACL"),
    UIAIssue(13, "UIA_ISSUE"),
    UIATransfer(14, "UIA_TRANSFER"),

    //new in V1.3
    Lock(100, "Lock");

    private int code;
    private String name;

    public int getCode(){
        return this.code;
    }
    public String getName(){
        return this.name;
    }

    private static final Map<Integer, TransactionType> allTransactionTypes = new HashMap<>();
    static{
        for( TransactionType type : TransactionType.values()){
            allTransactionTypes.put(type.getCode(), type);
        }
    }

    TransactionType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static TransactionType fromCode(int code) throws ContractException {
        Argument.require(allTransactionTypes.containsKey(code), String.format("invalid transaction type code '%d'", code));

        return allTransactionTypes.get(code);
    }

}
