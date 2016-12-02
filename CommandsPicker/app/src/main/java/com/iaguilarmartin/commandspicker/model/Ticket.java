package com.iaguilarmartin.commandspicker.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by iaguilarmartin on 2/12/16.
 */

public class Ticket implements Serializable {
    private int mTableNumber;
    private ArrayList<TicketRow> mRows;
    private double mTotal;

    public Ticket(int tableNumber) {
        mTableNumber = tableNumber;
        mRows = new ArrayList<TicketRow>();
        mTotal = 0;
    }

    public void addCourse(Course course) {
        int index = -1;

        for (int i = 0; i < mRows.size(); i++) {
            if (mRows.get(i).getCourseId().equalsIgnoreCase(course.getId())) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            TicketRow ticketRow = new TicketRow(1, course.getId(), course.getName(), course.getPrice());
            mRows.add(ticketRow);
        } else {
            TicketRow ticketRow = mRows.get(index);
            int quantity = ticketRow.getQuantity();
            quantity++;

            ticketRow.setQuantity(quantity);
            ticketRow.setTotal(quantity * course.getPrice());
        }
    }

    public int getTableNumber() {
        return mTableNumber;
    }

    public void setTableNumber(int tableNumber) {
        mTableNumber = tableNumber;
    }

    public ArrayList<TicketRow> getRows() {
        return mRows;
    }

    public double getTotal() {
        return mTotal;
    }

    public void setTotal(double total) {
        mTotal = total;
    }

    public class TicketRow implements Serializable {
        private int mQuantity;
        private String mCourseId;
        private String mCourseName;
        private double mTotal;

        public TicketRow(int quantity, String courseId, String courseName, double total) {
            mQuantity = quantity;
            mCourseId = courseId;
            mCourseName = courseName;
            mTotal = total;
        }

        public int getQuantity() {
            return mQuantity;
        }

        public void setQuantity(int quantity) {
            mQuantity = quantity;
        }

        public String getCourseName() {
            return mCourseName;
        }

        public void setCourseName(String courseName) {
            mCourseName = courseName;
        }

        public double getTotal() {
            return mTotal;
        }

        public void setTotal(double total) {
            mTotal = total;
        }

        public String getCourseId() {
            return mCourseId;
        }

        public void setCourseId(String courseId) {
            mCourseId = courseId;
        }

        @Override
        public String toString() {
            return String.format("%d x %s", mQuantity, mCourseName);
        }
    }
}
