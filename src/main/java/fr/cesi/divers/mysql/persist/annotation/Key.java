package fr.cesi.divers.mysql.persist.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Key {

    KeyType keyType();
    int length() default -1;
    boolean unsigned() default false;
    boolean notNull() default false;
    boolean autoincrement() default false;

}
