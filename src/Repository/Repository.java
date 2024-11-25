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
        return this.programStates.get(0);
    }

    @Override
    public void add(ProgramState state) {
        this.programStates.add(state);
    }

    @Override
    public void logProgramState() throws MyException {
        //here you can display the prg state
        System.out.println(getCurrentProgram().toString());
    }

    @Override
    public void logProgramStateExecToFile() throws MyException, IOException {
        ProgramState currentProgram = getCurrentProgram();
        PrintWriter logFile= new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(currentProgram.getExecutionStack().toString());
        logFile.println(currentProgram.getSymbolTable().toString());
        logFile.println(currentProgram.getOutput().toString());
        logFile.println(currentProgram.getFileTable().toString());
        logFile.println("----------------------------------------");
        logFile.close();
    }
}
