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
    },
    REGISTRATEONSUBJECT {
        {
            this.command = new RegistrationForSubjectCommand();
        }
    },
    GENERATEUNIVERSITIESBYCITY {
        {
            this.command = new GenerateUniversitiesByCityCommand();
        }
    },
    GENERATESPECIALTIESBYUNI {
        {
            this.command = new GenerateSpecialtyByUniCommand();
        }
    },
    REGISTRATIONFORSPECIALTY {
        {
            this.command = new RegistrationForSpecialtyCommand();
        }
    },
    RETURNTOMAIN {
        {
            this.command = new ReturnToMainCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogOutCommand();
        }
    },
    SETGRADE {
        {
            this.command = new SetGradeCommand();
        }
    };

    ActionCommand command;
    public ActionCommand getCommand() {
        return command;
    }
}
