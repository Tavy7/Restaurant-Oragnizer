package ro.unibuc.myapplication.Models;

import java.util.Date;

public class DateNavBarModel {
    String dayDate;

    public DateNavBarModel(String dayDate){
        this.dayDate = dayDate;
    }

    public String getDayDate() {
        return dayDate;
    }
}
