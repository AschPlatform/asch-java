package so.asch.sdk.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * OkHttp Rest API
 * @author kimziv
 *
 */
public final class RESTOkHttp {
    // private static Logger logger = LoggerFactory.getLogger(REST.class);
	private static OkHttpClient client;
	
	protected static OkHttpClient getHttpClient(){
	if(client==null)
	{
	    client = new OkHttpClient.Builder()
	        .build();
	}
	    return client;
	}

    protected static void addCustomeHeads(Map<String, String> heads, Request.Builder builder) {
        if (null == heads ) return;
        
        for (Map.Entry<String, String> entry : heads.entrySet()){
           	builder.addHeader(entry.getKey(), entry.getValue());
        }
    }

    protected static RequestBody createReqestBody(String parameterString, String charset) throws UnsupportedEncodingException
    {
    	
       	MediaType contentType = MediaType.parse("application/json; charset=utf-8");
       	RequestBody body=RequestBody.create(contentType, parameterString);
        return body;
    }

    protected static Response rawPost(String url, ParameterMap parameters, Map<String,String> customeHeads, String charset ) throws IOException{
        String parameterString = parameters == null ? "" : parameters.toJSONString();
        return rawPost(url, parameterString, customeHeads, charset);
    }

    protected static Response rawPost(String url, String requestBody, Map<String,String> customeHeads, String charset ) throws IOException{
        OkHttpClient httpClient = getHttpClient();
        RequestBody body =createReqestBody(requestBody, charset);
        Request.Builder builder=new Request.Builder();
        addCustomeHeads(customeHeads, builder);
        Request request = builder.url(url)
		.post(body)
		.build();		
        
        Response response = httpClient.newCall(request).execute();
        return response;
    }

    protected static String getResponseContent(Response response) throws IOException{

        if (null == response)
            throw new IOException("Get response failed");

        String content = response.body().string(); //EntityUtils.toString(response.getEntity());

//        if (logger.isDebugEnabled()) {
//            logger.debug(String.format("RESPONSE code:%d, reason:%s, content:%s", response.getStatusLine().getStatusCode(),
//                    response.getStatusLine().getReasonPhrase(),
//                    content));
//        }
      
         System.out.println("++++++content:"+content);

        if (!content.contains("\"success\":")){
            throw new IOException(String.format("Http server response failed, code:%d, reason:%s.\n content:%s",
                    response.code(),
                    content));
        }

        return content;
    }

    protected static String encodeUrl(String url)throws IOException{
        try{
            URL uri = new URL(url);

            return new URI(uri.getProtocol(), uri.getUserInfo(), uri.getHost(), uri.getPort(),
                    uri.getPath(), uri.getQuery(), null)
                    .toString();
        }
        catch(Exception ex){
            throw new IOException("invalid url: "+ url, ex);
        }
    }

    public static String post(String url, String parameters,  Map<String, String> customeHeaders, String charset) throws IOException{
        try {
            Response response = rawPost(url, parameters, customeHeaders, charset);
            return getResponseContent(response);
        }
        catch (IOException ex){
            String errorInfo = String.format("Exception when post,url:%s,data:%s", url, parameters);
           // logger.error(errorInfo, ex);
            //ex.printStackTrace();
            throw ex;
        }
    }

    public static String post(String url, ParameterMap parameters, Map<String,String> customeHeads, String charset) throws IOException {
        String parametersString = parameters == null ? "" : parameters.toJSONString();
        return post(url, parametersString, customeHeads, charset);
    }

    public static String post(String url, String parameters, Map<String,String> customeHeads ) throws IOException {
        return post(url, parameters, customeHeads, null);
    }

    public static String post(String url, ParameterMap parameters, Map<String,String> customeHeads ) throws IOException {
        return post(url, parameters, customeHeads, null);
    }

    public static String post(String url, String parameters ) throws IOException {
        return post(url, parameters, null, null);
    }

    public static String post(String url, ParameterMap parameters ) throws IOException {
        String parametersString = parameters == null ? "" : parameters.toJSONString();
        return post(url, parametersString, null, null);
    }

    public static Response rawGet(String url, String queryString) throws IOException{
        OkHttpClient httpClient = getHttpClient();
        String fullUrl = queryString == null ? url : url + "?" + queryString;
        Request.Builder builder=new Request.Builder();
        Request request = builder.url(encodeUrl(fullUrl))
		.get()
		.build();		

        Response response = httpClient.newCall(request).execute();
        return response;
    }

    public static Response rawGet(String url, ParameterMap parameters) throws IOException{
        return rawGet(url, parameters == null ? null : parameters.toQueryString());
    }

    public static String get(String url, ParameterMap parameters) throws IOException {
        return getResponseContent(rawGet(url, parameters));
    }
}
