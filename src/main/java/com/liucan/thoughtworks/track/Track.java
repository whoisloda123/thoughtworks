package com.liucan.thoughtworks.track;

import com.liucan.thoughtworks.session.Session;

/**
 * 一个会议包含多个track，一个track包含MorningSession和AfternoonSession,LunchSession
 *
 * @author liucan
 * @version 19-9-2
 */
public class Track {
    private final Session morningSession;
    private final Session lunchSession;
    private final Session afternoonSession;
    private final Session networkingSession;

    public Track(Session morningSession,
                 Session lunchSession,
                 Session afternoonSession,
                 Session networkingSession) {
        this.morningSession = morningSession;
        this.lunchSession = lunchSession;
        this.afternoonSession = afternoonSession;
        this.networkingSession = networkingSession;
    }
}
