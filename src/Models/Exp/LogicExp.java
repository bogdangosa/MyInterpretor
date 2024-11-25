package Models.Exp;

import Containers.MyIDictionary;
import Exceptions.ExpressionException;
import Exceptions.MyException;
import Models.ProgramState.SymbolTable;
import Models.Type.BoolType;
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
    public Value eval(SymbolTable table) throws MyException {
        Value value1 = exp1.eval(table);
        Value value2 = exp2.eval(table);

        if(!value2.sameTypeAs(new BoolType()))
            throw new ExpressionException("operand1 is not a boolean");
        if(value1.sameTypeAs(new BoolType()))
            throw new ExpressionException("operand1 is not a boolean");

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
}
