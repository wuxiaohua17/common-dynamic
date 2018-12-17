package cn.com.ut.dynamic.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AopAdvice {

	public void before4DAO(JoinPoint jp) {

		Signature signature = jp.getSignature();
		log.debug("dao method===" + signature.toLongString());

		log.debug("dao method end===" + signature.toLongString());
	}

	public Object around4Service(ProceedingJoinPoint pjp) throws Throwable {

		Signature signature = pjp.getSignature();
		MethodSignature ms = (MethodSignature) signature;
		log.debug("service method===" + signature.toLongString());
		try {
			return pjp.proceed();
		} catch (Throwable e) {
			throw e;
		} finally {
			log.debug("service method end===" + signature.toLongString());
		}

	}
}
