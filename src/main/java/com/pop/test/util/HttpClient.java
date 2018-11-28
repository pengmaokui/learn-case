package com.pop.test.util;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

/**
 * Created by pengmaokui on 2017/5/31.
 */
public class HttpClient implements Closeable {

	private static final String GET_SEPARATOR = "?";

	public static final String POST = "post";

	public static final String GET = "get";

	private CloseableHttpClient client;

	private HttpClientBuilder builder;

	private Charset charset;

	public HttpClient(boolean sslTrustAny, Charset charset) {

		this.charset = charset;

		try {
			builder = HttpClientBuilder.create();
			if (sslTrustAny) {
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new SecureRandom());
				builder.setSSLHostnameVerifier(new TrustAnyHostnameVerifier()).setSslcontext(sc);
			}

			builder.setMaxConnPerRoute(100).setMaxConnTotal(100);

			builder.setConnectionReuseStrategy(NoConnectionReuseStrategy.INSTANCE);
			builder.disableRedirectHandling().disableAutomaticRetries();
			client = builder.build();

		} catch (KeyManagementException e) {
			throw new RuntimeException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public CloseableHttpClient getApacheHttpClient() {
		return client;
	}

	public CloseableHttpResponse get(URL url, Map<String, String> param) throws IOException {

		List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
		for (Map.Entry<String, String> entry : param.entrySet()) {
			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		return get(new URL(url.toString() + GET_SEPARATOR + URLEncodedUtils.format(list, charset)));
	}

	public CloseableHttpResponse get(URL url) throws IOException {
		HttpGet get = new HttpGet(url.toString());
		return client.execute(get);
	}

	public CloseableHttpResponse post(URL url, Map<String, String> param) throws IOException {
		List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
		for (Map.Entry<String, String> entry : param.entrySet()) {
			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
		return post(url, entity);
	}

	public CloseableHttpResponse post(URL url, String body) throws IOException {
		return post(url, new StringEntity(body, charset));
	}

	public CloseableHttpResponse post(URL url, HttpEntity entity) throws IOException {

		HttpPost post = new HttpPost(url.toString());
		if (entity != null) {
			post.setEntity(entity);
		}

		return client.execute(post);
	}

	public void close() throws IOException {
		client.close();
	}

	public String executeString(String method, URL url, Map<String, String> param) throws IOException {

		CloseableHttpResponse resp = null;
		try {
			resp = execute(method, url, param);

			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new RuntimeException("服务器返回状态码:" + resp.getStatusLine().getStatusCode());
			}
			return IOUtils.toString(resp.getEntity().getContent(), charset);
		} finally {
			IOUtils.closeQuietly(resp);
		}
	}

	public CloseableHttpResponse execute(String method, URL url, Map<String, String> param) throws IOException {
		if (POST.equals(method)) {
			return post(url, param);
		} else if (GET.equals(method)) {
			return get(url, param);
		} else {
			throw new RuntimeException("不支持的method:" + method);
		}
	}

	public String executeString(String method, URL url, String body) throws IOException {

		CloseableHttpResponse resp = null;
		try {
			if (POST.equals(method)) {
				resp = post(url, body);
			} else if (GET.equals(method)) {
				resp = get(new URL(url.toString() + GET_SEPARATOR + body));
			} else {
				throw new RuntimeException("不支持的method:" + method);
			}

			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new RuntimeException("服务器返回状态码:" + resp.getStatusLine().getStatusCode());
			}
			return IOUtils.toString(resp.getEntity().getContent(), charset);
		} finally {
			IOUtils.closeQuietly(resp);
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}
}
