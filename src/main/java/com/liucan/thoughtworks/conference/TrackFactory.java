package com.liucan.thoughtworks.conference;

import java.util.List;

/**
 * @author liucan
 * @version 19-9-3
 */
public interface TrackFactory {

    /**
     * 通过传入的talk返回不同的track
     */
    List<Track> newTracks(List<Talk> talkList);
}
