package com.jetbrains;

import java.io.*;
import java.util.Arrays;

import com.mongodb.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.lang.Object;

import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import java.io.IOException;

import java.lang.*;
import java.util.*;
import java.util.function.BiConsumer;


public class TrainStation extends Application {

    public static DB database = new MongoClient("localhost", 27017).getDB("TrainStation");
    public static DBCollection collection = database.getCollection("Queues");

    public static DB databaseTrain = new MongoClient("localhost", 27017).getDB("Seat_Booking_Details");
    public static DBCollection collectionTrain = databaseTrain.getCollection("Passenger");

    File newFileTrain = new File("Train Queue details.txt");
    File report = new File("TrainQueue Report.txt");

    ArrayList<String> listPssngs1 = new ArrayList<String>();
    ArrayList<String> listPssngs2 = new ArrayList<String>();

    Map<String, Map<String, Integer>> myMapWDate1 = new LinkedHashMap<>();
    Map<String, Map<String, Integer>> myMapWDate2 = new LinkedHashMap<>();

    private static Passenger[] waitingRoom = new Passenger[42];
    private static Passenger[] waitingRoom2 = new Passenger[42];


    private static PassengerQueue trainPassQueue = new PassengerQueue();

    private static PassengerQueue trainQueue = new PassengerQueue();
    private static PassengerQueue trainQueue2 = new PassengerQueue();

    private static Passenger passenger2 = new Passenger();
    private static Passenger seatNum = new Passenger();

    private static Passenger passengerDt = new Passenger();

    ArrayList<Integer> seatNArr = new ArrayList<>();
    ArrayList<Integer> seatNArr2 = new ArrayList<>();

    Integer tripway = 0;

    Map<String, Integer> myMap1 = new HashMap<String, Integer>();
    Map<String, Integer> myMap2 = new HashMap<String, Integer>();
    private String Cname1;
    private File newFile;

    DatePicker date = new DatePicker();
    ArrayList<String> passengerD = new ArrayList<String>();
    ArrayList<String> passengerDtB2C = new ArrayList<String>();

    ArrayList<String> listToSort = new ArrayList<>();
    ArrayList<String> listToSort2 = new ArrayList<>();
    ArrayList<String> listWaitin = new ArrayList<>();
    ArrayList<String> listWaitin2 = new ArrayList<>();

    ArrayList<String> sortedFnlListC2B = new ArrayList<>();
    ArrayList<String> sortedFnlListB2C = new ArrayList<>();

    static Integer passgSz = 0;
    static Integer passgSz2 = 0;
    static Integer watinIndex = 0;

    static Integer listIndx = 0;
    static Integer listIndx2 = 0;
    static Integer start = 0;
    static Integer end = 0;

    Label labelH = new Label("TRAIN SEAT MAP");
    Label labelF1 = new Label("footer1");

    Button btnFEmty = new Button("   ");

    Button btnFBkd = new Button("   ");

    String datePicked = new String();

    ArrayList<String> trainList = new ArrayList<String>();
    ArrayList<String> trainList2 = new ArrayList<String>();


    @Override
    public void start(Stage primaryStage) throws Exception {
        Scanner sc = new Scanner(System.in);
        menu:
        while (true) {
            System.out.println("--------------------------------------------------");
            System.out.println("                DENUWARA EXPRESS                 ");
            System.out.println("--------------------------------------------------");
            System.out.println("Enter \"A\" to add a passenger to the trainQueue ");
            System.out.println("Enter \"V\" to view the trainQueue  ");
            System.out.println("Enter \"D\" to delete passenger from the trainQueue ");
            System.out.println("Enter \"S\" to store trainQueue data into a plain text file");
            System.out.println("Enter \"L\" to load data back from the file into the trainQueue ");
            System.out.println("Enter \"R\" to run the simulation and produce report ");
            System.out.println("Enter \"Q\" to quit");
            String option = sc.next().toLowerCase();
            switch (option) {
                case "a":
                    dateWayCheck(date, tripway);
                    loadPassengers(myMapWDate1, myMapWDate2, newFile, passengerD, passengerDtB2C,collectionTrain);
                    addPassenger(myMap1, myMap2, Cname1, tripway, date, waitingRoom, passgSz, watinIndex, listPssngs1, listPssngs2, listToSort, listWaitin, listIndx, sortedFnlListC2B, listWaitin2, waitingRoom2, passgSz2, listIndx2, trainQueue2, passenger2, sortedFnlListB2C, datePicked, seatNArr, trainList, seatNArr2);
                    break;
                case "v":
                    viewTrainQueue(labelH, labelF1, btnFEmty, btnFBkd, sortedFnlListC2B, sortedFnlListB2C, seatNArr, seatNum, trainPassQueue, trainList, trainList2);
                    break;
                case "d":
                    deletePassenger(sortedFnlListC2B, sortedFnlListB2C);
                    break;
                case "s":
                    storeTrainQueueDetails(sortedFnlListC2B, sortedFnlListB2C, newFileTrain, seatNArr, collection, trainQueue);
                    break;
                case "l":
                    loadTrainQueueDetails(sortedFnlListC2B, sortedFnlListB2C, newFileTrain, seatNArr, collection, trainQueue);
                    break;
                case "r":
                    runTheSimulation(sortedFnlListC2B, sortedFnlListB2C, newFileTrain, seatNArr, trainQueue, passengerDt, report);
                    break;
                case "q":
                    System.exit(0);
                    break menu;
                default:
                    System.out.println("Invalid input, Re-enter");
            }
        }
    }


