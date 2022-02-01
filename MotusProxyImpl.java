package proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.*;
import proxy.dto.EtatPartie;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

public class MotusProxyImpl implements MotusProxy{

    private HttpClient httpClient = HttpClient.newHttpClient();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String creerUnCompte(String pseudo) throws PseudoDejaPrisException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/motus/joueur"))
                .setHeader("Content-Type","application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("pseudo="+pseudo))
                .build();
        try {
            HttpResponse response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            HttpHeaders headers = response.headers();
            if(response.statusCode()==201){
                Optional<String> token = headers.firstValue("token");
                return token.get();
            }
            else {
                throw new PseudoDejaPrisException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String creerUnePartie(String tokenAuthentification) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/motus/partie"))
                .setHeader("token", tokenAuthentification)
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();
        try {
            HttpResponse response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            HttpHeaders headers = response.headers();
            if(response.statusCode()==201){
                Optional<String> token = headers.firstValue("tokenPartie");
                return token.get();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public EtatPartie proposerMot(String tokenPartie, String proposition) throws MotInexistantException, MaxNbCoupsException, TicketInvalideException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/motus/partie"))
                .setHeader("tokenPartie", tokenPartie)
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .PUT(HttpRequest.BodyPublishers.ofString("proposition="+proposition))
                .build();
        try {
            HttpResponse response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            EtatPartie etatPartie = objectMapper.readValue(response.body().toString(), EtatPartie.class);
            return etatPartie;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getPropositions(String tokenPartie) throws TicketInvalideException, PartieInexistanteException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/motus/partie"))
                .setHeader("tokenPartie", tokenPartie)
                .GET()
                .build();
        try {
            HttpResponse response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            List<String> liste = objectMapper.readValue(response.body().toString(), List.class);
            return liste;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

}
