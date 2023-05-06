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
        Object[][] newQueueList =  this.parseValuesIsBlockAndIsSuspended(list);
        DefaultTableModel defaultTableModel = new DefaultTableModel(newQueueList, ConstantsGUI.HEADERS);
        this.panelTable.changeTitle(title);
        this.panelTable.setTableProcess(defaultTableModel);
    }

    private Object[][] parseValuesIsBlockAndIsSuspended(Object[][] queueList){
        int size = queueList.length;
        for(int i = 0; i < size; i++){
            if(!queueList[i][4].equals("Sí") && !queueList[i][4].equals("No")){
                queueList[i][4] = queueList[i][4].equals(true) ? "Sí" : "No";
            }
        }
        return queueList;
    }
}
