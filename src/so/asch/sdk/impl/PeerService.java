package so.asch.sdk.impl;

import com.alibaba.fastjson.JSONObject;
import so.asch.sdk.Peer;
import so.asch.sdk.dbc.Argument;
import so.asch.sdk.dto.query.PeerQueryParameters;

public class PeerService extends AschRESTService implements Peer {
    @Override
    public JSONObject queryPeers(PeerQueryParameters parameters){
        try {
            Argument.require(Validation.isValidPeerQueryParameters(parameters), "invalid parameters");

            return get(AschServiceUrls.Peer.QUERY_PEERS, jsonFromObject(parameters));
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject getVersion() {
        return get(AschServiceUrls.Peer.GET_VERSION);
    }

    @Override
    public JSONObject getPeer(String ip, int port) {
        try {
            Argument.require(Validation.isValidIP(ip), "invalid ip");
            Argument.require(Validation.isValidPort(port), "invalid port");

            JSONObject parameters = new JSONObject()
                    .fluentPut("ip", ip)
                    .fluentPut("port", port);

            return get(AschServiceUrls.Peer.GET_PEER, parameters);
        } catch (Exception ex) {
            return fail(ex);
        }
    }
}
