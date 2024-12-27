package Models.Statement;

import Containers.MyIDictionary;
import Containers.MyIStack;
import Exceptions.ExpressionException;
import Exceptions.MyException;
import Exceptions.MyIOException;
import Exceptions.SymbolTableException;
import Models.Exp.Exp;
import Models.ProgramState.FileTable;
import Models.ProgramState.ProgramState;
import Models.ProgramState.SymbolTable;
import Models.Type.IntType;
import Models.Type.StringType;
import Models.Type.Type;
import Models.Value.IntValue;
import Models.Value.StringValue;
import Models.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStatement{
    Exp exp;
    private String id;

    public ReadFile(Exp exp, String id) {
        this.exp = exp;
        this.id = id;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable symbol_table = state.getSymbolTable();
        FileTable file_table = state.getFileTable();
        Value value = exp.eval(symbol_table,state.getHeapTable());

        if(!value.sameTypeAs(new StringType()))
            throw new ExpressionException(exp.toString()+" is not a string");
        StringValue file_name = (StringValue)value;
        if(!symbol_table.isDefined(id))
            throw new SymbolTableException(exp.toString()+" is not defined");
        if(!file_table.isDefined(file_name))
            throw new SymbolTableException(exp.toString()+" is not defined");
        BufferedReader reader = file_table.lookUp(file_name);
        try {
            IntValue intValue = new IntValue(Integer.parseInt(reader.readLine()));
            symbol_table.update(id, intValue);
        }catch (NumberFormatException e) {
            throw new MyIOException(exp.toString()+" is not a number");
        }
        catch (IOException e) {
            throw new MyIOException(e.toString());
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFile(exp, id);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeval = typeEnv.lookUp(id);
        Type typexp=exp.typecheck(typeEnv);
        if(!typexp.equals(new StringType()))
            throw new ExpressionException(exp.toString()+" is not a string");
        if(!typeval.equals(new IntType()))
            throw new ExpressionException("variable is not of int type");

        return typeEnv;
    }

    @Override
    public String toString() {
        return "ReadFile("+exp.toString()+","+id+")";
    }

}
