package com.theopus.knucaTelegram.bot;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class Metrics {

    private static final Logger log = LoggerFactory.getLogger(Metrics.class);

    private ExecutorService executorService;
    private CloseableHttpClient httpClient;

    public Metrics() {
        this.executorService = Executors.newFixedThreadPool(2);
        this.httpClient = HttpClients.createDefault();

    }

    public void track(String id, String userName, String message, boolean isCallback) {
        executorService.submit(() -> {
            try {

                HttpPost httpPost = new HttpPost("http://www.google-analytics.com/collect");
                List<NameValuePair> postParameters = new ArrayList<>();

                postParameters.add(new BasicNameValuePair("v", "1"));
                postParameters.add(new BasicNameValuePair("tid", "UA-112398090-1"));
                postParameters.add(new BasicNameValuePair("cid", id));
                postParameters.add(new BasicNameValuePair("uid", userName));
                postParameters.add(new BasicNameValuePair("t", "event"));

                if (!isCallback) {
                    postParameters.add(new BasicNameValuePair("ec", "Direct"));
                    postParameters.add(new BasicNameValuePair("ea", "action"));
                    postParameters.add(new BasicNameValuePair("el", message));
                } else {
                    postParameters.add(new BasicNameValuePair("ec", "Callback"));
                    postParameters.add(new BasicNameValuePair("ea", "action"));
                    postParameters.add(new BasicNameValuePair("el", message));
                }

                httpPost.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

                CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse execute = httpClient.execute(httpPost);
                log.info("{}", httpPost);
                log.info("Metrics = {}", execute);
                EntityUtils.consume(execute.getEntity());
            } catch (Exception e) {
                log.error("", e);
            }
        });
    }

    @PreDestroy
    public void destroy() throws IOException {
        executorService.shutdownNow();
        httpClient.close();
    }
}
