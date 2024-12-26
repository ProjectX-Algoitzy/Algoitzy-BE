package org.example.aop;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

  @Pointcut("execution(* org.example..*Controller.*(..))")
  public void controller() {
  }

  @Pointcut("execution(* org.example..*Service.*(..))")
  public void service() {
  }

  @Pointcut("execution(* org.example..*Repository.*(..))")
  public void repository() {
  }

  @Before("controller() || service() || repository()")
  public void beforeLogic(JoinPoint joinPoint) {
    loggingMethodInfo(joinPoint);
  }

  private void loggingMethodInfo(JoinPoint joinPoint) {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Method method = methodSignature.getMethod();
    log.info("<< METHOD NAME [ {} ] >>", method.getName());

    Object[] args = joinPoint.getArgs();
    for (int i = 0; i < args.length; i++) {
      Object arg = args[i];
      if (arg != null) {
        log.info("<< {}번째 PARAMETER >>", i + 1);
        log.info("{} ==> VALUE : {}\n", arg.getClass().getSimpleName(), arg);
      }

    }
  }

}
