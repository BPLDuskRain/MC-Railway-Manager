package com.RailManager.demo.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class LogAspect {
    @AfterReturning("@annotation(com.RailManager.demo.annotation.MyInsert)")
    public void insertReturn(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        System.out.print("[INSERT]" + joinPoint.getSignature() + '\t');
        for(Object arg : args) {
            System.out.print(arg.toString() + ' ');
        }
        System.out.println();
    }
    @AfterReturning("@annotation(com.RailManager.demo.annotation.MyUpdate)")
    public void updateReturn(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        System.out.print("[UPDATE]" + joinPoint.getSignature() + '\t');
        for(Object arg : args) {
            System.out.print(arg.toString() + ' ');
        }
        System.out.println();
    }
    @AfterReturning("@annotation(com.RailManager.demo.annotation.MyDelete)")
    public void deleteReturn(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        System.out.print("[DELETE]" + joinPoint.getSignature() + '\t');
        for(Object arg : args) {
            System.out.print(arg.toString() + ' ');
        }
        System.out.println();
    }
    @AfterReturning("@annotation(com.RailManager.demo.annotation.MySelect)")
    public void selectReturn(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        System.out.print("[SELECT]" + joinPoint.getSignature() + '\t');
        for(Object arg : args) {
            System.out.print(arg.toString() + ' ');
        }
        System.out.println();
    }
    @AfterReturning("@annotation(com.RailManager.demo.annotation.MyService)")
    public void serviceReturn(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        System.out.print("[SERVICE]" + joinPoint.getSignature() + '\t');
        for(Object arg : args) {
            System.out.print(arg.toString() + ' ');
        }
        System.out.println();
    }
}