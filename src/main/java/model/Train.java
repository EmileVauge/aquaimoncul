package model;

/**
 * Created by emile on 05/01/15.
 */
public class Train {
    String gareDepart;
    String gareArrivee;
    String horaireDepart;
    String horaireArrivee;
    boolean retard;
    private String lien;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    String numero;

    public String getGareDepart() {
        return gareDepart;
    }

    public void setGareDepart(String gareDepart) {
        this.gareDepart = gareDepart;
    }

    public String getGareArrivee() {
        return gareArrivee;
    }

    public void setGareArrivee(String gareArrivee) {
        this.gareArrivee = gareArrivee;
    }

    public String getHoraireDepart() {
        return horaireDepart;
    }

    public void setHoraireDepart(String horaireDepart) {
        this.horaireDepart = horaireDepart;
    }

    public String getHoraireArrivee() {
        return horaireArrivee;
    }

    public void setHoraireArrivee(String horaireArrivee) {
        this.horaireArrivee = horaireArrivee;
    }

    public boolean isRetard() {
        return retard;
    }

    public void setRetard(boolean retard) {
        this.retard = retard;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getLien() {
        return lien;
    }
}
