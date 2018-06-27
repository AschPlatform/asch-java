package so.asch.sdk.impl;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import so.asch.sdk.impl.Log.Logger;
import so.asch.sdk.impl.Log.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.util.Map;


/**
 * 简单的REST服务访问类
 * @author eagle
 */
public final class REST {

        private static Logger logger = LoggerFactory.getLogger(REST.class);

        protected static void addCustomeHeads(Map<String, String> heads, HttpEntityEnclosingRequest request) {
            if (null == heads ) return;

            for (Map.Entry<String, String> entry : heads.entrySet()){
                request.setHeader(entry.getKey(), entry.getValue());
            }
        }

        protected static StringEntity createEntity(String parameterString, String charset) throws UnsupportedEncodingException
        {
            StringEntity  entity = new StringEntity(parameterString, charset);
            entity.setContentType("application/json");

            return entity;
        }

        protected static HttpResponse rawPost(String url, ParameterMap parameters, Map<String,String> customeHeads, String charset ) throws IOException{
            String parameterString = parameters == null ? "" : parameters.toJSONString();
            return rawPost(url, parameterString, customeHeads, charset);
        }

        protected static HttpResponse rawPost(String url, String requestBody, Map<String,String> customeHeads, String charset ) throws IOException{
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost(encodeUrl(url));

            addCustomeHeads(customeHeads, post);
            StringEntity entity = createEntity(requestBody, charset);
            post.setEntity(entity);

            logger.debug(String.format("POST url:%s, body:%s", url, requestBody));
            return httpClient.execute(post);
        }

        protected static String getResponseContent(HttpResponse response) throws IOException{

            if (null == response)
                throw new IOException("Get response failed");

            String content = EntityUtils.toString(response.getEntity());

            if (logger.isDebugEnabled()) {
                logger.debug(String.format("RESPONSE code:%d, reason:%s, content:%s", response.getStatusLine().getStatusCode(),
                        response.getStatusLine().getReasonPhrase(),
                        content));
            }

            if (!content.contains("\"success\":")){
                throw new IOException(String.format("Http server response failed, code:%d, reason:%s.\n content:%s",
                        response.getStatusLine().getStatusCode(),
                        response.getStatusLine().getReasonPhrase(),
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
                HttpResponse response = rawPost(url, parameters, customeHeaders, charset);
                return getResponseContent(response);
            }
            catch (IOException ex){
                String errorInfo = String.format("Exception when post,url:%s,data:%s", url, parameters);
                logger.error(errorInfo, ex);
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

        public static HttpResponse rawGet(String url, String queryString) throws IOException{
            CloseableHttpClient httpClient = HttpClients.createDefault();

            String fullUrl = queryString == null ? url : url + "?" + queryString;

            logger.debug("GET url:" + fullUrl);
            HttpGet get = new HttpGet(encodeUrl(fullUrl));

            return httpClient.execute(get);
        }

        public static HttpResponse rawGet(String url, ParameterMap parameters) throws IOException{
            return rawGet(url, parameters == null ? null : parameters.toQueryString());
        }

        public static String get(String url, ParameterMap parameters) throws IOException {
            return getResponseContent(rawGet(url, parameters));
        }

}
