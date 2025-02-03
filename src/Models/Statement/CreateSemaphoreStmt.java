package Models.Statement;

import Containers.MyIDictionary;
import Controller.Controller;
import Exceptions.MyException;
import Exceptions.SemaphoreException;
import Exceptions.SymbolTableException;
import Models.Exp.Exp;
import Models.ProgramState.ProgramState;
import Models.ProgramState.SemaphoreTable;
import Models.ProgramState.SymbolTable;
import Models.Type.IntType;
import Models.Type.Type;
import Models.Value.IntValue;
import Models.Value.Value;

public class CreateSemaphoreStmt implements IStatement {
    String var_name;
    Exp exp;

    public CreateSemaphoreStmt(String var_name, Exp exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable symbolTable = state.getSymbolTable();
        if(!symbolTable.isDefined(var_name))
            throw new SymbolTableException("Variable '" + var_name + "' not found");
        Value value = exp.eval(symbolTable,state.getHeapTable());
        if(!value.sameTypeAs(new IntType()))
            throw new SemaphoreException("Variable '" + var_name + "' is not an Integer");

        IntValue sem_size = (IntValue)value;
        Controller.lock.lock();
        try {
            SemaphoreTable semaphoreTable = state.getSemaphoreTable();
            Integer new_free_location = semaphoreTable.put(sem_size);
            symbolTable.update(var_name, new IntValue(new_free_location));
        } finally {
            Controller.lock.unlock();
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CreateSemaphoreStmt(var_name, exp);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expType = exp.typecheck(typeEnv);
        if(!expType.equals(new IntType()))
            throw new MyException("Expression '" + exp + "' is not an Integer");
        Type varType = typeEnv.lookUp(var_name);
        if(!varType.equals(new IntType()))
            throw new MyException("Variable '" + var_name + "' is not an Integer");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "createSemaphore(" + var_name + ", " + exp + ")";
    }
}
