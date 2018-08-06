package controller.commands;

public enum CommandEnum {

    LOGIN {
        {
            this.command = new LoginCommand();
        }
    };

    ActionCommand command;
    public ActionCommand getCommand() {
        return command;
    }
}
