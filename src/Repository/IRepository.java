package Repository;

import Exceptions.MyException;
import Models.ProgramState.ProgramState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    ProgramState getCurrentProgram();
    void add(ProgramState state);
    void logProgramState(ProgramState currentProgram) throws MyException;
    void setProgramList(List<ProgramState> newProgramList);
    List<ProgramState> getProgramList();
    void logProgramStateExecToFile(ProgramState currentProgram) throws MyException, IOException;
}
