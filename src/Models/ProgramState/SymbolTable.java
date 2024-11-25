package Models.ProgramState;

import Containers.MyDictionary;
import Models.Value.Value;

import java.util.Map;


public class SymbolTable implements ISymbolTable {
    MyDictionary<String,Value> map;

    public SymbolTable() {
        map = new MyDictionary<>();
    }


    @Override
    public String toString() {
        String str = "SymbolTable:";
        for(Map.Entry<String,Value> entity: map.getAll().entrySet()){
            str += "\n"+entity.getKey() +"-->"+entity.getValue().toString();
        }
        return str;
    }

    @Override
    public void put(String key, Value value) {
        map.put(key, value);
    }

    @Override
    public Value lookUp(String key) {
        return map.lookUp(key);
    }

    @Override
    public void update(String key, Value value) {
        map.update(key, value);
    }

    @Override
    public boolean remove(String key) {
        return map.remove(key);
    }

    @Override
    public boolean isDefined(String key) {
        return map.isDefined(key);
    }
}
