package com.liucan.thoughtworks.session;

import com.liucan.thoughtworks.talk.Talk;

import java.util.List;

/**
 * 会议session，包含早上和下午session,每个session包含多个talk
 *
 * @author liucan
 * @version 19-9-2
 */
public interface Session {
    /**
     * 返回当前session包含的talk
     */
    List<Talk> sessions();

    void addTalk(Talk talk);
}
