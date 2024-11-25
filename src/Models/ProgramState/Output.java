package Models.ProgramState;

import Containers.MyList;
import Models.Value.Value;

public class Output implements IOutput {
    MyList<Value> list;

    public Output() {
        this.list = new MyList<Value>();
    }

    @Override
    public String toString() {
        String str = "Output:";
        for(Value val: list.getAll()){
            str += '\n'+ val.toString();
        }
        return str;
    }

    @Override
    public void add(Value elem) {
        list.add(elem);
    }
}
