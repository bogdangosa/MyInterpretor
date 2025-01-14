package Models.Statement;

import Containers.MyIDictionary;
import Containers.MyIStack;
import Exceptions.ExpressionException;
import Exceptions.MyException;
import Exceptions.SymbolTableException;
import Models.Exp.Exp;
import Models.ProgramState.ExecutionStack;
import Models.ProgramState.ProgramState;
import Models.ProgramState.SymbolTable;
import Models.Type.Type;
import Models.Value.Value;

public class AssignStmt implements IStatement {
    String id;
    Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable symbol_table = state.getSymbolTable();
        if(symbol_table.isDefined(id)){
            Value val = this.exp.eval(symbol_table,state.getHeapTable());
            Type typeId= (symbol_table.lookUp(id)).getType();
            if(val.getType().toString().equals(typeId.toString())) {
                symbol_table.update(id, val);
            }
            else throw new ExpressionException("declared type of variable"+id+" and type of " +
                    "the assigned expression do not match");
        }
        else throw new SymbolTableException("the used variable" +id + " was not declared before");
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStmt(id, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookUp(id);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return this.id + " = " + this.exp.toString();
    }
}
