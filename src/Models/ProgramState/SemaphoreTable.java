package Models.ProgramState;

import Containers.MyDictionary;
import Models.Value.IntValue;
import Models.Value.Value;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreTable implements ISemaphoreTable{

    private MyDictionary<Integer, Pair<IntValue, List<IntValue>>> semaphore_table;
    private static AtomicInteger max_free_adress;

    public  SemaphoreTable(MyDictionary<Integer, Pair<IntValue, List<IntValue>>> semaphore_table) {
        this.semaphore_table = semaphore_table;
        this.max_free_adress = new AtomicInteger(1);
    }

    public SemaphoreTable(){
        this.semaphore_table = new MyDictionary<>();
        this.max_free_adress = new AtomicInteger(1);
    }

    @Override
    public String toString() {
        String str = "SemaphoreTable:";
        for(Map.Entry<Integer, Pair<IntValue, List<IntValue>>> entity: semaphore_table.getAll().entrySet()){
            str += "\n"+entity.getKey().toString() +" | "+entity.getValue().toString();
        }
        return str;
    }

    @Override
    public Integer put(IntValue value) {
        for(int i=1;i<max_free_adress.get();i++){
            if(!this.semaphore_table.isDefined(i)){
                this.semaphore_table.put(i, new Pair<>(value, new ArrayList<>()));
                return i;
            }
        }
        semaphore_table.put(max_free_adress.get(), new Pair<>(value, new ArrayList<>()));
        max_free_adress.incrementAndGet();
        return max_free_adress.get()-1;
    }

    @Override
    public Pair<IntValue, List<IntValue>> lookUp(Integer key) {
        return semaphore_table.lookUp(key);
    }

    @Override
    public void update(Integer key, Pair<IntValue, List<IntValue>> value) {
        semaphore_table.update(key, value);
    }

    @Override
    public void addProgramStateId(Integer key,Integer programStateId) {
        ArrayList<IntValue> new_list = new ArrayList<>(semaphore_table.lookUp(key).getValue());
        new_list.add(new IntValue(programStateId));
        Integer sem_size = semaphore_table.lookUp(key).getKey().getValue();
        semaphore_table.update(key,new Pair<>(new IntValue(sem_size),new_list));
        System.out.println(semaphore_table);
    }

    @Override
    public void removeProgramStateId(Integer key, Integer programStateId) {
        ArrayList<IntValue> new_list = new ArrayList<>(semaphore_table.lookUp(key).getValue());
        new_list.remove(new IntValue(programStateId));
        Integer sem_size = semaphore_table.lookUp(key).getKey().getValue();
        semaphore_table.update(key,new Pair<>(new IntValue(sem_size),new_list));
        System.out.println(semaphore_table);
    }

    @Override
    public boolean remove(Integer key) {
        return semaphore_table.remove(key);
    }

    @Override
    public boolean isDefined(Integer key) {
        return semaphore_table.isDefined(key);
    }

    @Override
    public void setContent(Map<Integer, Pair<IntValue, List<IntValue>>> content) {
        semaphore_table.setContent(content);
    }

    @Override
    public Map<Integer, Pair<IntValue, List<IntValue>>> getContent() {
        System.out.println(semaphore_table.getContent());
        System.out.println(semaphore_table);
        return this.semaphore_table.getContent();
    }
}
