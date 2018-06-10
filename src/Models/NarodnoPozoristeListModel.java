package Models;

import Entites.NarodnoPozoriste;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author Filip
 */
public class NarodnoPozoristeListModel extends AbstractListModel<String> implements ComboBoxModel<String>{

    private Object selectedItem;
    List<NarodnoPozoriste> pozorista = new ArrayList<>();

    public NarodnoPozoristeListModel() {
    }

    @Override
    public int getSize() {
        if (!pozorista.isEmpty()) {
            return pozorista.size();
        }
        return 0;
    }

    @Override
    public String getElementAt(int index) {
        if (index >= 0 && index < pozorista.size()) {
            return pozorista.get(index).getNaziv();
        }
        return null;
    }
    
    public NarodnoPozoriste getNarodnoPozoristeByIndex(int index) {
        if (index >= 0 && index < pozorista.size()) {
            return pozorista.get(index);
        }
        return null;
    }
    
    public void updateModel(List<NarodnoPozoriste> pozorista) {
        this.pozorista = pozorista;
        fireContentsChanged("", 0, pozorista.size());
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selectedItem = anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selectedItem;
    }

}
