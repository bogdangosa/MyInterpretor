package Models.ProgramState;

import Models.Value.Value;

public interface ISymbolTable {
    void put(String key, Value value);
    Value lookUp(String key);
    void update(String key, Value value);
    boolean remove(String key);
    boolean isDefined(String key);
}
