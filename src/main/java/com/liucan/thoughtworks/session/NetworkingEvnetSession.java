package com.liucan.thoughtworks.session;

import com.liucan.thoughtworks.talk.Talk;

import java.util.Collections;
import java.util.List;

/**
 * 交友session，只包含一个session
 *
 * @author liucan
 * @version 19-9-2
 */
public class NetworkingEvnetSession implements Session {
    private Talk talk;

    public NetworkingEvnetSession(Talk talk) {
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
