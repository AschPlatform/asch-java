package so.asch.sdk.impl;

import so.asch.sdk.AschResult;
import so.asch.sdk.Peer;
import so.asch.sdk.dbc.Argument;
import so.asch.sdk.dto.query.PeerQueryParameters;

public class PeerService extends AschRESTService implements Peer {
    @Override
    public AschResult queryPeers(PeerQueryParameters parameters){
        try {
            Argument.require(Validation.isValidPeerQueryParameters(parameters), "invalid parameters");

            return get(AschServiceUrls.Peer.QUERY_PEERS, parametersFromObject(parameters));
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public AschResult getVersion() {
        return get(AschServiceUrls.Peer.GET_VERSION);
    }

    @Override
    public AschResult getPeer(String ip, int port) {
        try {
            Argument.require(Validation.isValidIP(ip), "invalid ip");
            Argument.require(Validation.isValidPort(port), "invalid port");

            ParameterMap parameters = new ParameterMap()
                    .put("ip", ip)
                    .put("port", port);

            return get(AschServiceUrls.Peer.GET_PEER, parameters);
        } catch (Exception ex) {
            return fail(ex);
        }
    }
}
