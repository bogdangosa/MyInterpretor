package Models.Statement;

import Containers.MyIDictionary;
import Exceptions.ExpressionException;
import Exceptions.MyException;
import Models.Exp.Exp;
import Models.ProgramState.ExecutionStack;
import Models.ProgramState.ProgramState;
import Models.ProgramState.SymbolTable;
import Models.Type.BoolType;
import Models.Type.Type;
import Models.Value.BoolValue;
import Models.Value.Value;

public class WhileStmt implements IStatement{
    Exp exp;
    IStatement stmt;

    public WhileStmt(Exp exp, IStatement stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        ExecutionStack stk=state.getExecutionStack();
        SymbolTable symbol_table = state.getSymbolTable();
        Value val = exp.eval(symbol_table,state.getHeapTable());

        if(!val.sameTypeAs(new BoolType()))
            throw new ExpressionException("Expression is not boolean");

        if(((BoolValue)val).getValue()){
            stk.push(this.deepCopy());
            stk.push(stmt);
        }


        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStmt(exp.deepCopy(), stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=exp.typecheck(typeEnv);
        if(!typexp.equals(new BoolType()))
            throw new ExpressionException("Expression is not boolean");
        stmt.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "while("+exp.toString()+"){\n"+stmt.toString()+"}";
    }
}
