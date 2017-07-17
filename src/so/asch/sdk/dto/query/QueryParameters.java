package so.asch.sdk.dto.query;

/**
 * Created by eagle on 17-7-16.
 */
public class QueryParameters{
    public Integer getLimit() {
        return limit;
    }

    public QueryParameters setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Integer getOffset() {
        return offset;
    }

    public QueryParameters setOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public QueryParameters setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    private Integer limit = null;
    private Integer offset = null;
    private String orderBy = null;
}