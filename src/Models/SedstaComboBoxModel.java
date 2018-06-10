package Models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author Filip
 */
public class SedstaComboBoxModel extends AbstractListModel<String> implements ComboBoxModel<String> {

    private List<String> list = new ArrayList<>();
    private Object selectedItem;

    public SedstaComboBoxModel() {
    }
    
    @Override
    public void setSelectedItem(Object anItem) {
        selectedItem = anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selectedItem;
    }

    @Override
    public int getSize() {
        if(!list.isEmpty()) {
            return list.size();
        }
        return 0;
    }

    @Override
    public String getElementAt(int index) {
        if(index >= 0 && index < list.size()) {
            return list.get(index);
        }
        return null;
    }

    public void updateModel(List<String> str) {
        this.list = str;
        fireIntervalAdded("", 0, list.size());
    }
}
