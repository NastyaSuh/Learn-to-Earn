package com.example.learntoearn;

public class Userinformantion {
    public String name;
    public String surname;

    public Userinformantion() {
    }

    public Userinformantion(String name, String surname){
        this.name = name;
        this.surname = surname;
    }

    public String  getUserName(){
        return name;
    }

    public String getUserSurname(){
        return surname;
    }

}
