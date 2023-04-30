package controllers;

import models.ProcessManager;

public class Controller {
    private ProcessManager processManager;

    public Controller(){
        this.processManager = new ProcessManager();
        this.initSimulation();
    }

    private void initSimulation(){
        processManager.initSimulation();
    }
    public static void main(String[] args) {
        new Controller();
    }
}
