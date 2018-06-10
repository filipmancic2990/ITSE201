
package DAO;

import Entites.KreditnaKartica;
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
public class KreditnaKarticaDAO {
    private SessionFactory factory = null;
    private Session session = null;
    private Transaction tx = null;

    public KreditnaKarticaDAO() {
        factory = new HibernateUtil().getSessionFactory();
    }

    public Integer addKreditnaKartica(KreditnaKartica k) {
        Integer i = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            i = (Integer)session.save(k);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Kreditna kartica sacuvana.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "IgracDAO: " + ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
            return i;
        }
    }

    public void deleteKreditnaKartica(KreditnaKartica k) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.delete(k);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Kreditna kartica obrisana.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public void updateKreditnaKartica(KreditnaKartica k) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.update(k);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Kreditna kartica azurirana.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public List<KreditnaKartica> readFromKreditnaKartica() {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            List<KreditnaKartica> kreditneKartice = session.createCriteria(KreditnaKartica.class).list();
            tx.commit();
            return kreditneKartice;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
        return null;
    }
    
    public KreditnaKartica getKreditnaKartica(int id) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            KreditnaKartica kreditnaKartica = (KreditnaKartica) session.createCriteria(KreditnaKartica.class).add(Restrictions.eq("id", id)).uniqueResult();
            tx.commit();
            return kreditnaKartica;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
        return null;
    }

}
