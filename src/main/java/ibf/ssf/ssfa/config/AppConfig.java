package ibf.ssf.ssfa.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import static ibf.ssf.ssfa.Constants.*;

@Configuration
public class AppConfig {
  private final Logger logger = Logger.getLogger(AppConfig.class.getName());

  @Value("${spring.redis.host}")
  private String redisHost;

  @Value("${spring.redis.port}")
  private Integer redisPort;

  @Bean(REDIS_TEMPLATE)
  public RedisTemplate<String, String> createRedisTemplate() {
    final String redisPassword = System.getenv(REDIS_PASSWORD);

    // configuring the database
    final RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
    redisConfig.setHostName(redisHost);
    redisConfig.setPort(redisPort);
    if (redisPassword != null) {
      logger.log(Level.INFO, "Setting Redis password");
      redisConfig.setPassword(redisPassword);
    }

    // create client and factory
    final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder()
        .build();
    final JedisConnectionFactory jedisFac = new JedisConnectionFactory(redisConfig, jedisClient);
    jedisFac.afterPropertiesSet();

    final RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisFac);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new StringRedisSerializer());
    return template;
  }
}
