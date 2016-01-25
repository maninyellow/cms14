package com.znsx.cms.aop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解方式的方法参数描述("name","id")。<br />
 * "name"-日志记录中的targetName，"id"-日志记录中的targetId。<br />
 * 通常情况两个参数的描述都是必须的，只有某些特殊情况不需要记录target信息时可以缺失。 <br />
 * 特别的当是创建类型的方法时，id不存在，所以没有"id"参数的描述，这时id来自于方法的返回值。
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-3-27 上午10:39:44
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogParam {
	String value();
}
