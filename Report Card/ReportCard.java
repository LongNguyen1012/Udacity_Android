package com.example.android.miwok;

import java.util.ArrayList;
import java.util.Collections;

import static android.media.CamcorderProfile.get;

/**
 * Created by Long Nguyen on 1/2/2017.
 */

public class ReportCard {

    private ArrayList<Integer> nAbsent;
    private ArrayList<Integer> nTardy = new ArrayList<Integer>(3);
    private ArrayList<Integer> mathGrade =  new ArrayList<Integer>(3);
    private ArrayList<Integer> readingGrade = new ArrayList<Integer>(3);
    private ArrayList<Integer> writingGrade = new ArrayList<Integer>(3);
    private ArrayList<Integer> speakingGrade = new ArrayList<Integer>(3);
    private String studentName;
    private String studentID;

    public ReportCard(String input_studentName, String input_id) {
        studentName = input_studentName;
        studentID = input_id;
        nAbsent = new ArrayList<Integer>(Collections.nCopies(3, 0));
        nTardy = new ArrayList<Integer>(Collections.nCopies(3, 0));
        readingGrade = new ArrayList<Integer>(Collections.nCopies(3, 0));
        writingGrade = new ArrayList<Integer>(Collections.nCopies(3, 0));
        speakingGrade = new ArrayList<Integer>(Collections.nCopies(3, 0));
        mathGrade = new ArrayList<Integer>(Collections.nCopies(3, 0));
    }

    public void setAbsent(int absent_time, int current_semester) {
        nAbsent.set(current_semester, absent_time);
    }

    public void setTardy(int tardy_time, int current_semester) {
        nTardy.set(current_semester, tardy_time);
    }

    public void setMathGrade(int grade, int current_semester) {
        mathGrade.set(current_semester, grade);
    }

    public void setReadingGrade(int grade, int current_semester) {
        readingGrade.set(current_semester, grade);
    }

    public void setWritingGrade(int grade, int current_semester) {
        writingGrade.set(current_semester, grade);
    }

    public void setSpeakingGrade(int grade, int current_semester) {
        speakingGrade.set(current_semester, grade);
    }

    public int getAbsent(int current_semester) {
        return nAbsent.get(current_semester);
    }

    public int getTardy(int current_semester) {
        return nTardy.get(current_semester);
    }

    public int getMathGrade(int current_semester) {
        return mathGrade.get(current_semester);
    }

    public int getSpeakkingGrade(int current_semester) {
        return speakingGrade.get(current_semester);
    }

    public int getReadingGrade(int current_semester) {
        return readingGrade.get(current_semester);
    }

    public int getWritingGrade(int current_semester) {
        return writingGrade.get(current_semester);
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentID() {
        return studentID;
    }

    @Override
    public String toString() {
        return "Student name : " + studentName + "\n" +
                "Student ID : " + studentID + "\n" +
                "Absent : " + nAbsent + "\n" +
                "Tardy : " + nTardy + "\n" +
                "Math : " + mathGrade + "\n" +
                "Speaking : " + speakingGrade + "\n" +
                "Writing : " + writingGrade + "\n" +
                "Reading : " + readingGrade;
    }
}
