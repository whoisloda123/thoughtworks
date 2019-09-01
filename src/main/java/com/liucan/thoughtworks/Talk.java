package com.liucan.thoughtworks;

/**
 * @author liucan
 * @version 19-9-1
 */
public class Talk implements Comparable {

    private String title;
    private String name;
    private int timeDuration;
    private boolean scheduled = false;
    private String scheduledTime;

    public Talk(String title, String name, int time) {
        this.title = title;
        this.name = name;
        this.timeDuration = time;
    }

    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }

    public boolean isScheduled() {
        return scheduled;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public int getTimeDuration() {
        return timeDuration;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int compareTo(Object obj) {
        Talk talk = (Talk)obj;
        return Integer.compare(talk.timeDuration, this.timeDuration);
    }
}
