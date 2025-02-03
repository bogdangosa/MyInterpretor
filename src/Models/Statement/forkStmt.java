package Models.Statement;

import Containers.MyIDictionary;
import Exceptions.MyException;
import Models.ProgramState.ExecutionStack;
import Models.ProgramState.ProgramState;
import Models.ProgramState.SymbolTable;
import Models.Type.Type;

public class forkStmt implements IStatement{
    IStatement stmt;

    public forkStmt(IStatement stmt) {
        this.stmt = stmt;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        return new ProgramState(new ExecutionStack(),
                        (SymbolTable) state.getSymbolTable().deepCopy(),
                        state.getOutput(),
                        state.getFileTable(),
                        state.getHeapTable(),
                        state.getSemaphoreTable(),stmt);
    }

    @Override
    public IStatement deepCopy() {
        return new forkStmt(stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        stmt.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork("+stmt+");";
    }
}
