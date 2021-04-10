package com.yc.springframework.stereotype;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

@Qualifier
@Target({ElementType.FIELD, ElementType.METHOD,  ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MyQualifier {
    String value() default "";
}
