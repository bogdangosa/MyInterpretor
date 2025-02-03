package Models.ProgramState;

import Containers.MyDictionary;
import Models.Value.Value;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class HeapTable implements IHeapTable {

    private MyDictionary<Integer, Value> heap_table;
    private static AtomicInteger max_free_adress;

    public  HeapTable(MyDictionary<Integer, Value> heap_table) {
        this.heap_table = heap_table;
        this.max_free_adress = new AtomicInteger(1);
    }

    public HeapTable(){
        this.heap_table = new MyDictionary<>();
        this.max_free_adress = new AtomicInteger(1);
    }

    @Override
    public String toString() {
        String str = "HeapTable:";
        for(Map.Entry<Integer, Value> entity: heap_table.getAll().entrySet()){
            str += "\n"+entity.getKey().toString() +" | "+entity.getValue().toString();
        }
        return str;
    }

    @Override
    public Integer put(Value value) {
        for(int i=1;i<max_free_adress.get();i++){
            if(!this.heap_table.isDefined(i)){
                this.heap_table.put(i, value);
                return i;
            }
        }
        heap_table.put(max_free_adress.get(), value);
        max_free_adress.incrementAndGet();
        return max_free_adress.get()-1;
    }

    @Override
    public Value lookUp(Integer key) {
        return heap_table.lookUp(key);
    }

    @Override
    public void update(Integer key, Value value) {
        heap_table.update(key, value);
    }

    @Override
    public boolean remove(Integer key) {
        return heap_table.remove(key);
    }

    @Override
    public boolean isDefined(Integer key) {
        return heap_table.isDefined(key);
    }

    @Override
    public void setContent(Map<Integer, Value> content) {
        heap_table.setContent(content);
    }

    @Override
    public Map<Integer, Value> getContent() {
        return this.heap_table.getContent();
    }
}
