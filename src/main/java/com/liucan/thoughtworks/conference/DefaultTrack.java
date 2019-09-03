package com.liucan.thoughtworks.conference;

import com.liucan.thoughtworks.conference.session.Session;

/**
 * 默认track实现类，可添加自己实现类
 *
 * @author liucan
 * @version 19-9-3
 */
public class DefaultTrack implements Track {
    private final Session morningSession;
    private final Session lunchSession;
    private final Session afternoonSession;
    private final Session networkingSession;

    public DefaultTrack(Session morningSession,
                        Session lunchSession,
                        Session afternoonSession,
                        Session networkingSession) {
        this.morningSession = morningSession;
        this.lunchSession = lunchSession;
        this.afternoonSession = afternoonSession;
        this.networkingSession = networkingSession;
    }

    @Override
    public Session morningSession() {
        return morningSession;
    }

    @Override
    public Session lunchSession() {
        return lunchSession;
    }

    @Override
    public Session afternoonSession() {
        return afternoonSession;
    }

    @Override
    public Session networkingEventSession() {
        return networkingSession;
    }

    @Override
    public String toString() {
        return morningSession.toString()
                + lunchSession.toString()
                + afternoonSession.toString()
                + networkingSession.toString();
    }
}
