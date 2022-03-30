package controller;

import command.ICommand;

public class Controller {
    private Context context;

    public Controller() {

    }

    public void runCommand(ICommand command) {
        command.execute(this.context);
    }

}