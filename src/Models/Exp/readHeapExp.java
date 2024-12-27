package Models.Exp;

import Containers.MyIDictionary;
import Exceptions.ExecutionException;
import Exceptions.HeapTableException;
import Exceptions.MyException;
import Models.ProgramState.HeapTable;
import Models.ProgramState.SymbolTable;
import Models.Type.RefType;
import Models.Type.Type;
import Models.Value.RefValue;
import Models.Value.Value;

public class readHeapExp implements Exp {
    Exp exp;

    public readHeapExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public Value eval(SymbolTable table,HeapTable heap) throws MyException {
        Value val = this.exp.eval(table,heap);

        if(!(val instanceof RefValue))
            throw new ExecutionException("Expression is not a reference");

        RefValue ref_value = (RefValue)val;
        if(!heap.isDefined(ref_value.getAddr()))
            throw new HeapTableException("Address is not defined!");

        return heap.lookUp(ref_value.getAddr());

    }

    @Override
    public Exp deepCopy() {
        return new readHeapExp(exp.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ=exp.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }

    @Override
    public String toString() {
        return "rh("+exp.toString()+")";
    }
}
