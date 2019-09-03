package com.liucan.thoughtworks.common.util;

import com.liucan.thoughtworks.conference.Talk;

import java.util.List;

/**
 * 通用工具类
 * @author liucan
 * @version 19-9-2
 */
public class ConferenceUtil {
    //最小session时间3个小时
    public final static int MIN_SESSION_TIME = 180;
    //最大session时间4个小时
    public final static int MAX_SESSION_TIME = 240;
    //午餐时间长度，分
    public final static int LUNCH_DURATION = 60;

    /**
     * 获取talksList总的duration时间
     */
    public static int getTotalTalksTime(List<Talk> talksList) {
        if (talksList == null || talksList.isEmpty()) {
            return 0;
        }
        return talksList.stream().mapToInt(Talk::getDuration).sum();
    }
}
