
package Models;

import Entites.Rezervacija;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author Filip
 */
public class RezervacijeListModel extends AbstractListModel{

    List<Rezervacija> rezervacije = new ArrayList<>();

    public RezervacijeListModel() {
    }
    
    @Override
    public int getSize() {
        if(!rezervacije.isEmpty()) {
            return rezervacije.size();
        }
        return 0;
    }

    @Override
    public String getElementAt(int index) {
        if(index >= 0 && index < rezervacije.size()) {
            return String.valueOf(rezervacije.get(index).getId());
        }
        return null;
    }
    
    public Rezervacija getRezervacijaByIndex(int index) {
        if(index >= 0 && index < rezervacije.size()) {
            return rezervacije.get(index);
        }
        return null;
    }
    
    public void updateModel(List<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
        fireContentsChanged("", 0, rezervacije.size());
    }
}
