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
@Table(name = "kreditna_kartica")
@XmlRootElement

public class KreditnaKartica {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "broj_kartice")
    private String broj;
    @Column(name = "datum_vazenja")
    private Date datumVazenja;
    @Column(name = "tip")
    private String tip;

    public KreditnaKartica() {
    }

    public KreditnaKartica(String broj, Date datumVazenja, String tip) {
        this.broj = broj;
        this.datumVazenja = datumVazenja;
        this.tip = tip;
    }

    public KreditnaKartica(int id, String broj, Date datumVazenja, String tip) {
        this.id = id;
        this.broj = broj;
        this.datumVazenja = datumVazenja;
        this.tip = tip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBroj() {
        return broj;
    }

    public void setBroj(String broj) {
        this.broj = broj;
    }

    public Date getDatumVazenja() {
        return datumVazenja;
    }

    public void setDatumVazenja(Date datumVazenja) {
        this.datumVazenja = datumVazenja;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @Override
    public String toString() {
        return "KreditnaKartica{" + "id=" + id + ", broj=" + broj + ", datumVazenja=" + datumVazenja + ", tip=" + tip + '}';
    }
    
}