    private void dateWayCheck(DatePicker date, Integer tripway) {
        Stage stageD = new Stage();
        Button btndDate = new Button("NEXT");
        btndDate.setStyle("-fx-border-color: #062C4C; -fx-border-width: 2px; -fx-font-weight: bold; -fx-text-fill: #062C4C;");
        Button wayCB = new Button("COLOMBO TO BADULLA");
        wayCB.setStyle("-fx-border-color: #062C4C; -fx-border-width: 2px; -fx-font-weight: bold; -fx-text-fill: #062C4C;");
        Button wayBC = new Button("BADULLA TO COLOMBO");
        wayBC.setStyle("-fx-border-color: #062C4C; -fx-border-width: 2px; -fx-font-weight: bold; -fx-text-fill: #062C4C;");
        HBox wayHbox = new HBox(20, wayCB, wayBC);
        wayHbox.setPadding(new Insets(0, 50, 10, 50));
        Label headLbl1 = new Label("GET PASSENGER DETAILS");
        headLbl1.setFont(new Font("Arial", 24));
        headLbl1.setStyle("-fx-font-weight: bolder;" + "-fx-text-fill: white;" + "-fx-background-color: #062C4C");
        headLbl1.setMaxWidth(515);
        headLbl1.setPadding(new Insets(20, 50, 20, 100));
        Label dateLbl1 = new Label("CHOOSE THE DATE");
        dateLbl1.setFont(new Font("Arial", 18));
        dateLbl1.setStyle("-fx-font-weight: bold;" + "-fx-text-fill: #062C4C;");
        dateLbl1.setPadding(new Insets(10, 50, 0, 50));
        Label dateLbl2 = new Label("CHOOSE THE DIRECTION");
        dateLbl2.setFont(new Font("Arial", 18));
        dateLbl2.setStyle("-fx-font-weight: bold;" + "-fx-text-fill: #062C4C;");
        dateLbl2.setPadding(new Insets(10, 50, 0, 50));
        HBox hboxDate = new HBox(10, this.date);
        date.setStyle("-fx-border-color: #062C4C; -fx-border-width: 2px;");
        hboxDate.setPadding(new Insets(0, 50, 10, 50));
        HBox hboxSbmt = new HBox(btndDate);
        hboxSbmt.setPadding(new Insets(20, 0, 20, 400));
        //    hboxSbmt.setSpacing(50);
        VBox wayDate = new VBox(headLbl1, dateLbl2, wayHbox, dateLbl1, hboxDate, hboxSbmt);
        wayDate.setStyle("-fx-background-color: #CECECE");
        wayDate.setPadding(new Insets(0, 0, 50, 0));
        wayDate.setSpacing(10);

        wayCB.setOnAction(new EventHandler<ActionEvent>() {                 // COLOMBO TO BADULLA setOnAction
            public void handle(ActionEvent event) {
                TrainStation.this.tripway = 1;
                wayCB.setDisable(true);
                wayCB.setStyle("-fx-background-color: #062C4C");
                wayBC.setDisable(true);
            }
        });
        wayBC.setOnAction(new EventHandler<ActionEvent>() {                 //  BADULLA TO COLOMBO setOnAction
            public void handle(ActionEvent event) {
                TrainStation.this.tripway = 2;
                wayBC.setDisable(true);
                wayBC.setStyle("-fx-background-color: #062C4C");
                wayCB.setDisable(true);
            }
        });
        btndDate.setOnAction(new EventHandler<ActionEvent>() {             // next button
            public void handle(ActionEvent event) {
                stageD.close();
            }
        });
        Scene scene = new Scene(wayDate, 515, 370);
        stageD.setScene(scene);
        stageD.showAndWait();
    }

    public static void main(String[] args) {
        Application.launch();
    }


