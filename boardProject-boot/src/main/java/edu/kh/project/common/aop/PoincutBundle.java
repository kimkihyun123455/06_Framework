package edu.kh.project.common.aop;

import org.aspectj.lang.annotation.Pointcut;

// Pointcut : 실제 Advice가 적용될 지점
// Pointcut 모아두는 클래스
public class PoincutBundle {

	// 작성하기 어려운 Pointcut을 미리 작성해두고
	// 필요한 곳에서 클래스명.메서드명()으로 호출
	
	// @Before("execution(* edu.kh.project..*Controller*.*(..))")
	// == @Before("PointcutBundle.controllerPointcut()")
	
	@Pointcut("execution(* edu.kh.project..*Controller*.*(..))")
	public void controllerPointcut() {}
	
	@Pointcut("execution(* edu.kh.project..*ServiceImpl*.*(..))")
	public void serviceImplPointcut() {}
	
}
