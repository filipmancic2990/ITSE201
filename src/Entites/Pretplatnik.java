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
@Table(name = "pretplatnik")
@XmlRootElement
public class Pretplatnik {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "adresa")
    private String adresa;
    @Column(name = "telefon")
    private String telefon;
    @Column(name = "id_kreditne_kartice")
    private int id_kreditne_kartice;
    @Transient
    private KreditnaKartica kreditnaKartica;

    public Pretplatnik() {
    }

    public Pretplatnik(int id, String adresa, String telefon, int kartica) {
        this.id = id;
        this.adresa = adresa;
        this.telefon = telefon;
        this.id_kreditne_kartice = kartica;
    }

    public Pretplatnik(String adresa, String telefon, int kartica) {
        this.adresa = adresa;
        this.telefon = telefon;
        this.id_kreditne_kartice = kartica;
    }

    public Pretplatnik(int id, String adresa, String telefon, int id_kreditne_kartice, KreditnaKartica kreditnaKartica) {
        this.id = id;
        this.adresa = adresa;
        this.telefon = telefon;
        this.id_kreditne_kartice = id_kreditne_kartice;
        this.kreditnaKartica = kreditnaKartica;
    }

    public Pretplatnik(String adresa, String telefon, int id_kreditne_kartice, KreditnaKartica kreditnaKartica) {
        this.adresa = adresa;
        this.telefon = telefon;
        this.id_kreditne_kartice = id_kreditne_kartice;
        this.kreditnaKartica = kreditnaKartica;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public int getId_kreditne_kartice() {
        return id_kreditne_kartice;
    }

    public void setId_kreditne_kartice(int id_kreditne_kartice) {
        this.id_kreditne_kartice = id_kreditne_kartice;
    }

    public KreditnaKartica getKreditnaKartica() {
        return kreditnaKartica;
    }

    public void setKreditnaKartica(KreditnaKartica kreditnaKartica) {
        this.kreditnaKartica = kreditnaKartica;
    }

    @Override
    public String toString() {
        return "Pretplatnik{" + "id=" + id + ", adresa=" + adresa + ", telefon=" + telefon + ", kartica=" + id_kreditne_kartice + '}';
    }
    
    
}
