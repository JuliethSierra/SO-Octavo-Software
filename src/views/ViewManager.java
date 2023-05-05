package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class ViewManager extends JFrame {

    private PanelMenu panelMenu;
    private PanelTable panelTable;
    private PanelMenuPartitions panelMenuPartitions;
    private PanelMenuReports panelMenuReports;
    private DialogCreateProcess dialogCreateProcess;
    private DialogCreateInitialPartitions dialogCreateInitialPartitions;

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
}
