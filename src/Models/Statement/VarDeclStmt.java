package Models.Statement;

import Containers.MyIDictionary;
import Exceptions.MyException;
import Exceptions.SymbolTableException;
import Models.ProgramState.ProgramState;
import Models.ProgramState.SymbolTable;
import Models.Type.Type;
import Models.Value.Value;

public class VarDeclStmt implements IStatement{
    private String id;
    Type type;

    public VarDeclStmt(String id, Type type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable symbol_table = state.getSymbolTable();
        if(!symbol_table.isDefined(id)){
            symbol_table.put(id,type.defaultValue());
        }
        else throw new SymbolTableException("Variable was already declared!");
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new VarDeclStmt(id, type.deepCopy());
    }

    @Override
    public String toString(){
        return type + " " + id;
    }
}
