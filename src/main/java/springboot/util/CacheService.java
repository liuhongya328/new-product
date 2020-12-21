package springboot.util;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;
@Component
public class CacheService { 

		 //这里因为有其他的template,所以名字起得不好看
		@Autowired
     	 RedisTemplate redisTemplate;
     	 
		public Long getIncrementNum(String key) {
        // 不存在准备创建 键值对
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long counter = entityIdCounter.incrementAndGet();
        if ((null == counter || counter.longValue() == 1)) {// 初始设置过期时间
            System.out.println("设置过期时间为1天!");
            entityIdCounter.expire(1, TimeUnit.DAYS);// 单位天
        }
        return counter;
    }
}