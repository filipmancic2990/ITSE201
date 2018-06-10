package Entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Filip
 */
@Entity
@Table(name = "narodno_pozoriste")
@XmlRootElement
public class NarodnoPozoriste {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "naziv")
    private String naziv;

    public NarodnoPozoriste() {
    }

    public NarodnoPozoriste(String naziv) {
        this.naziv = naziv;
    }

    public NarodnoPozoriste(int id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public String toString() {
        return "NarodnoPozoriste{" + "id=" + id + ", naziv=" + naziv + '}';
    }
    
}
