package Models.Statement;

import Containers.MyIDictionary;
import Exceptions.MyException;
import Exceptions.MyIOException;
import Models.Exp.Exp;
import Models.ProgramState.FileTable;
import Models.ProgramState.ProgramState;
import Models.ProgramState.SymbolTable;
import Models.Type.StringType;
import Models.Value.StringValue;
import Models.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStatement{
    Exp exp;

    public CloseRFile(Exp exp){
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable symbol_table = state.getSymbolTable();
        FileTable file_table = state.getFileTable();
        Value value = exp.eval(symbol_table);
        if(!value.sameTypeAs(new StringType()))
            throw new MyException("File name is not string!");
        StringValue file_name = (StringValue)value;
        if(!file_table.isDefined(file_name))
            throw new MyException(exp.toString()+" is not defined");
        BufferedReader reader = file_table.lookUp(file_name);
        try {
            reader.close();
        } catch (IOException e) {
            throw new MyIOException(e.toString());
        }
        file_table.remove(file_name);
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseRFile(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "CloseFile("+exp.toString()+")";
    }
}
