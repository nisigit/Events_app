package command;

import controller.Context;

public class RespondSponsorshipCommand implements ICommand {

    private long requestNumber;
    private int percentToSponsor;
    private boolean result;

    public RespondSponsorshipCommand(long requestNumber, int percentToSponsor) {
        this.requestNumber = requestNumber;
        this.percentToSponsor = percentToSponsor;
    }

    @Override
    public void execute(Context context) {

    }

    @Override
    public Boolean getResult() {
        return this.result;
    }

}