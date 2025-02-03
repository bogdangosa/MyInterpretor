package Models.ProgramState;

import Models.Value.Value;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface IHeapTable {
    Integer put(Value value);
    Value lookUp(Integer key);
    void update(Integer key, Value value);
    boolean remove(Integer key);
    boolean isDefined(Integer key);
    void setContent(Map<Integer, Value> content);
    Map<Integer, Value> getContent();

}
