package cn.com.ut.dynamic.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseAopConfig {

	@Autowired
	private AopAdvice aopAdvice;

	public abstract void pointcut4DAO();

	public abstract void pointcut4Service();

	@Before("pointcut4DAO()")
	public void before4DAO(JoinPoint jp) {

		aopAdvice.before4DAO(jp);
	}

	@Around("pointcut4Service()")
	public Object around4Service(ProceedingJoinPoint pjp) throws Throwable {

		return aopAdvice.around4Service(pjp);

	}

}
