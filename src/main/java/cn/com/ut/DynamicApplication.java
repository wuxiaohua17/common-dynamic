package cn.com.ut;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan

@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DynamicApplication extends BaseApplication {

	public static void main(String[] args) {

		SpringApplication application = new SpringApplication(DynamicApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}
}
