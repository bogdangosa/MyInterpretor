package View;

import Containers.MyIList;
import Containers.MyList;
import Models.Command.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class TextMenu {
    private Map<String, Command> commands;

    public  TextMenu(){
        commands=new HashMap<>();
    }
    public void addCommand(Command cmd){ commands.put(cmd.getKey(),cmd);}
    private void printMenu(){
        for(Command com : commands.values()){
            String line=String.format("%4s : %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }
    public void show(){
        Scanner scanner=new Scanner(System.in);
        while(true){
            printMenu();
            System.out.print("Input the option: ");
            String key=scanner.nextLine();
            Command command=commands.get(key);
            if (command==null){
                System.out.println("Invalid Option");
                continue;  }
            command.execute();
            commands.remove(command.getKey());
        }
    }
}
