package cn.com.ut.dynamic;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.pool.DruidDataSource;

import cn.com.ut.core.common.util.ArrayUtil;

@RestController
public class AppDBDynamicController implements ApplicationContextAware {

	@Autowired
	private DataSource dataSource;
	private ApplicationContext applicationContext;

	@RequestMapping(value = "/clearDB/{appDBKey}")
	public boolean clearDB(@PathVariable(value = "appDBKey") String appDBKey) {

		return ((AppDBDynamic) dataSource).clearDataSource(appDBKey);
	}

	@RequestMapping(value = "/queryDB")
	public ArrayList<String> queryDB() {

		System.out.println("1:" + applicationContext.getBeanDefinitionCount());
		String[] beanNamesForType = applicationContext.getBeanNamesForType(DruidDataSource.class);
		System.out.println("2:" + ArrayUtil.joinArrayElement(beanNamesForType, ","));
		System.out.println("3:" + applicationContext.getBeansOfType(DruidDataSource.class));

		ArrayList<String> dbs = new ArrayList<>();

		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext
				.getAutowireCapableBeanFactory();

		if (applicationContext.containsBean("test_app_123_ds")) {
			System.out.println("4:" + applicationContext.getBean("test_app_123_ds"));
			dbs.add("test_app_123_ds");
			
		}
		if (applicationContext.containsBean("test_app_456_ds")) {
			System.out.println("5:" + applicationContext.getBean("test_app_456_ds"));
			dbs.add("test_app_456_ds");

		}

		return dbs;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		this.applicationContext = applicationContext;

	}
}
