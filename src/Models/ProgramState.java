package Models;

import Containers.MyIList;
import Containers.MyIStack;
import Models.Statement.IStatement;
import Models.Value.IValue;

public class ProgramState {

    private MyIStack<IStatement> execution_stack;
    private MyIList<IValue> output;


}


