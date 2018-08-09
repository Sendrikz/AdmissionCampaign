package controller.commands;

public enum CommandEnum {

    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    REGISTRATION {
        {
            this.command = new RegistrationCommand();
        }
    };

    ActionCommand command;
    public ActionCommand getCommand() {
        return command;
    }
}
