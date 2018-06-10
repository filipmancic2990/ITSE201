package Entites;

import java.util.Date;
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
@Table(name = "igrac")
@XmlRootElement

public class Igrac {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "ime")
    private String ime;
    @Column(name = "prezime")
    private String prezime;
    @Column(name = "datum_rodjenja")
    private Date datumRodjenja;
    @Column(name = "kvalifikacija")
    private String kvalifikacija;

    public Igrac() {
    }

    public Igrac(int id, String ime, String prezime, Date datumRodjenja, String kvalifikacija) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
        this.kvalifikacija = kvalifikacija;
    }

    public Igrac(String ime, String prezime, Date datumRodjenja, String kvalifikacija) {
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
        this.kvalifikacija = kvalifikacija;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getKvalifikacija() {
        return kvalifikacija;
    }

    public void setKvalifikacija(String kvalifikacija) {
        this.kvalifikacija = kvalifikacija;
    }

    @Override
    public String toString() {
        return "Igrac{" + "ime=" + ime + ", prezime=" + prezime + ", datumRodjenja=" + datumRodjenja + ", kvalifikacija=" + kvalifikacija + '}';
    }
    
}
