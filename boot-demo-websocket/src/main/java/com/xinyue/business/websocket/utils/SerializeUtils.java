package com.xinyue.business.websocket.utils;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class SerializeUtils {
	
	   /**
	    **序列化方法
	    */
	    public static <T> byte[] serialize(T t,Class<T> clazz) {
	        return ProtobufIOUtil.toByteArray(t, RuntimeSchema.createFrom(clazz),
	                LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
	    }
	    /**
	    **反序列化方法
	    */
	    public static <T> T deSerialize(byte[] data,Class<T> clazz) {
	        RuntimeSchema<T> runtimeSchema = RuntimeSchema.createFrom(clazz);
	        T t = runtimeSchema.newMessage();
	        ProtobufIOUtil.mergeFrom(data, t, runtimeSchema);
	        return t;
	    }
	


	
	
	
	
}
