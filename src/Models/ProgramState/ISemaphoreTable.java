package Models.ProgramState;

import Models.Value.IntValue;
import Models.Value.Value;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface ISemaphoreTable {
    Integer put(IntValue value);
    Pair<IntValue, List<IntValue>> lookUp(Integer key);
    void update(Integer key, Pair<IntValue, List<IntValue>> value);
    void addProgramStateId(Integer key,Integer programStateId);
    void removeProgramStateId(Integer key,Integer programStateId);
    boolean remove(Integer key);
    boolean isDefined(Integer key);
    void setContent(Map<Integer, Pair<IntValue, List<IntValue>>> content);
    Map<Integer, Pair<IntValue, List<IntValue>>> getContent();

}
