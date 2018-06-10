package Entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Filip
 */

@Entity
@Table(name = "sediste")
@XmlRootElement
public class Sediste {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "vrsta")
    private String vrsta;
    @Column(name = "red")
    private String red;
    @Transient
    private String uuid;

    public Sediste(String kolona, String red) {
        this.vrsta = kolona;
        this.red = red;
    }

    public Sediste(int id, String kolona, String red) {
        this.id = id;
        this.vrsta = kolona;
        this.red = red;
    }

    public Sediste(String vrsta, String red, String uuid) {
        this.vrsta = vrsta;
        this.red = red;
        this.uuid = uuid;
    }

    public Sediste(int id, String vrsta, String red, String uuid) {
        this.id = id;
        this.vrsta = vrsta;
        this.red = red;
        this.uuid = uuid;
    }

    public Sediste(String uuid) {
        this.uuid = uuid;
    }

    public Sediste() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Sediste{" + "id=" + id + ", kolona=" + vrsta + ", red=" + red + '}';
    }
    
}
