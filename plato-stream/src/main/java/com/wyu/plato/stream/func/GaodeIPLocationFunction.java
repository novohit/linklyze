package com.wyu.plato.stream.func;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wyu.plato.stream.domain.WideInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author novo
 * @since 2023-03-30
 */
@Slf4j
public class GaodeIPLocationFunction extends RichMapFunction<WideInfo, String> {

    private transient CloseableHttpClient httpClient;

    private static final String key = "bb6af43dc82198b1175e7281e7d2d694";

    private static final String URL_PATTERN = "https://restapi.amap.com/v3/ip?ip=%s&output=json&key=%s";

    @Override
    public void open(Configuration parameters) throws Exception {
        //httpClient = HttpClients.createDefault();
        httpClient = createHttpClient();
    }

    @Override
    public String map(WideInfo value) throws Exception {
        // TODO mock
        Random random = new Random();
        List<String> refererList = Arrays.asList("https://www.baidu.com", "https://www.douyin.com", "https://www.google.com", "https://mp.weixin.qq.com/", "");
        value.setReferer(refererList.get(random.nextInt(refererList.size())));
        List<String> ipList = Arrays.asList("192.168.56.1",
                "119.133.7.205",
                "14.29.106.1",
                "1.15.255.255",
                "118.121.204.216",
                "27.17.234.255",
                "36.149.28.255",
                "42.48.34.255",
                "59.50.33.81");
        String ip = value.getIp();
        ip = ipList.get(random.nextInt(ipList.size()));
        value.setIp(ip);
        String url = String.format(URL_PATTERN, ip, key);
        String body = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            // 免费的并发有上限 可能会限流
            //Thread.sleep(1000);
            int code = httpResponse.getStatusLine().getStatusCode();
            if (code == HttpStatus.SC_OK) {
                // TODO 接入国外ip查询
                body = EntityUtils.toString(httpResponse.getEntity());
                JSONObject jsonObject = JSONObject.parseObject(body);
                String province = (String) jsonObject.get("province");
                value.setProvince(province);
                String city = (String) jsonObject.get("city");
                value.setCity(city);
            }
            //Thread.sleep(100);
        } catch (ClassCastException e) {
            // 非局域网
            if (value.getProvince() == null) {
                log.warn("ip解析异常,ip:[{}],body:[{}]", ip, body);
                value.setProvince("unknown");
            }
            value.setCity("unknown");
            // 解析局域网时"city":[ ],会出现类型转换错误，不用管
        } catch (Exception e) {
            log.error("ip解析异常", e);
            // 非局域网
            if (value.getProvince() == null) {
                value.setProvince("unknown");
            }
            value.setCity("unknown");
        }
        return JSON.toJSONString(value);
    }


    /**
     * 连接池调优
     *
     * @return
     */
    private CloseableHttpClient createHttpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);

        //MaxPerRoute是对maxtotal的细分，每个主机的并发最大是300，route是指域名
        connectionManager.setDefaultMaxPerRoute(300);
        //设置连接池最大是500个连接
        connectionManager.setMaxTotal(500);

        RequestConfig requestConfig = RequestConfig.custom()
                //返回数据的超时时间
                .setSocketTimeout(20000)
                //连接上服务器的超时时间
                .setConnectTimeout(10000)
                //从连接池中获取连接的超时时间
                .setConnectionRequestTimeout(1000)
                .build();

        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .build();

        return closeableHttpClient;
    }
}
