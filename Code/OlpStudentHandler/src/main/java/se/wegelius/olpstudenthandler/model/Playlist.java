/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.model;

import se.wegelius.olpstudenthandler.model.persistance.PlaylistPersistance;

/**
 *
 * @author asawe
 */
public class Playlist {
     private Integer playlistId;
     private int courseId;
     private int userId;

    public Playlist(Integer playlistId, int courseId, int userId) {
        this.playlistId = playlistId;
        this.courseId = courseId;
        this.userId = userId;
    }
    
    public Playlist(PlaylistPersistance p){
        this.courseId = p.getCourse().getCourseId();
        this.userId = p.getUser().getUserId();
        this.playlistId = p.getPlaylistId();
    }

    public Integer getPlaylistId() {
        return playlistId;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setPlaylistId(Integer playlistId) {
        this.playlistId = playlistId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
}
