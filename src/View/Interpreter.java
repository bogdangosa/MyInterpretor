package View;

import Containers.MyDictionary;
import Containers.MyIDictionary;
import Controller.Controller;
import Models.Command.RunCommand;
import Models.ProgramState.*;
import Models.Statement.*;
import Models.Type.*;
import Repository.IRepository;
import Repository.Repository;

import java.util.ArrayList;
import java.util.List;

import static HardcodedPrograms.HardcodedPrograms.hardcoded_programs;

public class Interpreter {
    private final List<Controller> programs = new ArrayList<>();

    public Interpreter(){
        TextMenu menu = new TextMenu();
        int index=1;
        for(IStatement problem : hardcoded_programs){
            createProgram(menu,problem,index++);
        }
        //menu.show();
    }

    public void main(String[] args) {
        TextMenu menu = new TextMenu();
        int index=1;
        for(IStatement problem : hardcoded_programs){
            createProgram(menu,problem,index++);
        }
        menu.show();
    }

    public Controller getProgram(int index) {
        return programs.get(index);
    }

    private void createProgram(TextMenu menu,IStatement example,int id){
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
        programs.add(controller);
        menu.addCommand(new RunCommand(stringId,"Example "+stringId,controller));
    }

    private Controller createProgramController(IStatement ex,String log_file_name,int id){
        ExecutionStack stack = new ExecutionStack();
        SymbolTable symT = new SymbolTable();
        Output out = new Output();
        FileTable file_table = new FileTable();
        HeapTable heap_table = new HeapTable();
        SemaphoreTable semaphore_table = new SemaphoreTable();
        ProgramState programstate = new ProgramState(stack, symT, out,file_table,heap_table,semaphore_table, ex);
        IRepository repo = new Repository(log_file_name);
        repo.add(programstate);
        return new Controller(repo,true);
    }


}
