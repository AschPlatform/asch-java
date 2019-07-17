package so.asch.sdk;

import so.asch.sdk.dbc.Argument;
import so.asch.sdk.dbc.ContractException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eagle on 17-7-14.
 */
public enum TransactionType {
    unknow (-1, "unknow"),

    basic_transfer(1, "basic.transfer"),
    basic_setName(2, "basic.setName"),
    basic_setPassword(3, "basic.setPassword"),
    basic_lock(4, "basic.lock"),
    basic_unlock(5, "basic.unlock"),
    basic_registerGroup(6,"basic.registerGroup"),
    basic_registerAgent(7,"basic.registerAgent"),
    basic_setAgent(8, "basic.setAgent"),
    basic_cancelAgent(9, "basic.cancelAgent"),
    basic_registerDelegate(10, "basic.registerDelegate"),
    basic_vote(11, "basic.vote"),
    basic_unvote(12, "basic.unvote"),

    uia_registerIssuer(100, "uia.registerIssuer"),
    uia_registerAsset(101, "uia.registerAsset"),
    uia_issue(102, "uia.issue"),
    uia_transfer(103, "uia.transfer"),

    chain_register(200, "chain.register"),
    chain_replaceDelegate(201, "chain.replaceDelegate"),
    chain_addDelegate(202,"chain.addDelegate"),
    chain_removeDelegate(203,"chain.removeDelegate"),
    chain_deposit(204, "chain.deposit"),
    chain_withdrawal(205, "chain.withdrawal"),

    proposal_propose(300, "proposal.propose"),
    proposal_vote(301, "proposal.vote"),
    proposal_activate(302,"proposal.activate"),

    gateway_openAccount(400, "gateway.openAccount"),
    gateway_registerMember(401, "gateway.registerMember"),
    gateway_deposit(402,"gateway.deposit"),
    gateway_withdrawal(403,"gateway.withdrawal"),
    gateway_submitWithdrawalTransaction(404, "gateway.submitWithdrawalTransaction"),
    gateway_submitWithdrawalSignature(405, "gateway.submitWithdrawalSignature"),
    gateway_submitOutTransactionId(406, "gateway.submitOutTransactionId"),

    group_vote(500, "group.vote"),
    group_activate(501, "group.activate"),
    group_addMember(502,"group.addMember"),
    group_removeMember(503,"group.removeMember"),
    group_replaceMember(504, "group.replaceMember"),

    contract_register(600, "contract.register"),
    contract_call(601, "contract.call"),
    contract_pay(602, "contract.pay");

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
