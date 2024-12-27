package View;

import Containers.*;
import Controller.Controller;
import Models.Exp.ArithExp;
import Models.Exp.ValueExp;
import Models.Exp.VarExp;
import Models.ProgramState.*;
import Models.Statement.*;
import Models.Type.BoolType;
import Models.Type.IntType;
import Models.Value.BoolValue;
import Models.Value.IntValue;
import Models.Value.StringValue;
import Models.Value.Value;
import Repository.IRepository;
import Repository.Repository;

import java.io.BufferedReader;

public class View {

    public static void main(String[] args) {

        IStatement ex1 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));
        IStatement ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new
                                ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new
                                        IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        IStatement ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));

        ExecutionStack stack = new ExecutionStack();
        SymbolTable symT = new SymbolTable();
        Output out = new Output();
        FileTable file_table = new FileTable();
        HeapTable heap_table = new HeapTable();
        ProgramState programstate = new ProgramState(stack, symT, out,file_table,heap_table, ex3);

        IRepository repo = new Repository("logs.txt");
        repo.add(programstate);
        Controller controller = new Controller(repo,true);
        controller.allStep();
    }
}
