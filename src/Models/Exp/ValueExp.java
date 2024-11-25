package Models.Exp;

import Containers.MyIDictionary;
import Exceptions.MyException;
import Models.ProgramState.SymbolTable;
import Models.Value.Value;

public class ValueExp implements Exp{
    Value value;

    public ValueExp(Value value) {
        this.value = value;
    }

    @Override
    public Value eval(SymbolTable table) throws MyException {
        return value;
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
