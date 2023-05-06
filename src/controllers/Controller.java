package controllers;

import models.ProcessManager;
import views.Utilities;
import views.ViewManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigInteger;
import java.security.Key;

public class Controller implements ActionListener, KeyListener {
    private ProcessManager processManager;
    private ViewManager viewManager;

    public Controller(){
        this.processManager = new ProcessManager();
        this.viewManager = new ViewManager(this, this);
        this.viewManager.showCreateInitialPartitions();
        //this.initSimulation();
    }

    private void initSimulation(){
        processManager.initSimulation();
    }
    public static void main(String[] args) {
        new Controller();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "AñadirParticion":
                this.addPartition();
                break;
            case "CancelarParticion":
                this.cancelAddPartition();
                break;
        }

    }

    private void addPartition() {
        String partitionName = this.viewManager.getPartitionName();
        BigInteger partitionSize = this.viewManager.getPartitionSize();

        if(this.processManager.isAlreadyPartitionName(partitionName)){
            Utilities.showErrorDialog("Ya existe una partición con este nombre");
        }
        else if(partitionName.trim().equals("")){
            Utilities.showErrorDialog("Ese nombre no está permitido para las particiones");
        }
        else if(partitionSize.equals(new BigInteger("-1"))){
            Utilities.showErrorDialog("Debe ingresar un tamaño para su partición");
        }
        else {
            this.processManager.addPartition(partitionName, partitionSize);
            if(this.viewManager.getIsPartitionsMenuActive())
                this.viewManager.setValuesToPartitionsTableInCrud(this.processManager.getPartitionsListAsMatrixObject(this.processManager.getPartitions()));
            else
                this.viewManager.setValuesToPartitionsTableInCreatePartition(processManager.getPartitionsListAsMatrixObject(processManager.getPartitions()));
            this.viewManager.cleanFieldsPartitionDialog();
        }
    }
    private void cancelAddPartition(){
        if(this.processManager.getPartitionsSize() > 0){
            this.viewManager.hideCreatePartitionsDialog();
            if(this.viewManager.getIsPartitionsMenuActive())
                this.viewManager.setValuesToPartitionsTableInCrud(this.processManager.getPartitionsListAsMatrixObject(this.processManager.getPartitions()));
            else
                this.viewManager.setValuesToTable(this.processManager.getProcessListAsMatrixObject(this.processManager.getInQueue()), "Procesos Existentes");
            this.viewManager.cleanFieldsPartitionDialog();
        }

        else
            Utilities.showErrorDialog("Debe ingresar al menos una partición");
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
