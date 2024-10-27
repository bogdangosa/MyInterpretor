package Containers;

import java.util.Stack;

public class MyStack<T>  implements MyIStack<T>{
    private Stack<T> stack;

    @Override
    public void push(T t) {
        stack.push(t);
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public T top() {
        return stack.peek();
    }


}
