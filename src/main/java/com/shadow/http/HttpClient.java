package com.shadow.http;

import com.shadow.framework.Invocation;
import com.sun.deploy.net.HttpRequest;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.invoke.MethodType;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author shadow
 * @create 2020-09-24
 * @description
 */
public class HttpClient {

	public String send(String hostname, Integer port, Invocation invocation) {
		// 网络传输
		try {
			URL url = new URL("http", hostname, port, "/");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setDoOutput(true);

			// 发送数据
			OutputStream outputStream = connection.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			oos.writeObject(invocation);
			oos.flush();
			oos.close();

			// 接收返回的数据并返回
			InputStream inputStream = connection.getInputStream();
			String result = IOUtils.toString(inputStream,"utf-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
