package cn.com.ut.dynamic;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.alibaba.druid.pool.DruidDataSource;

import cn.com.ut.core.common.util.CommonUtil;

public class AppDBDynamic extends AbstractRoutingDataSource
		implements DisposableBean, ApplicationContextAware {

	private Map<String, DataSource> dynamicDataSources = new HashMap<>();

	@Autowired
	private AppDBConfig appDBConfig;
	@Autowired
	private DruidDataSourceConfig druidDataSourceConfig;

	private ApplicationContext applicationContext;

	@Override
	public void afterPropertiesSet() {

	}

	@Override
	protected Object determineCurrentLookupKey() {

		return AppDBContextHolder.getAppDB();
	}

	private void destory(String beanName) {

		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext
				.getAutowireCapableBeanFactory();
		if (beanFactory.containsBean(beanName))
			beanFactory.destroySingleton(beanName);
		
//		if (beanFactory.containsBeanDefinition(beanName))
//			beanFactory.removeBeanDefinition(beanName);

	}

	public synchronized boolean clearDataSource(String appDBKey) {

		boolean done = false;
		DataSource dataSource = dynamicDataSources.get(appDBKey);
		if (dataSource != null) {
			dynamicDataSources.remove(appDBKey);
			if (dataSource instanceof DruidDataSource) {

				// ((DruidDataSource) dataSource).close();
				destory(appDBKey + "_ds");
				done = true;
			}
		}

		return done;
	}

	private synchronized DataSource createDataSource(String appDBKey) {

		DataSource dataSource = this.dynamicDataSources.get(appDBKey);
		if (dataSource != null) {
			return dataSource;

		} else {

			synchronized (this) {
				dataSource = this.dynamicDataSources.get(appDBKey);
				if (dataSource != null) {
					return dataSource;
				} else {

					AppDB appDB = appDBConfig.getAppDBMap().get(appDBKey);
					if (appDB == null) {
						throw new IllegalStateException("appDBKey is invalid");
					}

					Map<String, Object> dataSourceVo = new HashMap<>();

					String dbUrl = appDB.getUrl();
					if (dbUrl.startsWith(DruidDataSourceConfig.POSTGRESQL_URL_START)) {
						dataSourceVo.put("driverClassName",
								DruidDataSourceConfig.POSTGRESQL_DRIVER);
					} else {
						dataSourceVo.put("driverClassName", DruidDataSourceConfig.MYSQL_DRIVER);
					}

					dataSourceVo.put("name", appDB.getKey() + "_ds");
					dataSourceVo.put("username", appDB.getUsername());
					dataSourceVo.put("url", appDB.getUrl());
					dataSourceVo.put("password", appDB.getPassword());
					dataSourceVo.put("initialSize", druidDataSourceConfig.getInitialSize());
					dataSourceVo.put("maxActive", druidDataSourceConfig.getMaxActive());
					dataSourceVo.put("minIdle", druidDataSourceConfig.getMinIdle());

					BeanDefinition dataSourceBD = defaultBeanDefinition(DruidDataSource.class,
							dataSourceVo);
					((GenericBeanDefinition) dataSourceBD).setDestroyMethodName("close");

					DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext
							.getAutowireCapableBeanFactory();
					defaultListableBeanFactory.registerBeanDefinition(appDB.getKey() + "_ds",
							dataSourceBD);
					DruidDataSource druidDataSource = (DruidDataSource) applicationContext
							.getBean(appDB.getKey() + "_ds");
					this.dynamicDataSources.put(appDB.getKey(), druidDataSource);
					return druidDataSource;
				}
			}
		}

	}

	private BeanDefinition defaultBeanDefinition(Class<?> beanClass, Map<?, ?> propertyValues) {

		GenericBeanDefinition definition = new GenericBeanDefinition();
		definition.setBeanClass(beanClass);
		definition.setScope(BeanDefinition.SCOPE_SINGLETON);
		definition.setLazyInit(false);
		definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
		definition.setAutowireCandidate(true);

		if (propertyValues != null) {
			MutablePropertyValues mp = new MutablePropertyValues();
			mp.addPropertyValues(propertyValues);
			definition.setPropertyValues(mp);
		}
		return definition;

	}

	@Override
	protected DataSource determineTargetDataSource() {

		String lookupKey = (String) determineCurrentLookupKey();
		if (CommonUtil.isEmpty(lookupKey)) {

			throw new IllegalStateException("lookupKey can not be null");
		}

		DataSource dataSource = createDataSource(lookupKey);

		if (dataSource == null) {

			throw new IllegalStateException(
					"Cannot determine target DataSource for lookup key [" + lookupKey + "]");
		}

		return dataSource;
	}

	@Override
	public void destroy() throws Exception {

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		this.applicationContext = applicationContext;
	}

}
