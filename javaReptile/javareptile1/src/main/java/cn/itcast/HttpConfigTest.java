package cn.itcast;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpConfigTest {
    public static void main(String[] args) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.itcast.cn");
        //配置请求信息
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000) //创建连接的最长时间，单位是毫秒
                .setConnectionRequestTimeout(500)  //设置获取链接的最长时间，单位是毫秒
                .setSocketTimeout(10 * 1000)  //设置数据传输的最长时间，单位是毫秒
                .build();
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse httpResponse=null;
        try {
            httpResponse = client.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = httpResponse.getEntity();
                String content = EntityUtils.toString(entity, "utf-8");
                System.out.println(content.length());
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
        }
    }
}
