package com.liucan.thoughtworks;

import com.liucan.thoughtworks.strategy.DefaultSessionStragegy;

public class Main {

    public static void main(String[] args) {
        new ConferenceManager(new DefaultSessionStragegy()).tracks("input.txt");
    }
}
