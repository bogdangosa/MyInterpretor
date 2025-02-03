package Models.ProgramState;

import Models.Value.StringValue;

import java.io.BufferedReader;
import java.util.List;

public interface IFileTable {
    void put(StringValue key, BufferedReader value);
    BufferedReader lookUp(StringValue key);
    void update(StringValue key, BufferedReader value);
    boolean remove(StringValue key);
    boolean isDefined(StringValue key);
    List<String> toList();
}
