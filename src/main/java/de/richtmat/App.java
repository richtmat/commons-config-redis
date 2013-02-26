package de.richtmat;

import redis.clients.jedis.Jedis;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) {
		
		Jedis jedis = new Jedis("localhost");
		
		String value = jedis.get("key.one");
		
		
		System.out.println("Hello World! " + value);
	}
}
