package ro.unibuc.myapplication.Models;

public class DateNavBarModel {
    String dayDate;
    boolean isSelected = false;

    public DateNavBarModel(String dayDate){
        this.dayDate = dayDate;
    }

    public String getDayDate() {
        return dayDate;
    }

    public void setDayDate(String dayDate) {
        this.dayDate = dayDate;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
