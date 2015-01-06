package model;

/**
 * Created by emile on 05/01/15.
 */
public class Gare {
    String stoparea;
    Long id_gare;
    Double lng;
    Double lat;
    Long id_source;
    String name_gare;
    String shortcode;

    public String getStoparea() {
        return stoparea;
    }

    public void setStoparea(String stoparea) {
        this.stoparea = stoparea;
    }

    public Long getId_gare() {
        return id_gare;
    }

    public void setId_gare(Long id_gare) {
        this.id_gare = id_gare;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Long getId_source() {
        return id_source;
    }

    public void setId_source(Long id_source) {
        this.id_source = id_source;
    }

    public String getName_gare() {
        return name_gare;
    }

    public void setName_gare(String name_gare) {
        this.name_gare = name_gare;
    }

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }
}
