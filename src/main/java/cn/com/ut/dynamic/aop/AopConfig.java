package cn.com.ut.dynamic.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AopConfig extends BaseAopConfig {

	@Pointcut("(within(*..dao.impl.*DAOImpl)"
			+ " && !within(cn.com.ut..resouce..dao.impl.*DAOImpl))")
	public void pointcut4DAO() {

	}

	@Pointcut("within(*..service.*Service)" + " && !within(cn.com.ut..resource..service.impl.*)")
	public void pointcut4Service() {

	}

}
