package com.liucan.thoughtworks;

import com.liucan.thoughtworks.session.LunchSession;
import com.liucan.thoughtworks.session.NetworkingEvnetSession;
import com.liucan.thoughtworks.session.Session;
import com.liucan.thoughtworks.strategy.SessionStragegy;
import com.liucan.thoughtworks.talk.Talk;
import com.liucan.thoughtworks.track.Track;
import com.liucan.thoughtworks.util.ConferenceUtil;
import com.liucan.thoughtworks.util.TalkParserUtil;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 会议管理
 * @author liucan
 * @version 19-9-2
 */
public class ConferenceManager {
    private SessionStragegy sessionStragegy;
    private List<Track> tracks = new ArrayList<>();

    public ConferenceManager(SessionStragegy sessionStragegy) {
        this.sessionStragegy = sessionStragegy;
    }

    private List<Track> getScheduledTalksList(List<Session> morningSessions, List<Session> afternoonSessions) {
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
            Talk lunchTalk = new Talk("Lunch", "Lunch", ConferenceUtil.LUNCH_DURATION);
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
            // Scheduled Networking Event at the end of session, Time duration is just to initialize the Talk object.
            Talk networkingTalk = new Talk("Networking Event", "Networking Event", 60);
            networkingTalk.setStartTime(scheduledTime);
            NetworkingEvnetSession networkingEvnetSession = new NetworkingEvnetSession(networkingTalk);

            tracks.add(new Track(mornSession, lunchSession, afternoonSession, networkingEvnetSession));
        }
        return tracks;
    }

    /**
     * 获取所有tracks
     */
    public List<Track> tracks(String file) {
        List<Talk> talkList = new TalkParserUtil().getTalksFromFile(file);
        talkList.sort(Comparator.comparing(Talk::getDuration).reversed());

        int totalPossibleTracks = ConferenceUtil.getTotalTalksTime(talkList) / (6 * 60);

        List<Session> morningSessions = sessionStragegy.morningSession(talkList, totalPossibleTracks);
        //删除已经有的上午session
        morningSessions.forEach(e -> talkList.removeAll(e.sessions()));

        List<Session> afternoonSessions = sessionStragegy.afternoonSession(talkList, totalPossibleTracks);
        //删除已经有的下午session
        afternoonSessions.forEach(e -> talkList.removeAll(e.sessions()));

        //如果talkList还有数据，则看能否将剩下的session放入下午session
        if (!talkList.isEmpty()) {
            for (Session session : afternoonSessions) {
                List<Talk> lastAddTalkList = new ArrayList<>();
                List<Talk> talks = session.sessions();
                int totalTime = ConferenceUtil.getTotalTalksTime(talks);
                talkList.forEach(talk -> {
                    if (talk.getDuration() + totalTime <= ConferenceUtil.MAX_SESSION_TIME) {
                        talk.setScheduled(true);
                        session.addTalk(talk);
                        lastAddTalkList.add(talk);
                    }
                });

                talkList.removeAll(lastAddTalkList);
                if (!talkList.isEmpty()) {
                    break;
                }
            }
        }

        //如果talkList还不为空,则分配不成功
        if (!talkList.isEmpty()) {
            return Collections.emptyList();
        }

        return getScheduledTalksList(morningSessions, afternoonSessions);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int trackIndex = 0; trackIndex < tracks.size(); trackIndex++) {
            stringBuilder.append("Track " + (trackIndex + 1) + ":");
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(tracks.get(trackIndex).toString());
        }
        return stringBuilder.toString();
    }
}
