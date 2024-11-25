package Models.Statement;

import Containers.MyIDictionary;
import Containers.MyIStack;
import Exceptions.MyException;
import Models.Exp.Exp;
import Models.ProgramState.ExecutionStack;
import Models.ProgramState.ProgramState;
import Models.ProgramState.SymbolTable;
import Models.Type.BoolType;
import Models.Value.BoolValue;
import Models.Value.Value;

public class IfStmt  implements IStatement{
    Exp exp;
    IStatement thenS;
    IStatement elseS;

    public IfStmt(Exp exp, IStatement thenS, IStatement elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        ExecutionStack stack=state.getExecutionStack();
        SymbolTable symbol_table = state.getSymbolTable();
        Value value = exp.eval(symbol_table);
        if(value.sameTypeAs(new BoolType())) {
            BoolValue boolValue=(BoolValue)value;
            if(boolValue.getValue())
                stack.push(thenS);
            else
                stack.push(elseS);
        }
        else throw new MyException("If statement condition is not valid!");
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
    }

    @Override
    public String toString(){ return "if("+ exp.toString()+")\nthen{\n" +thenS.toString()
            +"\n}\nelse{\n"+elseS.toString()+"\n}";}
}