package Models.Command;

import Controller.Controller;

public class RunCommand extends Command{
    Controller controller;

    public RunCommand(String key, String desc,Controller controller){
        super(key, desc);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.allStep();
        }
        catch (Exception e) {

        }
    }
}
