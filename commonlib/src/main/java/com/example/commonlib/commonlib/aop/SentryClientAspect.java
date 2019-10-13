package com.example.commonlib.commonlib.aop;

import com.example.commonlib.commonlib.envconfig.EnvConfig;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
@Slf4j
public class SentryClientAspect {
    @Autowired
    private EnvConfig envConfig;

    @Around("execution(* io.sentry.SentryClient.send*(..))")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if(envConfig.isDebug()){
            log.debug("no sentry logging in debug mode");
            return;
        }
        proceedingJoinPoint.proceed();
    }
}
