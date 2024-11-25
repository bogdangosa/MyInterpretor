package Containers;

import java.util.List;

public interface MyIStack<T> {
    void push(T t);
    T pop();
    T top();
    List<T> getReversed();
    boolean isEmpty();
}
