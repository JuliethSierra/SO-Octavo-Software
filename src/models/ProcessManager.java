package models;

import java.math.BigInteger;
import java.util.ArrayList;

public class ProcessManager {
    private final int PROCESS_TIME = 5;
    private ArrayList<PartitionReport> dispatchList, executionList, expirationList, finishedList, canExecutionList;
    private ArrayList<Partition> partitions;

    private ArrayList<Process> inQueue, currentList, readyList, noExecutionList;

    public ProcessManager(){
        this.partitions = new ArrayList<>();
        this.inQueue = new ArrayList<>();
        this.currentList = new ArrayList<>();
        this.readyList = new ArrayList<>();
        this.dispatchList = new ArrayList<>();
        this.executionList = new ArrayList<>();
        this.expirationList = new ArrayList<>();
        this.finishedList = new ArrayList<>();
        this.noExecutionList = new ArrayList<>();
        this.canExecutionList = new ArrayList<>();
    }



    public boolean isAlreadyPartitionName(String name){
        for(Partition partition: partitions){
            if(partition.getName().equals(name))
                return true;
        }
        return false;
    }

    public boolean isAlreadyProcessName(String name){
        for(Process process: inQueue){
            if(process.getName().equals(name))
                return true;
        }
        return false;
    }

    public void addPartition(String partitionName, BigInteger partitionSize){
        this.partitions.add(new Partition(partitionName, partitionSize));
    }


    public void initSimulation(){
        this.copyToCurrentProcess();
        this.copyToCanExecutionProcess();
        this.initLoadToReady();
        this.initPartitions();
        for (int i = 0; i < readyList.size(); i++) {
            for (int j = 0; j < partitions.size(); j++) {
                if((readyList.get(i).getSize().compareTo(partitions.get(j).getSize()) == -1) || (readyList.get(i).getSize().compareTo(partitions.get(j).getSize()) == 0)){
                    this.loadToDispatchQueue(new PartitionReport(partitions.get(i).getName(), new Process(readyList.get(i).getName(), readyList.get(i).getTime(), readyList.get(i).getSize())));
                    if (readyList.get(i).getTime().compareTo(BigInteger.valueOf(PROCESS_TIME)) == 1 || readyList.get(i).getTime().compareTo(BigInteger.valueOf(PROCESS_TIME)) == 0) {
                        this.loadToExecQueue(new PartitionReport(partitions.get(i).getName(), new Process(readyList.get(i).getName(), this.consumeTimeProcess(readyList.get(i)), readyList.get(i).getSize())));
                    } else {
                        this.loadToExecQueue(new PartitionReport(partitions.get(i).getName(), new Process(readyList.get(i).getName(), readyList.get(i).getTime(), readyList.get(i).getSize())));
                    }
                    if (!(readyList.get(i).getTime().compareTo(BigInteger.valueOf(0)) == 0)) {
                        if(readyList.get(i).getTime().compareTo(BigInteger.valueOf(PROCESS_TIME)) == 1){
                            this.loadToExpirationQueue(new PartitionReport(partitions.get(i).getName(), new Process(readyList.get(i).getName(), this.consumeTimeProcess(readyList.get(i)), readyList.get(i).getSize())));
                            this.loadToReadyQueue(new Process(readyList.get(i).getName(), this.consumeTimeProcess(readyList.get(i)), readyList.get(i).getSize()));
                        } else {
                            this.loadToFinishedQueue(new PartitionReport(partitions.get(i).getName(), new Process(readyList.get(i).getName(), BigInteger.valueOf(0), readyList.get(i).getSize())));
                        }
                    }else {
                        this.loadToFinishedQueue(new PartitionReport(partitions.get(i).getName(), new Process(readyList.get(i).getName(), BigInteger.valueOf(0), readyList.get(i).getSize())));
                        return;
                    }
                } else {
                    this.loadToNoExecQueue(new Process(readyList.get(i).getName(), readyList.get(i).getTime(), readyList.get(i).getSize()));
                }
            }
        }
    }

    private void loadToReadyQueue(Process process) {
        this.readyList.add(process);
        System.out.println("ready " + readyList.toString());
    }

    private void loadToDispatchQueue(PartitionReport partitionReport) {
        this.dispatchList.add(partitionReport);
        System.out.println("dispatch " + dispatchList.toString());
    }
    private void loadToExecQueue(PartitionReport process) {
        this.executionList.add(process);
        System.out.println("execution " + executionList.toString());
    }
    private void loadToExpirationQueue(PartitionReport process) {
        this.expirationList.add(process);
        System.out.println("expiration " + expirationList.toString());
    }
    private void loadToFinishedQueue(PartitionReport process) {
        this.finishedList.add(process);
        System.out.println("finish " + finishedList.toString());
    }

    private void loadToNoExecQueue(Process process) {
        this.noExecutionList.add(process);
        System.out.println("noExec " + noExecutionList.toString());
    }

