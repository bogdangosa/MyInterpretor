package Models.Exp;

import Containers.MyIDictionary;
import Exceptions.MyException;
import Models.ProgramState.HeapTable;
import Models.ProgramState.SymbolTable;
import Models.Type.Type;
import Models.Value.Value;

public class ValueExp implements Exp{
    Value value;

    public ValueExp(Value value) {
        this.value = value;
    }

    @Override
    public Value eval(SymbolTable table, HeapTable heap) throws MyException {
        return value;
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(value);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
