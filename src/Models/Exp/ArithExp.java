package Models.Exp;

import Containers.MyIDictionary;
import Exceptions.ExpressionException;
import Exceptions.MyException;
import Models.ProgramState.HeapTable;
import Models.ProgramState.SymbolTable;
import Models.Type.IntType;
import Models.Type.Type;
import Models.Value.IntValue;
import Models.Value.Value;

public class ArithExp implements Exp{
    static final char PLUS_SIGN = '+';
    static final char MINUS_SIGN = '-';
    static final char STAR_SIGN = '*';
    static final char DIVIDE_SIGN = '/';

    Exp exp1;
    Exp exp2;
    char operator;

    public ArithExp(char operator, Exp exp1, Exp exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operator = operator;
    }

    @Override
    public Value eval(SymbolTable table, HeapTable heap) throws MyException {
        Value v1,v2;
        v1 = exp1.eval(table,heap);
        v2 = exp2.eval(table,heap);

        if(!v1.sameTypeAs(new IntType()))
            throw new ExpressionException("first operand is not an integer");
        if(!v1.sameTypeAs(new IntType()))
            throw new ExpressionException("second operand is not an integer");

        IntValue i1 = (IntValue)v1;
        IntValue i2 = (IntValue)v2;

        int n1,n2;
        n1= i1.getValue();
        n2 = i2.getValue();
        switch (operator){
            case PLUS_SIGN:
                return new IntValue(n1+n2);
            case MINUS_SIGN:
                return new IntValue(n1-n2);
            case STAR_SIGN:
                return new IntValue(n1*n2);
            case DIVIDE_SIGN:
                if(n2==0) throw new ExpressionException("division by zero");
                else  return new IntValue(n1/n2);
        }
        return null;
    }

    @Override
    public Exp deepCopy() {
        return new ArithExp(operator,exp1.deepCopy(),exp2.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1=exp1.typecheck(typeEnv);
        typ2=exp2.typecheck(typeEnv);
        if(!typ1.equals(new IntType()))
            throw new MyException("first operand is not an integer");
        if(!typ2.equals(new IntType()))
            throw new MyException("second operand is not an integer");
        return new IntType();
    }

    @Override
    public String toString() {
        return exp1.toString()+operator+exp2.toString();
    }
}