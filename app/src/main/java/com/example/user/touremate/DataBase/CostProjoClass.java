package com.example.user.touremate.DataBase;

/**
 * Created by User on 4/9/2018.
 */

public class CostProjoClass {
    private int CostAutoincrementID;
    private int AutoincrementByTourPlan;
    private String CostName;
    private double CostAmount;
    private String CostDetails;
    private String CosteTime;

    public CostProjoClass(int autoincrementByTourPlan, String costName, double costAmount, String costDetails, String costeTime) {
        AutoincrementByTourPlan = autoincrementByTourPlan;
        CostName = costName;
        CostAmount = costAmount;
        CostDetails = costDetails;
        CosteTime = costeTime;
    }

    public CostProjoClass(int costAutoincrementID, int autoincrementByTourPlan, String costName, double costAmount, String costDetails, String costeTime) {
        CostAutoincrementID = costAutoincrementID;
        AutoincrementByTourPlan = autoincrementByTourPlan;
        CostName = costName;
        CostAmount = costAmount;
        CostDetails = costDetails;
        CosteTime = costeTime;
    }

    public int getCostAutoincrementID() {
        return CostAutoincrementID;
    }

    public void setCostAutoincrementID(int costAutoincrementID) {
        CostAutoincrementID = costAutoincrementID;
    }

    public int getAutoincrementByTourPlan() {
        return AutoincrementByTourPlan;
    }

    public void setAutoincrementByTourPlan(int autoincrementByTourPlan) {
        AutoincrementByTourPlan = autoincrementByTourPlan;
    }

    public String getCostName() {
        return CostName;
    }

    public void setCostName(String costName) {
        CostName = costName;
    }

    public double getCostAmount() {
        return CostAmount;
    }

    public void setCostAmount(double costAmount) {
        CostAmount = costAmount;
    }

    public String getCostDetails() {
        return CostDetails;
    }

    public void setCostDetails(String costDetails) {
        CostDetails = costDetails;
    }

    public String getCosteTime() {
        return CosteTime;
    }

    public void setCosteTime(String costeTime) {
        CosteTime = costeTime;
    }
}
