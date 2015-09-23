package com.louisaseever.memberstatus;

import java.sql.Date;

/**
 * Created by LouisaSeever on 8/13/2015.
 */

public class CompletedAssignment {
    private static final String TAG = "CompletedAssignment";


    int _assignmentId = -1;
    int _memberId = -1;
    int _assignedPoints = 0;

    public CompletedAssignment(int memberId, int assignmentId, int assignedPoints){
        _assignmentId = assignmentId;
        _memberId = memberId;
        _assignedPoints = assignedPoints;
    }

    public CompletedAssignment(Member member, Assignment assignment, int assignedPoints){
        _assignmentId = assignment.getId();
        _memberId = member.getId();
        _assignedPoints = assignedPoints;
    }


    public int getAssignmentId() {
        return _assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this._assignmentId = assignmentId;
    }

    public int getMemberId() {
        return _memberId;
    }

    public void setMemberId(int memberId) {
        this._memberId = memberId;
    }

    public int getAssignedPoints() {
        return _assignedPoints;
    }

    public void setAssignedPoints(int assignedPoints) {
        this._assignedPoints = assignedPoints;
    }

    @Override
    public String toString() {
        return "CompletedAssignment{" +
                "assignmentId=" + _assignmentId +
                ", memberId=" + _memberId +
                ", assignedPoints=" + _assignedPoints +
                '}';
    }
}
