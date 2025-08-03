package org.example.clinicservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.clinicservice.exception.InvalidLoginException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ControllerExceptionAspect {

    @Around("execution(* org.example.clinicservice.controller.*.*(..))")
    public Object handleControllerExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (InvalidLoginException e) {
            log.error("Invalid login exception in controller {}.{}: {}", 
                     joinPoint.getTarget().getClass().getSimpleName(),
                     joinPoint.getSignature().getName(), 
                     e.getMessage());
            return ResponseEntity.badRequest().body("Invalid login: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected exception in controller {}.{}: {}", 
                     joinPoint.getTarget().getClass().getSimpleName(),
                     joinPoint.getSignature().getName(), 
                     e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Internal server error: " + e.getMessage());
        }
    }
}
