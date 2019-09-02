package com.liucan.thoughtworks;

import com.liucan.thoughtworks.strategy.DefaultSessionStrategy;
import com.liucan.thoughtworks.track.Track;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
        List<Track> tracks = conferenceManager.tracks("input.txt");
        if (tracks.isEmpty()) {
            System.out.println("未分配成功！");
            return;
        }
        String s = conferenceManager.toString();
        System.out.println(s);
    }
}