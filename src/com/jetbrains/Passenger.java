package com.jetbrains;

import java.lang.*;


public class Passenger {
    private String firstName;
    private String surname;
    private int secondInQueue;
    private int seatNo;

    public void setName(String fname, String sname){
        this.firstName = fname;
        this.surname = sname;
    }

    public String getName(){
        return firstName +" "+ surname;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getSurname(){
        return surname;
    }

    public void setSecond(Integer secondInQueue){
        this.secondInQueue = secondInQueue;
    }

    public Integer getSecond(){
        return secondInQueue;
    }


    public void setSeat (Integer seatNum){
        this.seatNo = seatNum;
    }

    public Integer getSeat(){
        return seatNo;
    }

}

