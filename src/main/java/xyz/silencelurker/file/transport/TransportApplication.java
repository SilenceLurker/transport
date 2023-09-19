package xyz.silencelurker.file.transport;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import xyz.silencelurker.file.transport.conf.RedisStatusCheckConfig;

/**
 * @author Silence_lurker
 */
@EnableAutoConfiguration
@EnableRedisRepositories
@SpringBootApplication
public class TransportApplication {

	public static void main(String[] args) {

		try {
			RedisTemplate<String, Object> template = new RedisTemplate<>();
			var valueOps = template.opsForValue();
			valueOps.set("test-Key" + UUID.randomUUID().toString(), "test-VALUE", 1);
		} catch (Exception e) {
			RedisStatusCheckConfig.redisStatus = false;
		}

		SpringApplication.run(TransportApplication.class, args);
	}

}
