package DAO;

import Entites.RezervisanaSedista;
import Entites.Sediste;
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
public class RezervisanaSedistaDAO {

    private SessionFactory factory = null;
    private Session session = null;
    private Transaction tx = null;
    private SedisteDAO sDAO = new SedisteDAO();
    
    public RezervisanaSedistaDAO() {
        factory = new HibernateUtil().getSessionFactory();
    }

    public void addRezervisanaSedista(RezervisanaSedista rs) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(rs);
            tx.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "addRezervisanaSedista: " + ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public void deleteRezervisanaSedista(RezervisanaSedista rs) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.delete(rs);
            tx.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "deleteRezervisanaSedista: " + ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public void deleteRezervisanaSedistaById(int id_rezervacije) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            String query = "delete from RezervisanaSedista where id_rezervacija = :id_rezervacije";
            session.createQuery(query).setInteger("id_rezervacije", id_rezervacije).executeUpdate();
            tx.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,  "DeleteRezervisanaSedistaById: " + ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public void updateRezervisanaSedista(RezervisanaSedista rs) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.update(rs);
            tx.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public List<RezervisanaSedista> readFromRezervisanaSedista() {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            List<RezervisanaSedista> rezervisanaSedista = session.createCriteria(RezervisanaSedista.class).list();
            tx.commit();
            return rezervisanaSedista;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
        return null;
    }
    
    public List<Sediste> readFromSedistaByRezervacijaId(int id) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            //String query = "from RezervisanaSedista where id_rezervacija = :id";
            //List<RezervisanaSedista> rezervisanaSedista = session.createQuery(query).setInteger("id", id).list();
            List<RezervisanaSedista> rezervisanaSedista = session.createCriteria(RezervisanaSedista.class).add(Restrictions.eq("id_rezervacija", id)).list();
            tx.commit();
            List<Sediste> sedista = new ArrayList<>();
            for(RezervisanaSedista rs : rezervisanaSedista) {
                sedista.add(sDAO.readFromSedisteById(rs.getId_sedista()));
            }
            return sedista;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,  "readFromSedistaByRezervacijaId: " + ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
        return null;
    }
}
