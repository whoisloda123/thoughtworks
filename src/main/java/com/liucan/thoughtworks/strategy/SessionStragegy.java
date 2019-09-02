package com.liucan.thoughtworks.strategy;

import com.liucan.thoughtworks.session.Session;
import com.liucan.thoughtworks.talk.Talk;

import java.util.List;

/**
 * session策略类，提供生成上午和下午session的策略方法
 *
 * @author liucan
 * @version 19-9-2
 */
public interface SessionStragegy {
    /**
     * 根据传入的talksList获取所有可能的上午session
     */
    List<Session> morningSession(List<Talk> talksList, int totalPossibleTracks);

    /**
     * 根据传入的talksList获取所有可能的下午session
     */
    List<Session> afternoonSession(List<Talk> talksList, int totalPossibleTracks);
}
