package com.liucan.thoughtworks.conference.session;

import com.liucan.thoughtworks.conference.Talk;

import java.util.Collections;
import java.util.List;

/**
 * 将lunch也当做session统一管理，只包含一个talk
 *
 * @author liucan
 * @version 19-9-2
 */
public class LunchSession implements Session {
    private Talk talk;

    public LunchSession(Talk talk) {
        this.talk = talk;
    }

    @Override
    public List<Talk> sessions() {
        return Collections.singletonList(talk);
    }

    @Override
    public void addTalk(Talk talk) {
        this.talk = talk;
    }

    @Override
    public String toString() {
        return talk.getStartTime() + " " + talk.getTitle() + System.lineSeparator();
    }
}
