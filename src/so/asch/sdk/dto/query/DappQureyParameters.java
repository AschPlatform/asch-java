package so.asch.sdk.dto.query;

/**
 * @author fisher
 * @version $Id: DappQureyParameters.java, v 0.1 2017/12/4 11:22 fisher Exp $
 */
public class DappQureyParameters extends QueryParameters {
    //limit	integer	N	限制结果集个数，最小值：0,最大值：100
    //orderBy	string	N	根据表中字段排序，如height:desc
    //offset	integer	N	偏移量，最小值0
    //generatorPublicKey	string	N	区块生成者公钥
    //totalAmount	integer	N	交易总额，最小值：0，最大值：10000000000000000
    //totalFee	integer	N	手续费总额，最小值：0，最大值：10000000000000000
    //previousBlock	string	N	上一个区块
    //height	integer	N	区块高度

    public String getGeneratorPublicKey() {
        return generatorPublicKey;
    }

    public DappQureyParameters setGeneratorPublicKey(String generatorPublicKey) {
        this.generatorPublicKey = generatorPublicKey;
        return this;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public DappQureyParameters setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public DappQureyParameters setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
        return this;
    }


    public String getPreviousBlock() {
        return previousBlock;
    }

    public DappQureyParameters setPreviousBlock(String previousBlock) {
        this.previousBlock = previousBlock;
        return this;
    }

    public Long getHeight() {
        return height;
    }

    public DappQureyParameters setHeight(Long height) {
        this.height = height;
        return this;
    }

    private String generatorPublicKey = null;
    private Integer totalAmount = null;
    private Integer totalFee = null;
    private String previousBlock = null;
    private Long height = null;
}
