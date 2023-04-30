package models;

import java.math.BigInteger;

public class Process {

    private String name;
    private BigInteger time;
    private BigInteger size;

    public Process(String name, BigInteger time, BigInteger size) {
        this.name = name;
        this.time = time;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getTime() {
        return time;
    }

    public void setTime(BigInteger time) {
        this.time = time;
    }

    public BigInteger getSize() {
        return size;
    }

    public void setSize(BigInteger size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Process{" +
                "name='" + name + '\'' +
                ", time=" + time +
                ", size=" + size +
                '}';
    }
}
