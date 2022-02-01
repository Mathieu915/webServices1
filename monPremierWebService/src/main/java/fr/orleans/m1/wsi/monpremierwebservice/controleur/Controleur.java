package fr.orleans.m1.wsi.monpremierwebservice.controleur;

import fr.orleans.m1.wsi.monpremierwebservice.modele.FacadePromotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mpws", produces = MediaType.APPLICATION_JSON_VALUE)
public class Controleur {

    @Autowired
    FacadePromotion facadePromotion;

    @PostMapping(value = "/etudiant")
    public ResponseEntity<String > inscrireEtudiant(@RequestParam String nom, @RequestParam String prenom, @RequestParam String adresse){
        String retour = facadePromotion.enregistrerEtudiant(nom, prenom, adresse);
        return ResponseEntity.status(HttpStatus.CREATED).header("token", retour).build();
    }

    


}
