package cn.itcast;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientPoolTest {
    public static void main(String[] args) {
        //创建连接池管理器
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        //设置最大连接数
        cm.setMaxTotal(100);
        //设置每个主机的最大连接数
        cm.setDefaultMaxPerRoute(10);
        //使用连接池管理器发起请求
        doGet(cm);
        doGet(cm);
    }

    public static void doGet(PoolingHttpClientConnectionManager cm) {
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(cm).build();
        HttpGet httpGet = new HttpGet("http://www.itcast.cn");
        CloseableHttpResponse httpResponse=null;
        try {
            httpResponse = client.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode()==200){
                String content = EntityUtils.toString(httpResponse.getEntity(), "utf8");
                System.out.println(content);
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
        }
    }
}
