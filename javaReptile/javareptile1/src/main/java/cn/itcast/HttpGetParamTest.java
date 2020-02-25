package cn.itcast;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpGetParamTest {
    public static void main(String[] args) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder("http://yun.itheima.com/search");
        builder.setParameter("keys", "java");
        HttpGet httpGet = new HttpGet(builder.build());
        CloseableHttpResponse httpResponse;
        try {
            httpResponse = client.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = httpResponse.getEntity();
                String content = EntityUtils.toString(entity, "utf-8");
                System.out.println(content);
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
        }
    }
}
