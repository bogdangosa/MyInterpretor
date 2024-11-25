package Models.Statement;

import Exceptions.MyException;
import Models.ProgramState.ProgramState;

public class NOStnt implements IStatement{

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new NOStnt();
    }

    @Override
    public String toString() {
        return "";
    }
}
