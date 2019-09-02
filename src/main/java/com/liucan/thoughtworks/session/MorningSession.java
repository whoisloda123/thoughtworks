package com.liucan.thoughtworks.session;

import com.liucan.thoughtworks.talk.Talk;

import java.util.List;

/**
 * 上午session
 *
 * @author liucan
 * @version 19-9-2
 */
public class MorningSession implements Session {

    private List<Talk> talks;

    public MorningSession(List<Talk> talks) {
        this.talks = talks;
    }

    @Override
    public List<Talk> sessions() {
        return talks;
    }

    @Override
    public void addTalk(Talk talk) {
        talks.add(talk);
    }
}
