package de.richtmat;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.apache.commons.configuration.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;

import uk.co.datumedge.redislauncher.LocalRedisServer;
import uk.co.datumedge.redislauncher.RedisServer;

public class RedisConfigurationTest {

	Configuration config = new RedisConfiguration();
	static RedisServer redisServer;
	
	static {
		System.setProperty("redislauncher.command", "/usr/local/bin/redis-server");
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		redisServer = LocalRedisServer.newInstance();
		redisServer.start();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		redisServer.stop();
	}
	
	@Before
	public void setUp() {
		Jedis jedis = new Jedis("localhost");
		jedis.set("config-test-key", "some value");
		jedis.set("key", "some key's value");
		jedis.set("key.int", "5");
		jedis.set("key&", "\"¤$");
	}
	
	@After
	public void tearDown() {
		Jedis jedis = new Jedis("localhost");
		jedis.flushAll();
	}

	@Test
	public void testContainsKey() {
		assertTrue(config.containsKey("key"));
		assertFalse(config.containsKey("false_key"));
	}

	@Test
	public void testGetKeys() {
		Iterator<String> keys = config.getKeys();
		assertNotNull(keys);
		int counter = 0;
		String key;
		while(keys.hasNext() && (key = keys.next()) != null) {
			assertNotNull(key);
			counter++;
		}
		assertEquals(4, counter);
	}

	@Test
	public void testGetProperty() {
		assertEquals("config-test-key", "some value", config.getProperty("config-test-key"));
        assertEquals("key", "some key's value", config.getProperty("key"));
        assertEquals("unknown property", null, config.getProperty("key3"));
        assertEquals("key.int", 5, config.getInt("key.int"));
        assertEquals("key&", "\"¤$", config.getProperty("key&"));
	}

	@Test
	public void testIsEmpty() {
		assertFalse(config.isEmpty());
		config.clear();
		assertTrue(config.isEmpty());
	}

	@Test
	public void testAddPropertyDirectStringObject() {
		assertEquals("unknown property", null, config.getProperty("key3"));
		assertFalse(config.containsKey("key3"));
		config.addProperty("key3", "yet another value");
		assertTrue(config.containsKey("key3"));
		assertEquals("key3", "yet another value", config.getProperty("key3"));
	}

}
