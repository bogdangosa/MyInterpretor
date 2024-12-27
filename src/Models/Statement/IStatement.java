package Models.Statement;

import Containers.MyIDictionary;
import Exceptions.MyException;
import Models.ProgramState.ProgramState;
import Models.Type.Type;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException;
    IStatement deepCopy();
    MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws
            MyException;
}
