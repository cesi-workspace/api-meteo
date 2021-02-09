package fr.cesi.meteo.infrastructure.http.annotation;

import fr.cesi.meteo.infrastructure.http.Method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Action {

    String path();
    Method method();

}
