package com.explorer.routemap.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class RMLogAdvice {

   // return type : * 또는 void
   // .* : 모든 method
   // (..) : 모든 Parameter
   @Pointcut("execution(* com.explorer.routemap.*.*.*Impl.*(..))")
   public void allPointcut() {
   }

   /*
    * @Before("allPointcut()") public void printLog(JoinPoint jp) {
    * System.out.println(jp.getSignature().getName()+" ] 실행 전"); }
    */

   /*
    * @Around("allPointcut()") public Object aroundLog(ProceedingJoinPoint pjp)
    * throws Throwable {
    * 
    * String methodName = pjp.getSignature().getName();
    * 
    * System.out.println(methodName+" : 실행 전");
    * 
    * Object obj = pjp.proceed();
    * 
    * System.out.println(methodName+" : 실행 후");
    * 
    * return obj;
    * 
    * }
    */

   // 예외가 발생했을 때 Advice
   @AfterThrowing(pointcut = "allPointcut()", throwing = "exception")
   public void afterThrowingMethod(JoinPoint jp, Exception exception) throws Exception {
      
      // 메소드명을 출력합니다.
      System.out.println("실행 : "+jp.getSignature().getName());

      // 발생한 예외의 메세지를 출력합니다.
      System.out.println(exception.getMessage());
      System.out.println("예외 메시지 출력 완료");
      
   }

}