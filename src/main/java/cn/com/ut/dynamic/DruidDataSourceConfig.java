package cn.com.ut.dynamic;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@Setter
@Getter
public class DruidDataSourceConfig {

	public static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";
	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	public static final String POSTGRESQL_URL_START = "jdbc:postgresql";
	

	private String driverClass = MYSQL_DRIVER;
	private int initialSize = DruidDataSource.DEFAULT_INITIAL_SIZE;
	private int maxActive = DruidDataSource.DEFAULT_MAX_ACTIVE_SIZE;
	private int minIdle = DruidDataSource.DEFAULT_MIN_IDLE;
	private int maxWait = DruidDataSource.DEFAULT_MAX_WAIT;
}
