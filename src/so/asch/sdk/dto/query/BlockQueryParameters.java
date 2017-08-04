package so.asch.sdk.dto.query;

/**
 * Created by eagle on 17-7-16.
 */
public class BlockQueryParameters extends QueryParameters {
    //limit	integer	N	限制结果集个数，最小值：0,最大值：100
    //orderBy	string	N	根据表中字段排序，如height:desc
    //offset	integer	N	偏移量，最小值0
    //generatorPublicKey	string	N	区块生成者公钥
    //totalAmount	integer	N	交易总额，最小值：0，最大值：10000000000000000
    //totalFee	integer	N	手续费总额，最小值：0，最大值：10000000000000000
    //reward	integer	N	奖励金额，最小值：0
    //previousBlock	string	N	上一个区块
    //height	integer	N	区块高度

    public String getGeneratorPublicKey() {
        return generatorPublicKey;
    }

    public BlockQueryParameters setGeneratorPublicKey(String generatorPublicKey) {
        this.generatorPublicKey = generatorPublicKey;
        return this;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public BlockQueryParameters setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public BlockQueryParameters setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
        return this;
    }

    public Integer getReward() {
        return reward;
    }

    public BlockQueryParameters setReward(Integer reward) {
        this.reward = reward;
        return this;
    }

    public String getPreviousBlock() {
        return previousBlock;
    }

    public BlockQueryParameters setPreviousBlock(String previousBlock) {
        this.previousBlock = previousBlock;
        return this;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public BlockQueryParameters setHeight(Integer height) {
        this.height = height;
        return this;
    }

    private String generatorPublicKey = null;
    private Integer totalAmount = null;
    private Integer totalFee = null;
    private Integer reward = null;
    private String previousBlock = null;
    private long height = 0;
}