package fr.orleans.m1.wsi.tp2.modele;

public class Video {
    private String url;
    private String titre;
    private String description;
    private String createur;

    public Video(String url, String titre, String description, String createur) {
        this.url = url;
        this.titre = titre;
        this.description = description;
        this.createur = createur;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateur() {
        return createur;
    }

    public void setCreateur(String createur) {
        this.createur = createur;
    }
}
