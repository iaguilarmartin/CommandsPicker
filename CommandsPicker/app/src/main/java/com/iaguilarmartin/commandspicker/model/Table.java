package com.iaguilarmartin.commandspicker.model;

import java.io.Serializable;

public class Table implements Serializable {
    private int mNumber;
    private Courses mCourses;

    public Table(int number) {
        mNumber = number;
        mCourses = new Courses();
    }

    public Table(int number, Courses courses) {
        mNumber = number;
        mCourses = courses;
    }

    public int getNumber() {
        return mNumber;
    }

    public Courses getCourses() {
        return mCourses;
    }

    public void setCourses(Courses courses) {
        mCourses = courses;
    }

    public Ticket generateTicket() {
        Ticket ticket = new Ticket(mNumber);

        double total = 0;

        for (Course course: mCourses.getStarters()) {
            ticket.addCourse(course);
            total = total + course.getPrice();
        }
        for (Course course: mCourses.getMainCourses()) {
            ticket.addCourse(course);
            total = total + course.getPrice();
        }
        for (Course course: mCourses.getDesserts()) {
            ticket.addCourse(course);
            total = total + course.getPrice();
        }

        ticket.setTotal(total);

        return ticket;
    }
}
