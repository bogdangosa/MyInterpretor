package Models.ProgramState;

import Containers.MyIDictionary;
import Containers.MyIList;
import Containers.MyIStack;
import Models.Statement.IStatement;
import Models.Value.StringValue;
import Models.Value.Value;

import java.io.BufferedReader;

public class ProgramState {

    private SymbolTable symbol_table;
    private ExecutionStack execution_stack;
    private Output output;
    private FileTable file_table;

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

    public FileTable getFileTable() {return file_table;}

    public ProgramState(ExecutionStack execution_stack, SymbolTable symbol_table, Output out,FileTable file_table, IStatement prog) {
        this.execution_stack = execution_stack;
        this.symbol_table = symbol_table;
        this.output = out;
        this.file_table = file_table;

        this.original_program = prog;

        this.execution_stack.push(prog);
    }

    @Override
    public String toString() {
        return this.execution_stack.toString() +
                "\n" + this.symbol_table.toString() +
                "\n" + this.output.toString() +
                "\n" + this.file_table.toString();
    }

}


