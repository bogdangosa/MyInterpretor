package Models.Statement;

import Containers.MyIDictionary;
import Exceptions.MyException;
import Models.Exp.Exp;
import Models.ProgramState.ProgramState;
import Models.ProgramState.SymbolTable;
import Models.Value.Value;

public class PrintStmt implements IStatement{
    Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable symbol_table = state.getSymbolTable();
        state.getOutput().add(this.exp.eval(symbol_table));
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "print(" + exp.toString() + ")";
    }
}
