
package DAO;

import Entites.Sediste;
import Util.HibernateUtil;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Filip
 */
public class SedisteDAO {
    private SessionFactory factory = null;
    private Session session = null;
    private Transaction tx = null;

    public SedisteDAO() {
        factory = new HibernateUtil().getSessionFactory();
    }

    public int addSediste(Sediste s) {
        int i = 0;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            i = (Integer)session.save(s);
            tx.commit();
//            JOptionPane.showMessageDialog(null, "Sediste sacuvano.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
            return i;
        }
    }

    public void deleteSediste(Sediste s) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.delete(s);
            tx.commit();
//            JOptionPane.showMessageDialog(null, "Sediste obrisano.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public void updateSediste(Sediste s) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.update(s);
            tx.commit();
//            JOptionPane.showMessageDialog(null, "Sediste azurirano.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public List<Sediste> readFromSediste() {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            List<Sediste> sedista = session.createCriteria(Sediste.class).list();
            tx.commit();
            return sedista;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
        return null;
    }
    
    public Sediste readFromSedisteById(int id) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            Sediste sediste = (Sediste) session.createCriteria(Sediste.class).add(Restrictions.eq("id", id)).uniqueResult();
            tx.commit();
            return sediste;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
        return null;
    }
}
