package com.louisaseever.memberstatus;

/**
 * Created by LouisaSeever on 8/11/2015.
 */

public class Member {

    private int _id = -1;
    private String _userName;
    private String _lastName;
    private String _firstName;
    private int _age;


    public Member(int id, String lastName, String firstName, String userName, int age){
        _id = id;
        _firstName = firstName;
        _lastName = lastName;
        _userName = userName;
        _age = age;
    }

    public Member(String lastName, String firstName, String userName, int age){
        _firstName = firstName;
        _lastName = lastName;
        _userName = userName;
        _age = age;
    }

    public Member(String lastName, String firstName){
        _firstName = firstName;
        _lastName = lastName;
        _userName = firstName;
        _age = 0;
    }


    public int getId(){
        return _id;
    }

    public void setId(int id){
        _id = id;
    }

    public String getUserName() {
        return _userName;
    }

    public void setUserName(String userName) {
        this._userName = _userName;
    }

    public String getLastName() {
        return _lastName;
    }

    public void setLastName(String lastName) {
        this._lastName = lastName;
    }

    public String getFirstName() {
        return _firstName;
    }

    public void setFirstName(String firstName) {
        this._firstName = _firstName;
    }

    public int getAge(){
        return _age;
    }
    public void setAge(int age){
        if (age>=0){
            _age=age;
        }
    }


    public void fromString(String input){
        _userName = input;
        throw new UnsupportedOperationException("fromString() not yet implemented");
    }


    @Override
    public String toString(){
        String output = String.format("id=%d",_id);
        output += "\n LastName=" + _lastName;
        output += "\n FirstName=" +_firstName;
        output += "\n UserName=" + _userName;
        output += String.format("\n age=%d", _age);

        return output;
    }
}
