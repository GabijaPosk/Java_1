package eif.viko.lt.gposkaite.java1.Anotations;
import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DeprecatedFeature {
    String message();
}