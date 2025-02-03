package Models.ProgramState;


import Exceptions.ExecutionException;
import Exceptions.MyException;
import Models.Statement.IStatement;


public class ProgramState {

    private SymbolTable symbol_table;
    private ExecutionStack execution_stack;
    private Output output;
    private FileTable file_table;
    private HeapTable heap_table;
    private SemaphoreTable semaphore_table;
    private int id;
    public static int maxId = 1;

    private IStatement original_program;

    public ExecutionStack getExecutionStack() {
        return execution_stack;
    }

    public SymbolTable getSymbolTable() {
        return symbol_table;
    }

    public Output getOutput() {
        return output;
    }

    public IStatement getOriginalProgram(){
        return original_program;
    }

    public FileTable getFileTable() {return file_table;}

    public HeapTable getHeapTable() {return heap_table;}

    public SemaphoreTable getSemaphoreTable() {return semaphore_table;}

    public Boolean isNotCompleted() {
        return !execution_stack.isEmpty();
    }

    private static synchronized int initializeId(){
        return maxId++;
    }

    public int getId() {
        return id;
    }

    public ProgramState(ExecutionStack execution_stack, SymbolTable symbol_table, Output out,FileTable file_table,HeapTable heap_table,SemaphoreTable semaphore_table, IStatement prog) {
        this.execution_stack = execution_stack;
        this.symbol_table = symbol_table;
        this.output = out;
        this.file_table = file_table;
        this.heap_table = heap_table;
        this.semaphore_table = semaphore_table;
        this.id = initializeId();

        this.original_program = prog;

        this.execution_stack.push(prog);
    }

    public ProgramState oneStep() throws MyException{
        if(execution_stack.isEmpty())
            throw new MyException("prgstate stack is empty");

        IStatement  currentStatement = execution_stack.pop();
        return currentStatement.execute(this);
    }

    @Override
    public String toString() {
        return  "Program "+ this.id +
                "\n" + this.execution_stack.toString() +
                "\n" + this.symbol_table.toString() +
                "\n" + this.output.toString() +
                "\n" + this.file_table.toString() +
                "\n" + this.heap_table.toString();
    }

}


