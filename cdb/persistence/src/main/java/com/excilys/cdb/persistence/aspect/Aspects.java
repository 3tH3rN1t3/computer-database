package com.excilys.cdb.persistence.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.excilys.cdb.logger.LoggerCDB;

@Aspect
@Component
public class Aspects {
	
	@Pointcut("within(com.excilys.cdb.persistence.repository..*)")
	public void dataAccessOperation() {}
	
	@Around("dataAccessOperation()")
	public Object dataAccessOperation(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object object = pjp.proceed();
		long endTime = System.currentTimeMillis();
		LoggerCDB.logger.info("Method " + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName() + " took " + (endTime-startTime) + " ms to execute");
		return object;
	}
}
