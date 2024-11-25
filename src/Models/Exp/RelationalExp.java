package Models.Exp;

import Containers.MyIDictionary;
import Exceptions.MyException;
import Models.ProgramState.SymbolTable;
import Models.Value.BoolValue;
import Models.Value.IntValue;
import Models.Value.Value;

public class RelationalExp implements Exp{
    Exp exp1, exp2;
    String sign;


    public RelationalExp(Exp exp1, Exp exp2,String sign) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.sign = sign;
    }

    @Override
    public Value eval(SymbolTable table) throws MyException {
        Value val1 = exp1.eval(table);
        Value val2 = exp2.eval(table);
        if (!(val1 instanceof IntValue) || !(val2 instanceof IntValue))
            throw new MyException("Wrong type of expression");
        int int1 = ((IntValue) val1).getValue();
        int int2 =  ((IntValue) val2).getValue();
        BoolValue result = new BoolValue();
        switch (sign){
            case "<":
                result = new BoolValue(int1 < int2);
                break;
            case "<=":
                result = new BoolValue(int1 <= int2);
                break;
            case ">":
                result = new BoolValue(int1 > int2);
                break;
            case ">=":
                result = new BoolValue(int1 >= int2);
                break;
            case "!=":
                result = new BoolValue(int1 != int2);
                break;
            case "==":
                result = new BoolValue(int1 == int2);
                break;
        }

        return result;
    }

    @Override
    public Exp deepCopy() {
        return null;
    }
}
