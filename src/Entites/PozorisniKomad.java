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
@Table(name = "pozorisni_komad")
@XmlRootElement
public class PozorisniKomad {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "naziv")
    private String naziv;
    @Column(name = "autor")
    private String autor;

    public PozorisniKomad() {
    }

    public PozorisniKomad(String naziv) {
        this.naziv = naziv;
    }

    public PozorisniKomad(int id, String naziv, String autor) {
        this.id = id;
        this.naziv = naziv;
        this.autor = autor;
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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "PozorisniKomad{" + "id=" + id + ", naziv=" + naziv + ", autor=" + autor + '}';
    }

}
