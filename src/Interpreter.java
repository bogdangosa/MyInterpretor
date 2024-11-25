import Containers.*;
import Controller.Controller;
import Models.Command.ExitCommand;
import Models.Command.RunCommand;
import Models.Exp.ArithExp;
import Models.Exp.ValueExp;
import Models.Exp.VarExp;
import Models.ProgramState.*;
import Models.Statement.*;
import Models.Type.BoolType;
import Models.Type.IntType;
import Models.Type.StringType;
import Models.Value.BoolValue;
import Models.Value.IntValue;
import Models.Value.StringValue;
import Models.Value.Value;
import Repository.IRepository;
import Repository.Repository;
import View.TextMenu;

import java.io.BufferedReader;

public class Interpreter {

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

        IStatement ex4 = new CompStmt(new VarDeclStmt("varf",new StringType()),new CompStmt(
                new AssignStmt("varf",new ValueExp(new StringValue("test.in"))),new CompStmt(
                new OpenRFile(new VarExp("varf")),new CompStmt(
                new VarDeclStmt("varc",new IntType()),new CompStmt(
                new ReadFile(new VarExp("varf"),"varc"),new CompStmt(
                new PrintStmt(new VarExp("varc")),new CompStmt(
                new ReadFile(new VarExp("varf"),"varc"),new CompStmt(
                new PrintStmt(new VarExp("varc")),
                new CloseRFile(new VarExp("varf"))
                ))))))));

        Controller ctr1 = createProgramController(ex1,"logs1.txt");
        Controller ctr2 = createProgramController(ex2,"logs2.txt");
        Controller ctr3 = createProgramController(ex3,"logs3.txt");
        Controller ctr4 = createProgramController(ex4,"logs4.txt");
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunCommand("1","Example 1",ctr1));
        menu.addCommand(new RunCommand("2","Example 2",ctr2));
        menu.addCommand(new RunCommand("3","Example 3",ctr3));
        menu.addCommand(new RunCommand("4","Example 4",ctr4));
        menu.show();
    }

    private static Controller createProgramController(IStatement ex,String log_file_name){
        ExecutionStack stack = new ExecutionStack();
        SymbolTable symT = new SymbolTable();
        Output out = new Output();
        FileTable file_table = new FileTable();
        ProgramState programstate = new ProgramState(stack, symT, out,file_table, ex);
        IRepository repo = new Repository(log_file_name);
        repo.add(programstate);
        return new Controller(repo,true);
    }


}
