package Models.Exp;

import Containers.MyIDictionary;
import Exceptions.MyException;
import Exceptions.SymbolTableException;
import Models.ProgramState.SymbolTable;
import Models.Value.Value;

public class VarExp implements Exp{
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public Value eval(SymbolTable table) throws MyException {
        Value val = table.lookUp(id);
        if (val == null)
            throw new SymbolTableException("Variable "+id+" not found!");
        return table.lookUp(id);
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(id);
    }

    @Override
    public String toString() {
        return id;
    }

}
