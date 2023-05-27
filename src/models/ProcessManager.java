package models;

import java.math.BigInteger;
import java.util.ArrayList;

public class ProcessManager {
    private final int PROCESS_TIME = 5;
    private ArrayList<FreeStorageReport> dispatchList, executionList, expirationList, finishedList, canExecutionList, noExecutionList;
    private ArrayList<FreeStorage> partitions;

    private ArrayList<Process> inQueue, currentList, readyList, neverExecutionList;

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
        this.neverExecutionList = new ArrayList<>();
    }

    public Object[][] getPartitionsListAsMatrixObject(ArrayList<FreeStorage> list){
        return this.parseArrayPartitionListToMatrixObject(list);
    }

    private Object[][] parseArrayPartitionListToMatrixObject(ArrayList<FreeStorage> list){
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

    public Object[][] getProcessListAsMatrixReportObject(ArrayList<FreeStorageReport> list){
        return this.parseArrayListToMatrixReportObject(list);
    }

    private Object[][] parseArrayListToMatrixReportObject(ArrayList<FreeStorageReport> list){
        int sizeQueue = list.size();
        Object[][] processList = new Object[sizeQueue][5];

        for(int i = 0; i < sizeQueue; i++){
            processList[i][0] = list.get(i).getPartitionName();
            processList[i][1] = list.get(i).getProcess().getName();
            processList[i][2] = list.get(i).getProcess().getTime();
            processList[i][3] = list.get(i).getProcess().getSize();
        }
        return processList;
    }

    public void updatePartitions(FreeStorage newPartition, int indexDataInTable) {
        this.partitions.set(indexDataInTable, newPartition);
    }

    public void deletePartition(int indexDataInTable) {
        this.partitions.remove(indexDataInTable);
    }

    public void cleanAllLists(){
        this.inQueue.clear();
        this.currentList.clear();
        this.readyList.clear();
        this.dispatchList.clear();
        this.executionList.clear();
        this.expirationList.clear();
        this.finishedList.clear();
        this.noExecutionList.clear();
        this.canExecutionList.clear();
        this.neverExecutionList.clear();
    }

    public FreeStorage getPartitionByIndex(int indexDataInTable) {
        return this.partitions.get(indexDataInTable);
    }


    public Process getProcessInQueue(int indexDataInTable) {
        return this.inQueue.get(indexDataInTable);
    }

    public void updateProcessInQueue(Process newProcess, int indexDataInTable) {
        this.inQueue.set(indexDataInTable, newProcess);
    }

    public void deleteProcessInQueue(int indexDataInTable) {
        this.inQueue.remove(indexDataInTable);
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

    public ArrayList<FreeStorageReport> getDispatchList() {
        return dispatchList;
    }

    public void setDispatchList(ArrayList<FreeStorageReport> dispatchList) {
        this.dispatchList = dispatchList;
    }

    public ArrayList<FreeStorageReport> getExecutionList() {
        return executionList;
    }

    public void setExecutionList(ArrayList<FreeStorageReport> executionList) {
        this.executionList = executionList;
    }

    public ArrayList<FreeStorageReport> getExpirationList() {
        return expirationList;
    }

    public void setExpirationList(ArrayList<FreeStorageReport> expirationList) {
        this.expirationList = expirationList;
    }

    public ArrayList<FreeStorageReport> getFinishedList() {
        return finishedList;
    }

    public void setFinishedList(ArrayList<FreeStorageReport> finishedList) {
        this.finishedList = finishedList;
    }

    public ArrayList<FreeStorageReport> getNoExecutionList() {
        return noExecutionList;
    }

    public void setNoExecutionList(ArrayList<FreeStorageReport> noExecutionList) {
        this.noExecutionList = noExecutionList;
    }

    public ArrayList<FreeStorageReport> getCanExecutionList() {
        return canExecutionList;
    }

    public void setCanExecutionList(ArrayList<FreeStorageReport> canExecutionList) {
        this.canExecutionList = canExecutionList;
    }

    public ArrayList<FreeStorage> getPartitions() {
        return partitions;
    }

    public void setPartitions(ArrayList<FreeStorage> partitions) {
        this.partitions = partitions;
    }

    public ArrayList<Process> getNeverExecutionList() {
        return neverExecutionList;
    }

    public void setNeverExecutionList(ArrayList<Process> neverExecutionList) {
        this.neverExecutionList = neverExecutionList;
    }

    public boolean isAlreadyPartitionName(String name){
        for(FreeStorage partition: partitions){
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
        this.partitions.add(new FreeStorage(partitionName, partitionSize));
    }

    public void addToInQueue(Process process){
        this.inQueue.add(process);
    }

    public void initSimulation(){
        this.copyToCurrentProcess();
        this.copyToCanExecutionProcess();
        this.initLoadToReady();
        this.getNoExecuted();
            for (int i = 0; i < readyList.size(); i++) {
                for (int j = 0; j < partitions.size(); j++) {
                if ((readyList.get(i).getSize().compareTo(partitions.get(j).getSize()) == -1) || (readyList.get(i).getSize().compareTo(partitions.get(j).getSize()) == 0)) {
                    this.loadToDispatchQueue(new FreeStorageReport(partitions.get(j).getName(), new Process(readyList.get(i).getName(), readyList.get(i).getTime(), readyList.get(i).getSize())));
                    if (readyList.get(i).getTime().compareTo(BigInteger.valueOf(PROCESS_TIME)) == 1 || readyList.get(i).getTime().compareTo(BigInteger.valueOf(PROCESS_TIME)) == 0) {
                        this.loadToExecQueue(new FreeStorageReport(partitions.get(j).getName(), new Process(readyList.get(i).getName(), this.consumeTimeProcess(readyList.get(i)), readyList.get(i).getSize())));
                    } else {
                        this.loadToExecQueue(new FreeStorageReport(partitions.get(j).getName(), new Process(readyList.get(i).getName(), readyList.get(i).getTime(), readyList.get(i).getSize())));
                    }
                    if (!(readyList.get(i).getTime().compareTo(BigInteger.valueOf(0)) == 0)) {
                        if (readyList.get(i).getTime().compareTo(BigInteger.valueOf(PROCESS_TIME)) == 1) {
                            this.loadToExpirationQueue(new FreeStorageReport(partitions.get(j).getName(), new Process(readyList.get(i).getName(), this.consumeTimeProcess(readyList.get(i)), readyList.get(i).getSize())));
                            this.loadToReadyQueue(new Process(readyList.get(i).getName(), this.consumeTimeProcess(readyList.get(i)), readyList.get(i).getSize()));

                        } else {
                            this.loadToFinishedQueue(new FreeStorageReport(partitions.get(j).getName(), new Process(readyList.get(i).getName(), BigInteger.valueOf(0), readyList.get(i).getSize())));
                        }

                    }else {
                        this.loadToFinishedQueue(new FreeStorageReport(partitions.get(j).getName(), new Process(readyList.get(i).getName(), BigInteger.valueOf(0), readyList.get(i).getSize())));
                    }
                    j= partitions.size();
                }
                else {
                    this.loadToNoExecQueue(new FreeStorageReport(partitions.get(j).getName(),new Process(readyList.get(i).getName(), readyList.get(i).getTime(), readyList.get(i).getSize())));
                }
            }

        }
        System.out.println("Listos:");
        System.out.println(readyList.toString());

        System.out.println("ejecutados:");
        System.out.println(canExecutionList.toString());

        System.out.println("no ejecutados:");
        System.out.println(noExecutionList.toString());

        System.out.println("finalizados:");
        System.out.println(finishedList.toString());

        System.out.println("no ejecutado:");
        System.out.println(neverExecutionList.toString());
    }

    private void getNoExecuted(){
        int count =0;
        for (int i = 0; i < readyList.size(); i++) {
            for (int j = 0; j < partitions.size(); j++) {
                if ((readyList.get(i).getSize().compareTo(partitions.get(j).getSize()) == 1)){
                    count++;
                }
            }
            if(count == partitions.size()){
                neverExecutionList.add(new Process(readyList.get(i).getName(), readyList.get(i).getTime(), readyList.get(i).getSize()));
            }
            count =0;
        }

    }

    private void loadToReadyQueue(Process process) {
        this.readyList.add(process);
    }

    private void loadToDispatchQueue(FreeStorageReport partitionReport) {
        this.dispatchList.add(partitionReport);
    }
    private void loadToExecQueue(FreeStorageReport process) {
        this.executionList.add(process);
    }
    private void loadToExpirationQueue(FreeStorageReport process) {
        this.expirationList.add(process);
    }
    private void loadToFinishedQueue(FreeStorageReport process) {
        this.finishedList.add(process);
    }

    private void loadToNoExecQueue(FreeStorageReport process) {
        this.noExecutionList.add(process);
    }

    private BigInteger consumeTimeProcess(Process process) {
        return (process.getTime().subtract(BigInteger.valueOf(PROCESS_TIME)));
    }

    public void copyToCurrentProcess(){
        currentList.addAll(inQueue);
    }
    public void copyToCanExecutionProcess(){
        for (int i = 0; i < inQueue.size(); i++) {
            for (int j = 0; j < partitions.size(); j++) {
                    if ((inQueue.get(i).getSize().compareTo(partitions.get(j).getSize()) == -1) || (inQueue.get(i).getSize().compareTo(partitions.get(j).getSize()) == 0)){
                        canExecutionList.add(new FreeStorageReport(partitions.get(j).getName(),inQueue.get(i)));
                    }
            }
        }
    }

    private void initLoadToReady() {

      /*  inQueue.add(new Process("p1", new BigInteger("10"), new BigInteger("10")));
        inQueue.add(new Process("p2", new BigInteger("15"), new BigInteger("5")));
        inQueue.add(new Process("p3", new BigInteger("9"), new BigInteger("15")));
        inQueue.add(new Process("p4", new BigInteger("14"), new BigInteger("20")));
        inQueue.add(new Process("p5", new BigInteger("10"), new BigInteger("30")));
        inQueue.add(new Process("p6", new BigInteger("5"), new BigInteger("15")));
        inQueue.add(new Process("p7", new BigInteger("4"), new BigInteger("150")));
        inQueue.add(new Process("p8", new BigInteger("12"), new BigInteger("20")));
        inQueue.add(new Process("p9", new BigInteger("21"), new BigInteger("10")));
        inQueue.add(new Process("p10", new BigInteger("15"), new BigInteger("1")));
        inQueue.add(new Process("p11", new BigInteger("10"), new BigInteger("3")));
        inQueue.add(new Process("p12", new BigInteger("11"), new BigInteger("1")));
        inQueue.add(new Process("p13", new BigInteger("10"), new BigInteger("20")));
        inQueue.add(new Process("p14", new BigInteger("6"), new BigInteger("15")));
        inQueue.add(new Process("p15", new BigInteger("10"), new BigInteger("12")));

        inQueue.add(new Process("p16", new BigInteger("4"), new BigInteger("250")));


        partitions.add(new FreeStorage("part1", new BigInteger("12")));
        partitions.add(new FreeStorage("part2", new BigInteger("18")));
        partitions.add(new FreeStorage("part3", new BigInteger("20")));
        partitions.add(new FreeStorage("part4", new BigInteger("35")));
        partitions.add(new FreeStorage("part5", new BigInteger("14")));*/

        readyList.addAll(inQueue);
    }

    public void cleanQueueList(){
        inQueue.clear();
    }
}
