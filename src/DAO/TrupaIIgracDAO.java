package DAO;

import Entites.Igrac;
import Entites.TrupaIIgrac;
import Util.HibernateUtil;
import java.util.ArrayList;
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
public class TrupaIIgracDAO {

    private SessionFactory factory = null;
    private Session session = null;
    private Transaction tx = null;
    private IgracDAO iDAO = new IgracDAO();

    public TrupaIIgracDAO() {
        factory = new HibernateUtil().getSessionFactory();
    }

    public void addTrupaIIgrac(TrupaIIgrac tIp) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(tIp);
            tx.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "addTrupaIIgrac: " + ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public void deleteTrupaIIgrac(TrupaIIgrac tIp) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.delete(tIp);
            tx.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "deleteTrupaIIgrac: " + ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public void deleteIgraceByTrupaId(int id_trupa) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            String query = "delete from TrupaIIgrac where id_trupa = :id_trupa";
            session.createQuery(query).setInteger("id_trupa", id_trupa).executeUpdate();
            tx.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "deleteIgraceByTrupaId: " + ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public void updateTrupaIIGrac(TrupaIIgrac tIp) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.update(tIp);
            tx.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "updateTrupaIIGrac" + ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public List<TrupaIIgrac> readAllFromTrupaIIgrac() {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            List<TrupaIIgrac> trupaIIgraci = session.createCriteria(TrupaIIgrac.class).list();
            tx.commit();
            return trupaIIgraci;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "readAllFrom: " + ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
        return null;
    }

    public List<Igrac> readFromIgracByTrupaId(int id) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            //String query = "from TrupaIIgrac where id_trupa = :id_trupa";
            List<TrupaIIgrac> trupaIIgraci = session.createCriteria(TrupaIIgrac.class).add(Restrictions.eq("id_trupa", id)).list();
            tx.commit();
            List<Igrac> igraci = new ArrayList<>();
            for (TrupaIIgrac tIp : trupaIIgraci) {
                igraci.add(iDAO.readFromIgracById(tIp.getId_igrac()));
            }
            return igraci;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "readFromSedistaByRezervacijaId: " + ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
        return null;
    }

    public void deleteIgracByIgracId(int id_igrac) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            String query = "delete from TrupaIIgrac where id_igrac = :id_igrac";
            session.createQuery(query).setInteger("id_trupa", id_igrac).executeUpdate();
            tx.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "deleteIgraceByTrupaId: " + ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

}
