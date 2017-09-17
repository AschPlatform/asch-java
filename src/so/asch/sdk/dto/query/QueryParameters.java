package so.asch.sdk.dto.query;

/**
 * Created by eagle on 17-7-16.
 */
public class QueryParameters{

    public enum SortOrder {
        ASC("asc"),
        DESC("desc");

        private String sortOrder;

        private SortOrder(String sortOrder){
            this.sortOrder = sortOrder;
        }

        public String getSortOrder() {
            return sortOrder;
        }
    }

    public Integer getLimit() {
        return limit;
    }

    public <T extends QueryParameters> T setLimit(Integer limit) {
        this.limit = limit;
        return (T)this;
    }

    public Integer getOffset() {
        return offset;
    }

    public <T extends QueryParameters> T setOffset(Integer offset) {
        this.offset = offset;
        return  (T) this;
    }

    public String getOrderBy() {
        return (null == orderByField || "" == orderByField) ? null :
                orderByField + ":" + order.getSortOrder();
    }

    public <T extends QueryParameters> T setOrderBy(String orderField, SortOrder order) {
        this.orderByField = orderField;
        this.order = order;
        return (T)this;
    }

    public <T extends QueryParameters> T orderByAscending(String fieldName){
        return this.setOrderBy(fieldName, SortOrder.ASC);
    }

    public  <T extends QueryParameters> T  orderByDescending(String fieldName){
        return this.setOrderBy(fieldName, SortOrder.DESC);
    }

    private Integer limit = null;
    private Integer offset = null;
    private String orderByField = null;
    private SortOrder order = SortOrder.DESC;
}