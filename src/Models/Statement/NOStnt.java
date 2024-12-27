package Models.Statement;

import Containers.MyIDictionary;
import Exceptions.MyException;
import Models.ProgramState.ProgramState;
import Models.Type.Type;

public class NOStnt implements IStatement{

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NOStnt();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "";
    }
}
