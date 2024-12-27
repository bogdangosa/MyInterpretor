package Models.Exp;

import Containers.MyIDictionary;
import Exceptions.ExpressionException;
import Exceptions.MyException;
import Models.ProgramState.HeapTable;
import Models.ProgramState.SymbolTable;
import Models.Type.BoolType;
import Models.Type.IntType;
import Models.Type.Type;
import Models.Value.BoolValue;
import Models.Value.Value;

public class LogicExp implements Exp{
    static final String OR = "or";
    static final String AND = "and";

    Exp exp1;
    Exp exp2;
    String operator;

    public LogicExp(String operator,Exp exp1, Exp exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operator = operator;
    }

    @Override
    public Value eval(SymbolTable table, HeapTable heap) throws MyException {
        Value value1 = exp1.eval(table,heap);
        Value value2 = exp2.eval(table,heap);

        if(!value2.sameTypeAs(new BoolType()))
            throw new ExpressionException("operand1 is not a boolean");
        if(value1.sameTypeAs(new BoolType()))
            throw new ExpressionException("operand2 is not a boolean");

        Value result;
        if(operator.equals(OR)){
            result = new BoolValue(((BoolValue) value1).getValue()
                                    || ((BoolValue) value2).getValue() );
            return result;
        }
        result = new BoolValue(((BoolValue) value1).getValue()
                && ((BoolValue) value2).getValue() );

        return result;
    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(operator,exp1.deepCopy(),exp2.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1=exp1.typecheck(typeEnv);
        typ2=exp2.typecheck(typeEnv);
        if(!typ1.equals(new BoolType()) || !typ2.equals(new BoolType()))
            throw new MyException("operand is not a boolean");
        return new BoolType();
    }
}
