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
    private final static String INPUT_FILE = "input.txt";

    private SessionStragegy sessionStragegy;

    public ConferenceManager(SessionStragegy sessionStragegy) {
        this.sessionStragegy = sessionStragegy;
    }

    private List<Track> getScheduledTalksList(List<Session> morningSessions, List<Session> afternoonSessions) {
        List<Track> tracks = new ArrayList<>();
        int totalPossibleTracks = morningSessions.size();

        for (int trackIndex = 0; trackIndex < totalPossibleTracks; trackIndex++) {
            System.out.println("Track " + trackIndex + 1 + ":");


            LocalTime time = LocalTime.of(9, 0);
            String scheduledTime = time.format(DateTimeFormatter.ofPattern("hh:mma "));

            //设置上午
            Session mornSession = morningSessions.get(trackIndex);
            for (Talk talk : mornSession.sessions()) {
                talk.setStartTime(scheduledTime);
                System.out.println(scheduledTime + talk.getTitle());

                time = time.plusMinutes(talk.getDuration());
                scheduledTime = time.format(DateTimeFormatter.ofPattern("hh:mma "));
            }

            //设置午餐
            Talk lunchTalk = new Talk("Lunch", "Lunch", ConferenceUtil.LUNCH_DURATION);
            lunchTalk.setStartTime(scheduledTime);
            LunchSession lunchSession = new LunchSession(lunchTalk);
            System.out.println(scheduledTime + "Lunch");

            //设置下午session
            time = time.plusMinutes(ConferenceUtil.LUNCH_DURATION);
            scheduledTime = time.format(DateTimeFormatter.ofPattern("hh:mma "));
            Session afternoonSession = afternoonSessions.get(trackIndex);
            for (Talk talk : afternoonSession.sessions()) {
                talk.setStartTime(scheduledTime);
                System.out.println(scheduledTime + talk.getTitle());

                time = time.plusMinutes(talk.getDuration());
                scheduledTime = time.format(DateTimeFormatter.ofPattern("hh:mma "));
            }

            //Networking Event
            // Scheduled Networking Event at the end of session, Time duration is just to initialize the Talk object.
            Talk networkingTalk = new Talk("Networking Event", "Networking Event", 60);
            networkingTalk.setStartTime(scheduledTime);
            NetworkingEvnetSession networkingEvnetSession = new NetworkingEvnetSession(networkingTalk);
            System.out.println(scheduledTime + "Networking Event\n");

            tracks.add(new Track(mornSession, lunchSession, afternoonSession, networkingEvnetSession));
        }
        return tracks;
    }

    /**
     * 获取所有tracks
     */
    public List<Track> tracks(String file) {
        List<Talk> talkList = new TalkParserUtil().getTalksFromFile(INPUT_FILE);
        talkList.sort(Comparator.comparing(Talk::getDuration).reversed());

        List<Session> morningSessions = sessionStragegy.morningSession(talkList);
        //删除已经有的上午session
        morningSessions.forEach(e -> talkList.removeAll(e.sessions()));

        List<Session> afternoonSessions = sessionStragegy.afternoonSession(talkList);
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
}
