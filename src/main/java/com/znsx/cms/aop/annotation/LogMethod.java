package com.znsx.cms.aop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解方式的日志描述
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-3-27 上午11:09:52
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogMethod {
	/**
	 * 操作目标对象类型
	 * 
	 * @return
	 */
	String targetType();

	/**
	 * 操作类型
	 * 
	 * @return
	 */
	String operationType();

	/**
	 * 操作内容名称
	 * 
	 * @return
	 */
	String name();

	/**
	 * 操作具体编码
	 * 
	 * @return
	 */
	String code();
}
