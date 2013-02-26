commons-config-redis
====================

# What's that?

commons-config-redis is an implementation of the [Apache Commons Configurations](http://commons.apache.org/proper/commons-configuration/ "Apache Commons Configurations") `Configuration` interface using the [Redis](http://github.com/antirez/redis "Redis") key-value store.

Redis is a very fast NoSQL key-value store and therefore a natural fit to hold configuration values. commons-config-redis uses [Jedis](http://github.com/xetorthio/jedis "Jedis") as a Redis client implementation. 

## How do I use it?

You need to build it from source with maven:

```
$ mvn install
```

Then you can use it as a maven dependency locally:

```xml
<dependency>
	<groupId>de.richtmat</groupId>
	<artifactId>commons-config-redis</artifactId>
	<version>1.0-SNAPSHOT</version>
</dependency>
```

To use it just create an instance of RedisConfiguration:
    
```java
Configuration config = new RedisConfiguration();
```

or inject it (via Spring) so you don't need to bind your class to commons-config-redis.

## Contributions welcome!

Fork it, change it, push it!