package fr.orleans.m1.wsi.tp2.controleur;

import fr.orleans.m1.wsi.tp2.modele.FacadeVideos;
import fr.orleans.m1.wsi.tp2.modele.Playlist;
import fr.orleans.m1.wsi.tp2.modele.User;
import fr.orleans.m1.wsi.tp2.modele.Video;
import fr.orleans.m1.wsi.tp2.modele.exceptions.MauvaisIdentifiantsException;
import fr.orleans.m1.wsi.tp2.modele.exceptions.PlaylistDejaCreeException;
import fr.orleans.m1.wsi.tp2.modele.exceptions.UtilisateurDejaExistantException;
import fr.orleans.m1.wsi.tp2.modele.exceptions.VideoDejaExistanteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/videows", produces = MediaType.APPLICATION_JSON_VALUE)
public class Controleur {


    @Autowired
    FacadeVideos facadeVideos;

    @PostMapping(value = "/user")
    public ResponseEntity<String> inscrireEtudiant(@RequestParam String nom, @RequestParam String pwd){
        try {
            User retour = facadeVideos.enregistrerUser(nom, pwd);
            return ResponseEntity.created(URI.create("http://localhost:8080/videows/user/"+retour.getNumUser()))
                    .header("nom", retour.getNom())
                    .header("pwd", retour.getPwd())
                    .header("num", retour.getNumUser())
                    .build();
        } catch (UtilisateurDejaExistantException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping(value = "/user/{numUser}")
    public ResponseEntity<User> getUser(@PathVariable String numUser, @RequestParam String nom, @RequestParam String pwd){
        try {
            User res = this.facadeVideos.getUtilisateur(numUser);
            if (res.getNom().equals(nom)&&res.getPwd().equals(pwd)){
                return ResponseEntity.ok(res);
            }
            else {
             return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (MauvaisIdentifiantsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/video")
    public ResponseEntity<Video> ajouterVideo(@RequestParam String nom, @RequestParam String pwd, @RequestParam String titre, @RequestParam String description, @RequestParam String url){
        try {
            String video = this.facadeVideos.ajouterVideo(titre,description,url,nom,pwd);
            return ResponseEntity.status(HttpStatus.CREATED).header("url", video).build();
        } catch (MauvaisIdentifiantsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (VideoDejaExistanteException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping(value = "/user/{numUser}/playlist")
    public ResponseEntity<Playlist> creerPlaylist(@PathVariable String numUser, @RequestParam String nom, @RequestParam String pwd, @RequestParam String titre){
        try {
            String playlist = this.facadeVideos.creerPlaylist(titre,nom,pwd);
            return ResponseEntity.created(URI.create("http://localhost:8080/videows/user/"+numUser+"/playlist/"+playlist)).build();
        } catch (MauvaisIdentifiantsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (PlaylistDejaCreeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping(value = "/user/{numUser}/playlist")
    public ResponseEntity<String> supprimerPlaylist(@PathVariable String numUser, @RequestParam String nom, @RequestParam String pwd, @RequestParam String titre){
        try {
            String playlist = this.facadeVideos.supprimerPlaylist(titre,nom,pwd);
            return ResponseEntity.ok(playlist);
        } catch (MauvaisIdentifiantsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(value = "/user/{numUser}/playlist")
    public ResponseEntity<List<Playlist>> getPlaylists(@PathVariable String numUser, @RequestParam String nom, @RequestParam String pwd){
        try {
            return ResponseEntity.ok(this.facadeVideos.getPlaylistUser(nom, pwd));
        } catch (MauvaisIdentifiantsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(value = "/user/{numUser}/playlist/{titrePlaylist}")
    public ResponseEntity<String> ajouterVideoPlaylist(@PathVariable String numUser, @PathVariable String titrePlaylist, @RequestParam String video, @RequestParam String nom, @RequestParam String pwd){
        try {
            String video1 = this.facadeVideos.ajouterVideoPlaylist(video, titrePlaylist, nom, pwd);
            return ResponseEntity.ok(video1);
        } catch (MauvaisIdentifiantsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping(value = "/user/{numUser}/playlist/{titrePlaylist}")
    public ResponseEntity<String> supprimerVideoPlaylist(@PathVariable String numUser, @PathVariable String titrePlaylist, @RequestParam String video, @RequestParam String nom, @RequestParam String pwd){
        try {
            String video1 = this.facadeVideos.supprimerVideoPlaylist(video, titrePlaylist, nom, pwd);
            return ResponseEntity.ok(video1);
        } catch (MauvaisIdentifiantsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(value = "/video")
    public ResponseEntity<List<Video>> voirVideos(){
        return ResponseEntity.ok(this.facadeVideos.getVideos());
    }

    @GetMapping(value = "/user/{numUser}/video")
    public ResponseEntity<List<Video>> voirMesVideos(@PathVariable String numUser,@RequestParam String nom, @RequestParam String pwd){
        try {
            return ResponseEntity.ok(this.facadeVideos.getVideosParUser(nom, pwd));
        } catch (MauvaisIdentifiantsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
