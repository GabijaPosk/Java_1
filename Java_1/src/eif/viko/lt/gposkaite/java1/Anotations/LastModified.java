package eif.viko.lt.gposkaite.java1.Anotations;
import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LastModified {
    String date();
}