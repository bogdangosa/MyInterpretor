package Models.ProgramState;

import Containers.MyIStack;
import Containers.MyStack;
import Containers.MyTree;
import Containers.Node;
import Models.Statement.CompStmt;
import Models.Statement.IStatement;

import java.util.List;

public class ExecutionStack implements IExecutionStack {
    private MyStack<IStatement> execution_stack;

    public ExecutionStack() {
        execution_stack = new MyStack<IStatement>();
    }

    @Override
    public String toString() {
        String str ="Execution stack:\n";
       /* MyTree<IStatement> stack_to_tree = new MyTree<>();
        stack_to_tree.setRoot(stackToTree(execution_stack.top().deepCopy()));*/
        return str+execution_stack.toString();
    }

    private Node<IStatement> stackToTree(IStatement curr_statement) {
        Node<IStatement> node = new Node<>(curr_statement);
        if (curr_statement instanceof CompStmt){
            node.setLeft(stackToTree(((CompStmt) curr_statement).getFirst().deepCopy()));
            node.setRight(stackToTree(((CompStmt) curr_statement).getSecond().deepCopy()));
        }
        return node;
    }

    @Override
    public void push(IStatement t) {
        execution_stack.push(t);
    }

    @Override
    public IStatement pop() {
        return execution_stack.pop();
    }

    @Override
    public IStatement top() {
        return execution_stack.top();
    }

    @Override
    public List<IStatement> getReversed() {
        return execution_stack.getReversed();
    }

    @Override
    public boolean isEmpty() {
        return execution_stack.isEmpty();
    }


}
