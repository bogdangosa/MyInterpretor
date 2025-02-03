package Models.ProgramState;

import Models.Value.Value;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface ISymbolTable {
    void put(String key, Value value);
    Value lookUp(String key);
    void update(String key, Value value);
    boolean remove(String key);
    boolean isDefined(String key);
    void setContent(Map<String, Value> content);
    Map<String, Value> getContent();
    List<Pair<String,String>> ToList();
    ISymbolTable deepCopy();
}
