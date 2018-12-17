package cn.com.ut.dynamic;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class AppDBConfig implements InitializingBean {

	private Map<String, AppDB> appDBMap = new HashMap<>();

	public Map<String, AppDB> getAppDBMap() {

		return appDBMap;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		AppDB db1 = new AppDB();

		db1.setKey("test_app_123");
		db1.setUrl(
				"jdbc:mysql://localhost:3308/test_app_123?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
		db1.setUsername("root");
		db1.setPassword("root");

		AppDB db2 = new AppDB();
		db2.setKey("test_app_456");
		db2.setUrl(
				"jdbc:mysql://localhost:3308/test_app_456?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
		db2.setUsername("root");
		db2.setPassword("root");

		appDBMap.put(db1.getKey(), db1);
		appDBMap.put(db2.getKey(), db2);
	}

}
