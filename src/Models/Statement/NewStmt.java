package Models.Statement;

import Containers.MyIDictionary;
import Exceptions.HeapTableException;
import Exceptions.MyException;
import Exceptions.SymbolTableException;
import Models.Exp.Exp;
import Models.ProgramState.HeapTable;
import Models.ProgramState.ProgramState;
import Models.ProgramState.SymbolTable;
import Models.Type.RefType;
import Models.Type.Type;
import Models.Value.RefValue;
import Models.Value.Value;

public class NewStmt implements IStatement{
    Exp exp;
    String var_name;


    public NewStmt(String var_name, Exp exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable symbol_table = state.getSymbolTable();
        HeapTable heap_table = state.getHeapTable();

        if(!symbol_table.isDefined(var_name))
            throw new SymbolTableException("the used variable" +var_name + " was not declared before");

        Value table_value = symbol_table.lookUp(var_name);
        Type typeId= table_value.getType();
        if (!(table_value.getType() instanceof RefType))
            throw new HeapTableException("Variable is not ref type!");
        RefType ref_type = (RefType)typeId;
        Value val = this.exp.eval(symbol_table,heap_table);

        if(!val.getType().equals(ref_type.getInner()))
            throw new HeapTableException("Variable ref is not the same type!");

        int address = heap_table.put(val);

        RefValue ref_table_value = (RefValue)table_value.deepCopy();
        ref_table_value.setAddress(address);
        symbol_table.update(var_name,ref_table_value);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewStmt(var_name, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookUp(var_name);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have " +
                    "different types ");
    }


    @Override
    public String toString() {
        return "new(" + var_name + ","+exp.toString()+")";
    }
}
