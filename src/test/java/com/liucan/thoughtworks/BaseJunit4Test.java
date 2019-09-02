package com.liucan.thoughtworks;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class BaseJunit4Test {
    @BeforeClass
    public static void beforeClass() {
        System.out.println("junit test begin:");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("junit test end:");
    }

    @Before
    public void before() {
        System.out.println("test fun begin:");
    }

    @After
    public void after() {
        System.out.println("test fun end:");
    }
}
