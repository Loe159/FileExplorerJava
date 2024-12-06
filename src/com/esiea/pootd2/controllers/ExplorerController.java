package com.esiea.pootd2.controllers;

import com.esiea.pootd2.commands.*;
import com.esiea.pootd2.commands.parsers.ICommandParser;
import com.esiea.pootd2.models.FolderInode;
import com.esiea.pootd2.models.FileInode;
import com.esiea.pootd2.models.Inode;

public class ExplorerController implements IExplorerController {
    private FolderInode currentFolder;

    public ExplorerController() {
        this.currentFolder = new FolderInode("/");
    }

    @Override
    public String executeCommand(String commandStr) {

        ICommandParser parser = new com.esiea.pootd2.commands.parsers.UnixLikeCommandParser();
        Command command = parser.parse(commandStr);

        if (command instanceof ListCommand) {
            return doCommand((ListCommand) command);
        }
        else if (command instanceof ChangeDirectoryCommand) {
            return doCommand((ChangeDirectoryCommand) command);
        }
        else if (command instanceof MakeDirectoryCommand) {
            return doCommand((MakeDirectoryCommand) command);
        }
        else if (command instanceof TouchCommand) {
            return doCommand((TouchCommand) command);
        }
        else if (command instanceof ErrorCommand) {
            return doCommand((ErrorCommand) command);
        }
        else {
            return "Commande inconnue.";
        }
    }

    private String doCommand(ListCommand command) {
        StringBuilder sb = new StringBuilder();
        for (Inode child : currentFolder.getChildren()) {
            sb.append(child.getName())
                    .append("\t")
                    .append(child.getSize())
                    .append("\n");
        }
        return sb.toString().isEmpty() ? "(Dossier vide)" : sb.toString().trim();
    }

    private String doCommand(ChangeDirectoryCommand command) {
        String path = command.getPath();
        if (path.equals("..")) {
            FolderInode parent = currentFolder.getParent();
            if (parent != null) {
                currentFolder = parent;
                return "Retour au dossier parent.";
            } else {
                return "Erreur : déjà à la racine.";
            }
        }


        for (Inode child : currentFolder.getChildren()) {
            if (child instanceof FolderInode && child.getName().equals(path)) {
                currentFolder = (FolderInode) child;
                return "Dossier changé : " + currentFolder.getName();
            }
        }

            return "Erreur : dossier non trouvé.";
    }

    private String doCommand(MakeDirectoryCommand command) {
        String directoryName = command.getDirectoryName();

        if (!checkName(directoryName)) return "Erreur : Nom invalide";

        for (Inode child : currentFolder.getChildren()) {
            if (child.getName().equals(directoryName)) {
                return "Erreur : un fichier avec ce nom existe déjà.";
            }
        }

        FolderInode newFolder = new FolderInode(directoryName);
        currentFolder.addChild(newFolder);
        return "Dossier créé : " + directoryName;
    }

    private String doCommand(TouchCommand command) {
        String fileName = command.getFileName();

        if (!checkName(fileName)) return "Erreur : Nom invalide";

        for (Inode child : currentFolder.getChildren()) {
            if (child.getName().equals(fileName)) {
                return "Erreur : un fichier avec ce nom existe déjà.";
            }
        }

        FileInode newFile = new FileInode(fileName);
        currentFolder.addChild(newFile);
        return "Fichier créé : " + fileName;
    }

    private String doCommand(ErrorCommand command) {
        return "Erreur : " + command.getErrorMessage();
    }

    private Boolean checkName(String name) {
        return !name.contains("/");
    }
}

