package com.liucan.thoughtworks.util;

import com.liucan.thoughtworks.exception.InvalidTalkException;
import com.liucan.thoughtworks.talk.Talk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析文件内容到talk
 *
 * @author liucan
 * @version 19-9-2
 */
public class TalkParserUtil {

    public List<Talk> getTalksFromFile(String fileName) {
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
            throw new InvalidTalkException("无效talk: " + talk);
        }

        String name = talk.substring(0, lastSpaceIndex);
        String timeStr = talk.substring(lastSpaceIndex + 1);

        if (name.trim().equals("")) {
            throw new InvalidTalkException("无效talk name: " + talk);
        } else if (!timeStr.endsWith(minSuffix) && !timeStr.endsWith(lightningSuffix)) {
            throw new InvalidTalkException("无效 talk time: " + talk);
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
}
