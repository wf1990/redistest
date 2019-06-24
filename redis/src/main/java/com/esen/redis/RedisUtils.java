package com.esen.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * @Description: 测试redis相关功能
 * @author: wufeng 
 * @date: 2019年6月24日 上午9:18:53
 */
public class RedisUtils {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost");
		//测试redis字符串类型数据
		jedis.set("city", "南京");
		System.out.println("字符串测试数据为："+jedis.get("city"));
		
		//测试redis列表类型数据
		jedis.del("city");
		jedis.lpush("city", "北京","上海","广州");
		List<String> citylist = jedis.lrange("city", 0, 100);
	    System.out.println("list测试数据为："+citylist);
	    
		//测试redis键值对类型数据
		Map<String,String> people = new HashMap<String,String>();
		people.put("name", "xiaoming");
		people.put("sex", "male");
		people.put("age", "17");
		people.put("height", "174");
		jedis.hmset("people", people);
		String[] arr = {"name","age","height"};
		List<String> list = jedis.hmget("people", arr);
		String[] res = new String[arr.length];
		list.toArray(res);
		people.clear();
		for(int i=0;i<arr.length;i++) {
			people.put(arr[i],res[i]);
		}
		System.out.println("map测试数据为："+people);
		
		//测试redis集合类型数据
		citylist.add("深圳");
		String[] cityarr = new String[citylist.size()];
		citylist.toArray(cityarr);
		jedis.sadd("citylist", cityarr);
		Set<String> smembers = jedis.smembers("citylist");
		System.out.println("集合测试数据为："+smembers);
		
		//测试redis有序集合数据类型
		Map<String,Double> scoreMap = new HashMap<String,Double>();
		scoreMap.put("北京", 4.0);
		scoreMap.put("上海", 3.0);
		scoreMap.put("南京", 7.0);
		scoreMap.put("广州", 9.0);
		scoreMap.put("深圳", 3.0);
		scoreMap.put("南京", 2.0);
		jedis.zadd("cityscore", scoreMap);
		Set<String> slist = jedis.zrevrange("cityscore", 0, 1000);
		System.out.println("有序集合测试结果："+slist);
		jedis.close();
	}
}
