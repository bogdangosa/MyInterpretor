import Containers.MyDictionary;
import Containers.MyIDictionary;
import Controller.Controller;
import Models.Command.ExitCommand;
import Models.Command.RunCommand;
import Models.Exp.*;
import Models.ProgramState.*;
import Models.Statement.*;
import Models.Type.*;
import Models.Value.BoolValue;
import Models.Value.IntValue;
import Models.Value.StringValue;
import Repository.IRepository;
import Repository.Repository;
import View.TextMenu;

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

        //   Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStatement ex5 = new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),new CompStmt(
                new NewStmt("v",new ValueExp(new StringValue("20"))),new CompStmt(
                new VarDeclStmt("a",new RefType(new RefType(new IntType()))),new CompStmt(
                new NewStmt("a",new VarExp("v")),new CompStmt(
                new PrintStmt(new VarExp("v")),new PrintStmt(new VarExp("a"))
                )))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStatement ex6 = new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),new CompStmt(
                new NewStmt("v",new ValueExp(new IntValue(20))),new CompStmt(
                new VarDeclStmt("a",new RefType(new RefType(new IntType()))),new CompStmt(
                new NewStmt("a",new VarExp("v")),new CompStmt(
                new PrintStmt(new readHeapExp(new VarExp("v"))),
                new PrintStmt(new ArithExp(
                        '+',new readHeapExp(new VarExp("v")),new ValueExp(new IntValue(5))))
        )))));

        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStatement ex7 = new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),new CompStmt(
                new NewStmt("v",new ValueExp(new IntValue(20))),new CompStmt(
                new PrintStmt(new readHeapExp(new VarExp("v"))),new CompStmt(
                        new writeHeapStmt("v",new ValueExp(new IntValue(30))),
                        new PrintStmt(new ArithExp(
                                '+',new readHeapExp(new VarExp("v")),new ValueExp(new IntValue(5))))
                ))));

        //  int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStatement ex8 = new CompStmt(new VarDeclStmt("v",new IntType()),new CompStmt(
                new AssignStmt("v",new ValueExp(new IntValue(4))),new CompStmt(
                new WhileStmt(new RelationalExp(new VarExp("v"),new ValueExp(new IntValue(0)),">"),
                new CompStmt(new PrintStmt(new VarExp("v")),
                new AssignStmt("v",new ArithExp('-',new VarExp("v"),new ValueExp(new IntValue(1)))))),
                new PrintStmt(new VarExp("v"))
        )));

        // Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStatement ex9 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a",
                                        new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a",
                                                new VarExp("v")),
                                        new CompStmt(
                                                new NewStmt("v",
                                                        new ValueExp(
                                                                new IntValue(30))),
                                                new PrintStmt(
                                                        new readHeapExp(
                                                                new readHeapExp(
                                                                        new VarExp(
                                                                                "a")))))))));

        IStatement ex10 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                        new CompStmt(new forkStmt(new CompStmt(new writeHeapStmt("a", new ValueExp(new IntValue(30))), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new readHeapExp(new VarExp("a"))))))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new readHeapExp(new VarExp("a")))))))));


        TextMenu menu = new TextMenu();
        createProgram(menu,ex1,1);
        createProgram(menu,ex2,2);
        createProgram(menu,ex3,3);
        createProgram(menu,ex4,4);
        createProgram(menu,ex5,5);
        createProgram(menu,ex6,6);
        createProgram(menu,ex7,7);
        createProgram(menu,ex8,8);
        createProgram(menu,ex9,9);
        createProgram(menu,ex10,10);
        menu.show();
    }

    private static void createProgram(TextMenu menu,IStatement example,int id){
        MyIDictionary<String, Type> typeEnv = new MyDictionary<String, Type>();
        try {
            example.typecheck(typeEnv);
        }
        catch (Exception e){
            System.out.println(id+": "+e);
            return;
        }
        String stringId = Integer.toString(id);
        Controller controller = createProgramController(example,"logs"+stringId+".txt",id);
        menu.addCommand(new RunCommand(stringId,"Example "+stringId,controller));
    }

    private static Controller createProgramController(IStatement ex,String log_file_name,int id){
        ExecutionStack stack = new ExecutionStack();
        SymbolTable symT = new SymbolTable();
        Output out = new Output();
        FileTable file_table = new FileTable();
        HeapTable heap_table = new HeapTable();
        ProgramState programstate = new ProgramState(stack, symT, out,file_table,heap_table, ex);
        IRepository repo = new Repository(log_file_name);
        repo.add(programstate);
        return new Controller(repo,true);
    }


}
