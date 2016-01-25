package com.znsx.cms.aop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解方式的接口描述
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-3-28 上午10:12:13
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InterfaceDescription {
	/**
	 * 是否需要登陆
	 * 
	 * @return
	 */
	boolean logon() default true;

	/**
	 * 统一接口方法名
	 * 
	 * @return
	 */
	String method();

	/**
	 * 统一接口指令号
	 * 
	 * @return
	 */
	String cmd();
}
