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
@Table(name = "trupa_i_igrac")
@XmlRootElement

public class TrupaIIgrac {

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int id;
    @Column(name = "id_trupa")
    public int id_trupa;
    @Column(name = "id_igrac")
    public int id_igrac;

    public TrupaIIgrac(int id_trupa, int id_igrac) {
        this.id_trupa = id_trupa;
        this.id_igrac = id_igrac;
    }

    public TrupaIIgrac(int id, int id_trupa, int id_igrac) {
        this.id = id;
        this.id_trupa = id_trupa;
        this.id_igrac = id_igrac;
    }

    public TrupaIIgrac() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_trupa() {
        return id_trupa;
    }

    public void setId_trupa(int id_trupa) {
        this.id_trupa = id_trupa;
    }

    public int getId_igrac() {
        return id_igrac;
    }

    public void setId_igrac(int id_igrac) {
        this.id_igrac = id_igrac;
    }

    @Override
    public String toString() {
        return "TrupaIIgraci{" + "id_trupa=" + id_trupa + ", id_igrac=" + id_igrac + '}';
    }

}
