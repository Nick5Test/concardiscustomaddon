package com.alten.testsigma.addons.generators;

import com.testsigma.sdk.TestDataFunction;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.TestData;
import com.testsigma.sdk.annotation.TestDataFunctionParameter;
import lombok.Data;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.Locale;

@Data
@com.testsigma.sdk.annotation.TestDataFunction(displayName = "Current month",
        description = "restituisce il mese due mesi fa")
public class NexiPay_currentMonth extends TestDataFunction {


    @Override
    public TestData generate() throws Exception {
        YearMonth currentYearMonth = YearMonth.now();
        YearMonth twoMonthsAgo = currentYearMonth.minusMonths(2);
        Month twoMonthsAgoMonth = twoMonthsAgo.getMonth();
        Year currentYear = Year.of(currentYearMonth.getYear());
        String monthName = twoMonthsAgoMonth.toString();
        String year = currentYear.toString();
        System.out.println("Il mese corrente è: " + monthName.toUpperCase());
        String mese="";

        switch (monthName){
            case "JANUARY":
                mese = "en";
                break;
            case "FEBRUARY":
                mese = "eb";
                break;
            case "MARCH":
                mese = "ar";
                break;
            case "APRIL":
                mese = "pr";
                break;
            case "MAY":
                mese = "ag";
                break;
            case "JUNE":
                mese = "iu";
                break;
            case "JULY":
                mese = "ug";
                break;
            case "AUGUST":
                mese = "go";
                break;
            case "SEPTEMBER":
                mese = "et";
                break;
            case "OCTOBER":
                mese = "tt";
                break;
            case "NOVEMBER":
                mese = "ov";
                break;
            case "DECEMBER":
                mese = "ic";
                break;
        }
        String halfDate = mese + " " + year;
        TestData testData = new TestData(halfDate);
        return testData;
    }
}