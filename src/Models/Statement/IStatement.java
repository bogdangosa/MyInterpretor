package Models.Statement;

import Exceptions.MyException;
import Models.ProgramState.ProgramState;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException;
    IStatement deepCopy();
}
