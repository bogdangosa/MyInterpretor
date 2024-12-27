package Models.Statement;

import Containers.MyIDictionary;
import Containers.MyIStack;
import Exceptions.MyException;
import Models.ProgramState.ExecutionStack;
import Models.ProgramState.ProgramState;
import Models.Type.Type;

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
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public String toString() {
        return first.toString() + "\n" + second.toString();
    }
}
