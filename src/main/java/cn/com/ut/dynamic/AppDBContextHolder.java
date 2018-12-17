package cn.com.ut.dynamic;

public class AppDBContextHolder {

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setAppDB(String appDB) {

		contextHolder.set(appDB);
	}

	public static String getAppDB() {

		return contextHolder.get();
	}

	public static void clearAppDB() {

		contextHolder.remove();
	}

}
