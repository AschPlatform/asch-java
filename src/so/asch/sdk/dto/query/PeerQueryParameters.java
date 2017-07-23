package so.asch.sdk.dto.query;

import so.asch.sdk.PeerState;

/**
 * Created by eagle on 17-7-16.
 */
public class PeerQueryParameters extends QueryParameters {
    //state	integer	N	节点状态,0: ,1:,2:,3:
    //os	string	N	内核版本
    //version	string	N	asch版本号
    //limit	integer	N	限制结果集个数，最小值：0,最大值：100
    //orderBy	string	N
    //offset	integer	N	偏移量，最小值0
    //port	integer	N	端口，1~65535

    public Integer getState() {
        return state == null ? null : state.getCode();
    }

    public PeerQueryParameters setState(PeerState state) {
        this.state = state;
        return this;
    }

    public String getOS() {
        return os;
    }

    public PeerQueryParameters setOS(String os) {
        this.os = os;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public PeerQueryParameters setVersion(String version) {
        this.version = version;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public PeerQueryParameters setPort(Integer port) {
        this.port = port;
        return this;
    }

    private PeerState state = null;
    private String os = null;
    private String version = null;
    private Integer port = null;

}