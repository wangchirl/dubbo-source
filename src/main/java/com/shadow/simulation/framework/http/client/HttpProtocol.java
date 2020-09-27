package com.shadow.simulation.framework.http.client;

import com.shadow.simulation.framework.support.Invocation;
import com.shadow.simulation.framework.support.Protocol;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author shadow
 * @create 2020-09-24
 * @description
 */
public class HttpProtocol implements Protocol {

	public Object send(com.shadow.simulation.framework.support.URL innerUrl, Invocation invocation) {
		// 网络传输
		try {
			URL url = new URL("http", innerUrl.getHostname(), innerUrl.getPort(), "/");
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
