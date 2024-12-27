package Models.Exp;

import Containers.MyIDictionary;
import Exceptions.MyException;
import Models.ProgramState.HeapTable;
import Models.ProgramState.SymbolTable;
import Models.Type.BoolType;
import Models.Type.IntType;
import Models.Type.Type;
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
    public Value eval(SymbolTable table, HeapTable heap) throws MyException {
        Value val1 = exp1.eval(table,heap);
        Value val2 = exp2.eval(table,heap);
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
        return new RelationalExp(exp1.deepCopy(), exp2.deepCopy(), sign);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1=exp1.typecheck(typeEnv);
        typ2=exp2.typecheck(typeEnv);
        if(!typ1.equals(new IntType()) || !typ2.equals(new IntType()))
            throw new MyException("Wrong type of expression");
        return new BoolType();
    }

    @Override
    public String toString() {
        return exp1.toString() + sign + exp2.toString();
    }
}
