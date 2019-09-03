package com.liucan.thoughtworks.conference;

import java.util.List;

/**
 * @author liucan
 * @version 19-9-3
 */
public interface TrackFactory {

    List<Track> newTracks(List<Talk> talkList);
}
