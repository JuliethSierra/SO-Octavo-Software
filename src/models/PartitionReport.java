package models;

import java.util.ArrayList;

public class PartitionReport {

    private String partitionName;
    private Process process;

    public PartitionReport(String partitionName, Process process) {
        this.partitionName = partitionName;
        this.process = process;
    }

    public String getPartitionName() {
        return partitionName;
    }

    public void setPartitionName(String partitionName) {
        this.partitionName = partitionName;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcessList(Process process) {
        this.process = process;
    }

    @Override
    public String toString() {
        return "PartitionReport{" +
                "partitionName='" + partitionName + '\'' +
                ", process=" + process +
                '}';
    }
}
