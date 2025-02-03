package Models.Statement;

import Containers.MyIDictionary;
import Controller.Controller;
import Exceptions.MyException;
import Exceptions.SemaphoreException;
import Exceptions.SymbolTableException;
import Models.ProgramState.ProgramState;
import Models.ProgramState.SemaphoreTable;
import Models.ProgramState.SymbolTable;
import Models.Type.IntType;
import Models.Type.Type;
import Models.Value.IntValue;
import Models.Value.Value;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class AcquireStmt implements IStatement{
    String var_name;

    public AcquireStmt(String var_name) {
        this.var_name = var_name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable symbolTable = state.getSymbolTable();
        if(!symbolTable.isDefined(var_name))
            throw new SymbolTableException("Variable '" + var_name + "' not found");
        Value value = symbolTable.lookUp(var_name);
        if(!value.sameTypeAs(new IntType()))
            throw new SemaphoreException("Variable '" + var_name + "' is not of type int");
        IntValue sem_index = (IntValue)value;
        Controller.lock.lock();
        try {
            SemaphoreTable semaphoreTable = state.getSemaphoreTable();
            if (!semaphoreTable.isDefined(sem_index.getValue()))
                throw new SemaphoreException("Variable '" + var_name + "' is not a semaphore variable");
            Pair<IntValue, List<IntValue>> sem_data = semaphoreTable.lookUp(sem_index.getValue());
            Integer sem_size = sem_data.getKey().getValue();
            List<IntValue> sem_indexes = sem_data.getValue();
            if (sem_size > sem_indexes.size()) {
                if (sem_indexes.contains(state.getId()))
                    return null;
                semaphoreTable.addProgramStateId(sem_index.getValue(), state.getId());
            } else
                state.getExecutionStack().push(new AcquireStmt(var_name));
        }finally {
            Controller.lock.unlock();
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AcquireStmt(var_name);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.lookUp(var_name);
        if(!varType.equals(new IntType()))
            throw new MyException("Variable '" + var_name + "' is not an Integer");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "acquire(" + var_name +")";
    }
}
