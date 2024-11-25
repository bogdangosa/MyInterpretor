package Models.Exp;

import Containers.MyIDictionary;
import Exceptions.MyException;
import Models.ProgramState.SymbolTable;
import Models.Value.Value;

public interface Exp {
    Value eval(SymbolTable table) throws MyException;
    Exp deepCopy();
}
