package com.liucan.thoughtworks.conference.session;

import com.liucan.thoughtworks.conference.Talk;

import java.util.List;

/**
 * morning session包含多个talks
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        talks.forEach(talk -> {
            stringBuilder.append(talk.getStartTime() + " " + talk.getTitle());
            stringBuilder.append(System.lineSeparator());
        });
        return stringBuilder.toString();
    }
}
