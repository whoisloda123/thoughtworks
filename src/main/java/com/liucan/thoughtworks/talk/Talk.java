package com.liucan.thoughtworks.talk;

/**
 * @author liucan
 * @version 19-9-1
 */
public class Talk {
    private String title;
    private String name;
    //持续时间，分
    private int duration;
    //是否已经处理过
    private boolean scheduled = false;
    //具体开始的时间
    private String startTime;

    public Talk(String title, String name, int duration) {
        this.title = title;
        this.name = name;
        this.duration = duration;
    }

    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }

    public boolean isScheduled() {
        return scheduled;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

//    @Override
//    public int compareTo(Object obj) {
//        Talk talk = (Talk)obj;
//        return Integer.compare(talk.timeDuration, this.timeDuration);
//    }
}
