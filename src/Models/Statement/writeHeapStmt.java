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

public class writeHeapStmt implements IStatement {
    Exp exp;
    String var_name;

    public writeHeapStmt(String var_name, Exp exp) {
        this.exp = exp;
        this.var_name = var_name;
    }


    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable symbol_table = state.getSymbolTable();
        HeapTable heap_table = state.getHeapTable();

        if(!symbol_table.isDefined(var_name))
            throw new SymbolTableException("Variable '" + var_name + "' not found");

        Value val = symbol_table.lookUp(var_name);
        if(!(val instanceof RefValue))
            throw new HeapTableException("Variable '" + var_name + "' is not a reference");

        RefValue ref_val = (RefValue)val;

        if(!heap_table.isDefined(ref_val.getAddr()))
            throw new HeapTableException("address '" + ref_val.getAddr() + "' is not defined");

        Value exp_val = exp.eval(symbol_table, heap_table);

        if(!exp_val.sameTypeAs(ref_val.getInnerType()))
            throw new HeapTableException("Type mismatch");

        heap_table.update(ref_val.getAddr(), exp_val);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new writeHeapStmt(var_name, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookUp(var_name);
        Type typexp = exp.typecheck(typeEnv);
        if(!(typevar instanceof RefType))
            throw new HeapTableException("Variable '" + var_name + "' is not a reference");
        if(!((RefType) typevar).getInner().equals(typexp))
            throw new HeapTableException("Type mismatch");

        return typeEnv;
    }

    @Override
    public String toString() {
        return "wh("+var_name+","+exp.toString()+")";
    }
}
