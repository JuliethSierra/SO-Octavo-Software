package controllers;

import models.FreeStorage;
import models.FreeStorageReport;
import models.Process;
import models.ProcessManager;
import views.Utilities;
import views.ViewManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigInteger;
import java.util.ArrayList;

public class Controller implements ActionListener, KeyListener {
    private ProcessManager processManager;
    private ViewManager viewManager;

    public Controller(){
        this.processManager = new ProcessManager();
        this.viewManager = new ViewManager(this, this);
        this.viewManager.showCreateInitialPartitions();
        //this.initSimulation();
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
            case "ModificarProceso":
                this.showModifyProcessDialog();
                break;
            case "ConfirmarModificacionProceso":
                this.confirmModifyProcess();
                break;
            case "EliminarProceso":
                this.deleteProcess();
                break;
            case "MenuParticiones":
                this.changeToPartitionsMenu();
                break;
            case "CrearParticion":
                this.showCreatePartitionDialog();
                break;
            case "ModificarParticion":
                this.showModifyPartitionDialog();
                break;
            case "ConfirmarModificacionParticion":
                this.confirmModifyPartition();
                break;
            case "EliminarParticion":
                this.deletePartition();
                break;
            case "Reportes":
                this.changeToReportMenu();
                break;
            case "Enviar":
                this.initSimulation();
                break;
            case "Actuales":
                this.setValuesToCurrentProcess();
                break;
            case "Ejecutados":
                this.setValuesToCanExecReport();
                break;
            case "Listos":
                this.setValuesToReadyReport();
                break;
            case "Despachados":
                this.setValuesToDispatchReport();
                break;
            case "Ejecucion":
                this.setValuesToExecReport();
                break;
            case "Expirados":
                this.setValuesToExepReport();
                break;
            case "Finalizados":
                this.setValuesToFinishedReport();
                break;
            case "FinalizadosPart":
                this.setValuesToFinishedReportSort();
                break;
            case "NoEjecutados":
                this.setValuesToNoExecReport();
                break;
            case "NuncaEjecutados":
                this.setValuesToNeverExecReport();
                break;
            case "NoEjecutadosSort":
                this.setValuesToNoExecReportSort();
                break;
            case "Atras":
                this.changeToMenu();
                break;
            case "ManualUsuario":
                this.openManual();
                break;
            case "Salir":
                System.exit(0);
                break;
        }

    }

    private void initSimulation(){
        if(this.processManager.getPartitions().size()==0){
            Utilities.showErrorDialog("Debe ingresar al menos una partición para iniciar la simulación");
        }else{
            if(this.processManager.getInQueue().size() == 0){
                Utilities.showErrorDialog("Debe ingresar al menos un proceso para iniciar la simulación");
            }
            else {
                int response = Utilities.showConfirmationWarning();
                if(response == 0){
                    processManager.initSimulation();
                    Utilities.showDoneCPUProcess();
                    processManager.cleanQueueList();
                    processManager.copyToCurrentProcess();
                    this.cleanMainTableProcess();
                    this.loadReportList();
                }
            }
        }
    }

    private void cleanMainTableProcess(){
        this.viewManager.setValuesToTable(processManager.getProcessListAsMatrixObject(processManager.getInQueue()), "Procesos Existentes");
    }

    private void addPartition() {
        String partitionName = this.viewManager.getPartitionName().trim();
        BigInteger partitionSize = this.viewManager.getPartitionSize();

        if(this.processManager.isAlreadyPartitionName(partitionName)){
            Utilities.showErrorDialog("Ya existe una partición con este nombre");
            viewManager.cleanAllFieldsPartition();
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
        String processName = this.viewManager.getProcessName().trim();
        BigInteger timeProcess = this.viewManager.getProcessTime();
        BigInteger sizeProcess = this.viewManager.getProcessSize();

        if(processName.trim().isEmpty()){
            Utilities.showErrorDialog("El nombre del proceso está vacío, ingrese algún valor");
        }
        else if(this.processManager.isAlreadyProcessName(processName)){
            Utilities.showErrorDialog("El nombre del proceso ya existe, ingrese otro nombre");
            viewManager.cleanAllFields();
        }
        else if(timeProcess.toString().equals("-1")){
            Utilities.showErrorDialog("El tiempo del proceso está vacío, ingrese un valor numérico entero");
        }
        else if(sizeProcess.toString().equals("-1")){
            Utilities.showErrorDialog("El tamaño del proceso está vacío, ingrese un valor numérico entero");
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

    private void showModifyProcessDialog(){
        if(this.viewManager.getIndexDataInTable() == -1){
            Utilities.showErrorDialog("Debe seleccionar un proceso");
        }
        else {
            Process processToModify = this.processManager.getProcessInQueue(this.viewManager.getIndexDataInTable());
            this.viewManager.setProcessName(processToModify.getName());
            this.viewManager.setProcessTime(processToModify.getTime());
            this.viewManager.setProcessSize(processToModify.getSize());
            this.viewManager.showModifyProcessDialog();
        }
    }

    private void confirmModifyProcess(){
        Process processToModify = this.processManager.getProcessInQueue(this.viewManager.getIndexDataInTable());
        String modifyNameProcess = this.viewManager.getProcessName().trim();

        if(modifyNameProcess.trim().equals("")){
            Utilities.showErrorDialog("El nombre del proceso está vacío, ingrese algún valor");
        }
        else if(!processToModify.getName().equals(modifyNameProcess)
                && this.processManager.isAlreadyProcessName(modifyNameProcess)){
            Utilities.showErrorDialog("Ya existe un proceso con este nombre");

        }
        else if(this.viewManager.getProcessTime().toString().trim().equals("-1")){
            Utilities.showErrorDialog("El tiempo del proceso está vacío, ingrese un valor numérico entero");
        }
        else if(this.viewManager.getProcessSize().toString().trim().equals("-1")){
            Utilities.showErrorDialog("El tamaño del proceso está vacío, ingrese un valor numérico entero");
        }
        else {
            Process newProcess = new Process(this.viewManager.getProcessName().trim(), this.viewManager.getProcessTime(), this.viewManager.getProcessSize());
            this.processManager.updateProcessInQueue(newProcess, this.viewManager.getIndexDataInTable());
            this.viewManager.hideCreateAndModifyProcessDialog();
            this.viewManager.setValuesToTable(this.processManager.getProcessListAsMatrixObject(this.processManager.getInQueue()), "Procesos Existentes");
        }
    }

    private void deleteProcess(){
        if(this.viewManager.getIndexDataInTable() == -1){
            Utilities.showErrorDialog("Debe seleccionar un proceso");
        }
        else {
            int confirmation = Utilities.showConfirmationWarning();
            if(confirmation == 0){
                this.processManager.deleteProcessInQueue(this.viewManager.getIndexDataInTable());
                this.viewManager.setValuesToTable(this.processManager.getProcessListAsMatrixObject(this.processManager.getInQueue()), "Procesos Existentes");
            }

        }

    }

    private void changeToPartitionsMenu(){
        this.viewManager.setPartitionsMenuActive(true);
        this.viewManager.changeTextInPartitionsMenu();
        this.viewManager.setValuesToPartitionsTableInCrud(this.processManager.getPartitionsListAsMatrixObject(this.processManager.getPartitions()));
        this.viewManager.changeToPartitionsMenu();
    }

    private void showCreatePartitionDialog(){
        this.viewManager.changeToCreatePartitionTexts();
        this.viewManager.showCreatePartitionDialogWithoutTable();
    }

    private void showModifyPartitionDialog(){
        if(this.viewManager.getIndexDataInTable() == -1){
            Utilities.showErrorDialog("Debe seleccionar una partición");
        }
        else {
                FreeStorage partitionToModify = this.processManager.getPartitionByIndex(this.viewManager.getIndexDataInTable());
                this.viewManager.setPartitionName(partitionToModify.getName());
                this.viewManager.setPartitionSize(partitionToModify.getSize().toString());
                this.viewManager.showModifyPartitionDialog();
        }
    }

    private void confirmModifyPartition(){
        FreeStorage partitionToModify = this.processManager.getPartitionByIndex(this.viewManager.getIndexDataInTable());
        String modifyPartitionName = this.viewManager.getPartitionName().trim();

        if(modifyPartitionName.trim().equals("")){
            Utilities.showErrorDialog("El nombre de la partición está vacío, ingrese algún valor");
        }
        else if(!partitionToModify.getName().equals(modifyPartitionName) && this.processManager.isAlreadyPartitionName(modifyPartitionName)){
            Utilities.showErrorDialog("Ya existe una partición con este nombre");

        }
        else {
            FreeStorage newPartition = new FreeStorage(this.viewManager.getPartitionName(), this.viewManager.getPartitionSize());
            this.processManager.updatePartitions(newPartition, this.viewManager.getIndexDataInTable());
            this.viewManager.hideCreatePartitionsDialog();
            this.viewManager.setValuesToPartitionsTableInCrud(this.processManager.getPartitionsListAsMatrixObject(this.processManager.getPartitions()));
        }
    }

    private void deletePartition(){
        if(this.viewManager.getIndexDataInTable() == -1){
            Utilities.showErrorDialog("Debe seleccionar una partición");
        }
            else {
                int confirmation = Utilities.showConfirmationWarning();
                if(confirmation == 0){
                    this.processManager.deletePartition(this.viewManager.getIndexDataInTable());
                    this.viewManager.setValuesToPartitionsTableInCrud(this.processManager.getPartitionsListAsMatrixObject(this.processManager.getPartitions()));
                }
            }
    }

    private void changeToMenu(){
        if(this.viewManager.getIsPartitionsMenuActive()){
            this.viewManager.setPartitionsMenuActive(false);

        }
        else
            this.processManager.cleanAllLists();
        this.viewManager.setValuesToTable(this.processManager.getProcessListAsMatrixObject(this.processManager.getInQueue()), "Procesos Existentes");
        this.viewManager.changeToMainMenu();
    }

    private void openManual(){
        try{
            java.lang.Process p = Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL "+"C:\\Users\\Usuario\\Desktop\\SO\\Software\\Renovar - ICETEX 2023-1.pdf");
        } catch (Exception e){
            System.out.println("El archivo no se puede abrir");
        }

    }

    private void changeToReportMenu(){
        if(this.viewManager.getReadyList().length != 0){
            this.viewManager.changeToReportMenu();
            this.viewManager.setValuesToCurrentProcess();
        }
        else {
            Utilities.showErrorDialog("Debe iniciar la simulación antes de ver los reportes");
        }

    }

    private ArrayList<FreeStorageReport> sotCanReport(){
        ArrayList<FreeStorageReport> list = new ArrayList<>();
        for (int j = 0; j < processManager.getPartitions().size(); j++) {
            for (int i = 0; i < processManager.getCanExecutionList().size(); i++) {
                if (processManager.getCanExecutionList().get(i).getPartitionName().equals(processManager.getPartitions().get(j).getName())) {
                    list.add(processManager.getCanExecutionList().get(i));
                }
            }
        }
        return list;
    }

    private ArrayList<FreeStorageReport> sotFinishedReport(){
        ArrayList<FreeStorageReport> list = new ArrayList<>();
        for (int j = 0; j < processManager.getPartitions().size(); j++) {
            for (int i = 0; i < processManager.getFinishedList().size(); i++) {
                if (processManager.getFinishedList().get(i).getPartitionName().equals(processManager.getPartitions().get(j).getName())) {
                    list.add(processManager.getFinishedList().get(i));
                }
            }
        }
        return list;
    }

    private ArrayList<FreeStorageReport> sortDispReport(){
        ArrayList<FreeStorageReport> list = new ArrayList<>();
        for (int j = 0; j < processManager.getPartitions().size(); j++) {
            for (int i = 0; i < processManager.getDispatchList().size(); i++) {
                if (processManager.getDispatchList().get(i).getPartitionName().equals(processManager.getPartitions().get(j).getName())) {
                    list.add(processManager.getDispatchList().get(i));
                }
            }
        }
        return list;
    }

    private ArrayList<FreeStorageReport> sortExecReport(){
        ArrayList<FreeStorageReport> list = new ArrayList<>();
        for (int j = 0; j < processManager.getPartitions().size(); j++) {
            for (int i = 0; i < processManager.getExecutionList().size(); i++) {
                if (processManager.getExecutionList().get(i).getPartitionName().equals(processManager.getPartitions().get(j).getName())) {
                    list.add(processManager.getExecutionList().get(i));
                }
            }
        }
        return list;
    }

    private ArrayList<FreeStorageReport> sortExpReport(){
        ArrayList<FreeStorageReport> list = new ArrayList<>();
        for (int j = 0; j < processManager.getPartitions().size(); j++) {
            for (int i = 0; i < processManager.getExpirationList().size(); i++) {
                if (processManager.getExpirationList().get(i).getPartitionName().equals(processManager.getPartitions().get(j).getName())) {
                    list.add(processManager.getExpirationList().get(i));
                }
            }
        }
        return list;
    }

    private ArrayList<FreeStorageReport> sortNoExecReport(){
        ArrayList<FreeStorageReport> list = new ArrayList<>();
        for (int j = 0; j < processManager.getPartitions().size(); j++) {
            for (int i = 0; i < processManager.getNoExecutionList().size(); i++) {
                if (processManager.getNoExecutionList().get(i).getPartitionName().equals(processManager.getPartitions().get(j).getName())) {
                    list.add(processManager.getNoExecutionList().get(i));
                }
            }
        }
        return list;
    }
    private void loadReportList(){
        viewManager.setCurrentList(processManager.getProcessListAsMatrixObject(processManager.getCurrentList()));
        viewManager.setCanExecList(processManager.getProcessListAsMatrixReportObject(this.sotCanReport()));
        viewManager.setInQueueList(processManager.getProcessListAsMatrixObject(processManager.getInQueue()));
        viewManager.setReadyList(processManager.getProcessListAsMatrixObject(processManager.getReadyList()));
        viewManager.setDispatchList(processManager.getProcessListAsMatrixReportObject(this.sortDispReport()));
        viewManager.setExecutionList(processManager.getProcessListAsMatrixReportObject(this.sortExecReport()));
        viewManager.setExpirationList(processManager.getProcessListAsMatrixReportObject(this.sortExpReport()));
        viewManager.setFinishedList(processManager.getProcessListAsMatrixReportObject(processManager.getFinishedList()));
        viewManager.setFinishedListSort(processManager.getProcessListAsMatrixReportObject(this.sotFinishedReport()));
        viewManager.setNoExecutionList(processManager.getProcessListAsMatrixReportObject(processManager.getNoExecutionList()));
        viewManager.setNoExecutionListSort(processManager.getProcessListAsMatrixReportObject(this.sortNoExecReport()));
        viewManager.setNeverExecutionLists(processManager.getProcessListAsMatrixObject(processManager.getNeverExecutionList()));
    }

    public void setValuesToCurrentProcess(){
        this.viewManager.setValuesToCurrentProcess();
    }
    public void setValuesToReadyReport(){
        this.viewManager.setValuesToReadyReport();
    }
    public void setValuesToCanExecReport(){
        this.viewManager.setValuesToCanExecReport();
    }
    public void setValuesToDispatchReport(){
        this.viewManager.setValuesToDispatchReport();
    }

    public void setValuesToExecReport(){
        this.viewManager.setValuesToExecReport();
    }

    public void setValuesToExepReport(){
        this.viewManager.setValuesToExepReport();
    }
    public void setValuesToFinishedReport(){
        this.viewManager.setValuesToFinishedReport();
    }

    public void setValuesToFinishedReportSort(){
        this.viewManager.setValuesToFinishedReportSort();
    }
    public void setValuesToNoExecReport(){
        this.viewManager.setValuesToNoExecReport();
    }
    public void setValuesToNeverExecReport(){
        this.viewManager.setValuesToNeverExecReport();
    }
    public void setValuesToNoExecReportSort(){
        this.viewManager.setValuesToNoExecReportSort();
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
