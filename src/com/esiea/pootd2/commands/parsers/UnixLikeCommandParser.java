package com.esiea.pootd2.commands.parsers;

import com.esiea.pootd2.commands.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnixLikeCommandParser implements ICommandParser {

    @Override
    public Command parse(String commandStr) {
        try {
            List<String> arguments = splitArguments(commandStr);

            return mapCommand(arguments);
        } catch (Exception e) {
            return new ErrorCommand("Erreur lors de l'analyse de la commande : " + e.getMessage());
        }
    }

    /**
     * Sépare les arguments de la ligne de commande.
     * Exemple : "mkdir folder1" -> ["mkdir", "folder1"]
     */
    private List<String> splitArguments(String commandStr) {
        if (commandStr == null || commandStr.isBlank()) {
            throw new IllegalArgumentException("Commande vide.");
        }

        String[] parts = commandStr.trim().split(" ");

        return new ArrayList<>(Arrays.asList(parts));
    }

    private Command mapCommand(List<String> arguments) {
        if (arguments.isEmpty()) {
            throw new IllegalArgumentException("Commande vide.");
        }

        String command = arguments.get(0);

        switch (command) {
            case "ls":
                return new ListCommand();
            case "cd":
                if (arguments.size() < 2) {
                    return new ErrorCommand("Usage : cd <chemin>");
                }
                return new ChangeDirectoryCommand(arguments.get(1));
            case "mkdir":
                if (arguments.size() < 2) {
                    return new ErrorCommand("Usage : mkdir <nom_dossier>");
                }
                return new MakeDirectoryCommand(arguments.get(1));
            case "touch":
                if (arguments.size() < 2) {
                    return new ErrorCommand("Usage : touch <nom_fichier>");
                }
                return new TouchCommand(arguments.get(1));
            default:
                return new ErrorCommand("Commande inconnue : " + command);
        }
    }
}
