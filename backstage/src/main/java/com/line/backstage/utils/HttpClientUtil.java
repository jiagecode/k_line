package com.line.backstage.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpClientUtil {

    private static HttpClientUtil instance;
    protected Charset charset;

    private HttpClientUtil() {
    }

    public static HttpClientUtil getInstance() {
        return getInstance(Charset.defaultCharset());
    }

    public static HttpClientUtil getInstance(Charset charset) {
        if (instance == null) {
            instance = new HttpClientUtil();
        }
        instance.setCharset(charset);
        return instance;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    /**
     * post请求
     */
    public String doPost(String url) throws Exception {
        return doPost(url, null, null);
    }

    public String doPost(String url, Map<String, Object> params) throws Exception {
        return doPost(url, params, null);
    }

    public String doPost(String url, Map<String, Object> params, Map<String, String> header) throws Exception {
        String body = null;
        try {
            // Post请求
            HttpPost httpPost = new HttpPost(url.trim());
            // 设置参数
            httpPost.setEntity(new UrlEncodedFormEntity(map2NameValuePairList(params), charset));
            // 设置Header
            if (header != null && !header.isEmpty()) {
                for (Iterator<Entry<String, String>> it = header.entrySet().iterator(); it.hasNext(); ) {
                    Entry<String, String> entry = (Entry<String, String>) it.next();
                    httpPost.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
                }
            }
            // 发送请求,获取返回数据
            body = execute(httpPost);
        } catch (Exception e) {
            throw e;
        }
        return body;
    }

    /**
     * postJson请求
     */
    public String doPostJson(String url, Map<String, Object> params) throws Exception {
        return doPostJson(url, params, null);
    }

    public String doPostJson(String url, Map<String, Object> params, Map<String, String> header) throws Exception {
        String json = null;
        if (params != null && !params.isEmpty()) {
            for (Iterator<Entry<String, Object>> it = params.entrySet().iterator(); it.hasNext(); ) {
                Entry<String, Object> entry = (Entry<String, Object>) it.next();
                Object object = entry.getValue();
                if (object == null) {
                    it.remove();
                }
            }
            json = JsonUtils.toJsonString(params);
        }
        return postJson(url, json, header);
    }

    public String doPostJson(String url, String json) throws Exception {
        return doPostJson(url, json, null);
    }

    public String doPostJson(String url, String json, Map<String, String> header) throws Exception {
        return postJson(url, json, header);
    }

    private String postJson(String url, String json, Map<String, String> header) throws Exception {
        String body = null;
        try {
            // Post请求
            HttpPost httpPost = new HttpPost(url.trim());
            // 设置参数
            httpPost.setEntity(new StringEntity(json, ContentType.DEFAULT_TEXT.withCharset(charset)));
            httpPost.setHeader(new BasicHeader("Content-Type", "application/json"));
            // 设置Header
            if (header != null && !header.isEmpty()) {
                for (Iterator<Entry<String, String>> it = header.entrySet().iterator(); it.hasNext(); ) {
                    Entry<String, String> entry = (Entry<String, String>) it.next();
                    httpPost.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
                }
            }
            // 发送请求,获取返回数据
            body = execute(httpPost);
        } catch (Exception e) {
            throw e;
        }
        return body;
    }

    public String postForm(String url, Map<String, String> parms, Map<String, String> header) throws Exception {
        ArrayList<BasicNameValuePair> list = new ArrayList<>();
        parms.forEach((key, value) -> list.add(new BasicNameValuePair(key, value)));

        String body = null;
        try {
            // Post请求
            HttpPost httpPost = new HttpPost(url.trim());
            // 设置参数
            httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            httpPost.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded"));
            // 设置Header
            if (header != null && !header.isEmpty()) {
                for (Iterator<Entry<String, String>> it = header.entrySet().iterator(); it.hasNext(); ) {
                    Entry<String, String> entry = (Entry<String, String>) it.next();
                    httpPost.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
                }
            }
            // 发送请求,获取返回数据
            body = execute(httpPost);
        } catch (Exception e) {
            throw e;
        }
        return body;
    }

    /**
     * put请求
     */
    public String doPutJson(String url, Map<String, Object> params) throws Exception {
        return doPutJson(url, params, null);
    }

    public String doPutJson(String url, Map<String, Object> params, Map<String, String> header) throws Exception {
        String json = null;
        if (params != null && !params.isEmpty()) {
            for (Iterator<Entry<String, Object>> it = params.entrySet().iterator(); it.hasNext(); ) {
                Entry<String, Object> entry = (Entry<String, Object>) it.next();
                Object object = entry.getValue();
                if (object == null) {
                    it.remove();
                }
            }
            json = JsonUtils.toJsonString(params);
        }
        return putJson(url, json, header);
    }

    public String doPutJson(String url, String json) throws Exception {
        return doPutJson(url, json, null);
    }

    public String doPutJson(String url, String json, Map<String, String> header) throws Exception {
        return putJson(url, json, header);
    }

    private String putJson(String url, String json, Map<String, String> header) throws Exception {
        String body = null;
        try {
            // Put请求
            HttpPut httpPut = new HttpPut(url.trim());
            // 设置参数
            httpPut.setEntity(new StringEntity(json, ContentType.DEFAULT_TEXT.withCharset(charset)));
            httpPut.setHeader(new BasicHeader("Content-Type", "application/json"));
            // 设置Header
            if (header != null && !header.isEmpty()) {
                for (Iterator<Entry<String, String>> it = header.entrySet().iterator(); it.hasNext(); ) {
                    Entry<String, String> entry = (Entry<String, String>) it.next();
                    httpPut.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
                }
            }
            // 发送请求,获取返回数据
            body = execute(httpPut);
        } catch (Exception e) {
            throw e;
        }
        return body;
    }

    /**
     * delete请求
     */
    public String doDeleteJson(String url, Map<String, Object> params) throws Exception {
        return doDeleteJson(url, params, null);
    }

    public String doDeleteJson(String url, Map<String, Object> params, Map<String, String> header) throws Exception {
        String json = null;
        if (params != null && !params.isEmpty()) {
            for (Iterator<Entry<String, Object>> it = params.entrySet().iterator(); it.hasNext(); ) {
                Entry<String, Object> entry = (Entry<String, Object>) it.next();
                Object object = entry.getValue();
                if (object == null) {
                    it.remove();
                }
            }
            json = JsonUtils.toJsonString(params);
        }
        return deleteJson(url, json, header);
    }

    public String doDeleteJson(String url, String json) throws Exception {
        return doDeleteJson(url, json, null);
    }

    public String doDeleteJson(String url, String json, Map<String, String> header) throws Exception {
        return deleteJson(url, json, header);
    }

    private String deleteJson(String url, String json, Map<String, String> header) throws Exception {
        String body = null;
        try {
            // Delete请求
            HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(url.trim());
            // 设置参数
            httpDelete.setEntity(new StringEntity(json, ContentType.DEFAULT_TEXT.withCharset(charset)));
            httpDelete.setHeader(new BasicHeader("Content-Type", "application/json"));
            // 设置Header
            if (header != null && !header.isEmpty()) {
                for (Iterator<Entry<String, String>> it = header.entrySet().iterator(); it.hasNext(); ) {
                    Entry<String, String> entry = (Entry<String, String>) it.next();
                    httpDelete.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
                }
            }
            // 发送请求,获取返回数据
            body = execute(httpDelete);
        } catch (Exception e) {
            throw e;
        }
        return body;
    }


    /**
     * get请求
     */
    public String doGet(String url) throws Exception {
        return doGet(url, null, null);
    }

    public String doGet(String url, Map<String, String> header) throws Exception {
        return doGet(url, null, header);
    }

    public String doGet(String url, Map<String, Object> params, Map<String, String> header) throws Exception {
        String body = null;
        try {
            // Get请求
            HttpGet httpGet = new HttpGet(url.trim());
            // 设置参数
            if (params != null && !params.isEmpty()) {
                String str = EntityUtils.toString(new UrlEncodedFormEntity(map2NameValuePairList(params), charset));
                String uri = httpGet.getURI().toString();
                if (uri.indexOf("?") >= 0) {
                    httpGet.setURI(new URI(httpGet.getURI().toString() + "&" + str));
                } else {
                    httpGet.setURI(new URI(httpGet.getURI().toString() + "?" + str));
                }
            }
            // 设置Header
            if (header != null && !header.isEmpty()) {
                for (Iterator<Entry<String, String>> it = header.entrySet().iterator(); it.hasNext(); ) {
                    Entry<String, String> entry = (Entry<String, String>) it.next();
                    httpGet.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
                }
            }
            // 发送请求,获取返回数据
            body = execute(httpGet);
        } catch (Exception e) {
            throw e;
        }
        return body;
    }

    /**
     * 下载文件
     */
    public void doDownload(String url, String path) throws Exception {
        download(url, null, path);
    }

    public void doDownload(String url, Map<String, Object> params, String path) throws Exception {
        download(url, params, path);
    }


    private void download(String url, Map<String, Object> params, String path) throws Exception {
        // Get请求
        HttpGet httpGet = new HttpGet(url.trim());
        if (params != null && !params.isEmpty()) {
            // 设置参数
            String str = EntityUtils.toString(new UrlEncodedFormEntity(map2NameValuePairList(params)));
            String uri = httpGet.getURI().toString();
            if (uri.indexOf("?") >= 0) {
                httpGet.setURI(new URI(httpGet.getURI().toString() + "&" + str));
            } else {
                httpGet.setURI(new URI(httpGet.getURI().toString() + "?" + str));
            }
        }
        // 发送请求,下载文件
        downloadFile(httpGet, path);
    }

    private void downloadFile(HttpRequestBase requestBase, String path) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            CloseableHttpResponse response = httpclient.execute(requestBase);
            try {
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    byte[] b = EntityUtils.toByteArray(entity);
                    OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(path)));
                    out.write(b);
                    out.flush();
                    out.close();
                }
                EntityUtils.consume(entity);
            } catch (Exception e) {
                throw e;
            } finally {
                response.close();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            httpclient.close();
        }
    }

    private String execute(HttpRequestBase requestBase) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String body = null;
        try {
            CloseableHttpResponse response = httpclient.execute(requestBase);
            try {
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    body = EntityUtils.toString(entity, charset.toString());
                }
                EntityUtils.consume(entity);
            } catch (Exception e) {
                throw e;
            } finally {
                response.close();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            httpclient.close();
        }
        return body;
    }

    public static List<NameValuePair> map2NameValuePairList(Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<String> it = params.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                if (params.get(key) != null) {
                    String value = String.valueOf(params.get(key));
                    list.add(new BasicNameValuePair(key, value));
                }
            }
            return list;
        }
        return null;
    }

}
