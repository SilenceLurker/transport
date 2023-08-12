package xyz.silencelurker.file.transport.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;

import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;

/**
 * @author Silence_Lurker
 */
@Configuration
public class RedisConfiuration {

    @Resource
    RedisConnectionFactory factory;

    public @PreDestroy void flushTestDb() {
        factory.getConnection().flushDb();
    }
}
