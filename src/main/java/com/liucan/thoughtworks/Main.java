package com.liucan.thoughtworks;

import com.liucan.thoughtworks.strategy.DefaultSessionStragegy;

public class Main {

    public static void main(String[] args) {
        ConferenceManager conferenceManager = new ConferenceManager(new DefaultSessionStragegy());
        conferenceManager.tracks("input.txt");

        System.out.println("---------这是分割线------------- ");

        String s = conferenceManager.toString();
        System.out.println(s);
    }
}
