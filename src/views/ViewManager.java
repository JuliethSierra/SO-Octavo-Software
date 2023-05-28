package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.math.BigInteger;

public class ViewManager extends JFrame {

    private PanelMenu panelMenu;
    private PanelTable panelTable;
    private PanelMenuPartitions panelMenuPartitions;
    private PanelMenuReports panelMenuReports;
    private DialogCreateProcess dialogCreateProcess;
    private DialogCreateInitialPartitions dialogCreateInitialPartitions;

    private Object[][] inQueue, currentList, canExecList, readyList, dispatchList, executionList, expirationList, finishedList, noExecutionList, neverExecutionLists, finishedListSort,  noExecutionListSort;


    private boolean isPartitionsMenuActive = false;


    public ViewManager(ActionListener actionListener, KeyListener keyListener){
        this.setLayout(new BorderLayout());
        this.setTitle("Quinto Software");
        this.setFont(ConstantsGUI.MAIN_MENU);
        this.setSize(700, 500);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.getContentPane().setBackground(Color.decode("#f2e9e4"));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.initComponents(actionListener, keyListener);
        this.setVisible(true);
    }

    private void initComponents(ActionListener actionListener, KeyListener keyListener) {
        this.panelMenu = new PanelMenu(actionListener);
        this.add(this.panelMenu, BorderLayout.WEST);

        this.panelTable = new PanelTable();
        this.add(this.panelTable, BorderLayout.CENTER);

        this.panelMenuPartitions = new PanelMenuPartitions(actionListener);
        this.panelMenuReports = new PanelMenuReports(actionListener);

        this.dialogCreateProcess = new DialogCreateProcess(actionListener, keyListener);

        this.dialogCreateInitialPartitions = new DialogCreateInitialPartitions(actionListener, keyListener);
        this.readyList = new Object[0][0];
    }

    public void showCreateInitialPartitions() {
        this.dialogCreateInitialPartitions.setVisible(true);
    }

    public String getPartitionName() {
        return this.dialogCreateInitialPartitions.getPartitionName();
    }

    public BigInteger getPartitionSize() {
        return this.dialogCreateInitialPartitions.getPartitionSize();
    }

    public boolean getIsPartitionsMenuActive(){
        return this.isPartitionsMenuActive;
    }

    public void setValuesToPartitionsTableInCreatePartition(Object[][] list){
        DefaultTableModel defaultTableModel = new DefaultTableModel(list, ConstantsGUI.PARTITIONS_TABLE_HEADERS);
        this.dialogCreateInitialPartitions.setTableProcess(defaultTableModel);
    }

    public void setValuesToPartitionsTableInCrud(Object[][] list){
        DefaultTableModel defaultTableModel = new DefaultTableModel(list, ConstantsGUI.PARTITIONS_TABLE_HEADERS);
        this.panelTable.setTableProcess(defaultTableModel);
    }

    public void cleanFieldsPartitionDialog(){
        this.dialogCreateInitialPartitions.cleanAllFields();
    }

    public void hideCreatePartitionsDialog(){
        this.dialogCreateInitialPartitions.cleanAllFields();
        this.dialogCreateInitialPartitions.changeTextToCRUD();
        this.dialogCreateInitialPartitions.setVisible(false);
    }

