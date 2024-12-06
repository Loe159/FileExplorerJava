package com.esiea.pootd2.interfaces;

import com.esiea.pootd2.controllers.IExplorerController;

import java.util.Scanner;

public abstract class IUserInterface {
    protected IExplorerController controller;

    public IUserInterface(IExplorerController controller) {
        this.controller = controller;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue dans l'explorateur de fichiers ! Tapez 'exit' pour quitter.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.equals("exit")) {
                System.out.println("Au revoir !");
                break;
            }

            String result = controller.executeCommand(input);
            if (!result.isEmpty()) {
                System.out.println(result);
            }
        }

        scanner.close();
    }
}
