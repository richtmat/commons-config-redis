package de.richtmat;

import java.util.Iterator;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.Configuration;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisConfiguration extends AbstractConfiguration implements
		Configuration {

	private Jedis client;

	public RedisConfiguration() {
		this.client = new Jedis("localhost");
	}
	
	public RedisConfiguration(String host, int port, int timeout) {
		this.client = new Jedis(host, port, timeout);
	}

	public RedisConfiguration(JedisPool pool) {
		this.client = pool.getResource();
	}
	
	public boolean containsKey(String key) {
		return client.exists(key);
	}

	public Iterator<String> getKeys() {
		String pattern = "*";
		return client.keys(pattern).iterator();
	}

	public Object getProperty(String key) {
		return client.get(key);
	}

	public boolean isEmpty() {
		return client.dbSize() == 0;
	}

	@Override
	protected void addPropertyDirect(String key, Object value) {
		client.set(key, String.valueOf(value));
	}

	@Override
	protected void clearPropertyDirect(String key) {
		client.del(key);
	}
}
