package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelMenuReports extends JPanel {

    private JLabel titleMenuReports;

    private Button existingProcesses, readyReport, dispatchedReport, executionReport, expirationReport, blockReport, wakeReport, finishedReport, back, neverExecution, finishedReportSort, noExecutionListSort, noExecutionList;

    public PanelMenuReports(ActionListener listener){
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.decode("#4a4e69"));
        this.setPreferredSize(new Dimension(350,80));
        this.setMaximumSize(new Dimension(350,80));
        this.setMinimumSize(new Dimension(350,80));
        this.initComponents(listener);
        this.setVisible(true);
    }

    private void initComponents(ActionListener listener) {
        titleMenuReports = new JLabel("Reportes");
        titleMenuReports.setForeground(Color.WHITE);
        titleMenuReports.setFont(ConstantsGUI.FONT_MENU_TITLE);
        Utilities.addComponent(this, this.titleMenuReports, 0, 1);

        existingProcesses = new Button("Procesos actuales");
        existingProcesses.addActionListener(listener);
        existingProcesses.setActionCommand("Actuales");
        Utilities.addComponent(this, this.existingProcesses, 0, 2);

        blockReport = new Button("Ejecutados");
        blockReport.addActionListener(listener);
        blockReport.setActionCommand("Ejecutados");
        Utilities.addComponent(this, this.blockReport, 0, 3);

        readyReport = new Button("Listos");
        readyReport.addActionListener(listener);
        readyReport.setActionCommand("Listos");
        Utilities.addComponent(this, this.readyReport, 0, 4);

        dispatchedReport = new Button("Despachados");
        dispatchedReport.addActionListener(listener);
        dispatchedReport.setActionCommand("Despachados");
        Utilities.addComponent(this, this.dispatchedReport, 0, 5);

        executionReport = new Button("Ejecución");
        executionReport.addActionListener(listener);
        executionReport.setActionCommand("Ejecucion");
        Utilities.addComponent(this, this.executionReport, 0, 6);

        expirationReport = new Button("Expirados");
        expirationReport.addActionListener(listener);
        expirationReport.setActionCommand("Expirados");
        Utilities.addComponent(this, this.expirationReport, 0, 7);

        finishedReport = new Button("Finalizados");
        finishedReport.addActionListener(listener);
        finishedReport.setActionCommand("Finalizados");
        Utilities.addComponent(this, this.finishedReport, 0, 8);

        finishedReportSort = new Button("Fin. Partición");
        finishedReportSort.addActionListener(listener);
        finishedReportSort.setActionCommand("FinalizadosPart");
        Utilities.addComponent(this, this.finishedReportSort, 0, 9);

        noExecutionList = new Button("No Ejecutados");
        noExecutionList.addActionListener(listener);
        noExecutionList.setActionCommand("NoEjecutados");
        Utilities.addComponent(this, this.noExecutionList, 0, 10);

        noExecutionListSort = new Button("No Ejecutados Part");
        noExecutionListSort.addActionListener(listener);
        noExecutionListSort.setActionCommand("NoEjecutadosSort");
        Utilities.addComponent(this, this.noExecutionListSort, 0, 11);


        neverExecution = new Button("Nunca Ejecutados");
        neverExecution.addActionListener(listener);
        neverExecution.setActionCommand("NuncaEjecutados");
        Utilities.addComponent(this, this.neverExecution, 0, 12);

        back = new Button("Atrás");
        back.addActionListener(listener);
        back.setActionCommand("Atras");
        Utilities.addComponent(this, this.back, 0, 14);
    }
}
