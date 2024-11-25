package Models.ProgramState;

import Models.Statement.IStatement;

import java.util.List;

public interface IExecutionStack {
    void push(IStatement t);
    IStatement pop();
    IStatement top();
    List<IStatement> getReversed();
    boolean isEmpty();
}
