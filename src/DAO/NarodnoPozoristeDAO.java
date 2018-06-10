
package DAO;

import Entites.NarodnoPozoriste;
import Util.HibernateUtil;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Filip
 */
public class NarodnoPozoristeDAO {
    private SessionFactory factory = null;
    private Session session = null;
    private Transaction tx = null;

    public NarodnoPozoristeDAO() {
        factory = new HibernateUtil().getSessionFactory();
    }

    public void addNarodnoPozoriste(NarodnoPozoriste np) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(np);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Narodno pozoriste sacuvano.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public void deleteNarodnoPozoriste(NarodnoPozoriste np) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.delete(np);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Narodno pozoriste obrisano.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public void updateNarodnoPozoriste(NarodnoPozoriste np) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.update(np);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Narodno pozoriste azurirano.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public List<NarodnoPozoriste> readFromNarodnoPozoriste() {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            List<NarodnoPozoriste> narodnaPozorista = session.createCriteria(NarodnoPozoriste.class).list();
            tx.commit();
            return narodnaPozorista;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
        return null;
    }
}
