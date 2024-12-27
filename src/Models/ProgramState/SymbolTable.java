package Models.ProgramState;

import Containers.MyDictionary;
import Models.Value.Value;

import java.util.Map;


public class SymbolTable implements ISymbolTable {
    MyDictionary<String,Value> map;

    public SymbolTable() {
        map = new MyDictionary<>();
    }

    public SymbolTable(MyDictionary<String,Value> map) {
        this.map = map;
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

    @Override
    public void setContent(Map<String, Value> content) {
        map.setContent(content);
    }

    @Override
    public Map<String, Value> getContent() {
        return this.map.getContent();
    }

    @Override
    public ISymbolTable deepCopy(){
        return new SymbolTable((MyDictionary)map.deepCopy());
    }
}
