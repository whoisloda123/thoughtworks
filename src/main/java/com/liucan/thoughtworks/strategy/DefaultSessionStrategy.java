package com.liucan.thoughtworks.strategy;

import com.liucan.thoughtworks.common.util.ConferenceUtil;
import com.liucan.thoughtworks.conference.Talk;
import com.liucan.thoughtworks.conference.session.AfternoonSession;
import com.liucan.thoughtworks.conference.session.MorningSession;
import com.liucan.thoughtworks.conference.session.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认实现morningSession，  afternoonSession策略类，可添加自己实现类
 *
 * @author liucan
 * @version 19-9-2
 */
public class DefaultSessionStrategy implements SessionStrategy {
    //用来限制最大tracks
    private int totalPossibleTracks;

    /**
     * 根据传入的talksList获取所有可能的morningSession
     */
    @Override
    public List<Session> morningSession(List<Talk> talksList) {
        int possibleCombinationCount = 0;
        int maxSessionTimeLimit = ConferenceUtil.MIN_SESSION_TIME;

        List<Session> sessions = new ArrayList<>();

        for (int i = 0; i < talksList.size(); i++) {
            int start = i;
            int totalTime = 0;
            List<Talk> possibleCombinationList = new ArrayList<>();

            while (start != talksList.size()) {
                int currentCount = start;
                start++;
                Talk currentTalk = talksList.get(currentCount);
                if (currentTalk.isArrange()) {
                    continue;
                }
                int currentTalkTime = currentTalk.getDuration();
                /**
                 * 如果当前talk时间大于最大session时间，continue
                 * 如果当前talk时间和totalTime大于最大session时间,continue
                 */
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
                possibleCombinationList.forEach(e -> e.setArrange(true));
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
     * 根据传入的talksList获取所有可能的afternoonSession
     */
    @Override
    public List<Session> afternoonSession(List<Talk> talksList) {
        int minSessionTimeLimit = ConferenceUtil.MIN_SESSION_TIME;
        int maxSessionTimeLimit = ConferenceUtil.MAX_SESSION_TIME;
        int possibleCombinationCount = 0;

        List<Session> sessions = new ArrayList<>();

        for (int i = 0; i < talksList.size(); i++) {
            int start = i;
            int totalTime = 0;
            List<Talk> possibleCombinationList = new ArrayList<>();

            while (start != talksList.size()) {
                int currentCount = start;
                start++;
                Talk currentTalk = talksList.get(currentCount);
                if (currentTalk.isArrange()) {
                    continue;
                }
                int currentTalkTime = currentTalk.getDuration();
                /**
                 * 如果当前talk时间大于最大session时间，continue
                 * 如果当前talk时间和totalTime大于最大session时间,continue
                 */
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
                possibleCombinationList.forEach(e -> e.setArrange(true));
                sessions.add(new AfternoonSession(possibleCombinationList));

                possibleCombinationCount++;
                if (possibleCombinationCount == totalPossibleTracks) {
                    break;
                }
            }
        }
        return sessions;
    }

    public int getTotalPossibleTracks() {
        return totalPossibleTracks;
    }

    public void setTotalPossibleTracks(int totalPossibleTracks) {
        this.totalPossibleTracks = totalPossibleTracks;
    }

}
