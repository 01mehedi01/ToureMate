package com.example.user.touremate.DataBase;

import android.util.Log;
import android.widget.Toast;

/**
 * Created by User on 4/6/2018.
 */

public class EventProjoClass {

    private int AutoincrementEvent;
    private int UserLohinID;
    private String EventName;
    private String StartDate;
    private String ReturnDate;
    private Double EstimateBudget;

    public EventProjoClass(int autoincrementEvent,Double estimateBudget) {
        AutoincrementEvent = autoincrementEvent;
        EstimateBudget = estimateBudget;
    }

    public EventProjoClass(int autoincrementEvent, String eventName, String startDate, Double estimateBudget, String returnDate) {

        AutoincrementEvent = autoincrementEvent;
        EventName = eventName;
        StartDate = startDate;
        EstimateBudget = estimateBudget;
        ReturnDate = returnDate;
    }

    public EventProjoClass(int autoincrementEvent, String eventName, String startDate, String returnDate, Double estimateBudget,int userLohinID) {
        AutoincrementEvent = autoincrementEvent;
        EventName = eventName;
        ReturnDate = returnDate;
        EstimateBudget = estimateBudget;
        StartDate = startDate;
        UserLohinID = userLohinID;

    }


    public EventProjoClass(int userLohinID, String eventName, String startDate, String returnDate, Double estimateBudget) {
        UserLohinID = userLohinID;
        EventName = eventName;
        StartDate = startDate;
        ReturnDate = returnDate;
        EstimateBudget = estimateBudget;
    }

    public EventProjoClass() {
    }

    public int getAutoincrementEvent() {
        return AutoincrementEvent;
    }

    public void setAutoincrementEvent(int autoincrementEvent) {
        AutoincrementEvent = autoincrementEvent;
    }

    public int getUserLohinID() {
        return UserLohinID;
    }

    public void setUserLohinID(int userLohinID) {
        UserLohinID = userLohinID;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(String returnDate) {
        ReturnDate = returnDate;
    }

    public Double getEstimateBudget() {
        return EstimateBudget;
    }

    public void setEstimateBudget(Double estimateBudget) {
        EstimateBudget = estimateBudget;
    }
}
