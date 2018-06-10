package Util;


import Entites.Igrac;
import Entites.KreditnaKartica;
import Entites.NarodnoPozoriste;
import Entites.PozorisnaPredstava;
import Entites.PozorisniKomad;
import Entites.Pretplatnik;
import Entites.Rezervacija;
import Entites.RezervisanaSedista;
import Entites.Sediste;
import Entites.Trupa;
import Entites.TrupaIIgrac;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Filip
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration().addPackage("Entiteti")
                    .addAnnotatedClass(Igrac.class)
                    .addAnnotatedClass(KreditnaKartica.class)
                    .addAnnotatedClass(NarodnoPozoriste.class)
                    .addAnnotatedClass(PozorisnaPredstava.class)
                    .addAnnotatedClass(PozorisniKomad.class)
                    .addAnnotatedClass(Pretplatnik.class)
                    .addAnnotatedClass(Rezervacija.class)
                    .addAnnotatedClass(Sediste.class)
                    .addAnnotatedClass(Trupa.class)
                    .addAnnotatedClass(RezervisanaSedista.class)
                    .addAnnotatedClass(TrupaIIgrac.class)
                    .configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
