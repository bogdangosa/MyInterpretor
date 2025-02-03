package HardcodedPrograms;

import Models.Exp.*;
import Models.Statement.*;
import Models.Type.BoolType;
import Models.Type.IntType;
import Models.Type.RefType;
import Models.Type.StringType;
import Models.Value.BoolValue;
import Models.Value.IntValue;
import Models.Value.StringValue;

import java.util.ArrayList;
import java.util.List;

public class HardcodedPrograms {
    public static final ArrayList<IStatement> hardcoded_programs = new ArrayList<IStatement>(List.of(
            new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v")))),

        new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new
                                ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new
                                        IntValue(1)))), new PrintStmt(new VarExp("b")))))),
        new CompStmt(new VarDeclStmt("a",new BoolType()),

                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v")))))),

        new CompStmt(new VarDeclStmt("varf",new StringType()),new CompStmt(
                new AssignStmt("varf",new ValueExp(new StringValue("test.in"))),new CompStmt(
                new OpenRFile(new VarExp("varf")),new CompStmt(
                new VarDeclStmt("varc",new IntType()),new CompStmt(
                new ReadFile(new VarExp("varf"),"varc"),new CompStmt(
                new PrintStmt(new VarExp("varc")),new CompStmt(
                new ReadFile(new VarExp("varf"),"varc"),new CompStmt(
                new PrintStmt(new VarExp("varc")),
                new CloseRFile(new VarExp("varf"))
        )))))))),

        //   Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),new CompStmt(
                new NewStmt("v",new ValueExp(new IntValue(20))),new CompStmt(
                new VarDeclStmt("a",new RefType(new RefType(new IntType()))),new CompStmt(
                new NewStmt("a",new VarExp("v")),new CompStmt(
                new PrintStmt(new VarExp("v")),new PrintStmt(new VarExp("a"))
        ))))),

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),new CompStmt(
                new NewStmt("v",new ValueExp(new IntValue(20))),new CompStmt(
                new VarDeclStmt("a",new RefType(new RefType(new IntType()))),new CompStmt(
                new NewStmt("a",new VarExp("v")),new CompStmt(
                new PrintStmt(new readHeapExp(new VarExp("v"))),
                new PrintStmt(new ArithExp(
                        '+',new readHeapExp(new VarExp("v")),new ValueExp(new IntValue(5))))
        ))))),

        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        new CompStmt(new VarDeclStmt("v",new RefType(new IntType())),new CompStmt(
                new NewStmt("v",new ValueExp(new IntValue(20))),new CompStmt(
                new PrintStmt(new readHeapExp(new VarExp("v"))),new CompStmt(
                new writeHeapStmt("v",new ValueExp(new IntValue(30))),
                new PrintStmt(new ArithExp(
                        '+',new readHeapExp(new VarExp("v")),new ValueExp(new IntValue(5))))
        )))),

        //  int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        new CompStmt(new VarDeclStmt("v",new IntType()),new CompStmt(
                new AssignStmt("v",new ValueExp(new IntValue(4))),new CompStmt(
                new WhileStmt(new RelationalExp(new VarExp("v"),new ValueExp(new IntValue(0)),">"),
                        new CompStmt(new PrintStmt(new VarExp("v")),
                                new AssignStmt("v",new ArithExp('-',new VarExp("v"),new ValueExp(new IntValue(1)))))),
                new PrintStmt(new VarExp("v"))
        ))),

        // Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        new CompStmt(
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
                                                                                "a"))))))))),

        new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                        new CompStmt(new forkStmt(new CompStmt(new writeHeapStmt("a", new ValueExp(new IntValue(30))), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new readHeapExp(new VarExp("a"))))))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new readHeapExp(new VarExp("a"))))))))),
        new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(
                new VarDeclStmt("b",new IntType()),new CompStmt(
                new VarDeclStmt("c",new IntType()),new CompStmt(
                new AssignStmt("a",new ValueExp(new IntValue(1))),new CompStmt(
                new AssignStmt("b",new ValueExp(new IntValue(2))),new CompStmt(
                new AssignStmt("c",new ValueExp(new IntValue(5))),new CompStmt(
                new SwitchStmt(new ArithExp('*',new VarExp("a"),new ValueExp(new IntValue(10))),
                        new ArithExp('*',new VarExp("b"),new VarExp("c")),
                        new ValueExp(new IntValue(10)),
                        new CompStmt(new PrintStmt(new VarExp("a")),new PrintStmt(new VarExp("b"))),
                        new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))),new PrintStmt(new ValueExp(new IntValue(200)))),
                        new PrintStmt(new ValueExp(new IntValue(300)))
                        ),
                new PrintStmt(new ValueExp(new IntValue(300)))
        ))))))),
        new CompStmt(
                new VarDeclStmt("v1", new RefType(new IntType())), new CompStmt(
                new VarDeclStmt("cnt",new IntType()), new CompStmt(
                new NewStmt("v1",new ValueExp(new IntValue(1))),new CompStmt(
                new CreateSemaphoreStmt("cnt",new readHeapExp(new VarExp("v1"))),new CompStmt(
                new forkStmt(new CompStmt(new AcquireStmt("cnt"),new CompStmt(
                        new writeHeapStmt("v1",new ArithExp('*',new readHeapExp(new VarExp("v1")),new ValueExp(new IntValue(10)))),new CompStmt(
                        new PrintStmt(new readHeapExp(new VarExp("v1"))),
                        new ReleaseStmt("cnt")
                )))),new CompStmt(
                new forkStmt(new CompStmt(new AcquireStmt("cnt"),new CompStmt(
                        new writeHeapStmt("v1",new ArithExp('*',new readHeapExp(new VarExp("v1")),new ValueExp(new IntValue(10)))),new CompStmt(
                        new writeHeapStmt("v1",new ArithExp('*',new readHeapExp(new VarExp("v1")),new ValueExp(new IntValue(2)))),new CompStmt(
                        new PrintStmt(new readHeapExp(new VarExp("v1"))),
                        new ReleaseStmt("cnt")
                ))))),new CompStmt(
                new AcquireStmt("cnt"),new CompStmt(
                new PrintStmt(new ArithExp('-',new readHeapExp(new VarExp("v1")),
                        new ValueExp(new IntValue(1)))),
                new ReleaseStmt("cnt")
            ))))))))

    ));
}
