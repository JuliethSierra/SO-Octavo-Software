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
        this.dialogCreateInitialPartitions.setVisible(false);
    }

    public void setValuesToTable(Object[][] list, String title) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(list, ConstantsGUI.HEADERS_WITHOUT_PARTITION);
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

}
