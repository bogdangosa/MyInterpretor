package Containers;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T>{
    protected List<T> list;

    public MyList() {
        list = new ArrayList<T>();
    }

    @Override
    public void add(T elem) {
        list.add(elem);
    }

    @Override
    public String toString() {
        return this.list.toString();
    }

    public List<T> getAll(){
        return list;
    }
}
