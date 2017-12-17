package com.my.biz.config.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

public class DefaultKeySerializer implements RedisSerializer<Object> {
	private final Charset charset = Charset.forName("UTF8");
	
	@Override
	public byte[] serialize(Object o) throws SerializationException {
		if (o == null) {
			return null;
		} else {
			if (o instanceof byte[]) {
				return (byte[]) o;
			}
			return o.toString().getBytes(charset);
		}
	}
	
	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		return (bytes == null ? null : new String(bytes, charset));
	}
}