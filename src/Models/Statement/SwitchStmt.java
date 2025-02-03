package Models.Statement;

import Containers.MyIDictionary;
import Exceptions.MyException;
import Models.Exp.Exp;
import Models.Exp.RelationalExp;
import Models.ProgramState.ExecutionStack;
import Models.ProgramState.ProgramState;
import Models.Type.Type;

public class SwitchStmt implements IStatement {
    Exp exp1;
    Exp exp2;
    Exp exp;
    IStatement stmt1;
    IStatement stmt2;
    IStatement stmt_default;

    public SwitchStmt(Exp exp, Exp exp1, Exp exp2, IStatement stmt1, IStatement stmt2,IStatement stmt_default) {
        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.stmt_default = stmt_default;
    }


    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        ExecutionStack executionStack = state.getExecutionStack();
        executionStack.push(new IfStmt(
                new RelationalExp(exp,exp1,"=="),
                stmt1,
                new IfStmt(
                    new RelationalExp(exp,exp2,"=="),
                    stmt2,
                    stmt_default
                )
        ));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new SwitchStmt(exp, exp1, exp2, stmt1, stmt2, stmt_default);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        Type typexp1 = exp1.typecheck(typeEnv);
        Type typexp2= exp2.typecheck(typeEnv);
        if (!typexp.equals(typexp1))
            throw new MyException("Type mismatch");
        if (!typexp.equals(typexp2))
            throw new MyException("Type mismatch");
        stmt1.typecheck(typeEnv.deepCopy());
        stmt2.typecheck(typeEnv.deepCopy());
        stmt_default.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "switch("+exp+")\n" +
                "(case "+exp1 + ": "+ stmt1.toString() +")\n"+
                "(case "+exp2 + ": "+ stmt2.toString() +")\n"+
                "(default: "+ stmt_default.toString() +");";
    }
}
