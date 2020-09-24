package com.shadow.registry;

import com.shadow.framework.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shadow
 * @create 2020-09-24
 * @description
 */
public class RemoteRegistry {

	private static Map<String,List<URL>> services = new HashMap<>();

	public static void registry(String interfaceName, URL url) {
		List<URL> urls = services.get(interfaceName) == null ? new ArrayList<>() : services.get(interfaceName);
		urls.add(url);
		services.put(interfaceName, urls);
	}

}
