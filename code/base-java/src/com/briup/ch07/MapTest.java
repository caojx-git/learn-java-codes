package com.briup.ch07;

import java.util.*;

public class MapTest {
	public static void main(String[] args) {
		Map map = new HashMap();
		map.put("a", "aaa");
		map.put("b", "bbb");
		map.put("c", "ccc");
		map.put("d", "ddd");
		map.put("e", "eee");
		map.put("a", "zzz");// 键不能重复，我们这里把map.put("a", "aaa");覆盖
		map.put(null, null);

		// System.out.println(map.remove("a"));
		System.out.println(map.get("a"));
		System.out.println(map.size());

		// 1.keyset视图
		Set keys = map.keySet();
		// 遍历map中键值对，注：hashMap中没有Iteartor；
		Iterator i1 = keys.iterator();
		while (i1.hasNext()) {
			String key = (String) i1.next();
			String value = (String) map.get(key);
			System.out.println(key + "=" + value);
		}
		// 增强for循环
		for (Object o : keys) {
			String key = (String) o;
			String value = (String) map.get(key);
			System.out.println(key + "=" + value);
		}

		// 2.value 视图
		Collection values = map.values();// List
		System.out.println(values.size());
		Iterator i2 = values.iterator();
		while (i2.hasNext()) {
			System.out.println(i2.next());
		}
		
		
		for (Object o : values) {
			System.out.println(o);
		}

		// 3.entry视图
		Set set = map.entrySet();
		Iterator i3 = set.iterator();
		while (i3.hasNext()) {
			Map.Entry e = (Map.Entry) i3.next();
			String key = (String) e.getKey();
			String value = (String) e.getValue();
			System.out.println(key + "=" + value);
		}

		for (Object o : set) {
			Map.Entry e = (Map.Entry) o;
			String key = (String) e.getKey();
			String value = (String) e.getValue();
			System.out.println(key + "=" + value);
		}
	}
}
