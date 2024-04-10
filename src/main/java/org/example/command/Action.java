package org.example.command;

import lombok.Getter;

@Getter
public enum Action {
    CREATE("Create vehicle", new Create()),
    INSERT("Insert vehicle into indicated position", new Insert()),
    FIND("Find vehicle by index", new Find()),
    DELETE("Delete vehicle by index", new Delete()),
    UPDATE("Update vehicles", new Update()),
    GET_CREATED_DATE("Get date of creation vehicle", new GetCreatedLocalDate()),
    GET_UPDATED_DATE("Get date of update vehicle", new GetUpdatedLocalDate()),
    GET_RESTYLING_AMOUNT("Get restyling amount", new GetRestylingAmount()),
    PRINT("Print vehicles", new Print()),
    EXIT("Exit", null);

    private final String name;
    private final Command command;

    Action(String name, Command command) {
        this.name = name;
        this.command = command;
    }

    public Command execute() {
        if (command != null) {
            command.execute();
        }
        return command;
    }
}
