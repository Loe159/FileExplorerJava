package com.esiea.pootd2.interfaces;
import com.esiea.pootd2.controllers.IExplorerController;
import java.util.Scanner;

public class TextInterface extends IUserInterface {
    private final IExplorerController controller;

    public TextInterface(IExplorerController controller) {
        super(controller);
        this.controller = controller;
    }
}