package com.liucan.thoughtworks.conference;

import com.liucan.thoughtworks.conference.session.Session;

/**
 * 一个会议包含多个track，一个track包含MorningSession和AfternoonSession,LunchSession,NetworkingEventSession
 *
 * @author liucan
 * @version 19-9-2
 */
public interface Track {
    /**
     * 返回morning session
     */
    Session morningSession();

    /**
     * 返回lunch session
     */
    Session lunchSession();

    /**
     * 返回afternoon session
     */
    Session afternoonSession();

    /**
     * 返回networking event session
     */
    Session networkingEventSession();
}
