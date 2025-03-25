package com.onlinebanking.bank.config.aop;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAdvice {
  @Around("within(com.onlinebanking.bank.controller..*)"
      + "|| within(com.onlinebanking.bank.repository..*) "
      + "|| within(com.onlinebanking.bank.service..*)"
  )
  public Object appLoggingAdvice(ProceedingJoinPoint joinPoint) {
    Logger logger = logger(joinPoint);
    Object[] args = joinPoint.getArgs();
    try {
      if (logger.isDebugEnabled()) {
        logger.debug("Enter {}() with argument[s] = {}", joinPoint.getSignature().getName(), Arrays.toString(args));
      } else {
        logger.info("Enter {}() with argument[s] = {}", joinPoint.getSignature().getName(), Arrays.toString(args));
      }

      Object result = joinPoint.proceed();

      if (logger.isDebugEnabled()) {
        logger.debug("Exit {}() with result = {}", joinPoint.getSignature().getName(), result);
      } else {
        logger.info("Enter {}() with argument[s] = {}", joinPoint.getSignature().getName(), Arrays.toString(args));
      }
      return result;
    } catch (Throwable e) {
      logger.error("Error occurred: {} in {}()", Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature().getName());
      throw new RuntimeException(e);
    }
  }

  Logger logger(JoinPoint joinPoint) {
    return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
  }
}

