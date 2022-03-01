package com.implermine;

public class ServiceImpl implements Service{

    private Repository repository;

    private String idCode = "100";
    private String height;

    private String getPrivate(){
        return "How did you get this";
    }

    private String getOtherPrivate(int thisInt, String thatString){
        return "How did you get here" + thisInt + " " + thatString;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public ServiceImpl(int number, String randString){
        System.out.println("You sent: " + number + " " + randString);
    }

    public ServiceImpl(){
        System.out.println("You called default constructor");
    }

    public ServiceImpl(Repository repository){
        this.repository = repository;
    }
}
