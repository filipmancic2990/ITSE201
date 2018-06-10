package DAO;

import Entites.Igrac;
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
public class IgracDAO {

    private SessionFactory factory = null;
    private Session session = null;
    private Transaction tx = null;

    public IgracDAO() {
        factory = new HibernateUtil().getSessionFactory();
    }

    public void addIgrac(Igrac i) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(i);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Igrac sacuvan.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "IgracDAO: " + ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public void deleteIgrac(Igrac i) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.delete(i);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Igrac obrisan.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "IgracDAO: " + ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public void updateIgrac(Igrac i) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.update(i);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Igrac azuriran.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "IgracDAO: " + ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public List<Igrac> readFromIgrac() {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            List<Igrac> igraci = session.createCriteria(Igrac.class).list();
            tx.commit();
            return igraci;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "IgracDAO: " + ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
        return null;
    }
    
    public Igrac readFromIgracById(int id) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            Igrac igrac = (Igrac) session.createCriteria(Igrac.class).add(Restrictions.eq("id", id)).uniqueResult();
            tx.commit();
            return igrac;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "IgracDAO: " + ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
        return null;
    }
}
