package Repository;

import Exceptions.MyException;
import Models.ProgramState.ProgramState;
import Models.Value.Value;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Repository implements IRepository {

    private List<ProgramState> programStates;
    private String logFilePath;

    public Repository(String logFilePath) {
        this.programStates = new ArrayList<>();
        this.logFilePath = logFilePath;
    }


    @Override
    public ProgramState getCurrentProgram() {
        return programStates.getFirst();
    }

    @Override
    public void add(ProgramState state) {
        this.programStates.add(state);
    }

    @Override
    public void logProgramState(ProgramState currentProgram) throws MyException {
        //here you can display the prg state
        System.out.println(currentProgram.toString());
    }

    @Override
    public void setProgramList(List<ProgramState> newProgramList) {
        programStates = newProgramList;
    }

    @Override
    public List<ProgramState> getProgramList() {
        return programStates;
    }

    @Override
    public void logProgramStateExecToFile(ProgramState currentProgram) throws MyException, IOException {
        PrintWriter logFile= new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println("Program "+currentProgram.getId());
        logFile.println(currentProgram.getExecutionStack().toString());
        logFile.println(currentProgram.getSymbolTable().toString());
        logFile.println(currentProgram.getOutput().toString());
        logFile.println(currentProgram.getFileTable().toString());
        logFile.println(currentProgram.getHeapTable().toString());
        logFile.println("----------------------------------------");
        logFile.close();
    }
}
