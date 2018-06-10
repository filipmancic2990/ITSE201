package DAO;

import Entites.Rezervacija;
import Entites.RezervisanaSedista;
import Entites.Sediste;
import Util.HibernateUtil;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Filip
 */
public class RezervacijaDAO {

    private SessionFactory factory = null;
    private Session session = null;
    private Transaction tx = null;
    private SedisteDAO sDAO = new SedisteDAO();
    private RezervisanaSedistaDAO rsDAO = new RezervisanaSedistaDAO();

    public RezervacijaDAO() {
        factory = new HibernateUtil().getSessionFactory();
    }

    public void addRezervacija(Rezervacija r) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            int iR = (Integer) session.save(r);
            tx.commit();
            for(Sediste s : r.getSedista()) {
                rsDAO.addRezervisanaSedista(new RezervisanaSedista(s.getId(), iR));
            }
            JOptionPane.showMessageDialog(null, "Rezervacija sacuvana.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public void deleteRezervacija(Rezervacija r) {
        rsDAO.deleteRezervisanaSedistaById(r.getId());
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.delete(r);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Rezervacija obrisana.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public void updateRezervacija(Rezervacija r) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.update(r);
            tx.commit();
            rsDAO.deleteRezervisanaSedistaById(r.getId());
            for(Sediste s : r.getSedista()) {
                rsDAO.addRezervisanaSedista(new RezervisanaSedista(s.getId(), r.getId()));
            }
            JOptionPane.showMessageDialog(null, "Rezervacija azurirana.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public List<Rezervacija> readFromRezervacija() {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            List<Rezervacija> rezervacije = session.createCriteria(Rezervacija.class).list();
            tx.commit();
            for (int i = 0; i < rezervacije.size(); i++) {
                Rezervacija r = rezervacije.get(i);

            }
            return rezervacije;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
        return null;
    }

    public void deleteRezervacijeByPretplatnikID(int id) {
        List<Rezervacija> rezervacije = readFromRezervacija();
        for (Rezervacija r : rezervacije) {
            if (r.getIdPretplatnika() == id) {
                rsDAO.deleteRezervisanaSedistaById(id);
                deleteRezervacija(r);
            }
        }
    }
    
    public void deleteRezervacijeByPredstavaID(int id) {
        List<Rezervacija> rezervacije = readFromRezervacija();
        for (Rezervacija r : rezervacije) {
            if (r.getIdPozorisnePredstave() == id) {
                rsDAO.deleteRezervisanaSedistaById(id);
                deleteRezervacija(r);
            }
        }
    }
}
