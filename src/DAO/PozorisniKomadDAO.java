
package DAO;

import Entites.PozorisniKomad;
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
public class PozorisniKomadDAO {
    private SessionFactory factory = null;
    private Session session = null;
    private Transaction tx = null;

    public PozorisniKomadDAO() {
        factory = new HibernateUtil().getSessionFactory();
    }

    public void addPozorisniKomad(PozorisniKomad pk) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(pk);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Pozorisni komad sacuvan.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public void deletePozorisniKomad(PozorisniKomad pk) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.delete(pk);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Pozorisni komad obrisan.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public void updatePozorisniKomad(PozorisniKomad pk) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.update(pk);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Pozorisni komad azuriran.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public List<PozorisniKomad> readFromPozorisniKomad() {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            List<PozorisniKomad> pozorisniKomadi = session.createCriteria(PozorisniKomad.class).list();
            tx.commit();
            return pozorisniKomadi;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
        return null;
    }
}
