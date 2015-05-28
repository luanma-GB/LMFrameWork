package cc.android.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解View
 * 
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CcIocView {
	public int id();

	public String click() default "";

	public String longClick() default "";

	public String itemClick() default "";

	public String itemLongClick() default "";

	public CcIocSelect select() default @CcIocSelect(selected = "");
}
