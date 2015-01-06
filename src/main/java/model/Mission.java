package model;

import java.util.Date;

/**
 * Created by emile on 05/01/15.
 */
public class Mission {
    Date time_theorique;
    String info;
    Date time_reel;
    String brand;
    String terminus;
    Integer minutes_retard;
    Long id_mission;
    Long id_train;
    Long num;

    public Date getTime_theorique() {
        return time_theorique;
    }

    public void setTime_theorique(Date time_theorique) {
        this.time_theorique = time_theorique;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getTime_reel() {
        return time_reel;
    }

    public void setTime_reel(Date time_reel) {
        this.time_reel = time_reel;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTerminus() {
        return terminus;
    }

    public void setTerminus(String terminus) {
        this.terminus = terminus;
    }

    public Integer getMinutes_retard() {
        return minutes_retard;
    }

    public void setMinutes_retard(Integer minutes_retard) {
        this.minutes_retard = minutes_retard;
    }

    public Long getId_mission() {
        return id_mission;
    }

    public void setId_mission(Long id_mission) {
        this.id_mission = id_mission;
    }

    public Long getId_train() {
        return id_train;
    }

    public void setId_train(Long id_train) {
        this.id_train = id_train;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }
}
