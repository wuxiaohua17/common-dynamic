package cn.com.ut.dynamic;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class DALConfig {

	@Bean
	public DataSource dataSource() {

		AppDBDynamic appDBDynamic = new AppDBDynamic();
		return appDBDynamic;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
		return jdbcTemplate;
	}

	@Bean
	public DataSourceTransactionManager transactionManager() {

		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(
				dataSource());
		return transactionManager;
	}
}
