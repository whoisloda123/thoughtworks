package com.liucan.thoughtworks;

import com.liucan.thoughtworks.common.exception.BizException;
import com.liucan.thoughtworks.conference.DefaultTrackFactory;
import com.liucan.thoughtworks.conference.Talk;
import com.liucan.thoughtworks.conference.Track;
import com.liucan.thoughtworks.conference.TrackFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 会议管理类
 * 直接调用ConferenceManager.tracks传入文件名即可
 *
 * @author liucan
 * @version 19-9-2
 */
public class ConferenceManager {
    private List<Track> tracks = new ArrayList<>();
    private TrackFactory trackFactory;

    public ConferenceManager(TrackFactory trackFactory) {
        this.trackFactory = trackFactory;
    }

    public static ConferenceManager newConferenceManager() {
        return new ConferenceManager(new DefaultTrackFactory());
    }

    /**
     * 解析文件获取所有tracks
     * @param file 文件名
     */
    public List<Track> tracks(String file) {
        tracks = trackFactory.newTracks(getTalksFromFile(file));
        return tracks;
    }

    private List<Talk> getTalksFromFile(String fileName) {
        List<Talk> talks = new ArrayList<>();
        try (FileReader fileReader = new FileReader(new File(getClass().getClassLoader().getResource(fileName).toURI()));
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String strLine = bufferedReader.readLine();
            while (strLine != null) {
                talks.add(getTalk(strLine));
                strLine = bufferedReader.readLine();
            }
        } catch (Exception e) {
            System.out.println("读取文件异常:" + fileName + e);
        }
        return talks;
    }

    private Talk getTalk(String talk) throws Exception {
        String minSuffix = "min";
        String lightningSuffix = "lightning";

        int lastSpaceIndex = talk.lastIndexOf(" ");
        if (lastSpaceIndex == -1) {
            throw new BizException("无效talk: " + talk);
        }

        String name = talk.substring(0, lastSpaceIndex);
        String timeStr = talk.substring(lastSpaceIndex + 1);

        if (name.trim().equals("")) {
            throw new BizException("无效talk name: " + talk);
        } else if (!timeStr.endsWith(minSuffix) && !timeStr.endsWith(lightningSuffix)) {
            throw new BizException("无效 talk time: " + talk);
        }


        int time = 0;
        //解析时间
        if (timeStr.endsWith(minSuffix)) {
            time = Integer.parseInt(timeStr.substring(0, timeStr.indexOf(minSuffix)));
        } else if (timeStr.endsWith(lightningSuffix)) {
            time = 5;
        }
        return new Talk(talk, time);
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
