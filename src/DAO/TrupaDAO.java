package DAO;

import Entites.Trupa;
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
public class TrupaDAO {

    private SessionFactory factory = null;
    private Session session = null;
    private Transaction tx = null;

    public TrupaDAO() {
        factory = new HibernateUtil().getSessionFactory();
    }

    public void addTrupa(Trupa t) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(t);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Trupa je sacuvana.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }
    
    public void deleteTrupa(Trupa t) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.delete(t);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Trupa je obrisana.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }
    
    public void updateTrupa(Trupa t) {
         try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.update(t);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Trupa je obrisana.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }
    
    public List<Trupa> readFromTrupa() {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            List<Trupa> trupe = session.createCriteria(Trupa.class).list();
            tx.commit();
            return trupe;
        } catch (HibernateException ex) {
            JOptionPane.showMessageDialog(null, ex, "Warrining", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
        return null;
    }
}
