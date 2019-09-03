package com.liucan.thoughtworks.conference.session;

import com.liucan.thoughtworks.conference.Talk;

import java.util.List;

/**
 * afternoonSession包含多个talks
 *
 * @author liucan
 * @version 19-9-2
 */
public class AfternoonSession implements Session {

    private List<Talk> talks;

    public AfternoonSession(List<Talk> talks) {
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Talk talk : talks) {
            stringBuilder.append(talk.getStartTime() + " " + talk.getTitle());
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}
