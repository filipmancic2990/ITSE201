package DAO;

import Entites.KreditnaKartica;
import Entites.Pretplatnik;
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
public class PretplatnikDAO {

    private SessionFactory factory = null;
    private Session session = null;
    private Transaction tx = null;
    private KreditnaKarticaDAO krDAO = new KreditnaKarticaDAO();
    private RezervacijaDAO rDAO = new RezervacijaDAO();

    public PretplatnikDAO() {
        factory = new HibernateUtil().getSessionFactory();
    }

    public void addPretplatnik(Pretplatnik p) {
        int i = krDAO.addKreditnaKartica(p.getKreditnaKartica());
        p.setId_kreditne_kartice(i);
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(p);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Pretplatnik sacuvan.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public void deletePretplatnik(Pretplatnik p) {
        rDAO.deleteRezervacijeByPretplatnikID(p.getId());
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.delete(p);
            tx.commit();
            krDAO.deleteKreditnaKartica(p.getKreditnaKartica());
            JOptionPane.showMessageDialog(null, "Pretplatnik obrisan.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public void updatePretplatnik(Pretplatnik p) {
        krDAO.updateKreditnaKartica(p.getKreditnaKartica());
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.update(p);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Pretplatnik azuriran.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public List<Pretplatnik> readFromPretplatnik() {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            List<Pretplatnik> pretplatnici = session.createCriteria(Pretplatnik.class).list();
            tx.commit();
            List<Pretplatnik> pretplatnici1 = new ArrayList<>();
            for (Pretplatnik p : pretplatnici) {
                p.setKreditnaKartica(krDAO.getKreditnaKartica(p.getId_kreditne_kartice()));
                pretplatnici1.add(p);
            }
            return pretplatnici1;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
        return null;
    }

    public Pretplatnik getPretplatnikById(int id) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            Pretplatnik pretplatnik = (Pretplatnik) session.createCriteria(Pretplatnik.class).add(Restrictions.eq("id", id)).uniqueResult();
            tx.commit();
            pretplatnik.setKreditnaKartica(krDAO.getKreditnaKartica(pretplatnik.getId_kreditne_kartice()));
            return pretplatnik;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
        return null;
    }
}
