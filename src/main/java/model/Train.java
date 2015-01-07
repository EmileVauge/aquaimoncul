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
    private String info;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Train train = (Train) o;

        if (retard != train.retard) return false;
        if (gareArrivee != null ? !gareArrivee.equals(train.gareArrivee) : train.gareArrivee != null) return false;
        if (gareDepart != null ? !gareDepart.equals(train.gareDepart) : train.gareDepart != null) return false;
        if (horaireArrivee != null ? !horaireArrivee.equals(train.horaireArrivee) : train.horaireArrivee != null)
            return false;
        if (horaireDepart != null ? !horaireDepart.equals(train.horaireDepart) : train.horaireDepart != null)
            return false;
        if (info != null ? !info.equals(train.info) : train.info != null) return false;
        if (lien != null ? !lien.equals(train.lien) : train.lien != null) return false;
        if (numero != null ? !numero.equals(train.numero) : train.numero != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gareDepart != null ? gareDepart.hashCode() : 0;
        result = 31 * result + (gareArrivee != null ? gareArrivee.hashCode() : 0);
        result = 31 * result + (horaireDepart != null ? horaireDepart.hashCode() : 0);
        result = 31 * result + (horaireArrivee != null ? horaireArrivee.hashCode() : 0);
        result = 31 * result + (retard ? 1 : 0);
        result = 31 * result + (lien != null ? lien.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (numero != null ? numero.hashCode() : 0);
        return result;
    }
}
