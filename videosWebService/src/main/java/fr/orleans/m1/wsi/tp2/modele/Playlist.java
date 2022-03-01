package fr.orleans.m1.wsi.tp2.modele;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    String nom;
    List<Video> videos;

    public Playlist(String nom) {
        this.nom = nom;
        this.videos = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public void addVideo(Video video){
        videos.add(video);
    }

    public void deleteVideo(Video video){
        videos.remove(video);
    }
}
