package com.jetbrains;

import java.util.ArrayList;
import java.util.Arrays;

public class PassengerQueue {

    private Passenger[] queueArray = new Passenger[42];
    private Passenger[] passengerQueue = new Passenger[21];
    private Passenger[] passengerTrainQueue = new Passenger[42];

    private static ArrayList<String> list = new ArrayList<String>();
    private static ArrayList<Integer> numbers = new ArrayList<Integer>();
    private static ArrayList<Integer> inNumbers = new ArrayList<Integer>();

    private int first;
    private int last;
    private int size;
    private int maxStayInQueue;
    private int maxLength = 21;
    private int i;
    private int avgTime;
    private int seatNo;
    private int minTime;
    private Passenger length;
    private String detail;
    private String firstName;
    private String surname;

    public void add(Passenger pssenger) {
        passengerQueue[last] = pssenger;
        if (!isFull()) {
            last++;
            size++;
        }
    }


    public void setSecond(Integer index) {
        minTime = passengerQueue[index].getSecond();
    }

    public Integer getSecond() {
        return minTime;
    }

    public void setName(Integer index) {
        this.firstName = passengerQueue[index].getFirstName();
        this.surname = passengerQueue[index].getSurname();
    }

    public String getName() {
        return firstName + " " + surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSeat(Integer index) {
        this.seatNo = passengerQueue[index].getSeat();
    }

    public Integer getSeat() {
        return seatNo;
    }


    public Integer getSize() {
        return size;
    }


    public void show() {
        for (int i = 0; i < size; i++) {
            if (passengerQueue[i] != null) {
                System.out.println("Name = " + passengerQueue[i].getName());
                //   return passengerQueue[i].getName();
            }
        }

    }

    public void setPassng(Integer loop) {
        for (int i = 0; i < size; i++) {
            if (passengerQueue[i] != null) {
                if (i == loop) {
                    String name = passengerQueue[i].getName();
                    Integer seat = passengerQueue[i].getSeat();
                    detail = name + "=-" + seat;
                }
            }
        }
    }

    public String getPassng() {
        return detail;
    }


    public String toString() {

        return Arrays.toString(passengerQueue);
    }



    public void setMaxStayInQueue(Integer index) {
        PassengerQueue.this.maxStayInQueue = PassengerQueue.this.maxStayInQueue + passengerQueue[index].getSecond() ;
    }

    public Integer getMaxStayInQueue() {
        return PassengerQueue.this.maxStayInQueue;
    }

    public Passenger getlength(Integer index) {
        if (passengerQueue[index].getName() != null) {
            if (!passengerQueue[index].getName().equals("EMPTY")) {
                PassengerQueue.this.i = PassengerQueue.this.i + 1;
                int length = PassengerQueue.this.i;
                System.out.println("Queue Length = " + length);
            } else {
                System.out.println("EMPTY");
            }
        } else {
            System.out.println("EMPTY");
        }
        return passengerQueue[index];
    }

    public Passenger board(Passenger passenger) {

        try {
            System.out.println("=========================================================");
            System.out.println("=========================================================");
            System.out.println("CHECKING IN - " + passenger.getName());
            System.out.println("WAIT FOR " + passenger.getSecond() + " SECONDS ");
            Thread.sleep(passenger.getSecond() * 1000);
            System.out.println(passenger.getName() + " HAS BEEN CHECKED IN");
            System.out.println("=========================================================");

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return passenger;
    }

    public void delete(Integer indx) {
        passengerQueue[indx] = null;
    }

    public Passenger remove(){
        Passenger passenger = passengerQueue[first];
        if(!isEmpty()){
            first=(first+1)%21;
            size--;

        }
        else{
            System.out.println("TrainQueue is Empty");
        }
        return passenger;
    }

    public void removeAll() {
        for (int i = 0; i < size; i++) {

            passengerQueue[i] = passengerQueue[i + 1];

        }
        size--;
        first--;
    }


    public String delete() {

        String data = String.valueOf(passengerQueue[first]);
        if (!isEmpty()) {

            size--;
            last--;
        } else {
            System.out.println("Queue is Empty");
        }
        return data;
    }

    private boolean isEmpty() {
        return size == 0;
    }

    private boolean isFull() {
        return size == 21;
    }


    public void clear() {
        // modCount++;
        // Let gc do its work
        if(!isEmpty()){
            for (int i = 0; i < size; i++)
                passengerQueue[i] = null;
            size = first = last = 0;
        } else {
            System.out.println("Train Queue is empty");
        }
    }


    public ArrayList<String> sortByName(ArrayList<Integer> input) {
        String temp;
        for (int i = 0; i < input.size()-1; i++)
        {
            for(int j = 0; j < input.size()-i-1; j++)
            {
                if(input.get(j) > input.get(j + 1))
                {
                    Integer tempVar = input.get(j + 1);
                    input.set(j + 1, input.get(j));
                    input.set(j, tempVar);
                }
            }
            list.add(String.valueOf(input));
        }
        return list;
    }


}
