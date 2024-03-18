package org.example;

import org.example.command.Action;
import org.example.command.Command;
import org.example.util.UserInputUtil;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        final Action[] actions = Action.values();
        final List<String> names = getNames(actions);
        Command command;
        do {
            command = executeCommand(actions, names);
        }
        while (command != null);
    }

    private static Command executeCommand(Action[] actions, List<String> names) {
        int userInput = UserInputUtil.getUserInput("What do you want", names);
        final Action action = actions[userInput];
        return action.execute();
    }

    private static List<String> getNames(Action[] actions) {
        final List<String> names = new ArrayList<>(actions.length);
        for (Action action : actions) {
            names.add(action.getName());
        }
        return names;
    }
}