    private void loadPassengers(Map<String, Map<String, Integer>> mapWDate1, Map<String, Map<String, Integer>> myMapWDate1, File newFile, ArrayList<String> dtC2B, ArrayList<String> passengerDtC2B, DBCollection collectionTrain) throws IOException {


        DBCursor cursor = TrainStation.collectionTrain.find();
        Iterator<DBObject> fields = cursor.iterator();
        Map map = new HashMap<>();

        if (this.passengerD != null) {
            this.passengerD.clear();
        }

        if (TrainStation.this.tripway == 1) {                        // load details about passengers - COLOMBO TO BADULLA
            while (cursor.hasNext()) {
                map = cursor.next().toMap();
                String fllName = (String) map.get("FULL_NAME");
                String seat = (String) map.get("SEAT_NO");
                String date = (String) map.get("DATE");
                String tripway = (String) map.get("TRIP_WAY");
                String passDtl = fllName + "=-" + seat + "~" + date;

                if (tripway.equals("C2B")) {
                    this.passengerD.add(passDtl);
                }
            }
        } else if (TrainStation.this.tripway == 2) {                // load details about passengers - BADULLA TO COLOMBO
            while (cursor.hasNext()) {
                map = cursor.next().toMap();
                String fllName = (String) map.get("FULL_NAME");
                String seat = (String) map.get("SEAT_NO");
                String date = (String) map.get("DATE");
                String tripway = (String) map.get("TRIP_WAY");
                String passDtl = fllName + "=-" + seat + "~" + date;
                if (tripway.equals("B2C")) {
                    this.passengerD.add(passDtl);
                }
            }
        }

       // System.out.println(this.passengerD);
        // System.out.println(this.passengerDtB2C);


    }

