
package DAO;

import Entites.PozorisnaPredstava;
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
public class PozorisnaPredstavaDAO {
    private SessionFactory factory = null;
    private Session session = null;
    private Transaction tx = null;
    private RezervacijaDAO rDAO = new RezervacijaDAO();

    public PozorisnaPredstavaDAO() {
        factory = new HibernateUtil().getSessionFactory();
    }

    public void addPozorisnaPredstava(PozorisnaPredstava pp) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(pp);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Pozorisna predstava sacuvana.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public void deletePozorisnaPredstava(PozorisnaPredstava pp) {
        rDAO.deleteRezervacijeByPredstavaID(pp.getId());
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.delete(pp);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Pozorisna predstava obrisana.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public void updatePozorisnaPredstava(PozorisnaPredstava pp) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.update(pp);
            tx.commit();
            JOptionPane.showMessageDialog(null, "Pozorisna predstava azurirana.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
    }

    public List<PozorisnaPredstava> readFromPozorisnaPredstava() {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            List<PozorisnaPredstava> pozorisnePredstave = session.createCriteria(PozorisnaPredstava.class).list();
            tx.commit();
            return pozorisnePredstave;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
        return null;
    }

    public List<PozorisnaPredstava> readFromPozorisnaPredstavaByTrupaId(int id_trupa) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            List<PozorisnaPredstava> pozorisnePredstave = session.createCriteria(PozorisnaPredstava.class).add(Restrictions.eq("idTrupe", id_trupa)).list();
            tx.commit();
            return pozorisnePredstave;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
        return null;
    }
    
    public List<PozorisnaPredstava> readFromPozorisnaPredstavaByPozorisniKomadId(int id_pozorisni_komad) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            List<PozorisnaPredstava> pozorisnePredstave = session.createCriteria(PozorisnaPredstava.class).add(Restrictions.eq("idPozorisniKomad", id_pozorisni_komad)).list();
            tx.commit();
            return pozorisnePredstave;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
        return null;
    }

    public List<PozorisnaPredstava> readFromPozorisnaPredstavaByNarodnoPozoristeId(int id_narodnog_pozorisna) {
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            List<PozorisnaPredstava> pozorisnePredstave = session.createCriteria(PozorisnaPredstava.class).add(Restrictions.eq("idNarodnoPozoriste", id_narodnog_pozorisna)).list();
            tx.commit();
            return pozorisnePredstave;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "readFromPozorisnaPredstavaByNarodnoPozoristeId: " + ex, "Warrning", JOptionPane.WARNING_MESSAGE);
        } finally {
            session.close();
        }
        return null;
    }
}
