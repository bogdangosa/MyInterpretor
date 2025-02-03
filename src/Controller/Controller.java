package Controller;
import Exceptions.ExecutionException;
import Exceptions.MyException;
import Exceptions.MyIOException;
import Models.ProgramState.*;
import Models.Statement.IStatement;
import Models.Value.RefValue;
import Models.Value.Value;
import Repository.IRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repo;
    private boolean display_flag;
    ExecutorService executor;
    public static ReentrantLock lock;

    public Controller(IRepository repo, boolean display_flag) {
        this.repo = repo;
        this.display_flag = display_flag;
        lock = new ReentrantLock();
    }

    private ProgramState oneStep(ProgramState state) throws MyException {
        ExecutionStack stack=state.getExecutionStack();
        if(stack.isEmpty()) throw new ExecutionException("program state stack is empty");
        IStatement currentStatement = stack.pop();
        return currentStatement.execute(state);
    }

    public void displayState(ProgramState prg){
        if(display_flag) {
            repo.logProgramState(prg);
            try {
                repo.logProgramStateExecToFile(prg);
            }
            catch (IOException e) {
                throw new MyIOException(e.toString());
            }
        }
    }

    public void allStepOneProgram(){
        ProgramState prg = repo.getCurrentProgram();
        displayState(prg);
        while (!prg.getExecutionStack().isEmpty()){
            oneStep(prg);
            displayState(prg);
            prg.getHeapTable().setContent(safeGarbageCollector(
                    getAddrFromSymTable(prg.getSymbolTable().getContent().values()),
                    getAddrFromHeap(prg.getHeapTable().getContent().values()),
                    prg.getHeapTable().getContent()));

            /*prg.getHeapTable().setContent(unsafeGarbageCollector(
                    getAddrFromSymTable(prg.getSymbolTable().getContent().values()),
                    prg.getHeapTable().getContent()));*/

            displayState(prg);
        }
    }

    public void initialize(){
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<ProgramState>  prgList=removeCompletedPrograms(repo.getProgramList());
    }


    public void allStep(){
        //executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<ProgramState>  prgList=removeCompletedPrograms(repo.getProgramList());
        while(prgList.size() > 0){
            oneStepForAllPrg();
            prgList=removeCompletedPrograms(repo.getProgramList());
        }
        executor.shutdownNow();

    }

    public void oneStepForAllPrg(){

        //before the execution, print the PrgState List into the log file
        List<ProgramState>  prgList=repo.getProgramList();

        //remove the removeCompletedPrograms programs
        prgList=removeCompletedPrograms(repo.getProgramList());
        repo.setProgramList(prgList);

        prgList.forEach(this::displayState);
        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());

        List<ProgramState> newPrgList = null;
        try {
            newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try { return future.get();}
                        catch(InterruptedException e){
                            return null;
                            }
                        catch (java.util.concurrent.ExecutionException e){
                            Throwable cause = e.getCause();
                            if(cause instanceof MyIOException){
                                throw (MyIOException)cause;
                            }
                            else
                                throw new MyIOException(cause.toString());
                            }
                        }
                    ).filter(p -> p!=null)
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //add the new created threads to the list of existing threads
        prgList.addAll(newPrgList);
        repo.setProgramList(prgList);
        prgList.forEach(this::displayState);
    }

    Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value>
            heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr,
                                             List<Integer> heapTableAddr, Map<Integer,Value>
                                                     heap){

        Map<Integer,Value> garbage =  heap.entrySet().stream()
                .filter(e->!symTableAddr.contains(e.getKey()))
                .filter(e->!heapTableAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        for(Map.Entry<Integer,Value> garbage_addr : garbage.entrySet()){
            heap.remove(garbage_addr.getKey());
        }
        return heap;
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }

    List<Integer> getAddrFromHeap(Collection<Value> heapValues){
        return heapValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }

    List<ProgramState> removeCompletedPrograms(List<ProgramState> inPrgList){
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public int getNrOfProgramStates(){
        return repo.getProgramList().size();
    }


    public Output getOutput(){
        return repo.getCurrentProgram().getOutput();
    }

    public FileTable getFileTable(){
        return repo.getCurrentProgram().getFileTable();
    }

    public SemaphoreTable getSemaphoreTable(){
        return repo.getCurrentProgram().getSemaphoreTable();
    }

    public SymbolTable getSymbolTableOfProgramState(int programId){
        return repo.getCurrentProgram(programId).getSymbolTable();
    }

    public HeapTable getHeapTableOfProgramState(int programId){
        return repo.getCurrentProgram(programId).getHeapTable();
    }

    public ExecutionStack getExecutionStackOfProgramState(int programId){
        return repo.getCurrentProgram(programId).getExecutionStack();
    }

}

