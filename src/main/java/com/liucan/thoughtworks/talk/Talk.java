package com.liucan.thoughtworks.talk;

/**
 * @author liucan
 * @version 19-9-1
 */
public class Talk {
    //title，如Writing Fast Tests Against Enterprise Rails 60min
    private String title;
    //持续时间，分
    private int duration;
    //是否已经安排过
    private boolean arrange = false;
    //具体开始的时间
    private String startTime;

    public Talk(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public void setArrange(boolean arrange) {
        this.arrange = arrange;
    }

    public boolean isArrange() {
        return arrange;
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
}
