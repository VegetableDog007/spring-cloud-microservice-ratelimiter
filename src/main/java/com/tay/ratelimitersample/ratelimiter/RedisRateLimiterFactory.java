package com.tay.ratelimitersample.ratelimiter;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPool;

@Component
public class RedisRateLimiterFactory {
	
	@Autowired
	private JedisPool jedisPool;
	private final WeakHashMap<String, RedisRateLimiter> limiterMap = new WeakHashMap<String, RedisRateLimiter>();
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	
	public RedisRateLimiter get(String keyPrefix, TimeUnit timeUnit, int permits) {
		if(limiterMap.containsKey(keyPrefix)) {
			return limiterMap.get(keyPrefix);
		}
		else {
			synchronized(this) {
				RedisRateLimiter redisRateLimiter = new RedisRateLimiter(jedisPool, timeUnit, permits);
				limiterMap.put(keyPrefix, redisRateLimiter);
				return redisRateLimiter;
			}
		}
	}
}
