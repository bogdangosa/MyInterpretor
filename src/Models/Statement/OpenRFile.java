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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStatement{
    Exp exp;

    public OpenRFile(Exp exp){
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable symbol_table = state.getSymbolTable();
        FileTable file_table = state.getFileTable();

        Value value = exp.eval(symbol_table);
        if(!value.sameTypeAs(new StringType()))
            throw new MyException("File name is not string!");
        if(file_table.isDefined((StringValue) value))
            throw new MyException("File name already exists!");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(((StringValue) value).getValue()));
            file_table.put((StringValue) value, reader);
        } catch (FileNotFoundException e) {
            throw new MyIOException("File not found!");
        }

        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenRFile(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "OpenRFile("+exp.toString()+")";
    }
}
