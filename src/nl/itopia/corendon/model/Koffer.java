//This class is a blueprint for a koffer

package nl.itopia.corendon.model;

public class Koffer {
    String merknaam; int gewicht; int lengte; int breedte; int hoogte; String kleur;
    int status; //0 gezocht,  1 gevonden en 2 afgehandeld
    
    Koffer(String merknaam, int gewicht, int lengte, int breedte, int hoogte, String kleur, int status) {
        this.merknaam = merknaam;
        this.gewicht = gewicht;
        this.lengte = lengte;
        this.breedte = breedte;
        this.hoogte = hoogte;
        this.kleur = kleur;
        this.status = status;
    }
    
    void uploadKoffer(Koffer x){
        
    }
    
    void verwijderKoffer(Koffer x){
        
    }
    
    void aanpassenKoffer(Koffer x){ //te weinig parmameters
        
    }
    
    
    
    
    
}
