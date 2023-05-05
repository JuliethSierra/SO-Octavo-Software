package controllers;

import models.ProcessManager;
import views.ViewManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class Controller implements ActionListener, KeyListener {
    private ProcessManager processManager;
    private ViewManager viewManager;

    public Controller(){
        this.processManager = new ProcessManager();
        this.viewManager = new ViewManager(this, this);
        this.viewManager.showCreateInitialPartitions();
        this.initSimulation();
    }

    private void initSimulation(){
        processManager.initSimulation();
    }
    public static void main(String[] args) {
        new Controller();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c)) {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
