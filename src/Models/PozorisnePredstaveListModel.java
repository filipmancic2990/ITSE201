package Models;

import Entites.PozorisnaPredstava;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author Filip
 */
public class PozorisnePredstaveListModel extends AbstractListModel<String> implements ComboBoxModel<String>{
    
    private Object selectedItem;
    private List<PozorisnaPredstava> pozorisnePredstave = new ArrayList<>();
        
    @Override
    public int getSize() {
        if(!pozorisnePredstave.isEmpty()) {
            return pozorisnePredstave.size();
        }
        return 0;
    }

    @Override
    public String getElementAt(int index) {
        if(index >= 0 && index < pozorisnePredstave.size()) {
            return String.valueOf(pozorisnePredstave.get(index).getId());
        }
        return null;
    }
    
    public PozorisnaPredstava getPozorisnaPredstavaByIndex(int index) {
        if(index >= 0 && index < pozorisnePredstave.size()) {
            return pozorisnePredstave.get(index);
        }
        return null;
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selectedItem = anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selectedItem;
    }

    public void updateModel(List<PozorisnaPredstava> pozorisnePredstave) {
        this.pozorisnePredstave = pozorisnePredstave;
        fireContentsChanged("", 0, pozorisnePredstave.size());
    }
    
    public List<PozorisnaPredstava> getPozorisnePredstave() {
        return pozorisnePredstave;
    }
    
}
