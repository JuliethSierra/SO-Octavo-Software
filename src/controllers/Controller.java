package controllers;

import models.Partition;
import models.Process;
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
            case "CrearProceso":
                this.showCreateProcessDialog();
                break;
            case "AñadirProceso":
                this.confirmAddProcess();
                break;
            case "CancelarAñadirProceso":
                this.cancelAddProcess();
                break;
            case "Salir":
                System.exit(0);
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

    private void showCreateProcessDialog(){
        if(this.processManager.getPartitions().size() == 0){
            Utilities.showErrorDialog("Debe tener al menos una partición para poder crear procesos");
        }
        else {
            this.viewManager.showCreateProcessDialog();
        }
    }

    private void confirmAddProcess(){
        String processName = this.viewManager.getProcessName();
        BigInteger timeProcess = this.viewManager.getProcessTime();
        BigInteger sizeProcess = this.viewManager.getProcessSize();

        if(processName.trim().isEmpty()){
            Utilities.showErrorDialog("El nombre del proceso está vacío. Ingrese algún valor");
        }
        else if(this.processManager.isAlreadyProcessName(processName)){
            Utilities.showErrorDialog("El nombre del proceso ya existe. Ingrese otro nombre");
        }
        else if(timeProcess.toString().equals("-1")){
            Utilities.showErrorDialog("El tiempo del proceso está vacío. Ingrese un valor numérico entero");
        }
        else if(sizeProcess.toString().equals("-1")){
            Utilities.showErrorDialog("El tamaño del proceso está vacío. Ingrese un valor numérico entero");
        }
        else{
            Process newProcess = new Process(processName, timeProcess, sizeProcess);
            this.processManager.addToInQueue(newProcess);
            this.viewManager.setValuesToTable(this.processManager.getProcessListAsMatrixObject(this.processManager.getInQueue()), "Procesos Existentes");
            this.viewManager.hideCreateAndModifyProcessDialog();
        }
    }

    private void cancelAddProcess(){
        this.viewManager.hideCreateAndModifyProcessDialog();
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
