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
@Table(name = "rezervisana_sedista")
@XmlRootElement
public class RezervisanaSedista {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "id_sedista")
    private int id_sedista;
    @Column(name = "id_rezervacija")
    private int id_rezervacija;

    public RezervisanaSedista() {
    }

    public RezervisanaSedista(int id, int id_sedista, int id_rezervacije) {
        this.id = id;
        this.id_sedista = id_sedista;
        this.id_rezervacija = id_rezervacije;
    }

    public RezervisanaSedista(int id_sedista, int id_rezevacije) {
        this.id_rezervacija = id_rezevacije;
        this.id_sedista = id_sedista;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_sedista() {
        return id_sedista;
    }

    public void setId_sedista(int id_sedista) {
        this.id_sedista = id_sedista;
    }

    public int getId_rezervacija() {
        return id_rezervacija;
    }

    public void setId_rezervacija(int id_rezervacija) {
        this.id_rezervacija = id_rezervacija;
    }

    @Override
    public String toString() {
        return "RezervisanaSedista{" + "id=" + id + ", id_sedista=" + id_sedista + ", id_rezervacije=" + id_rezervacija + '}';
    }
    
}