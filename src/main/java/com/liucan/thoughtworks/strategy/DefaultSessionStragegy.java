package com.liucan.thoughtworks.strategy;

import com.liucan.thoughtworks.session.AfternoonSession;
import com.liucan.thoughtworks.session.MorningSession;
import com.liucan.thoughtworks.session.Session;
import com.liucan.thoughtworks.talk.Talk;
import com.liucan.thoughtworks.util.ConferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认session策略类
 *
 * @author liucan
 * @version 19-9-2
 */
public class DefaultSessionStragegy implements SessionStragegy {

    /**
     * 根据传入的talksList获取所有可能的上午session
     */
    @Override
    public List<Session> morningSession(List<Talk> talksList) {
        int minSessionTimeLimit = ConferenceUtil.MIN_SESSION_TIME;
        int maxSessionTimeLimit = ConferenceUtil.MIN_SESSION_TIME;
        int possibleCombinationCount = 0;
        int totalPossibleTracks = ConferenceUtil.getTotalTalksTime(talksList) / 6 * 60;


        List<Session> sessions = new ArrayList<>();

        for (int i = 0; i < talksList.size(); i++) {
            int start = i;
            int totalTime = 0;
            List<Talk> possibleCombinationList = new ArrayList<>();

            while (start != talksList.size()) {
                int currentCount = start;
                start++;
                Talk currentTalk = talksList.get(currentCount);
                if (currentTalk.isScheduled()) {
                    continue;
                }
                int currentTalkTime = currentTalk.getDuration();
                // If the current talk time is greater than maxSessionTimeLimit or
                // sum of the current time and total of talk time added in list  is greater than maxSessionTimeLimit.
                // then continue.
                if (currentTalkTime > maxSessionTimeLimit || currentTalkTime + totalTime > maxSessionTimeLimit) {
                    continue;
                }
                possibleCombinationList.add(currentTalk);
                totalTime += currentTalkTime;

                if (totalTime == maxSessionTimeLimit) {
                    break;
                }
            }

            //有效session
            if (totalTime == maxSessionTimeLimit) {
                possibleCombinationList.forEach(e -> e.setScheduled(true));
                sessions.add(new MorningSession(possibleCombinationList));

                possibleCombinationCount++;
                if (possibleCombinationCount == totalPossibleTracks) {
                    break;
                }
            }
        }
        return sessions;
    }

    /**
     * 根据传入的talksList获取所有可能的下午session
     */
    @Override
    public List<Session> afternoonSession(List<Talk> talksList) {
        int minSessionTimeLimit = ConferenceUtil.MIN_SESSION_TIME;
        int maxSessionTimeLimit = ConferenceUtil.MAX_SESSION_TIME;
        int possibleCombinationCount = 0;
        int totalPossibleTracks = ConferenceUtil.getTotalTalksTime(talksList) / 6 * 60;


        List<Session> sessions = new ArrayList<>();

        for (int i = 0; i < talksList.size(); i++) {
            int start = i;
            int totalTime = 0;
            List<Talk> possibleCombinationList = new ArrayList<>();

            while (start != talksList.size()) {
                int currentCount = start;
                start++;
                Talk currentTalk = talksList.get(currentCount);
                if (currentTalk.isScheduled()) {
                    continue;
                }
                int currentTalkTime = currentTalk.getDuration();
                // If the current talk time is greater than maxSessionTimeLimit or
                // sum of the current time and total of talk time added in list  is greater than maxSessionTimeLimit.
                // then continue.
                if (currentTalkTime > maxSessionTimeLimit || currentTalkTime + totalTime > maxSessionTimeLimit) {
                    continue;
                }
                possibleCombinationList.add(currentTalk);
                totalTime += currentTalkTime;

                if (totalTime >= maxSessionTimeLimit) {
                    break;
                }
            }

            //有效session
            if (totalTime >= minSessionTimeLimit && totalTime <= maxSessionTimeLimit) {
                possibleCombinationList.forEach(e -> e.setScheduled(true));
                sessions.add(new AfternoonSession(possibleCombinationList));

                possibleCombinationCount++;
                if (possibleCombinationCount == totalPossibleTracks) {
                    break;
                }
            }
        }
        return sessions;
    }
}
