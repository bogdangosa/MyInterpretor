package Models.Statement;

import Containers.MyIDictionary;
import Exceptions.MyException;
import Models.Exp.Exp;
import Models.ProgramState.ProgramState;
import Models.ProgramState.SymbolTable;
import Models.Type.Type;
import Models.Value.Value;

public class PrintStmt implements IStatement{
    Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable symbol_table = state.getSymbolTable();
        state.getOutput().add(this.exp.eval(symbol_table,state.getHeapTable()));
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "print(" + exp.toString() + ")";
    }
}
