package Controller;

import Containers.MyIStack;
import Exceptions.ExecutionException;
import Exceptions.MyException;
import Exceptions.MyIOException;
import Models.ProgramState.ExecutionStack;
import Models.ProgramState.ProgramState;
import Models.Statement.IStatement;
import Repository.IRepository;

import java.io.IOException;

public class Controller {
    private IRepository repo;
    private boolean display_flag;

    public Controller(IRepository repo, boolean display_flag) {
        this.repo = repo;
        this.display_flag = display_flag;
    }

    private ProgramState oneStep(ProgramState state) throws MyException {
        ExecutionStack stack=state.getExecutionStack();
        if(stack.isEmpty()) throw new ExecutionException("prgstate stack is empty");
        IStatement currentStatement = stack.pop();
        return currentStatement.execute(state);
    }
    public void allStep(){
        ProgramState prg = repo.getCurrentProgram();

        if(display_flag) {
            repo.logProgramState();
            try {
                repo.logProgramStateExecToFile();
            }
            catch (IOException e) {
                throw new MyIOException(e.toString());
            }
        }
        while (!prg.getExecutionStack().isEmpty()){
            oneStep(prg);

            if(display_flag) {
                repo.logProgramState();
                try {
                    repo.logProgramStateExecToFile();
                }
                catch (IOException e) {
                    throw new MyIOException(e.toString());
                }
            }
        }
    }
}
