package fr.orleans.m1.wsi.tp2.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private String numUser;
    private String nom;
    private String pwd;
    private List<Playlist> playlists;

    public User(String nom, String pwd) {
        this.numUser = UUID.randomUUID().toString();
        this.nom = nom;
        this.pwd = pwd;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public String getNumUser() {
        return numUser;
    }

    public void setNumUser(String numUser) {
        this.numUser = numUser;
    }

    public void addPlaylist(Playlist playlist){
        this.playlists.add(playlist);
    }

    public void deletePlaylist(String titre) {
        List<Playlist> newList = new ArrayList<>();
        for (Playlist playlist : playlists){
            if(!playlist.getNom().equals(titre)){
                newList.add(playlist);
            }
        }
        playlists = newList;
    }
}
