package Models;

import Entites.Trupa;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author Filip
 */
public class TrupaListModel extends AbstractListModel<String> implements ComboBoxModel<String>{
    
    List<Trupa> trupe = new ArrayList<>();
    private Object selectedItem;
    
    @Override
    public int getSize() {
        if(!trupe.isEmpty()) {
            return trupe.size();
        }
        return 0;
    }

    @Override
    public String getElementAt(int index) {
        if(index >= 0 && index < trupe.size()) {
            return trupe.get(index).getName();
        }
        return null;
    }
    
    public Trupa getTrupaByIndex(int index) {
        if(index >= 0 && index < trupe.size()) {
            return trupe.get(index);
        }
        return null;
    }
    
    public void updateModel(List<Trupa> trupe) {
        this.trupe = trupe;
        fireContentsChanged("", 0, trupe.size());
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
