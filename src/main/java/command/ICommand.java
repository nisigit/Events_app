package command;

public interface ICommand {

    void execute(Context context);

    Object getResult();

}