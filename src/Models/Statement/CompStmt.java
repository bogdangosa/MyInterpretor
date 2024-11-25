package Models.Statement;

import Containers.MyIStack;
import Exceptions.MyException;
import Models.ProgramState.ExecutionStack;
import Models.ProgramState.ProgramState;

public class CompStmt implements IStatement {

    IStatement first, second;

    public CompStmt(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    public IStatement getFirst() {
        return first;
    }
    public IStatement getSecond() {
        return second;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        ExecutionStack stk=state.getExecutionStack();
        stk.push(second);
        stk.push(first);
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }

    @Override
    public String toString() {
        return first.toString() + "\n" + second.toString();
    }
}
