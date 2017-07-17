package so.asch.sdk.impl;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 简单的REST服务访问类
 * @author eagle.asch
 */
public class REST {

        private static Logger logger = LoggerFactory.getLogger(REST.class);
        private static final Map<String, String> magicHeaders = new HashMap<String, String>();

        static {
            magicHeaders.put("magic","594fe0f3");
            magicHeaders.put("version","");
        }

        protected static void addCustomeHeads(Map<String, String> heads, HttpEntityEnclosingRequest request) {
            if (null == heads ) return;

            for (Map.Entry<String, String> entry : heads.entrySet()){
                request.setHeader(entry.getKey(), entry.getValue());
            }
        }

        protected static StringEntity createEntity(JSONObject parameters, String charset) throws UnsupportedEncodingException
        {
            String parameterString = parameters == null ? "" : parameters.toString();
            StringEntity  entity = new StringEntity(parameterString, charset);
            entity.setContentEncoding(charset);
            entity.setContentType("application/json");

            return entity;
        }

        protected static HttpResponse rawPost(String url, JSONObject parameters, Map<String,String> customeHeads, String charset ) throws IOException{
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);

            addCustomeHeads(customeHeads, post);
            StringEntity entity = createEntity(parameters, charset);
            post.setEntity(entity);

            return httpClient.execute(post);
        }

        protected static JSONObject getJSONObject(HttpResponse response) throws IOException{

            if (null == response)
                throw new IOException("Get response failed");
            if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode())
                throw new IOException(String.format("Http server response failed, %s", response.getStatusLine().toString()));

            String json = EntityUtils.toString(response.getEntity());
            return JSONObject.parseObject(json);
        }

        public static JSONObject post(String url, JSONObject parameters, boolean withMagic, String charset) throws IOException{
            try {
                Map<String, String> headers = withMagic ? magicHeaders : null;
                HttpResponse response = rawPost(url, parameters, headers, null);
                return getJSONObject(response);
            }
            catch (IOException ex){
                String errorInfo = String.format("Exception when post,url:s%,data:%s", url, parameters);
                logger.error(errorInfo, ex);
                throw ex;
            }
        }

        public static JSONObject post(String url, JSONObject parameters ) throws IOException {
            return post(url, parameters, false, null);
        }

        public static JSONObject postWithMagic(String url, JSONObject parameters ) throws IOException {
            return post(url, parameters, true, null);
        }



        public static HttpResponse rawGet(String url, JSONObject parameters) throws IOException{
            CloseableHttpClient httpClient = HttpClients.createDefault();

            String query = "";
            if (null != parameters) {
                int i;
                List<String> parameterList = new ArrayList<>();
                parameters.forEach((key, value) -> parameterList.add(key +"="+ (value == null ? "" : value.toString())));
                query = "?"+ String.join("&", parameterList);
            }

            HttpGet get = new HttpGet(url + query);
            return httpClient.execute(get);
        }

        public static JSONObject get(String url, JSONObject parameters) throws IOException {
            return getJSONObject(rawGet(url, parameters));
        }

}
