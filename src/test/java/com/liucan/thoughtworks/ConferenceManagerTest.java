package com.liucan.thoughtworks;

import com.liucan.thoughtworks.strategy.DefaultSessionStrategy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author liucan
 * @version 19-9-2
 */
public class ConferenceManagerTest extends BaseJunit4Test {
    private ConferenceManager conferenceManager = new ConferenceManager(new DefaultSessionStrategy());

    @Before
    public void before() {
        System.out.println("测试会议管理开始");
    }

    @After
    public void after() {
        System.out.println("测试A会议管理结束");
    }

    @Test
    public void tracks() {
        conferenceManager.tracks("input.txt");
        String s = conferenceManager.toString();
        System.out.println(s);
    }
}