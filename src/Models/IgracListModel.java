package Models;

import Entites.Igrac;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author Filip
 */
public class IgracListModel extends AbstractListModel {
    List<Igrac> igraci = new ArrayList<>();

    public IgracListModel() {
    }

    @Override
    public int getSize() {
        if(!igraci.isEmpty()) {
            return igraci.size();
        }
        return 0;
    }

    @Override
    public String getElementAt(int index) {
        if(index >= 0 && index < igraci.size()) {
            Igrac i = igraci.get(index);
            return i.getIme() + " " + i.getPrezime();
        }
        return null;
    }
    
    public Igrac getIgracByIndex(int index) {
        if(index >= 0 && index < igraci.size()) {
            return igraci.get(index);
        }
        return null;
    }
    
    public void updateModel(List<Igrac> igraci) {
        this.igraci = igraci;
        fireContentsChanged("", 0, igraci.size());
    }
    
}
