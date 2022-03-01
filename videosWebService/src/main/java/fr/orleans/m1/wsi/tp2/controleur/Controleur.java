package fr.orleans.m1.wsi.tp2.controleur;

import fr.orleans.m1.wsi.tp2.modele.FacadeVideos;
import fr.orleans.m1.wsi.tp2.modele.User;
import fr.orleans.m1.wsi.tp2.modele.exceptions.MauvaisIdentifiantsException;
import fr.orleans.m1.wsi.tp2.modele.exceptions.UtilisateurDejaExistantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "/videows", produces = MediaType.APPLICATION_JSON_VALUE)
public class Controleur {

    @Autowired
    FacadeVideos facadeVideos;

    @PostMapping(value = "/user")
    public ResponseEntity<String> inscrireEtudiant(@RequestParam String nom, @RequestParam String pwd){
        try {
            String retour = facadeVideos.enregistrerUser(nom, pwd);
            return ResponseEntity.created(URI.create("http://localhost:8080/videows/user/"+retour)).build();
        } catch (UtilisateurDejaExistantException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping(value = "/user/{numUser}")
    public ResponseEntity<User> getUser(@PathVariable String numUser){
        try {
            return ResponseEntity.ok(this.facadeVideos.getUtilisateur(numUser));
        } catch (MauvaisIdentifiantsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    

}
