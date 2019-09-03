package com.liucan.thoughtworks.conference;

import com.liucan.thoughtworks.common.util.ConferenceUtil;
import com.liucan.thoughtworks.conference.session.LunchSession;
import com.liucan.thoughtworks.conference.session.NetworkingEventSession;
import com.liucan.thoughtworks.conference.session.Session;
import com.liucan.thoughtworks.strategy.DefaultSessionStrategy;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 默认track工厂实现类，可添加自己实现类
 *
 * @author liucan
 * @version 19-9-3
 */
public class DefaultTrackFactory implements TrackFactory {

    private DefaultSessionStrategy sessionStrategy = new DefaultSessionStrategy();

    @Override
    public List<Track> newTracks(List<Talk> talkList) {
        //先排序
        //talkList.sort(Comparator.comparing(Talk::getDuration).reversed());

        int totalPossibleTracks = ConferenceUtil.getTotalTalksTime(talkList) / (6 * 60);
        sessionStrategy.setTotalPossibleTracks(totalPossibleTracks);

        List<Session> morningSessions = sessionStrategy.morningSession(talkList);
        //删除已经有的上午session
        morningSessions.forEach(e -> talkList.removeAll(e.sessions()));

        List<Session> afternoonSessions = sessionStrategy.afternoonSession(talkList);
        //删除已经有的下午session
        afternoonSessions.forEach(e -> talkList.removeAll(e.sessions()));

        //如果talkList还不为空,则分配不成功
        if (!talkList.isEmpty()) {
            System.out.println("还剩下未分配的talkList：" + talkList.toString());
            return Collections.emptyList();
        }

        return talksListByMorningAndAfterSessions(morningSessions, afternoonSessions);
    }

    private List<Track> talksListByMorningAndAfterSessions(List<Session> morningSessions, List<Session> afternoonSessions) {
        List<Track> tracks = new ArrayList<>();

        int totalPossibleTracks = morningSessions.size();

        for (int trackIndex = 0; trackIndex < totalPossibleTracks; trackIndex++) {
            LocalTime time = LocalTime.of(9, 0);
            String scheduledTime = time.format(DateTimeFormatter.ofPattern("hh:mma"));

            //设置上午
            Session mornSession = morningSessions.get(trackIndex);
            for (Talk talk : mornSession.sessions()) {
                talk.setStartTime(scheduledTime);
                time = time.plusMinutes(talk.getDuration());
                scheduledTime = time.format(DateTimeFormatter.ofPattern("hh:mma"));
            }

            //设置午餐
            Talk lunchTalk = new Talk("Lunch", ConferenceUtil.LUNCH_DURATION);
            lunchTalk.setStartTime(scheduledTime);
            LunchSession lunchSession = new LunchSession(lunchTalk);

            //设置下午session
            time = time.plusMinutes(ConferenceUtil.LUNCH_DURATION);
            scheduledTime = time.format(DateTimeFormatter.ofPattern("hh:mma"));
            Session afternoonSession = afternoonSessions.get(trackIndex);
            for (Talk talk : afternoonSession.sessions()) {
                talk.setStartTime(scheduledTime);
                time = time.plusMinutes(talk.getDuration());
                scheduledTime = time.format(DateTimeFormatter.ofPattern("hh:mma"));
            }

            //Networking Event
            Talk networkingTalk = new Talk("Networking Event", 60);
            networkingTalk.setStartTime(scheduledTime);
            NetworkingEventSession networkingEventSession = new NetworkingEventSession(networkingTalk);

            tracks.add(new DefaultTrack(mornSession, lunchSession, afternoonSession, networkingEventSession));
        }
        return tracks;
    }
}