    private BigInteger consumeTimeProcess(Process process) {
        return (process.getTime().subtract(BigInteger.valueOf(PROCESS_TIME)));
    }

    public void copyToCurrentProcess(){
        currentList.addAll(inQueue);
        System.out.println(currentList.toString());
    }
    public void copyToCanExecutionProcess(){
        for (int j = 0; j < partitions.size(); j++) {
            for (int i = 0; i < inQueue.size(); i++) {
                    if ((inQueue.get(i).getSize().compareTo(partitions.get(j).getSize()) == -1) || (inQueue.get(i).getSize().compareTo(partitions.get(j).getSize()) == 0)){
                        canExecutionList.add(new PartitionReport(partitions.get(i).getName(),inQueue.get(i)));
                    }
            }
        }
        System.out.println("canExec " + canExecutionList.toString());
    }


    private void initLoadToReady() {
        //readyList.addAll(inQueue);
        readyList.add(new Process("p1",new BigInteger("10"), new BigInteger("10")));
        readyList.add(new Process("p2",new BigInteger("10"), new BigInteger("30")));
        readyList.add(new Process("p3",new BigInteger("10"), new BigInteger("10")));
        readyList.add(new Process("p4",new BigInteger("10"), new BigInteger("20")));
        readyList.add(new Process("p5",new BigInteger("10"), new BigInteger("10")));
        readyList.add(new Process("p6",new BigInteger("10"), new BigInteger("100")));
    }

    private void initPartitions() {
        partitions.add(new Partition("part1", new BigInteger("10")));
        partitions.add(new Partition("part2", new BigInteger("20")));
        partitions.add(new Partition("part3", new BigInteger("30")));
    }



    public Object[][] getPartitionsListAsMatrixObject(ArrayList<Partition> list){
        return this.parseArrayPartitionListToMatrixObject(list);
    }

    private Object[][] parseArrayPartitionListToMatrixObject(ArrayList<Partition> list){
        int sizeQueue = list.size();
        Object[][] processList = new Object[sizeQueue][5];

        for(int i = 0; i < sizeQueue; i++){
            processList[i][0] = list.get(i).getName();
            processList[i][1] = list.get(i).getSize();
        }
        return processList;
    }

    public int getPartitionsSize(){
        return this.partitions.size();
    }

    public Object[][] getProcessListAsMatrixObject(ArrayList<Process> list){
        return this.parseArrayListToMatrixObject(list);
    }

    private Object[][] parseArrayListToMatrixObject(ArrayList<Process> list){
        int sizeQueue = list.size();
        Object[][] processList = new Object[sizeQueue][5];

        for(int i = 0; i < sizeQueue; i++){
            processList[i][0] = list.get(i).getName();
            processList[i][1] = list.get(i).getTime();
            processList[i][2] = list.get(i).getSize();
        }
        return processList;
    }



    public void addToInQueue(Process process){
        this.inQueue.add(process);
    }

    public ArrayList<Process> getInQueue() {
        return inQueue;
    }

    public void setInQueue(ArrayList<Process> inQueue) {
        this.inQueue = inQueue;
    }

    public ArrayList<Process> getCurrentList() {
        return currentList;
    }

    public void setCurrentList(ArrayList<Process> currentList) {
        this.currentList = currentList;
    }

    public ArrayList<Process> getReadyList() {
        return readyList;
    }

    public void setReadyList(ArrayList<Process> readyList) {
        this.readyList = readyList;
    }

    public ArrayList<PartitionReport> getDispatchList() {
        return dispatchList;
    }

    public void setDispatchList(ArrayList<PartitionReport> dispatchList) {
        this.dispatchList = dispatchList;
    }

    public ArrayList<PartitionReport> getExecutionList() {
        return executionList;
    }

    public void setExecutionList(ArrayList<PartitionReport> executionList) {
        this.executionList = executionList;
    }

    public ArrayList<PartitionReport> getExpirationList() {
        return expirationList;
    }

    public void setExpirationList(ArrayList<PartitionReport> expirationList) {
        this.expirationList = expirationList;
    }

    public ArrayList<PartitionReport> getFinishedList() {
        return finishedList;
    }

    public void setFinishedList(ArrayList<PartitionReport> finishedList) {
        this.finishedList = finishedList;
    }

    public ArrayList<Process> getNoExecutionList() {
        return noExecutionList;
    }

    public void setNoExecutionList(ArrayList<Process> noExecutionList) {
        this.noExecutionList = noExecutionList;
    }

    public ArrayList<PartitionReport> getCanExecutionList() {
        return canExecutionList;
    }

    public void setCanExecutionList(ArrayList<PartitionReport> canExecutionList) {
        this.canExecutionList = canExecutionList;
    }

    public ArrayList<Partition> getPartitions() {
        return partitions;
    }

    public void setPartitions(ArrayList<Partition> partitions) {
        this.partitions = partitions;
    }
}
