package fr.orleans.m1.wsi.tp2.modele;

import fr.orleans.m1.wsi.tp2.modele.exceptions.MauvaisIdentifiantsException;
import fr.orleans.m1.wsi.tp2.modele.exceptions.PlaylistDejaCreeException;
import fr.orleans.m1.wsi.tp2.modele.exceptions.UtilisateurDejaExistantException;
import fr.orleans.m1.wsi.tp2.modele.exceptions.VideoDejaExistanteException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FacadeVideos {

    Map<String,User> users;
    List<Video> videos;

    public FacadeVideos(){
        users = new HashMap<>();
        videos = new ArrayList<>();
    }

    public User enregistrerUser(String nom, String pwd) throws UtilisateurDejaExistantException {
        if(!users.containsKey(nom)){
            User user = new User(nom, pwd);
            users.put(nom, user);
            return user;
        }
        else {
            throw new UtilisateurDejaExistantException();
        }
    }

    public String ajouterVideo(String titre, String description, String url, String nom, String pwd) throws MauvaisIdentifiantsException, VideoDejaExistanteException {
        if(users.containsKey(nom) && users.get(nom).getPwd().equals(pwd)){
            for (Video v : videos){
                if(v.getTitre().equals(titre)){
                    throw new VideoDejaExistanteException();
                }
            }
            Video video = new Video(url, titre, description, nom);
            videos.add(video);
            users.get(nom).addVideo(video);
            return titre;
        }
        else {
            throw new MauvaisIdentifiantsException();
        }
    }

    public String creerPlaylist(String titre, String nom, String pwd) throws MauvaisIdentifiantsException, PlaylistDejaCreeException {
        if(users.containsKey(nom) && users.get(nom).getPwd().equals(pwd)){
            for (Playlist p : users.get(nom).getPlaylists()){
                if (p.getNom().equals(titre)){
                    throw new PlaylistDejaCreeException();
                }
            }
            Playlist playlist = new Playlist(titre);
            users.get(nom).addPlaylist(playlist);
            return titre;
        }
        else {
            throw new MauvaisIdentifiantsException();
        }
    }

    public String supprimerPlaylist(String titre, String nom, String pwd) throws MauvaisIdentifiantsException {
        if(users.containsKey(nom) && users.get(nom).getPwd().equals(pwd)){
            users.get(nom).deletePlaylist(titre);
            return titre;
        }
        else {
            throw new MauvaisIdentifiantsException();
        }
    }

    public String ajouterVideoPlaylist(String video, String playlist, String nom, String pwd) throws MauvaisIdentifiantsException {
        if(users.containsKey(nom) && users.get(nom).getPwd().equals(pwd)){
            for(Playlist p : users.get(nom).getPlaylists()){
                if (p.getNom().equals(playlist)){
                    for (Video v : videos){
                        if (v.getTitre().equals(video)){
                            p.addVideo(v);
                        }
                    }
                }
            }
            return video;
        }
        else {
            throw new MauvaisIdentifiantsException();
        }
    }


    public String supprimerVideoPlaylist(String video, String playlist, String nom, String pwd) throws MauvaisIdentifiantsException {
        if(users.containsKey(nom) && users.get(nom).getPwd().equals(pwd)){
            for(Playlist p : users.get(nom).getPlaylists()){
                if (p.getNom().equals(playlist)){
                    for (Video v : videos){
                        if (v.getTitre().equals(video)){
                            p.deleteVideo(v);
                        }
                    }
                }
            }
            return video;
        }
        else {
            throw new MauvaisIdentifiantsException();
        }
    }


    public User getUtilisateur(String numUser) throws MauvaisIdentifiantsException {
        for (User user : users.values()){
            if (user.getNumUser().equals(numUser)){
                return user;
            }
        }
        throw new MauvaisIdentifiantsException();
    }

    public List<Video> getVideos(){
        return this.videos;
    }

    public List<Video> getVideosParUser(String nom, String pwd) throws MauvaisIdentifiantsException {
        if(users.containsKey(nom) && users.get(nom).getPwd().equals(pwd)){
            return users.get(nom).getMesVideos();
        }
        else {
            throw new MauvaisIdentifiantsException();
        }
    }

    public List<Playlist> getPlaylistUser(String nom, String pwd) throws MauvaisIdentifiantsException {
        if(users.containsKey(nom) && users.get(nom).getPwd().equals(pwd)){
            return users.get(nom).getPlaylists();
        }
        else {
            throw new MauvaisIdentifiantsException();
        }
    }
}
