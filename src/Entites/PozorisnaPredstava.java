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
@Table(name = "pozorisna_predstava")
@XmlRootElement
public class PozorisnaPredstava {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "id_narodnog_pozorista")
    private int idNarodnoPozoriste;
    @Column(name = "id_pozorisnog_komada")
    private int idPozorisniKomad;
    @Column(name = "id_trupe")
    private int idTrupe;
    @Column(name = "datum_odrzavanja")
    private Date datumOdrzavanja;
    @Column(name = "broj_raspolozivih_mesta")
    private int brojRaspolozivihMesta;
    @Column(name = "cena_ulaznice")
    private double cenaUlaznice;
    @Column(name = "producent")
    private String producent;

    public PozorisnaPredstava() {
    }

    public PozorisnaPredstava(int narodnoPozoriste, int pozorisniKomad, int trupa, Date datumOdrzavanja, int brojRaspolozivihMesta, double cenaUlaznice, String producent) {
        this.idNarodnoPozoriste = narodnoPozoriste;
        this.idPozorisniKomad = pozorisniKomad;
        this.idTrupe = trupa;
        this.datumOdrzavanja = datumOdrzavanja;
        this.brojRaspolozivihMesta = brojRaspolozivihMesta;
        this.cenaUlaznice = cenaUlaznice;
        this.producent = producent;
    }
    
    public PozorisnaPredstava(int id, int narodnoPozoriste, int pozorisniKomad, int trupa, Date datumOdrzavanja, int brojRaspolozivihMesta, double cenaUlaznice, String producent) {
        this.id = id;
        this.idNarodnoPozoriste = narodnoPozoriste;
        this.idPozorisniKomad = pozorisniKomad;
        this.idTrupe = trupa;
        this.datumOdrzavanja = datumOdrzavanja;
        this.brojRaspolozivihMesta = brojRaspolozivihMesta;
        this.cenaUlaznice = cenaUlaznice;
        this.producent = producent;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdNarodnoPozoriste() {
        return idNarodnoPozoriste;
    }

    public void setIdNarodnoPozoriste(int idNarodnoPozoriste) {
        this.idNarodnoPozoriste = idNarodnoPozoriste;
    }

    public int getIdPozorisniKomad() {
        return idPozorisniKomad;
    }

    public void setIdPozorisniKomad(int idPozorisniKomad) {
        this.idPozorisniKomad = idPozorisniKomad;
    }

    public int getIdTrupe() {
        return idTrupe;
    }

    public void setIdTrupe(int idTrupe) {
        this.idTrupe = idTrupe;
    }

    public Date getDatumOdrzavanja() {
        return datumOdrzavanja;
    }

    public void setDatumOdrzavanja(Date datumOdrzavanja) {
        this.datumOdrzavanja = datumOdrzavanja;
    }

    public int getBrojRaspolozivihMesta() {
        return brojRaspolozivihMesta;
    }

    public void setBrojRaspolozivihMesta(int brojRaspolozivihMesta) {
        this.brojRaspolozivihMesta = brojRaspolozivihMesta;
    }

    public double getCenaUlaznice() {
        return cenaUlaznice;
    }

    public void setCenaUlaznice(double cenaUlaznice) {
        this.cenaUlaznice = cenaUlaznice;
    }

    public String getProducent() {
        return producent;
    }

    public void setProducent(String producent) {
        this.producent = producent;
    }

    @Override
    public String toString() {
        return "PozorisnaPredstava{" + "id=" + id + ", narodnoPozoriste=" + idNarodnoPozoriste + ", pozorisniKomad=" + idPozorisniKomad + ", trupa=" + idTrupe + ", datumOdrzavanja=" + datumOdrzavanja + ", brojRaspolozivihMesta=" + brojRaspolozivihMesta + ", cenaUlaznice=" + cenaUlaznice + ", producent=" + producent + '}';
    }
    
}
