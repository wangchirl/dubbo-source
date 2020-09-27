package com.shadow.simulation.framework.registry;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shadow
 * @create 2020-09-24
 * @description
 * 本地注册
 */
public class LocalRegistry {

	private static Map<String,Class> services = new HashMap<>();

	public static void registry(String interfaceName, Class impl) {
		services.put(interfaceName, impl);
	}

	public static Class get(String interfaceName) {
		return services.get(interfaceName);
	}
}
