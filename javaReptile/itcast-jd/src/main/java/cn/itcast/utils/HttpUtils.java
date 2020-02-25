package cn.itcast.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Component
public class HttpUtils {
    PoolingHttpClientConnectionManager cm;

    public HttpUtils() {
        this.cm = new PoolingHttpClientConnectionManager();
        this.cm.setMaxTotal(100);
        this.cm.setDefaultMaxPerRoute(10);
    }

    /**
     * 获取HttpClient
     * 获取HttpGet对象
     * httpGet设置参数
     * 执行请求得到返回结果
     * 判断是否有返回数据
     * 如果不等于空将内容转化为utf-8返回
     *
     * @param url
     * @return
     */
    public String doGetHtml(String url) {
        System.out.println(url);
        //获取httpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        //设置httpClient请求对象，设置url地址
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(this.getConfig());
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            System.out.println(response);
            if (response.getStatusLine().getStatusCode() == 200) {
                if (response.getEntity() != null) {
                    String content = EntityUtils.toString(response.getEntity(), "utf8");
                    return content;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
        return "";
    }

    public String doGetImage(String url) {
        //获取httpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        //设置httpClient请求对象，设置url地址
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(this.getConfig());
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            /**
             * 获取图片、
             * 获取图片后缀名
             * 创建图片名
             * 下载图片
             * 声明OutPutStream
             */
            if (response.getEntity() != null) {
                //获取图片
                //获取图片后缀
                String extName = url.substring(url.lastIndexOf("."));
                //创建图片名
                String picName = UUID.randomUUID().toString() + extName;
                //下载图片
                //声明OutputStream
                OutputStream outputStream = new FileOutputStream(new File("C:\\IdeaProject\\javaReptile\\" + picName));
                response.getEntity().writeTo(outputStream);
                return picName;
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
        return "";
    }

    /**
     * @return
     */
    public RequestConfig getConfig() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(1000)  //创建连接的最长时间
                .setConnectionRequestTimeout(500) //获取连接的最长时间
                .setSocketTimeout(10000)  //数据传输的最长时间
                .build();
        return requestConfig;
    }

}
