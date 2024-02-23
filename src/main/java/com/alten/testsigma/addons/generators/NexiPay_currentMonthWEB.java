package com.alten.testsigma.addons.generators;

import com.testsigma.sdk.Result;
import com.testsigma.sdk.TestData;
import com.testsigma.sdk.TestDataFunction;
import lombok.Data;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;

@Data
@com.testsigma.sdk.annotation.TestDataFunction(displayName = "Current month WEB",
        description = "restituisce il mese corrente")
public class NexiPay_currentMonthWEB extends TestDataFunction {


    @Override
    public TestData generate() throws Exception {
        com.testsigma.sdk.Result result;
        YearMonth currentYearMonth = YearMonth.now();
        YearMonth previousYearMonth = currentYearMonth.minusMonths(1);
        Month previousMonth = previousYearMonth.getMonth();
        Year currentYear = Year.of(currentYearMonth.getYear());
        String monthName = previousMonth.toString();
        String year = currentYear.toString();
        System.out.println("Il mese corrente è: " + monthName.toUpperCase());
        String mese="";

        switch (monthName){
            case "JANUARY":
                mese = "Gennaio";
                break;
            case "FEBRUARY":
                mese = "Febbraio";
                break;
            case "MARCH":
                mese = "Marzo";
                break;
            case "APRIL":
                mese = "Aprile";
                break;
            case "MAY":
                mese = "Maggio";
                break;
            case "JUNE":
                mese = "Giugno";
                break;
            case "JULY":
                mese = "Luglio";
                break;
            case "AUGUST":
                mese = "Agosto";
                break;
            case "SEPTEMBER":
                mese = "Settembre";
                break;
            case "OCTOBER":
                mese = "Ottobre";
                break;
            case "NOVEMBER":
                mese = "Novembre";
                break;
            case "DECEMBER":
                mese = "Dicembre";
                break;
        }
        String halfDate = mese + " " + year;
        setSuccessMessage("Store date: "+halfDate);
        result = Result.SUCCESS;
        TestData testData = new TestData(halfDate);
        return testData;
    }
}