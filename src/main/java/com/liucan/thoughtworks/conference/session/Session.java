package com.liucan.thoughtworks.conference.session;

import com.liucan.thoughtworks.conference.Talk;

import java.util.List;

/**
 * 会议session接口,一个session包含多个talks
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
