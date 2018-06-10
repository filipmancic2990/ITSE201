package Models;

import Entites.PozorisniKomad;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author Filip
 */
public class PozorisniKomadListModel extends AbstractListModel<String> implements ComboBoxModel<String> {

    private String selectedItem;
    List<PozorisniKomad> pozorisniKomadi = new ArrayList<>();

    public PozorisniKomadListModel() {
    }
    
    @Override
    public int getSize() {
        if(!pozorisniKomadi.isEmpty()) {
            return pozorisniKomadi.size();
        }
        return 0;
    }

    @Override
    public String getElementAt(int index) {
        if(index >= 0 && index < pozorisniKomadi.size()) {
            return pozorisniKomadi.get(index).getNaziv();
        }
        return null;
    }
    
    public PozorisniKomad getPozorisniKomadByIndex(int index) {
        if(index >= 0 && index < pozorisniKomadi.size()) {
            return pozorisniKomadi.get(index);
        }
        return null;
    }
    
    public void updateModel(List<PozorisniKomad> pozorisniKomadi) {
        this.pozorisniKomadi = pozorisniKomadi;
        fireContentsChanged("", 0, pozorisniKomadi.size());
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selectedItem = (String)anItem;
    }

    @Override
    public String getSelectedItem() {
        return selectedItem;
    }
    
}
