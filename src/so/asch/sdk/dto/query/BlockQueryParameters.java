package so.asch.sdk.dto.query;

/**
 * Created by eagle on 17-7-16.
 */
public class BlockQueryParameters extends QueryParameters {
    //limit	integer	N	限制结果集个数，最小值：0,最大值：100
    //orderBy	string	N	根据表中字段排序，如height:desc
    //offset	integer	N	偏移量，最小值0
    //transactions	boolean	N	是否包含交易信息


    public Boolean getTransactions() {
        return transactions;
    }

    public BlockQueryParameters setTransactions(Boolean transactions) {
        this.transactions = transactions;
        return this;
    }

    private Boolean transactions = null;
}