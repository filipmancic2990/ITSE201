package Entites;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
@Table(name = "rezervacija")
@XmlRootElement
public class Rezervacija {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "datum_rezervisanja")
    private Date datumRezervisanja;
    @Column(name = "datum_preuzimanja")
    private Date datumPreuzimanja;
    @Column(name = "broj_mesta")
    private int brojMesta;
    @Column(name = "ukupan_iznos")
    private double ukupanIznos;
    @Column(name = "id_pretplatnika")
    private int idPretplatnika;
    @Column(name = "id_pozorisne_predstave")
    private int idPozorisnePredstave;
    @Transient
    private List<Sediste> sedista = new ArrayList<>();

    public Rezervacija() {
    }

    public Rezervacija(int id, Date datumRezervisanja, Date datumPreuzimanja, double cena, int pretplatnik, int pozorisnaPredstava) {
        this.id = id;
        this.datumRezervisanja = datumRezervisanja;
        this.datumPreuzimanja = datumPreuzimanja;
        this.ukupanIznos = cena;
        this.idPretplatnika = pretplatnik;
        this.idPozorisnePredstave = pozorisnaPredstava;
    }

    public Rezervacija(Date datumRezervisanja, Date datumPreuzimanja, double cena, int pretplatnik, int pozorisnaPredstava) {
        this.datumRezervisanja = datumRezervisanja;
        this.datumPreuzimanja = datumPreuzimanja;
        this.ukupanIznos = cena;
        this.idPretplatnika = pretplatnik;
        this.idPozorisnePredstave = pozorisnaPredstava;
    }

    public Rezervacija(Date datumRezervisanja, Date datumPreuzimanja, int brojMesta, double ukupanIznos, int idPretplatnika, int idPozorisnePredstave) {
        this.datumRezervisanja = datumRezervisanja;
        this.datumPreuzimanja = datumPreuzimanja;
        this.brojMesta = brojMesta;
        this.ukupanIznos = ukupanIznos;
        this.idPretplatnika = idPretplatnika;
        this.idPozorisnePredstave = idPozorisnePredstave;
    }

    public Rezervacija(int id, Date datumRezervisanja, Date datumPreuzimanja, int brojMesta, double ukupanIznos, int idPretplatnika, int idPozorisnePredstave) {
        this.id = id;
        this.datumRezervisanja = datumRezervisanja;
        this.datumPreuzimanja = datumPreuzimanja;
        this.brojMesta = brojMesta;
        this.ukupanIznos = ukupanIznos;
        this.idPretplatnika = idPretplatnika;
        this.idPozorisnePredstave = idPozorisnePredstave;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatumRezervisanja() {
        return datumRezervisanja;
    }

    public void setDatumRezervisanja(Date datumRezervisanja) {
        this.datumRezervisanja = datumRezervisanja;
    }

    public Date getDatumPreuzimanja() {
        return datumPreuzimanja;
    }

    public void setDatumPreuzimanja(Date datumPreuzimanja) {
        this.datumPreuzimanja = datumPreuzimanja;
    }

    public List<Sediste> getSedista() {
        return sedista;
    }

    public void setSedista(List<Sediste> sedista) {
        this.sedista = sedista;
    }

    public double getUkupanIznos() {
        return ukupanIznos;
    }

    public void setUkupanIznos(double ukupanIznos) {
        this.ukupanIznos = ukupanIznos;
    }

    public int getIdPretplatnika() {
        return idPretplatnika;
    }

    public void setIdPretplatnika(int idPretplatnika) {
        this.idPretplatnika = idPretplatnika;
    }

    public int getIdPozorisnePredstave() {
        return idPozorisnePredstave;
    }

    public void setIdPozorisnePredstave(int idPozorisnePredstave) {
        this.idPozorisnePredstave = idPozorisnePredstave;
    }

    public int getBrojMesta() {
        return brojMesta;
    }

    public void setBrojMesta(int brojMesta) {
        this.brojMesta = brojMesta;
    }
    
    @Override
    public String toString() {
        return "Rezervacija{" + "id=" + id + ", datumRezervisanja=" + datumRezervisanja + ", datumPreuzimanja=" + datumPreuzimanja + ", sedista=" + sedista + ", cena=" + ukupanIznos + ", pretplatnik=" + idPretplatnika + ", pozorisnaPredstava=" + idPozorisnePredstave + '}';
    }
    
}