    private void addPassenger(Map<String, Integer> myMap1, Map<String, Integer> myMap2, String Cname1, Integer tripway, DatePicker date, Passenger[] waitingRoom, Integer sz, int passgSz, ArrayList<String> listPssngs1, ArrayList<String> listPssngs2, ArrayList<String> listToSort, ArrayList<String> listWaitin, Integer listIndx, ArrayList<String> fnlListC2B, ArrayList<String> sortedFnlListC2B, Passenger[] waitingRoom2, Integer sz2, Integer passgSz2, PassengerQueue trainQueue2, Passenger passenger2, ArrayList<String> sortedFnlListB2C, String datePicked, ArrayList<Integer> seatNArr, ArrayList<String> trainList, ArrayList<Integer> seatNArr2) throws IOException {
        Random random = new Random();

        ArrayList<String> sortedList = new ArrayList<>();

        ArrayList<String> PnS = new ArrayList<String>();
        int seatIndx = 0;

        Stage stage = new Stage();                          // stage with passengers to be added to the waiting room
        stage.setTitle("PASSENGERS");
        FlowPane flowPane = new FlowPane(20, 20);
        flowPane.setStyle("-fx-padding: 25;");
        Label labl = new Label("CLICK TO ADD PASSENGER TO WAITING ROOM");
        labl.setFont(new Font("Arial", 18));
        labl.setStyle("-fx-font-weight: bold;" + "-fx-text-fill: #062C4C;");
        Button inToWaitR = new Button("ADD");
        inToWaitR.setStyle("-fx-border-color: #062C4C; -fx-border-width: 2px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #062C4C");
        HBox submithbx = new HBox(inToWaitR);
        submithbx.setStyle("-fx-padding: 25;");
        HBox btnHbx = new HBox(flowPane);

        for (int j = 1; j < 43; j++) {

            ///////////////////////////add wht cms after loadin
            for (String pos1 : this.passengerD) {
                //   System.out.println(pos1);
                String[] keyValue1 = pos1.split("=");
                String[] keyValue2 = pos1.split("=-");
                String SnD1 = keyValue1[1];
                String PName = keyValue1[0];
                String SnD2 = keyValue2[1];
                String[] fllName = PName.split("_");
                String[] SnD1Splt = SnD1.split("~");
                String[] SnD1Splt2 = SnD2.split("~");
                String SeatNo1 = SnD1Splt[0];
                String SeatNum = SnD1Splt2[0];
                String fname = fllName[0];
                String sname = fllName[1];

                if (SnD1.contains("-" + String.valueOf(j) + "~" + this.date.getValue().toString())) {
                    TrainStation.this.datePicked = this.date.getValue().toString();
                    Button btns = new Button(fname + " " + sname);

                   if (TrainStation.this.listPssngs1 != null){
                       if (TrainStation.this.listPssngs1.contains(fname + " " + sname)){
                           btns.setDisable(true);
                       }
                   }

                    btns.setStyle("-fx-border-color: #062C4C; -fx-border-width: 2px; -fx-font-weight: bold; -fx-text-fill: #062C4C; -fx-margin: 5;");
                    btns.setId(Integer.toString(j));
                    flowPane.getChildren().add(btns);
                    flowPane.setHgap(20);
                    flowPane.setVgap(20);

                    btns.setOnAction(new EventHandler<ActionEvent>() {           // add to waiting room gui
                        public void handle(ActionEvent event) {
                            TrainStation.this.tripway = 1;
                            btns.setStyle("-fx-background-color: #062C4C");
                            btns.setDisable(true);
                            TrainStation.this.listPssngs1.add(fname + " " + sname);
                            watinIndex = watinIndex + 1;
                            int waitingTime = random.nextInt(6) + random.nextInt(6) + random.nextInt(6);
                            Passenger pssenger = new Passenger();
                            pssenger.setName(fname, sname);
                            pssenger.setSeat(Integer.parseInt(SeatNum));
                            waitingRoom[watinIndex] = pssenger;

                          ////  System.out.println(TrainStation.waitingRoom[watinIndex].getName() + ">" + TrainStation.waitingRoom[watinIndex].getSeat());

                            listWaitin.add(PName + "=" + SeatNo1);


                            inToWaitR.setOnAction(new EventHandler<ActionEvent>() {             //waiting room gui
                                public void handle(ActionEvent event) {
                                    while (0 < listWaitin.size()) {
                                        Stage stageWR = new Stage();
                                        FlowPane flowPaneWR = new FlowPane();
                                        flowPaneWR.setHgap(20);
                                        flowPaneWR.setVgap(20);
                                        flowPaneWR.setStyle("-fx-padding: 25;");
                                        Label headLbl = new Label("WAITING ROOM");
                                        headLbl.setFont(new Font("Arial", 18));
                                        headLbl.setStyle("-fx-font-weight: bold;" + "-fx-text-fill: #062C4C;");
                                        HBox headHbx = new HBox(headLbl);
                                        headHbx.setStyle("-fx-padding: 25;");
                                        Button add2Q = new Button("ADD TO QUEUE");
                                        add2Q.setStyle("-fx-border-color: #062C4C; -fx-border-width: 2px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #062C4C");
                                        HBox footHbx = new HBox(add2Q);
                                        footHbx.setStyle("-fx-padding: 25;");

                                        for (String passInWR : listWaitin) {
                                            String[] keyValue1 = passInWR.split("=-");
                                            String SnD1 = keyValue1[1];
                                            String fllname = keyValue1[0];
                                            String[] keyValue2 = fllname.split("_");
                                            String fstName = keyValue2[0];
                                            String surName = keyValue2[1];
                                            Button btnsWR = new Button(fstName + " " + surName);
                                            btnsWR.setStyle("-fx-border-color: #062C4C; -fx-border-width: 2px; -fx-font-weight: bold; -fx-text-fill: #062C4C; -fx-margin: 5;");
                                          //  btnsWR.setStyle("-fx-margin: 5;");
                                            flowPaneWR.getChildren().add(btnsWR);
                                        }

                                        add2Q.setOnAction(new EventHandler<ActionEvent>() {              //waiting room gui
                                            public void handle(ActionEvent event) {
                                                int queueSize = random.nextInt(5) + 1;
                                                if (listWaitin.size() < queueSize) {

                                                    queueSize = listWaitin.size();
                                                }
                                                for (int r = 0; r < queueSize; r++) {
                                                    listToSort.add(listWaitin.get(r));                   ////////////////////////////////////////delete later
                                                }

                                                listWaitin.removeIf(listToSort::contains);

                                                for (String passngToSort : listToSort) {
                                                    String[] keyValueSort = passngToSort.split("=-");
                                                    String seatNo = keyValueSort[1];
                                                    String PName = keyValueSort[0];
                                                    TrainStation.this.seatNArr.add(Integer.parseInt(seatNo));

                                                }

                                                TrainStation.trainQueue.sortByName(TrainStation.this.seatNArr);  //used a method (to sort) created in passengerQueue class

                                            //    System.out.println(TrainStation.this.seatNArr);
                                                sortedList.removeIf(listToSort::contains);

                                                for (Integer seatNum : TrainStation.this.seatNArr) {
                                                    for (String listPass : listToSort) {
                                                        //   TrainStation.listIndx = TrainStation.listIndx + 1;
                                                        String[] keyValueList = listPass.split("=-");
                                                        String passNmList = keyValueList[0];
                                                        String[] fllName = passNmList.split("_");
                                                        String fName = fllName[0];
                                                        String sName = fllName[1];
                                                        int passStList = Integer.parseInt(keyValueList[1]);
                                                        if (passStList == seatNum) {
                                                            // input to an array
                                                            sortedList.add(fName + " " + sName + "=" + seatNum);
//
                                                        }
                                                    }
                                                }

                                                listToSort.clear();
                                              //  System.out.println(sortedList);
                                                stageWR.close();

                                            }
                                        });

                                        VBox vbox = new VBox(headHbx, flowPaneWR, footHbx);
                                        Scene scene = new Scene(vbox, 500, 500);
                                        stageWR.setScene(scene);
                                        stageWR.showAndWait();

                                    }

                                    int indx = 0;
                                    for (Integer seatNum : TrainStation.this.seatNArr) {              ///sorted final train queue array
                                        for (String listPass : sortedList) {

                                            String[] keyValueList = listPass.split("=");
                                            String passNmList = keyValueList[0];
                                            String[] fllName = passNmList.split(" ");
                                            String fName = fllName[0];
                                            String sName = fllName[1];
                                            int passStList = Integer.parseInt(keyValueList[1]);
                                            if (passStList == seatNum) {
                                                // input to an array
                                                TrainStation.this.sortedFnlListC2B.add(fName + " " + sName + "=-" + seatNum);
                                                Passenger pssng = new Passenger();
                                                pssng.setName(fName, sName);
                                                pssng.setSeat(passStList);
                                                int queueSize = random.nextInt(6) + random.nextInt(6) + random.nextInt(6);
                                                pssng.setSecond(queueSize);
                                             //   System.out.println(pssng.getSecond());
                                                trainQueue.add(pssng);
                                                TrainStation.trainQueue.setMaxStayInQueue(indx);
                                              //  System.out.println(trainQueue.getMaxStayInQueue());

                                                // trainQueue.show();
                                                indx++;
                                            }
                                        }
                                    }

                                    Stage stage = new Stage();
                                    stage.setTitle("Train Queue");
                                    ListView listView = new ListView<>();
                                    for (String pass : TrainStation.this.sortedFnlListC2B) {
                                        listView.getItems().add(pass);
                                    }
                                    HBox hbox = new HBox(listView);
                                    Scene scene = new Scene(hbox, 300, 500);
                                    stage.setScene(scene);
                                    stage.show();

                                    System.out.println(TrainStation.this.sortedFnlListC2B);
                                    //   System.out.println(trainQueue.toString());
                                    trainQueue.show();
                                }
                            });
                        }
                    });

                }
            }

        }

        VBox vbox = new VBox(20, labl, btnHbx, submithbx);
        Scene scene = new Scene(vbox, 500, 500);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void viewTrainQueue(Label h, Label labelH, Button fEmty, Button btnFEmty, ArrayList<String> fnlListC2B, ArrayList<String> sortedFnlListC2B, ArrayList<Integer> seatNArr, Passenger seatNum, PassengerQueue trainPassQueue, ArrayList<String> list, ArrayList<String> trainList) {
        System.out.println(TrainStation.this.sortedFnlListC2B);
        TrainStation.trainQueue.show();


        ArrayList<String> passFrmTrQ = new ArrayList<String>();
        //   System.out.println(this.trainList);

        for (int i = 0; i < TrainStation.trainQueue.getSize(); i++) {
            TrainStation.trainQueue.setPassng(i);
          //  System.out.println(TrainStation.trainQueue.getPassng());
            passFrmTrQ.add(TrainStation.trainQueue.getPassng());

        }

      //  System.out.println(passFrmTrQ);

        Stage stage = new Stage();
        stage.setTitle("Train Queue");
        VBox vbox;
        VBox vBox1 = null;
        VBox vBox2 = null;
        VBox vBox3 = null;
        VBox vBox4 = null;

        ArrayList<Integer> seatSorted = new ArrayList<>();

        for (String pos : passFrmTrQ) {
            String[] keyValue2 = pos.split("=-");                 //split name | seat no. &date
            Integer seat = Integer.valueOf(keyValue2[1]);
            if (!pos.equals("EMPTY=-" + seat)) {
                seatSorted.add(seat);
            } else {
                seatSorted.add(seat);
            }
        }

        int loopEnd = seatSorted.size();

        int r = 0;
        for (int t = 1; t < 43; t++) {
            int finals = r;
            if (loopEnd != 0) {

                if (t == seatSorted.get(finals)) {

                    String pssFllName = passFrmTrQ.get(finals);
                    System.out.println(pssFllName);
                    if (!pssFllName.equals("EMPTY=-" + t)) {
                        String[] keyValueNm = pssFllName.split("=");
                        this.trainList.add(keyValueNm[0]);
                        r++;
                        loopEnd--;
                    //    System.out.println(this.trainList);
                    } else {

                        this.trainList.add("EMPTY");
                        r++;
                        loopEnd--;
                       // System.out.println(this.trainList);
                    }
                } else {
                    //   System.out.println("nn");
                    this.trainList.add("EMPTY");
                }
            } else {
                this.trainList.add("EMPTY");
            }

        }


        for (int j = 1; j < 5; j++) {
            vbox = new VBox();
            vbox.setId(Integer.toString(j));
            vbox.setPadding(new Insets(30, 5, 20, 5));

            if (j == 1) {
                start = 1;
                end = 12;
            } else if (j == 2) {
                start = 12;
                end = 22;
            } else if (j == 3) {
                start = 22;
                end = 32;
            } else {
                start = 32;
                end = 43;
            }
            int finalIs = start;
            int finalIe = end;


            for (int i = finalIs; i < finalIe; i++) {


                String btnName = this.trainList.get(i - 1);
                Button btns = new Button(btnName + "=" + i);
                if (!btnName.equals("EMPTY")){
                    btns.setStyle("-fx-background-color: #062C4C; -fx-text-fill: white;" );
                } else {
                    btns.setStyle("-fx-background-color: #A9A9A9");
                }
                vbox.setSpacing(5);
                btns.setId(Integer.toString(i));
                vbox.getChildren().add(btns);
            }
            if (j == 1) {
                vBox1 = new VBox(vbox);
            } else if (j == 2) {
                vBox2 = new VBox(vbox);
            } else if (j == 3) {
                vBox3 = new VBox(vbox);
            } else {
                vBox4 = new VBox(vbox);
            }
        }

        this.trainList.clear();

        labelF1.setText("BOOKED SEATS");
        labelF1.setStyle("-fx-padding: 4;");

        HBox hbox = new HBox(10, vBox1, vBox2, labelH, vBox3, vBox4);
        HBox hboxF1 = new HBox(10, btnFEmty, labelF1);

        Label headLbl = new Label("SEAT MAP");
        headLbl.setFont(new Font("Arial", 18));
        headLbl.setStyle("-fx-font-weight: bold;" + "-fx-text-fill: #062C4C;");
        HBox headbx = new HBox( headLbl);
        headbx.setPadding(new Insets(5, 100, 10, 250));

        VBox vboxWF = new VBox(10, hboxF1);
        VBox vboxW = new VBox(10,headbx, hbox, vboxWF);

        btnFEmty.setStyle("-fx-background-color: #A9A9A9; ");
        btnFBkd.setStyle("-fx-background-color: #062C4C; ");
        vboxWF.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 1;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: black;");

        Scene scene = new Scene(vboxW, 600, 600);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void deletePassenger(ArrayList<String> fnlListC2B, ArrayList<String> sortedFnlListC2B) {
        Scanner input1 = new Scanner(System.in);
        System.out.println("Enter First Name:");
        String fname = input1.next().toLowerCase();
        System.out.println("Enter Surname:");
        String sname = input1.next().toLowerCase();
        int index = 0;

        ArrayList<String> passFrmTrQ = new ArrayList<String>();
        ArrayList<String> passFrmRm = new ArrayList<String>();
        ArrayList<String> timeArray = new ArrayList<String>();


        for (int i = 0; i < TrainStation.trainQueue.getSize(); i++) {
            TrainStation.trainQueue.setPassng(i);
       //     System.out.println(TrainStation.trainQueue.getPassng());
            passFrmTrQ.add(TrainStation.trainQueue.getPassng() + "~" + String.valueOf(TrainStation.trainQueue.getSecond()));
            TrainStation.trainQueue.setSecond(i);
        }

        // clear tranqueue
    //    TrainStation.trainQueue.show();         //METHOD CREATED IN PASSENGERQUEUEU CLASS
        TrainStation.trainQueue.clear();        //METHOD CREATED IN PASSENGERQUEUEU CLASS


        int del = 0;
        for (String pos2 : passFrmTrQ) {
         //   System.out.println(pos2);
            String[] keyValue2 = pos2.split("=-");                 //split name | seat no. &date
            String SnT = keyValue2[1];               // seat no
            String PName2 = keyValue2[0];
            String[] keyname = PName2.split(" ");
            String[] keytime = SnT.split("~");
            String seat = keytime[0];
            String seconds = keytime[1];
            String fNm = keyname[0];
            String sNm = keyname[1];
         //   System.out.println(SnT);
            if (!(fname + " " + sname).equals(fNm + " " + sNm)) {
                Passenger passngr = new Passenger();
                passngr.setName(fNm, sNm);
                passngr.setSeat(Integer.parseInt(seat));
                passngr.setSecond(Integer.parseInt(seconds));
                TrainStation.trainQueue.add(passngr);
            } else {
                del = 1;
            }
            index++;
        }

        if (del == 1 ){
            System.out.println("Passenger " + fname + " " + sname + " was removed from the train queue.");
        } else {
            System.out.println("There are no seats booked for this name.");
        }

        TrainStation.trainQueue.show();

    }


    private void storeTrainQueueDetails(ArrayList<String> fnlListC2B, ArrayList<String> sortedFnlListC2B, File newFileTrain, ArrayList<Integer> seatNArr, DBCollection collection, PassengerQueue trainQueue) {


        if (TrainStation.trainQueue.getSize() == 0){
            System.out.println("Train queue is empty");
        } else {
            TrainStation.collection.drop();          //erase the old database before storing data

            Map<String, Object> docMap = new HashMap<>();
            ArrayList<String> passFrmTrQ = new ArrayList<String>();

            for (int i = 0; i < TrainStation.trainQueue.getSize(); i++) {
                TrainStation.trainQueue.setName(i);
                TrainStation.trainQueue.setSecond(i);
                TrainStation.trainQueue.setSeat(i);

                BasicDBObject object = new BasicDBObject();
                object.put("FULL_NAME", TrainStation.trainQueue.getName());
                object.put("SEAT_NO", TrainStation.trainQueue.getSeat());
                object.put("secondsInQueue", TrainStation.trainQueue.getSecond());

                collection.insert(object);

            }
        }

    }

    private void loadTrainQueueDetails(ArrayList<String> sortedFnlListC2B, ArrayList<String> sortedFnlListB2C, File newFileTrain, ArrayList<Integer> seatNArr, DBCollection collection, PassengerQueue trainQueue) throws IOException {


        DBCursor cursor = collection.find();
        Iterator<DBObject> fields = cursor.iterator();
        Map map = new HashMap<>();
        while (cursor.hasNext()) {
            map = cursor.next().toMap();
            String fllName = (String) map.get("FULL_NAME");
            Integer seat = (Integer) map.get("SEAT_NO");
            Integer time = (Integer) map.get("secondsInQueue");
            String passDtl = fllName + "=-" + seat;
            String[] keyVal = fllName.split(" ");
            String fstNm = keyVal[0];
            String srNm = keyVal[1];

            Passenger pssngr = new Passenger();
            pssngr.setName(fstNm, srNm);
            pssngr.setSeat(seat);
            pssngr.setSecond(time);
            System.out.println(pssngr.getSecond());
            TrainStation.trainQueue.add(pssngr);

        }

        TrainStation.trainQueue.show();

    }

    public class Person {

        private String firstName = null;
        private String lastName = null;
        private String seatNo = null;
        private String waitedTime = null;
        private String avgWaitedTime = null;

        public Person() {
        }

        public Person(String firstName, String lastName, String seatNo, String waitedTime, String avgWaitedTime) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.seatNo = seatNo;
            this.waitedTime = waitedTime;
            this.avgWaitedTime = avgWaitedTime;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getSeatNo() {
            return seatNo;
        }

        public void setSeatNo(String seatNo) {
            this.seatNo = seatNo;
        }

        public String getWaitedTime() {
            return waitedTime;
        }

        public void setWaitedTime(String waitedTime) {
            this.waitedTime = waitedTime;
        }

        public String getAvgWaitedTime() {
            return avgWaitedTime;
        }

        public void setAvgWaitedTime(String avgWaitedTime) {
            this.avgWaitedTime = avgWaitedTime;
        }

    }

    private void runTheSimulation(ArrayList<String> sortedFnlListC2B, ArrayList<String> sortedFnlListB2C, File newFileTrain, ArrayList<Integer> seatNArr, PassengerQueue trainQueue, Passenger passengerDt, File report) {

        ArrayList<String> pssIndDtls = new ArrayList<String>();
        ArrayList<String> trainqDtls = new ArrayList<String>();
        ArrayList<Integer> waitingTimes = new ArrayList<Integer>();

        Random random = new Random();
        int avgTime = 0;



        for (int i = 0; i < TrainStation.trainQueue.getSize(); i++) {

            TrainStation.trainQueue.setName(i);
            TrainStation.trainQueue.setSeat(i);
            TrainStation.trainQueue.setSecond(i);

            pssIndDtls.add(TrainStation.trainQueue.getFirstName());
            pssIndDtls.add(TrainStation.trainQueue.getSurname());
            pssIndDtls.add(TrainStation.trainQueue.getSeat().toString());
            pssIndDtls.add(TrainStation.trainQueue.getSecond().toString());
            pssIndDtls.add(String.valueOf(TrainStation.trainQueue.getSecond() / (i + 1)));

            trainqDtls.add("FIRST NAME = " + TrainStation.trainQueue.getFirstName() + " | SURNAME = " + TrainStation.trainQueue.getSurname() + " | SEAT = " + TrainStation.trainQueue.getSeat().toString() + " | WAITED TIME = " + TrainStation.trainQueue.getSecond().toString() + " | AVERAGE WAITED TIME = " + String.valueOf(TrainStation.trainQueue.getSecond() / (i + 1)));

        }


        //////////////////////////TEXT FILE INPUT START

        if (this.report == null){
            assert false;
            try (FileWriter fw = new FileWriter(this.report, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {

                for (String trainqDtl : trainqDtls) {
                    bw.newLine();
                    bw.write(String.valueOf(trainqDtl));
                    bw.newLine();
                }

                System.out.println("THE ABOVE DETAILS WAS SAVED IN FILE: TrainQueue Report.txt");
            } catch (IOException e) {
                System.out.println("ERROR WHEN STORING DATA TO THE FILE");
            }
        } else {
            this.report.delete();
            try (FileWriter fw = new FileWriter(this.report, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {

                for (String trainqDtl : trainqDtls) {
                    bw.newLine();
                    bw.write(String.valueOf(trainqDtl));
                    bw.newLine();
                }

                System.out.println("THE ABOVE DETAILS WAS SAVED IN FILE: TrainQueue Report.txt");
            } catch (IOException e) {
                System.out.println("ERROR WHEN STORING DATA TO THE FILE");
            }
        }
        //////////////////////////TEXT FILE INPUT ENDS


        int loops = TrainStation.trainQueue.getSize();

        int finalAvg = avgTime;


        Stage primaryStage = new Stage();

        TableView tableView = new TableView();

        TableColumn<String, Person> column1 = new TableColumn<>("First Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<String, Person> column2 = new TableColumn<>("Last Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<String, Person> column3 = new TableColumn<>("Seat No");
        column3.setCellValueFactory(new PropertyValueFactory<>("seatNo"));

        TableColumn<String, Person> column4 = new TableColumn<>("Waited Time");
        column4.setCellValueFactory(new PropertyValueFactory<>("waitedTime"));

        TableColumn<String, Person> column5 = new TableColumn<>("Avg Waited Time");
        column5.setCellValueFactory(new PropertyValueFactory<>("avgWaitedTime"));


        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column5);

        //   for (String details : pssIndDtls){
        int j = 0;
        for (int l = 0; l < pssIndDtls.size() / 5; l++) {
            tableView.getItems().add(new Person(pssIndDtls.get(l + j), pssIndDtls.get(l + 1 + j), pssIndDtls.get(l + 2 + j), pssIndDtls.get(l + 3 + j), pssIndDtls.get(l + 4 + j)));
            j = j + 4;
        }
        //   }


        Label topLabl = new Label("TRAIN QUEUE REPORT");
        topLabl.setStyle("-fx-font-weight: bold;" + "-fx-text-fill: #062C4C;");
        HBox topHbx = new HBox(topLabl);
        Label btmLbl1 = new Label("MAXIMUM LENGTH OF THE QUEUE ATTAINED");
        Label btmLbl2 = new Label("MAXIMUM WAITING TIME");
        Label btmLbl3 = new Label("MINIMUM WAITING TIME");
        Label btmLbl4 = new Label("AVERAGE TIME OF PASSENGERS");
        VBox btmLftVbx = new VBox(btmLbl1, btmLbl2, btmLbl3, btmLbl4);
        Label btmRLbl1 = new Label("   " + TrainStation.trainQueue.getSize());
        TrainStation.trainQueue.setSecond(0);
        Label btmRLbl2 = new Label("   " + TrainStation.trainQueue.getSecond());
        TrainStation.trainQueue.setSecond(TrainStation.trainQueue.getSize() - 1);
        Label btmRLbl3 = new Label("   " + TrainStation.trainQueue.getSecond());

        Label btmRLbl4 = new Label("   " + TrainStation.trainQueue.getSecond() / TrainStation.trainQueue.getSize());

        for ( int i = 0; i < loops; i++ ){

            System.out.println("=========================================================");
            System.out.println("=========================================================");
            System.out.println("CHECKING IN - " );
            System.out.println("WAIT FOR SECONDS ");
            System.out.println("=========================================================");

            TrainStation.trainQueue.removeAll();
            TrainStation.trainQueue.show();

        }

        VBox btmRghtVbx = new VBox(btmRLbl1, btmRLbl2, btmRLbl3, btmRLbl4);
        HBox bttmHbx = new HBox(btmLftVbx, btmRghtVbx);
        VBox vbox = new VBox(topHbx, tableView, bttmHbx);

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);

        primaryStage.showAndWait();

    }

}


