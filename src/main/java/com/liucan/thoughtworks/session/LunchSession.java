package com.liucan.thoughtworks.session;

import com.liucan.thoughtworks.talk.Talk;

import java.util.Collections;
import java.util.List;

/**
 * 午餐session，只包含一个talk
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
}
