package com.esiea.pootd2;

import com.esiea.pootd2.controllers.ExplorerController;
import com.esiea.pootd2.interfaces.HttpInterface;
import com.esiea.pootd2.interfaces.TextInterface;

public class ExplorerApp {
    public static void main(String[] args) {
        ExplorerController controller = new ExplorerController();

        // Vérifier si un argument est passé
        if (args.length > 0 && "http".equals(args[0])) {
            // Si l'argument est "http", on utilise l'interface web
            System.out.println("Lancement de l'interface HTTP...");
            HttpInterface httpInterface = new HttpInterface(controller);
            httpInterface.run();
        } else {
            // si aucun argument, on utilise le terminal
            System.out.println("Lancement de l'interface Text...");
            TextInterface textInterface = new TextInterface(controller);
            textInterface.run();
        }
    }
}
