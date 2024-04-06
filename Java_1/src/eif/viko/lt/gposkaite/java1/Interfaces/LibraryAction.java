package eif.viko.lt.gposkaite.java1.Interfaces;

@FunctionalInterface
public interface LibraryAction<T, R> {
    R execute(T t);
}