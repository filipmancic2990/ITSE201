
package Models;

import Entites.Pretplatnik;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author Filip
 */
public class PretplatniciListModel extends AbstractListModel{

    List<Pretplatnik> pretplatnici = new ArrayList<>();

    public PretplatniciListModel() {
    }
    
    @Override
    public int getSize() {
        if(!pretplatnici.isEmpty()) {
            return pretplatnici.size();
        }
        return 0;
    }

    @Override
    public String getElementAt(int index) {
        if(index >= 0 && index < pretplatnici.size()) {
            return String.valueOf(pretplatnici.get(index).getId());
        }
        return null;
    }
    
    public Pretplatnik getPretplatnikByIndex(int index) {
        if(index >= 0 && index < pretplatnici.size()) {
            return pretplatnici.get(index);
        }
        return null;
    }
    
    public void updateModel(List<Pretplatnik> pretplatnici) {
        this.pretplatnici = pretplatnici;
        fireContentsChanged("", 0, pretplatnici.size());
    }
    
}
