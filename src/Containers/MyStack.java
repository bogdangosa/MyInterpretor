package Containers;

import Exceptions.StackException;

import java.util.*;

public class MyStack<T>  implements MyIStack<T>{
    protected Stack<T> stack;

    public MyStack() {
        stack = new Stack<>();
    }

    public MyStack(Stack<T> stack) {
        this.stack = stack;
    }

    @Override
    public void push(T t) {
        stack.push(t);
    }

    @Override
    public T pop() {
        if (stack.isEmpty())
            throw new StackException("Stack   empty,can't pop");
        return stack.pop();
    }

    @Override
    public T top() {
        return stack.peek();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public List<T> getReversed(){
        return stack.reversed();
    }

    @Override
    public String toString() {
        return this.stack.toString();
    }

    public MyStack<T> deepCopy(){
        return new MyStack((Stack) this.stack.clone());
    }
}
