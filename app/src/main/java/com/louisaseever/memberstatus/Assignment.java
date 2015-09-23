package com.louisaseever.memberstatus;

import java.security.InvalidParameterException;

/**

 * Created by LouisaSeever on 8/13/2015.
 */
public class Assignment {
    private static final String TAG = "Assignment";

    private int _id;
    private String _category;
    private String _description;
    private int _minPoints;
    private int _maxPoints;

    public Assignment(){
        _id = -1;
        _category = "";
        _description = "";
        _minPoints = 0;
        _maxPoints = 0;
    }

    public Assignment(String category, String description, int minPoints, int maxPoints){
        verifyPoints(minPoints, maxPoints);
        _id = -1;
        _category = category;
        _description = description;
         _minPoints = minPoints;
        _maxPoints = maxPoints;
    }

    public Assignment(int id, String category, String description, int minPoints, int maxPoints){
        verifyPoints(minPoints, maxPoints);
        _id = id;
        _category = category;
        _description = description;
        _minPoints = minPoints;
        _maxPoints = maxPoints;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "_category='" + _category + "\'" +
                "_description='" + _description + '\'' +
                ", _minPoints=" + _minPoints +
                ", _maxPoints=" + _maxPoints +
                '}';
    }

    public void fromString(){
        throw new UnsupportedOperationException("fromString not yet implemented");

    }
    public int getId() {
        return _id;
    }

    public int getMaxPoints() {
        return _maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this._maxPoints = maxPoints;
    }

    public int getMinPoints() {

        return _minPoints;
    }

    public void setMinPoints(int minPoints) {

        this._minPoints = minPoints;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String _description) {
        this._description = _description;
    }

    public String getCategory() {

        return _category;
    }

    public void setCategory(String category) {
        this._category = category;
    }

    private void verifyPoints(int minPoints, int maxPoints){
        if (minPoints > maxPoints){
            throw new InvalidParameterException("MinPoints must be less than or equal masPoints");
        }
    }
}
