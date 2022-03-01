package fr.orleans.m1.wsi.tp2.controleur;

import fr.orleans.m1.wsi.tp2.modele.Etudiant;
import fr.orleans.m1.wsi.tp2.modele.FacadePromotion;
import fr.orleans.m1.wsi.tp2.modele.exceptions.EtudiantInexistantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(value = "/mpws", produces = MediaType.APPLICATION_JSON_VALUE)
public class Controleur {

    @Autowired
    FacadePromotion facadePromotion;

    @PostMapping(value = "/etudiant")
    public ResponseEntity<String> inscrireEtudiant(@RequestParam String nom, @RequestParam String prenom, @RequestParam String adresse){
        String retour = facadePromotion.enregistrerEtudiant(nom, prenom, adresse);
        return ResponseEntity.created(URI.create("http://localhost:8080/mpws/etudiant/"+retour)).build();
    }

    @GetMapping(value = "/etudiant/{numeroEtudiant}")
    public ResponseEntity<Etudiant> getEtudiant(@PathVariable String numeroEtudiant) {
        try {
            return ResponseEntity.ok(facadePromotion.getEtudiantById(numeroEtudiant));
        } catch (EtudiantInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/etudiant")
    public ResponseEntity<Collection<Etudiant>> getEtudiants()  {
        return ResponseEntity.ok(facadePromotion.getEtudiants());
    }

}