    public void setValuesToTable(Object[][] list, String title) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(list, ConstantsGUI.HEADERS_WITHOUT_PARTITION);
        this.panelTable.changeTitle(title);
        this.panelTable.setTableProcess(defaultTableModel);
    }

    public void setValuesToTableReport(Object[][] list, String title) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(list, ConstantsGUI.HEADERS_WITH_PARTITION);
        this.panelTable.changeTitle(title);
        this.panelTable.setTableProcess(defaultTableModel);
    }


    public void showCreateProcessDialog(){
        this.dialogCreateProcess.changeButtonToCreate();
        this.dialogCreateProcess.setVisible(true);
    }

    public String getProcessName(){
        return this.dialogCreateProcess.getNameProcess();
    }

    public BigInteger getProcessTime(){
        return this.dialogCreateProcess.getTimeProcess();
    }

    public BigInteger getProcessSize(){
        return this.dialogCreateProcess.getProcessSize();
    }
    public void hideCreateAndModifyProcessDialog(){
        this.dialogCreateProcess.setVisible(false);
        this.dialogCreateProcess.cleanAllFields();
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void cleanAllFields(){
        this.dialogCreateProcess.cleanAllFields();
    }

    public void cleanAllFieldsPartition(){
        this.dialogCreateInitialPartitions.cleanAllFields();
    }
    public int getIndexDataInTable(){
        return this.panelTable.getIndexDataProcess();
    }
    public void setProcessName(String processName){
        this.dialogCreateProcess.setProcessName(processName);
    }
    public void setProcessTime(BigInteger processTime){
        this.dialogCreateProcess.setTimeProcess(processTime);
    }
    public void setProcessSize(BigInteger processSize){
        this.dialogCreateProcess.setProcessSize(processSize);
    }

    public void showModifyProcessDialog(){
        this.dialogCreateProcess.changeButtonToModify();
        this.dialogCreateProcess.setVisible(true);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void setPartitionsMenuActive(boolean isActive){
        this.isPartitionsMenuActive = isActive;
    }

    public void changeTextInPartitionsMenu() {
        this.dialogCreateInitialPartitions.changeTextToCRUD();
    }

    public void changeToPartitionsMenu(){
        this.remove(this.panelMenu);
        this.add(this.panelMenuPartitions, BorderLayout.WEST);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void showCreatePartitionDialogWithoutTable(){
        this.dialogCreateInitialPartitions.removeTable();
        this.dialogCreateInitialPartitions.resizeDialog();
        this.dialogCreateInitialPartitions.changeTextToCRUD();
        this.dialogCreateInitialPartitions.setVisible(true);
    }

    public void setPartitionName(String name) {
        this.dialogCreateInitialPartitions.setPartitionName(name);
    }

    public void setPartitionSize(String size) {
        this.dialogCreateInitialPartitions.setPartitionSize(size);
    }

    public void showModifyPartitionDialog(){
        this.dialogCreateInitialPartitions.changeButtonToModify();
        this.showCreatePartitionDialogWithoutTable();
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void changeToCreatePartitionTexts(){
        this.dialogCreateInitialPartitions.changeButtonToCreate();
    }


    public void changeToMainMenu(){
        this.remove(panelMenuReports);
        this.remove(panelMenuPartitions);
        this.add(this.panelMenu, BorderLayout.WEST);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void changeToReportMenu() {
        this.remove(this.panelMenu);
        this.add(this.panelMenuReports, BorderLayout.WEST);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void setCurrentList(Object[][] currentList) {
        this.currentList = currentList;
    }
    public void setInQueueList(Object[][] inQueue) {
        this.inQueue = inQueue;
    }

    public void setCanExecList(Object[][] inQueue) {
        this.canExecList = inQueue;
    }
    public void setReadyList(Object[][] readyList) {
        this.readyList = readyList;
    }
    public void setDispatchList(Object[][] dispatchList) {
        this.dispatchList = dispatchList;
    }
    public void setExecutionList(Object[][] executionList) {
        this.executionList = executionList;
    }
    public void setExpirationList(Object[][] expirationList) {
        this.expirationList = expirationList;
    }
    public void setFinishedList(Object[][] finishedList) {
        this.finishedList = finishedList;
    }

    public void setFinishedListSort(Object[][] finishedListSort) {
        this.finishedListSort = finishedListSort;
    }
    public void setNoExecutionList(Object[][] noExecutionList) {
        this.noExecutionList = noExecutionList;
    }

    public void setNoExecutionListSort(Object[][] noExecutionListSort) {
        this.noExecutionListSort = noExecutionListSort;
    }


    public void setNeverExecutionLists(Object[][] neverExecutionLists) {
        this.neverExecutionLists = neverExecutionLists;
    }

    public void setValuesToCurrentProcess(){
        this.setValuesToTable(this.currentList, "Procesos Actuales");
    }
    public void setValuesToReadyReport(){
        this.setValuesToTable(this.readyList, "Procesos Listos");
    }
    public void setValuesToCanExecReport(){
        this.setValuesToTableReport(this.canExecList, "Procesos Ejecutados");
    }
    public void setValuesToDispatchReport(){
        this.setValuesToTableReport(this.dispatchList, "Procesos Despachados");
    }

    public void setValuesToExecReport(){
        this.setValuesToTableReport(this.executionList, "Procesos en Ejecución");
    }

    public void setValuesToExepReport(){
        this.setValuesToTableReport(this.expirationList, "Procesos Expirados");
    }
    public void setValuesToFinishedReport(){
        this.setValuesToTableReport(this.finishedList, "Procesos Finalizados");
    }

    public void setValuesToFinishedReportSort(){
        this.setValuesToTableReport(this.finishedListSort, "Procesos Finalizados por partición");
    }
    public void setValuesToNoExecReport(){
        this.setValuesToTableReport(this.noExecutionList, "Procesos No Ejecutados");
    }

    public void setValuesToNeverExecReport(){
        this.setValuesToTable(this.neverExecutionLists, "Procesos Nunca Ejecutados");
    }

    public void setValuesToNoExecReportSort(){
        this.setValuesToTable(this.noExecutionListSort, "No Ejecutados partición");
    }
    public Object[][] getReadyList() {
        return readyList;
    }
}
