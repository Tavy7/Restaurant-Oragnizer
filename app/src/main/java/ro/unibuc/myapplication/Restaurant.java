package ro.unibuc.myapplication;

import java.util.ArrayList;

import ro.unibuc.myapplication.Models.Employee;
import ro.unibuc.myapplication.Models.Schedule;
import ro.unibuc.myapplication.Models.Table;

public class Restaurant {
    protected String name;
    protected boolean isOpen;
    protected ArrayList<Employee> employees;
    protected ArrayList<Table> tables;
    protected ArrayList<Schedule> calendar;
    protected Admin admin;

    protected static final String dbName = "Restaurant-Database";
}
