package controller;

import command.ICommand;

public class Controller {
    private Context context;

    public Controller() {
        context = new Context();
    }

    public void runCommand(ICommand command) {
        command.execute(context);
    }

}