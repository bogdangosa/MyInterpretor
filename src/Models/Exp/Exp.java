package Models.Exp;

import Containers.MyIDictionary;
import Exceptions.MyException;
import Models.ProgramState.HeapTable;
import Models.ProgramState.SymbolTable;
import Models.Type.Type;
import Models.Value.Value;

public interface Exp {
    Value eval(SymbolTable table, HeapTable heap) throws MyException;
    Exp deepCopy();
    Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException;
}
