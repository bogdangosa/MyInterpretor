package Repository;

import Exceptions.MyException;
import Models.ProgramState.ProgramState;

import java.io.IOException;

public interface IRepository {

    ProgramState getCurrentProgram();

    void add(ProgramState state);

    void logProgramState() throws MyException;

    void logProgramStateExecToFile() throws MyException, IOException;
}
