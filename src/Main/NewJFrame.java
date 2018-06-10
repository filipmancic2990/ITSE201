package Main;

import Entites.*;
import DAO.*;
import Models.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Filip
 */
public class NewJFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private List<NarodnoPozoriste> narodnaPozorista = new ArrayList<>();
    private List<PozorisnaPredstava> pozorisnePredstave = new ArrayList<>();
    private List<PozorisniKomad> pozorisniKomadi = new ArrayList<>();
    private List<Rezervacija> rezervacije = new ArrayList<>();

    private NarodnoPozoristeDAO npDAO = new NarodnoPozoristeDAO();
    private PozorisniKomadDAO pkDAO = new PozorisniKomadDAO();
    private PozorisnaPredstavaDAO ppDAO = new PozorisnaPredstavaDAO();
    private RezervacijaDAO rDAO = new RezervacijaDAO();
    private TrupaDAO tDAO = new TrupaDAO();
    private IgracDAO iDAO = new IgracDAO();
    private PretplatnikDAO pDAO = new PretplatnikDAO();
    private RezervisanaSedistaDAO rsDAO = new RezervisanaSedistaDAO();
    private TrupaIIgracDAO tIiDAO = new TrupaIIgracDAO();
    private SedisteDAO sDAO = new SedisteDAO();

    private NarodnoPozoristeListModel narodnoPozoristeListModel = new NarodnoPozoristeListModel();
    private PozorisnePredstaveListModel pozorisnePredstaveListModel = new PozorisnePredstaveListModel();
    private PozorisnePredstaveListModel pozorisnePredstaveListModelR = new PozorisnePredstaveListModel();
    private PozorisniKomadListModel pozorisniKomadListModel = new PozorisniKomadListModel();
    private RezervacijeListModel rezervacijeListModel = new RezervacijeListModel();
    private TrupaListModel trupaListModel = new TrupaListModel();
    private IgracListModel igracListModel = new IgracListModel();
    private IgracListModel igraciTrupaListModel = new IgracListModel();
    private PretplatniciListModel pretplatniciListModel = new PretplatniciListModel();

    //Rezervacije panel
    private NarodnoPozoriste selectedNPR = null;
    private PozorisniKomad selectedPKR = null;
    private PozorisnaPredstava selectedPPR = null;
    private List<Rezervacija> selectedRezervacijeList = new ArrayList<>();
    private Rezervacija selectedRezervacijaR = null;
    private Pretplatnik pretplatnikR = null;
    private List<Sediste> sedistaR = new ArrayList<>();
    private KreditnaKartica kreditnaKartica = null;
    private List<Pretplatnik> pretplatniciR = new ArrayList<>();

    //Narodna pozorista i predstave
    private NarodnoPozoriste npNP = null;
    private PozorisnaPredstava ppNP = null;

    //Pozorisni komadi
    private PozorisniKomad pkPK = null;

    //Trupa i Igraci
    private Trupa selectedTrupaTI = null;
    private Igrac selectedIgracTI = null;
    private List<Trupa> trupe = new ArrayList<>();
    private List<Igrac> igraci = new ArrayList<>();
    private List<Igrac> trupaIIgraci = new ArrayList<>();

    //Queries
    QueryDAO qDAO = new QueryDAO();

    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        initComponents();
        lNarodnaPozorista.setModel(narodnoPozoristeListModel);
        lPozorisnePredstave.setModel(pozorisnePredstaveListModel);
        lPozorisnePredstaveR.setModel(pozorisnePredstaveListModelR);
        lPozorisniKomadiPK.setModel(pozorisniKomadListModel);
        lRezervacija.setModel(rezervacijeListModel);
        lTrupe.setModel(trupaListModel);
        lIgraci.setModel(igracListModel);
        lPretplatniciR.setModel(pretplatniciListModel);
        lIgraciTrupa.setModel(igraciTrupaListModel);
        cbPozorista.setModel(narodnoPozoristeListModel);
        cbPozorisniKomad.setModel(pozorisniKomadListModel);
        cbPozorisniKomadP.setModel(pozorisniKomadListModel);
        cbTrupaP.setModel(trupaListModel);
        refreshAllModels();

        cbPozorista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = cbPozorista.getSelectedIndex();
                selectedNPR = narodnoPozoristeListModel.getNarodnoPozoristeByIndex(index);
                lPozorisnePredstaveR.setSelectedIndex(-1);
                selectedPPR = null;
                selectedRezervacijaR = null;
                lRezervacija.clearSelection();
                refreshPozorisnePredstaveR();
                clearPredstaveLbl();
                clearRezervacije();
            }
        });

        cbPozorisniKomadP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = cbPozorisniKomadP.getSelectedIndex();
                selectedPKR = pozorisniKomadListModel.getPozorisniKomadByIndex(index);
                lPozorisnePredstaveR.setSelectedIndex(-1);
                selectedPPR = null;
                selectedRezervacijaR = null;
                lRezervacija.clearSelection();
                refreshPozorisnePredstaveR();
                clearPredstaveLbl();
                clearRezervacije();
            }
        });

        lPozorisnePredstaveR.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectedPPR = pozorisnePredstaveListModelR.getPozorisnaPredstavaByIndex(lPozorisnePredstaveR.getSelectedIndex());
                    if (selectedPPR != null) {
                        System.out.println("lPozorisnePredstaveR: " + selectedPPR.toString());
                        lblBrMesta.setText(String.valueOf(selectedPPR.getBrojRaspolozivihMesta()));
                        lblDatumOdrzavanja.setText(String.valueOf(selectedPPR.getDatumOdrzavanja()));
                        lCenaUlaznice.setText(String.valueOf(selectedPPR.getCenaUlaznice()));
                        refreshRezervacijeR();
                        clearRezervacije();
                    }
                    refreshRezervacijeR();
                }
            }
        });

        lRezervacija.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectedRezervacijaR = rezervacijeListModel.getRezervacijaByIndex(lRezervacija.getSelectedIndex());
                    if (selectedRezervacijaR != null) {
                        System.out.println("lRezervacija: " + selectedRezervacijaR.toString());
                        lPretplatniciR.setSelectedValue(String.valueOf(selectedRezervacijaR.getIdPretplatnika()), true);
                        pretplatnikR = pretplatniciListModel.getPretplatnikByIndex(lPretplatniciR.getSelectedIndex());
                        tfAdresa.setText(pretplatnikR.getAdresa());
                        tfBrojTelefona.setText(pretplatnikR.getTelefon());
                        tfBrojkartice.setText(pretplatnikR.getKreditnaKartica().getBroj());
                        cbTipKartice.setSelectedItem(pretplatnikR.getKreditnaKartica().getTip());
                        dpDatumVazenjaKartice.setDate(pretplatnikR.getKreditnaKartica().getDatumVazenja());
                        dpDatumRezervisanja.setDate(selectedRezervacijaR.getDatumRezervisanja());
                        dpDatumPreuzimanja.setDate(selectedRezervacijaR.getDatumPreuzimanja());
                        lblCena.setText(String.valueOf(selectedRezervacijaR.getUkupanIznos()));
                        sedistaR = selectedRezervacijaR.getSedista();
                        refreshPSedista();
                    }
                    refreshPSedista();
                }
            }
        });

        lNarodnaPozorista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    npNP = narodnoPozoristeListModel.getNarodnoPozoristeByIndex(lNarodnaPozorista.getSelectedIndex());
                    System.out.println("lNarodnaPozorisna: " + npNP.toString());
                    tfNazivPozorista.setText(npNP.getNaziv());
                    refreshPozorisnePredstaveNP();
                }
            }
        });

        lPretplatniciR.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    pretplatnikR = pretplatniciListModel.getPretplatnikByIndex(lPretplatniciR.getSelectedIndex());
                    System.out.println("lPretplatniciR: " + pretplatnikR.toString());
                    tfAdresa.setText(pretplatnikR.getAdresa());
                    tfBrojTelefona.setText(pretplatnikR.getTelefon());
                    tfBrojkartice.setText(pretplatnikR.getKreditnaKartica().getBroj());
                    cbTipKartice.setSelectedItem(pretplatnikR.getKreditnaKartica().getTip());
                    dpDatumVazenjaKartice.setDate(pretplatnikR.getKreditnaKartica().getDatumVazenja());
                }
            }
        });

        lPozorisnePredstave.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    ppNP = pozorisnePredstaveListModel.getPozorisnaPredstavaByIndex(lPozorisnePredstave.getSelectedIndex());
                    System.out.println("lPozorisnePredstave: " + ppNP.toString());
                    Trupa trupa = null;
                    PozorisniKomad pk = null;
                    for (Trupa t : trupe) {
                        if (t.getId() == ppNP.getIdTrupe()) {
                            trupa = t;
                            break;
                        }
                    }
                    for (PozorisniKomad pk1 : pozorisniKomadi) {
                        if (pk1.getId() == ppNP.getIdPozorisniKomad()) {
                            pk = pk1;
                            break;
                        }
                    }
                    cbPozorisniKomadP.setSelectedItem(pk.getNaziv());
                    cbPozorisniKomadP.repaint();
                    cbTrupaP.setSelectedItem(trupa.getName());
                    cbTrupaP.repaint();
                    dpDatumOdrzavanja.setDate(ppNP.getDatumOdrzavanja());
                    tfBrojRaspolozivihMesta.setText(String.valueOf(ppNP.getBrojRaspolozivihMesta()));
                    tfProducent.setText(ppNP.getProducent());
                    tfCenaUlaznice.setText(String.valueOf(ppNP.getCenaUlaznice()));
                }
            }
        });

        lPozorisniKomadiPK.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    pkPK = pozorisniKomadListModel.getPozorisniKomadByIndex(lPozorisniKomadiPK.getSelectedIndex());
                    System.out.println("lPozorisniKomadiPK: " + pkPK.toString());
                    tfNazivPozorisnogKomada.setText(pkPK.getNaziv());
                    tfAutorPozorisnogKomada.setText(pkPK.getAutor());
                }
            }
        });

        lTrupe.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectedTrupaTI = trupaListModel.getTrupaByIndex(lTrupe.getSelectedIndex());
                    System.out.println("lTrupe: " + selectedTrupaTI.toString());
                    tfNazivTrupe.setText(selectedTrupaTI.getName());
                    refreshTrupaIIgraci();
                }
            }
        });

        lIgraci.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectedIgracTI = igracListModel.getIgracByIndex(lIgraci.getSelectedIndex());
                    System.out.println("lIgraci: " + selectedIgracTI.toString());
                    tfImeIgraca.setText(selectedIgracTI.getIme());
                    tfPrezimeIgraca.setText(selectedIgracTI.getPrezime());
                    dpDatumRodjenja.setDate(selectedIgracTI.getDatumRodjenja());
                    tfPozicijaIgraca.setText(selectedIgracTI.getKvalifikacija());
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        cbPozorista = new javax.swing.JComboBox<>();
        cbPozorisniKomad = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblBrMesta = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lCenaUlaznice = new javax.swing.JLabel();
        lblDatumOdrzavanja = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lRezervacija = new javax.swing.JList<>();
        bRefreshRezervacije = new javax.swing.JButton();
        bDeleteRezervacije = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblCena = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tfAdresa = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tfBrojTelefona = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tfBrojkartice = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cbTipKartice = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        bCreateRezervacija = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        bAddSediste = new javax.swing.JButton();
        dpDatumRezervisanja = new org.jdesktop.swingx.JXDatePicker();
        dpDatumPreuzimanja = new org.jdesktop.swingx.JXDatePicker();
        dpDatumVazenjaKartice = new org.jdesktop.swingx.JXDatePicker();
        jScrollPane8 = new javax.swing.JScrollPane();
        lPozorisnePredstaveR = new javax.swing.JList<>();
        bUpdateRezervacija = new javax.swing.JButton();
        bRefreshR = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        lPretplatniciR = new javax.swing.JList<>();
        bCreatePretplatnikR = new javax.swing.JButton();
        bUpdatePretplatnikR = new javax.swing.JButton();
        bDeletePretplatnikR = new javax.swing.JButton();
        bRefreshPretplatniciR = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        pSedistaP = new javax.swing.JPanel();
        bClearSedista = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        tfNazivPozorista = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        lNarodnaPozorista = new javax.swing.JList<>();
        bCreatePozoriste = new javax.swing.JButton();
        bDeletePozoriste = new javax.swing.JButton();
        bRefreshPozorista = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        lPozorisnePredstave = new javax.swing.JList<>();
        bCreatePredstavu = new javax.swing.JButton();
        bDeletePredstavu = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        cbPozorisniKomadP = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        cbTrupaP = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        tfBrojRaspolozivihMesta = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        tfProducent = new javax.swing.JTextField();
        bUpdatePredstavu = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        dpDatumOdrzavanja = new org.jdesktop.swingx.JXDatePicker();
        bRefreshPredstave = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        tfCenaUlaznice = new javax.swing.JTextField();
        bUpdatePozoriste = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        tfNazivTrupe = new javax.swing.JTextField();
        bCreateTrupa = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        lTrupe = new javax.swing.JList<>();
        bRefreshTrupa = new javax.swing.JButton();
        bDeleteTrupa = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        lIgraci = new javax.swing.JList<>();
        bDeleteIgraci = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        tfImeIgraca = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        tfPrezimeIgraca = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        tfPozicijaIgraca = new javax.swing.JTextField();
        bCreateIgraca = new javax.swing.JButton();
        bUpdateIgraca = new javax.swing.JButton();
        bRefreshIgraci = new javax.swing.JButton();
        dpDatumRodjenja = new org.jdesktop.swingx.JXDatePicker();
        jScrollPane10 = new javax.swing.JScrollPane();
        lIgraciTrupa = new javax.swing.JList<>();
        bAddIgracToTrupa = new javax.swing.JButton();
        bDeleteIgracFromTrupa = new javax.swing.JButton();
        bRefreshLIgracTrupa = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        bUpdate = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lPozorisniKomadiPK = new javax.swing.JList<>();
        jLabel32 = new javax.swing.JLabel();
        tfNazivPozorisnogKomada = new javax.swing.JTextField();
        tfAutorPozorisnogKomada = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        bCreatePozorisniKomad = new javax.swing.JButton();
        bUpdatePozorisniKomad = new javax.swing.JButton();
        bDeletePozorisniKomad = new javax.swing.JButton();
        bRefreshPozorisniKomadi = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        bAQuery = new javax.swing.JButton();
        bBQuery = new javax.swing.JButton();
        bDQuery = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        taQuery = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        cbPozorista.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbPozorisniKomad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Datum odrzavanja: ");

        jLabel2.setText("Broj raspolozivih mesta: ");

        lblBrMesta.setText("n/a");

        jLabel3.setText("Cena ulaznice:");

        lCenaUlaznice.setText("unavailable");

        lblDatumOdrzavanja.setText("n/a");

        lRezervacija.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(lRezervacija);

        bRefreshRezervacije.setText("Refresh");
        bRefreshRezervacije.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefreshRezervacijeActionPerformed(evt);
            }
        });

        bDeleteRezervacije.setText("Delete");
        bDeleteRezervacije.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeleteRezervacijeActionPerformed(evt);
            }
        });

        jLabel4.setText("Datum rezervisanja:");

        jLabel5.setText("Datum preuzimanja:");

        jLabel6.setText("Iznos:");

        lblCena.setText("n/a");

        jLabel9.setText("Adresa:");

        jLabel10.setText("Broj telefona:");

        jLabel11.setText("Broj kartice:");

        jLabel12.setText("Tip kartice:");

        cbTipKartice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "K", "D" }));

        jLabel13.setText("Datum vazenja:");

        bCreateRezervacija.setText("Create");
        bCreateRezervacija.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCreateRezervacijaActionPerformed(evt);
            }
        });

        jLabel30.setText("Rezervacije:");

        bAddSediste.setText("+");
        bAddSediste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddSedisteActionPerformed(evt);
            }
        });

        lPozorisnePredstaveR.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane8.setViewportView(lPozorisnePredstaveR);

        bUpdateRezervacija.setText("Update");
        bUpdateRezervacija.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUpdateRezervacijaActionPerformed(evt);
            }
        });

        bRefreshR.setText("Refresh");
        bRefreshR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefreshRActionPerformed(evt);
            }
        });

        lPretplatniciR.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane9.setViewportView(lPretplatniciR);

        bCreatePretplatnikR.setText("Create");
        bCreatePretplatnikR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCreatePretplatnikRActionPerformed(evt);
            }
        });

        bUpdatePretplatnikR.setText("Update");
        bUpdatePretplatnikR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUpdatePretplatnikRActionPerformed(evt);
            }
        });

        bDeletePretplatnikR.setText("Delete");
        bDeletePretplatnikR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeletePretplatnikRActionPerformed(evt);
            }
        });

        bRefreshPretplatniciR.setText("Refresh");
        bRefreshPretplatniciR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefreshPretplatniciRActionPerformed(evt);
            }
        });

        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane3.setMaximumSize(new java.awt.Dimension(100, 100));

        pSedistaP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pSedistaP.setLayout(new org.jdesktop.swingx.VerticalLayout());
        jScrollPane3.setViewportView(pSedistaP);

        bClearSedista.setText("Clear");
        bClearSedista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bClearSedistaActionPerformed(evt);
            }
        });

        jLabel7.setText("Sedista:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbPozorista, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbPozorisniKomad, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bRefreshR)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDatumOdrzavanja))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(bRefreshRezervacije)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bDeleteRezervacije)))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblBrMesta)
                        .addGap(99, 99, 99)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(bRefreshPretplatniciR)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bDeletePretplatnikR)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bUpdatePretplatnikR)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bCreatePretplatnikR))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel12))
                                .addGap(40, 40, 40)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfBrojkartice)
                                    .addComponent(tfBrojTelefona)
                                    .addComponent(tfAdresa)
                                    .addComponent(dpDatumVazenjaKartice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbTipKartice, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(bClearSedista)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bUpdateRezervacija)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bCreateRezervacija))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dpDatumRezervisanja, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                                    .addComponent(dpDatumPreuzimanja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(78, 78, 78)
                                .addComponent(lblCena)))
                        .addGap(95, 95, 95))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bAddSediste))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lCenaUlaznice)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbPozorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(lblBrMesta)
                    .addComponent(jLabel3)
                    .addComponent(lCenaUlaznice)
                    .addComponent(lblDatumOdrzavanja))
                .addGap(4, 4, 4)
                .addComponent(cbPozorisniKomad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(tfAdresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(tfBrojTelefona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(dpDatumRezervisanja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(dpDatumPreuzimanja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(lblCena))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(bAddSediste))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfBrojkartice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(dpDatumVazenjaKartice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbTipKartice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8)
                            .addComponent(jScrollPane2))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bRefreshRezervacije)
                            .addComponent(bDeleteRezervacije)
                            .addComponent(bRefreshR)
                            .addComponent(bCreateRezervacija)
                            .addComponent(bUpdateRezervacija)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bCreatePretplatnikR)
                            .addComponent(bUpdatePretplatnikR)
                            .addComponent(bDeletePretplatnikR)
                            .addComponent(bRefreshPretplatniciR)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bClearSedista)))
                .addGap(12, 12, 12))
        );

        jTabbedPane1.addTab("Rezervacije", jPanel1);

        jLabel14.setText("Naziv:");

        lNarodnaPozorista.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(lNarodnaPozorista);

        bCreatePozoriste.setText("Create");
        bCreatePozoriste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCreatePozoristeActionPerformed(evt);
            }
        });

        bDeletePozoriste.setText("Delete");
        bDeletePozoriste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeletePozoristeActionPerformed(evt);
            }
        });

        bRefreshPozorista.setText("Refresh");
        bRefreshPozorista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefreshPozoristaActionPerformed(evt);
            }
        });

        jLabel15.setText("Pozorista");

        lPozorisnePredstave.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(lPozorisnePredstave);

        bCreatePredstavu.setText("Create");
        bCreatePredstavu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCreatePredstavuActionPerformed(evt);
            }
        });

        bDeletePredstavu.setText("Delete");
        bDeletePredstavu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeletePredstavuActionPerformed(evt);
            }
        });

        jLabel17.setText("Pozorisni komad:");

        cbPozorisniKomadP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel18.setText("Trupa:");

        cbTrupaP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel19.setText("Datum odrzavanja:");

        jLabel20.setText("Broj raspolozivih mesta:");

        jLabel21.setText("Producent:");

        bUpdatePredstavu.setText("Update");
        bUpdatePredstavu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUpdatePredstavuActionPerformed(evt);
            }
        });

        jLabel24.setText("Predstave");

        bRefreshPredstave.setText("Refresh");
        bRefreshPredstave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefreshPredstaveActionPerformed(evt);
            }
        });

        jLabel34.setText("Cena ulaznice:");

        bUpdatePozoriste.setText("Update");
        bUpdatePozoriste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUpdatePozoristeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNazivPozorista)
                        .addGap(9, 9, 9))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(bRefreshPozorista)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bDeletePozoriste))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bUpdatePozoriste)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bCreatePozoriste)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(bRefreshPredstave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bDeletePredstavu))
                    .addComponent(jLabel24)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addComponent(bUpdatePredstavu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bCreatePredstavu))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tfCenaUlaznice, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel19)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel18))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(cbPozorisniKomadP, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cbTrupaP, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(1, 1, 1))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addGap(86, 86, 86)
                                .addComponent(tfProducent, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dpDatumOdrzavanja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfBrojRaspolozivihMesta))))))
                .addContainerGap(594, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(cbPozorisniKomadP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(cbTrupaP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19)
                            .addComponent(dpDatumOdrzavanja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel20)
                            .addComponent(tfBrojRaspolozivihMesta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfProducent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfCenaUlaznice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bUpdatePredstavu)
                            .addComponent(bCreatePredstavu))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(tfNazivPozorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel24)
                            .addComponent(bUpdatePozoriste)
                            .addComponent(bCreatePozoriste))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                            .addComponent(jScrollPane4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bDeletePozoriste)
                            .addComponent(bDeletePredstavu)
                            .addComponent(bRefreshPozorista)
                            .addComponent(bRefreshPredstave)))))
        );

        jTabbedPane1.addTab("Pozoriste i predstave", jPanel2);

        jLabel22.setText("Naziv:");

        bCreateTrupa.setText("Create");
        bCreateTrupa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCreateTrupaActionPerformed(evt);
            }
        });

        lTrupe.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane6.setViewportView(lTrupe);

        bRefreshTrupa.setText("Refresh");
        bRefreshTrupa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefreshTrupaActionPerformed(evt);
            }
        });

        bDeleteTrupa.setText("Delete");
        bDeleteTrupa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeleteTrupaActionPerformed(evt);
            }
        });

        jLabel23.setText("Trupe");

        lIgraci.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane7.setViewportView(lIgraci);

        bDeleteIgraci.setText("Delete");
        bDeleteIgraci.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeleteIgraciActionPerformed(evt);
            }
        });

        jLabel26.setText("Igraci:");

        jLabel25.setText("Ime:");

        jLabel27.setText("Prezime:");

        jLabel28.setText("Datum rodjenja");

        jLabel29.setText("Pozicija:");

        bCreateIgraca.setText("Create");
        bCreateIgraca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCreateIgracaActionPerformed(evt);
            }
        });

        bUpdateIgraca.setText("Update");
        bUpdateIgraca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUpdateIgracaActionPerformed(evt);
            }
        });

        bRefreshIgraci.setText("Refresh");
        bRefreshIgraci.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefreshIgraciActionPerformed(evt);
            }
        });

        lIgraciTrupa.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane10.setViewportView(lIgraciTrupa);

        bAddIgracToTrupa.setText("<");
        bAddIgracToTrupa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddIgracToTrupaActionPerformed(evt);
            }
        });

        bDeleteIgracFromTrupa.setText("Delete");
        bDeleteIgracFromTrupa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeleteIgracFromTrupaActionPerformed(evt);
            }
        });

        bRefreshLIgracTrupa.setText("Refresh");
        bRefreshLIgracTrupa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefreshLIgracTrupaActionPerformed(evt);
            }
        });

        jLabel8.setText("Igraci:");

        bUpdate.setText("Update");
        bUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(bRefreshTrupa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bDeleteTrupa))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bCreateTrupa))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfNazivTrupe, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(bRefreshLIgracTrupa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bDeleteIgracFromTrupa)))
                        .addGap(84, 84, 84)
                        .addComponent(bAddIgracToTrupa))
                    .addComponent(jLabel8))
                .addGap(89, 89, 89)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(bRefreshIgraci)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bDeleteIgraci)))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel25)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfImeIgraca, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel27)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                                    .addComponent(tfPrezimeIgraca, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel28)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(dpDatumRodjenja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel29)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfPozicijaIgraca, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(bUpdateIgraca)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bCreateIgraca))))
                    .addComponent(jLabel26))
                .addGap(259, 259, 259))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(tfNazivTrupe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bCreateTrupa)
                        .addComponent(bUpdate))
                    .addComponent(jLabel23)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel26)
                        .addComponent(jLabel8)))
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10)
                    .addComponent(jScrollPane7)
                    .addComponent(jScrollPane6)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(tfImeIgraca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(tfPrezimeIgraca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(dpDatumRodjenja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(tfPozicijaIgraca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bCreateIgraca)
                            .addComponent(bUpdateIgraca))
                        .addGap(0, 297, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bRefreshTrupa)
                    .addComponent(bDeleteTrupa)
                    .addComponent(bDeleteIgraci)
                    .addComponent(bRefreshIgraci)
                    .addComponent(bDeleteIgracFromTrupa)
                    .addComponent(bRefreshLIgracTrupa))
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addComponent(bAddIgracToTrupa)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Trupe i igraci", jPanel4);

        jLabel31.setText("Pozorisni komadi");

        lPozorisniKomadiPK.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lPozorisniKomadiPK);

        jLabel32.setText("Naziv:");

        jLabel33.setText("Autor");

        bCreatePozorisniKomad.setText("Create");
        bCreatePozorisniKomad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCreatePozorisniKomadActionPerformed(evt);
            }
        });

        bUpdatePozorisniKomad.setText("Update");
        bUpdatePozorisniKomad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUpdatePozorisniKomadActionPerformed(evt);
            }
        });

        bDeletePozorisniKomad.setText("Delete");
        bDeletePozorisniKomad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeletePozorisniKomadActionPerformed(evt);
            }
        });

        bRefreshPozorisniKomadi.setText("Refresh");
        bRefreshPozorisniKomadi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefreshPozorisniKomadiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel32)
                                    .addComponent(jLabel33))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfNazivPozorisnogKomada)
                                    .addComponent(tfAutorPozorisnogKomada, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(bUpdatePozorisniKomad)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bCreatePozorisniKomad))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(bRefreshPozorisniKomadi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bDeletePozorisniKomad)))
                .addContainerGap(1010, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(tfNazivPozorisnogKomada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfAutorPozorisnogKomada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bCreatePozorisniKomad)
                            .addComponent(bUpdatePozorisniKomad))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bDeletePozorisniKomad)
                    .addComponent(bRefreshPozorisniKomadi))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Pozorisni komadi", jPanel5);

        bAQuery.setText("a");
        bAQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAQueryActionPerformed(evt);
            }
        });

        bBQuery.setText("b");
        bBQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBQueryActionPerformed(evt);
            }
        });

        bDQuery.setText("d");
        bDQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDQueryActionPerformed(evt);
            }
        });

        taQuery.setColumns(20);
        taQuery.setRows(5);
        jScrollPane11.setViewportView(taQuery);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bAQuery)
                    .addComponent(bBQuery)
                    .addComponent(bDQuery))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 1310, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(bAQuery)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bBQuery)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bDQuery)
                        .addGap(0, 456, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Upiti", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Prva");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bDeletePredstavuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeletePredstavuActionPerformed
        if (!lPozorisnePredstave.isSelectionEmpty()) {
            int result = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete pozorisnu predstavu?", "Brisanje", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                ppDAO.deletePozorisnaPredstava(pozorisnePredstaveListModel.getPozorisnaPredstavaByIndex(lPozorisnePredstave.getSelectedIndex()));
                refreshPozorisnePredstaveNP();
                refreshPozorisnePredstaveR();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pozorisna predstava nije izabrana.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bDeletePredstavuActionPerformed

    private void bRefreshPozoristaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefreshPozoristaActionPerformed
        refreshNarodnaPozorista();
    }//GEN-LAST:event_bRefreshPozoristaActionPerformed

    private void bCreatePozoristeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCreatePozoristeActionPerformed
        if (!tfNazivPozorista.getText().isEmpty() && tfNazivPozorista.getText().length() < 25) {
            String naziv = tfNazivPozorista.getText();
            npDAO.addNarodnoPozoriste(new NarodnoPozoriste(naziv));
            refreshNarodnaPozorista();
        } else {
            JOptionPane.showMessageDialog(null, "Polja nisu validna (prazna).", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bCreatePozoristeActionPerformed

    private void bRefreshTrupaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefreshTrupaActionPerformed
        refreshTrupa();
    }//GEN-LAST:event_bRefreshTrupaActionPerformed

    private void bRefreshRezervacijeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefreshRezervacijeActionPerformed
        refreshRezervacijeR();
        clearRezervacije();
    }//GEN-LAST:event_bRefreshRezervacijeActionPerformed

    private void bDeleteRezervacijeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeleteRezervacijeActionPerformed
        if (!lRezervacija.isSelectionEmpty()) {
            int result = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete rezervaciju?", "Brisanje", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                selectedPPR.setBrojRaspolozivihMesta(selectedPPR.getBrojRaspolozivihMesta() + selectedRezervacijaR.getBrojMesta());
                ppDAO.updatePozorisnaPredstava(selectedPPR);
                rDAO.deleteRezervacija(rezervacijeListModel.getRezervacijaByIndex(lRezervacija.getSelectedIndex()));
                lblBrMesta.setText(String.valueOf(selectedPPR.getBrojRaspolozivihMesta()));
                refreshRezervacije();
                refreshRezervacijeR();
                selectedRezervacijaR = null;
                clearRezervacije();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Rezervacija nije izabrana.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bDeleteRezervacijeActionPerformed

    private void bCreateRezervacijaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCreateRezervacijaActionPerformed
        if (!lPozorisnePredstaveR.isSelectionEmpty()) {
            if (!lPretplatniciR.isSelectionEmpty()) {
                Rezervacija r = new Rezervacija();
                if (dpDatumRezervisanja.getDate() != null
                        && dpDatumPreuzimanja.getDate() != null
                        && !sedistaR.isEmpty()) {
                    if (dpDatumRezervisanja.getDate().before(selectedPPR.getDatumOdrzavanja())) {
                        if (pretplatnikR.getKreditnaKartica().getDatumVazenja().after(dpDatumPreuzimanja.getDate())) {
                            if (!checkSedistaDuplicates()) {
                                if (selectedPPR.getBrojRaspolozivihMesta() >= sedistaR.size()) {
                                    selectedPPR.setBrojRaspolozivihMesta(selectedPPR.getBrojRaspolozivihMesta() - sedistaR.size());
                                    r.setBrojMesta(sedistaR.size());
                                    r.setDatumPreuzimanja(dpDatumPreuzimanja.getDate());
                                    r.setDatumRezervisanja(dpDatumRezervisanja.getDate());
                                    r.setSedista(sedistaR);
                                    r.setUkupanIznos(selectedPPR.getCenaUlaznice() * sedistaR.size());
                                    r.setIdPozorisnePredstave(selectedPPR.getId());
                                    r.setIdPretplatnika(pretplatnikR.getId());
                                    rDAO.addRezervacija(r);
                                    ppDAO.updatePozorisnaPredstava(selectedPPR);
                                    refreshRezervacijeR();
                                    refreshPozorisnePredstaveR();
                                    lblBrMesta.setText(String.valueOf(selectedPPR.getBrojRaspolozivihMesta()));
                                    lblCena.setText(String.valueOf(r.getUkupanIznos()));
                                } else {
                                    JOptionPane.showMessageDialog(null, "Nije moguce napraviti rezervaciju zato sto nema dovoljno mesta.", "Greska", JOptionPane.WARNING_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Nije moguce napraviti rezervaciju zato sto ima dva ili vise istih sedista", "Greska", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Nije moguce napraviti rezervaciju zato sto kreditna kartica istice.", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Datum rezervacije mora biti pre odrzavanje predstave.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Polja nisu validna (prazna).", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Pretplatnik nije izabran", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Predstava nije izabrana", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bCreateRezervacijaActionPerformed

    private void bAddSedisteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddSedisteActionPerformed
        if (selectedPPR != null) {
            sedistaR.add(new Sediste(UUID.randomUUID().toString()));
            lblBrMesta.setText(String.valueOf(selectedPPR.getBrojRaspolozivihMesta()));
            refreshPSedista();
        } else {
            refreshPSedista();
        }
    }//GEN-LAST:event_bAddSedisteActionPerformed

    private void bUpdateRezervacijaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUpdateRezervacijaActionPerformed
        if (!lRezervacija.isSelectionEmpty()) {
            if (dpDatumRezervisanja.getDate() != null
                    && dpDatumPreuzimanja.getDate() != null
                    && !sedistaR.isEmpty()) {
                if (dpDatumRezervisanja.getDate().before(selectedPPR.getDatumOdrzavanja())) {
                    if (pretplatnikR.getKreditnaKartica().getDatumVazenja().after(dpDatumPreuzimanja.getDate())) {
                        if (!checkSedistaDuplicates()) {
                            int i = sedistaR.size() - selectedRezervacijaR.getBrojMesta();
                            if (selectedPPR.getBrojRaspolozivihMesta() >= i) {
                                if (i < 0) {
                                    selectedPPR.setBrojRaspolozivihMesta(selectedPPR.getBrojRaspolozivihMesta() + Math.abs(i));
                                } else {
                                    selectedPPR.setBrojRaspolozivihMesta(selectedPPR.getBrojRaspolozivihMesta() - Math.abs(i));
                                }
                                selectedRezervacijaR.setBrojMesta(sedistaR.size());
                                selectedRezervacijaR.setDatumPreuzimanja(dpDatumPreuzimanja.getDate());
                                selectedRezervacijaR.setDatumRezervisanja(dpDatumRezervisanja.getDate());
                                selectedRezervacijaR.setSedista(sedistaR);
                                selectedRezervacijaR.setUkupanIznos(selectedPPR.getCenaUlaznice() * sedistaR.size());
                                selectedRezervacijaR.setIdPozorisnePredstave(selectedPPR.getId());
                                rDAO.updateRezervacija(selectedRezervacijaR);
                                ppDAO.updatePozorisnaPredstava(selectedPPR);
                                lblBrMesta.setText(String.valueOf(selectedPPR.getBrojRaspolozivihMesta()));
                                lblCena.setText(String.valueOf(selectedRezervacijaR.getUkupanIznos()));
                                refreshRezervacijeR();
                                refreshPozorisnePredstaveR();
                            } else {
                                JOptionPane.showMessageDialog(null, "Nije moguce napraviti rezervaciju zato sto nema dovoljno mesta.", "Greska", JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Nije moguce napraviti rezervaciju zato sto ima dva ili vise istih sedista", "Greska", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Nije moguce napraviti rezervaciju zato sto kreditna kartica istice.", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Datum rezervisanja mora biti pre odrzavanja predstave.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Polja nisu validna (prazna).", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_bUpdateRezervacijaActionPerformed

    private void bDeletePozoristeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeletePozoristeActionPerformed
        if (!lNarodnaPozorista.isSelectionEmpty()) {
            List<PozorisnaPredstava> pptmp = ppDAO.readFromPozorisnaPredstavaByNarodnoPozoristeId(narodnoPozoristeListModel.getNarodnoPozoristeByIndex(lNarodnaPozorista.getSelectedIndex()).getId());
            if (pptmp == null && pptmp.isEmpty()) {
                int result = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete narodno pozoriste?", "Brisanje", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    npDAO.deleteNarodnoPozoriste(narodnoPozoristeListModel.getNarodnoPozoristeByIndex(lNarodnaPozorista.getSelectedIndex()));
                    refreshNarodnaPozorista();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nije moguce obrisati zato sto narodno pozoriste ima predstave koje izvodi.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Narodno pozoriste nije selektovano.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bDeletePozoristeActionPerformed

    private void bCreatePredstavuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCreatePredstavuActionPerformed
        if (!lNarodnaPozorista.isSelectionEmpty()) {
            if (cbPozorisniKomadP.getSelectedItem() != null
                    && cbTrupaP.getSelectedItem() != null
                    && !tfBrojRaspolozivihMesta.getText().isEmpty()
                    && !tfProducent.getText().isEmpty()
                    && tfProducent.getText().length() < 50
                    && !tfCenaUlaznice.getText().isEmpty()) {
                boolean b = true;
                PozorisniKomad pk = pozorisniKomadListModel.getPozorisniKomadByIndex(cbPozorisniKomadP.getSelectedIndex());
                for (PozorisnaPredstava pp : pozorisnePredstaveListModel.getPozorisnePredstave()) {
                    if (pp.getDatumOdrzavanja().equals(dpDatumOdrzavanja.getDate()) && pp.getIdPozorisniKomad() == pk.getId()) {
                        b = false;
                        break;
                    }
                }
                if (b) {
                    ppNP.setBrojRaspolozivihMesta(Integer.valueOf(tfBrojRaspolozivihMesta.getText()));
                    ppNP.setCenaUlaznice(Double.parseDouble(tfCenaUlaznice.getText()));
                    ppNP.setDatumOdrzavanja(dpDatumOdrzavanja.getDate());
                    ppNP.setIdNarodnoPozoriste(npNP.getId());
                    ppNP.setIdPozorisniKomad(pk.getId());
                    Trupa trupa = trupaListModel.getTrupaByIndex(cbTrupaP.getSelectedIndex());
                    ppNP.setIdTrupe(trupa.getId());
                    ppNP.setProducent(tfProducent.getText());
                    ppDAO.addPozorisnaPredstava(ppNP);
                    refreshPozorisnePredstaveNP();
                    refreshPozorisnePredstaveR();
                } else {
                    JOptionPane.showMessageDialog(null, "Postoji pozorisna predstava za izabrani datum.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Polja nisu validna (prazna)", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Narodno pozorisne nije izabrano", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bCreatePredstavuActionPerformed

    private void bRefreshPredstaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefreshPredstaveActionPerformed
        refreshPozorisnePredstaveNP();
    }//GEN-LAST:event_bRefreshPredstaveActionPerformed

    private void bUpdatePredstavuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUpdatePredstavuActionPerformed
        if (!lPozorisnePredstave.isSelectionEmpty()) {
            ppNP = pozorisnePredstaveListModel.getPozorisnaPredstavaByIndex(lPozorisnePredstave.getSelectedIndex());
            if (cbPozorisniKomadP.getSelectedItem() != null
                    && cbTrupaP.getSelectedItem() != null
                    && !tfBrojRaspolozivihMesta.getText().isEmpty()
                    && !tfProducent.getText().isEmpty()
                    && tfProducent.getText().length() < 50
                    && !tfCenaUlaznice.getText().isEmpty()) {
                boolean b = true;
                for (PozorisnaPredstava pp : pozorisnePredstaveListModel.getPozorisnePredstave()) {
                    if (pp.getDatumOdrzavanja().equals(dpDatumOdrzavanja.getDate())) {
                        b = false;
                        break;
                    }
                }
                if (b) {
                    ppNP.setBrojRaspolozivihMesta(Integer.valueOf(tfBrojRaspolozivihMesta.getText()));
                    ppNP.setCenaUlaznice(Double.parseDouble(tfCenaUlaznice.getText()));
                    ppNP.setDatumOdrzavanja(dpDatumOdrzavanja.getDate());
                    ppNP.setIdNarodnoPozoriste(narodnoPozoristeListModel.getNarodnoPozoristeByIndex(lNarodnaPozorista.getSelectedIndex()).getId());
                    PozorisniKomad pk = (PozorisniKomad) cbPozorisniKomadP.getSelectedItem();
                    ppNP.setIdPozorisniKomad(pk.getId());
                    Trupa trupa = (Trupa) cbTrupaP.getSelectedItem();
                    ppNP.setIdTrupe(trupa.getId());
                    ppNP.setProducent(tfProducent.getText());
                    ppDAO.updatePozorisnaPredstava(ppNP);
                    refreshPozorisnePredstaveNP();
                    refreshPozorisnePredstave();
                } else {
                    JOptionPane.showMessageDialog(null, "Postoji pozorisna predstava za izabrani datum.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Polja nisu validna (prazna)", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_bUpdatePredstavuActionPerformed

    private void bCreateTrupaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCreateTrupaActionPerformed
        if (!tfNazivTrupe.getText().isEmpty() && tfNazivTrupe.getText().length() < 25) {
            tDAO.addTrupa(new Trupa(tfNazivTrupe.getText()));
            refreshTrupa();
        } else {
            JOptionPane.showMessageDialog(null, "Polja nisu validna (prazna).", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bCreateTrupaActionPerformed

    private void bDeleteTrupaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeleteTrupaActionPerformed
        if (!lTrupe.isSelectionEmpty()) {
            List<PozorisnaPredstava> pptmp = ppDAO.readFromPozorisnaPredstavaByTrupaId(selectedTrupaTI.getId());
            if (pptmp == null && pptmp.isEmpty()) {
                int result = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete trupu?", "Brisanje", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    tDAO.deleteTrupa(selectedTrupaTI);
                    refreshTrupa();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ne mozete da obrisete ovu trupu zato sto ima pozorisna predstava koja sadrzi ovu trupu.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Trupa nije izabrana.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bDeleteTrupaActionPerformed

    private void bRefreshIgraciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefreshIgraciActionPerformed
        refreshIgraci();
    }//GEN-LAST:event_bRefreshIgraciActionPerformed

    private void bDeleteIgraciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeleteIgraciActionPerformed
        if (!lIgraci.isSelectionEmpty()) {
            int result = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete igraca?", "Brisanje", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                tIiDAO.deleteIgracByIgracId(igracListModel.getIgracByIndex(lIgraci.getSelectedIndex()).getId());
                iDAO.deleteIgrac(igracListModel.getIgracByIndex(lIgraci.getSelectedIndex()));
                refreshIgraci();
                refreshTrupaIIgraci();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Igrac nije izabran.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bDeleteIgraciActionPerformed

    private void bUpdateIgracaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUpdateIgracaActionPerformed
        if (!lIgraci.isSelectionEmpty()) {
            if (!tfImeIgraca.getText().isEmpty() && !tfPrezimeIgraca.getText().isEmpty() && !tfPozicijaIgraca.getText().isEmpty()
                    && tfImeIgraca.getText().length() < 25 && tfPrezimeIgraca.getText().length() < 25) {
                selectedIgracTI.setIme(tfImeIgraca.getText());
                selectedIgracTI.setPrezime(tfPrezimeIgraca.getText());
                selectedIgracTI.setDatumRodjenja(dpDatumRodjenja.getDate());
                selectedIgracTI.setKvalifikacija(tfPozicijaIgraca.getText());
                iDAO.updateIgrac(selectedIgracTI);
                refreshIgraci();
            } else {
                JOptionPane.showMessageDialog(null, "Polja nisu validna (prazna).", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Igrac nije izabran.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bUpdateIgracaActionPerformed

    private void bCreateIgracaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCreateIgracaActionPerformed
        Igrac igrac = new Igrac();
        if (!tfImeIgraca.getText().isEmpty() && !tfPrezimeIgraca.getText().isEmpty() && !tfPozicijaIgraca.getText().isEmpty()
                && tfImeIgraca.getText().length() < 25 && tfPrezimeIgraca.getText().length() < 25) {
            igrac.setIme(tfImeIgraca.getText());
            igrac.setPrezime(tfPrezimeIgraca.getText());
            igrac.setDatumRodjenja(dpDatumRodjenja.getDate());
            igrac.setKvalifikacija(tfPozicijaIgraca.getText());
            iDAO.addIgrac(igrac);
            refreshIgraci();
        } else {
            JOptionPane.showMessageDialog(null, "Polja nisu validna (prazna).", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bCreateIgracaActionPerformed

    private void bRefreshPozorisniKomadiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefreshPozorisniKomadiActionPerformed
        refreshPozorisniKomadi();
    }//GEN-LAST:event_bRefreshPozorisniKomadiActionPerformed

    private void bDeletePozorisniKomadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeletePozorisniKomadActionPerformed
        if (!lPozorisniKomadiPK.isSelectionEmpty()) {
            List<PozorisnaPredstava> pptmp = ppDAO.readFromPozorisnaPredstavaByPozorisniKomadId(pozorisniKomadListModel.getPozorisniKomadByIndex(lPozorisniKomadiPK.getSelectedIndex()).getId());
            if (pptmp == null && pptmp.isEmpty()) {
                int result = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete pozorisni komad?", "Brisanje", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    pkDAO.deletePozorisniKomad(pozorisniKomadListModel.getPozorisniKomadByIndex(lPozorisniKomadiPK.getSelectedIndex()));
                    refreshPozorisniKomadi();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ne mozete da obrisete zato sto ima pozorisnih predstava koje sadrze ovaj pozorisni komad.", "Warning!", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pozorisni komad nije izabran", "Warning!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bDeletePozorisniKomadActionPerformed

    private void bUpdatePozorisniKomadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUpdatePozorisniKomadActionPerformed
        if (!lPozorisniKomadiPK.isSelectionEmpty()) {
            if (!tfNazivPozorisnogKomada.getText().isEmpty() && !tfAutorPozorisnogKomada.getText().isEmpty() && tfNazivPozorisnogKomada.getText().length() < 25 && tfAutorPozorisnogKomada.getText().length() < 50) {
                pkPK.setNaziv(tfNazivPozorisnogKomada.getText());
                pkPK.setAutor(tfAutorPozorisnogKomada.getText());
                pkDAO.updatePozorisniKomad(pkPK);
                refreshPozorisniKomadi();
            } else {
                JOptionPane.showMessageDialog(null, "Polja nisu validna (prazna)", "Warning!", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pozorisni komad nije izabran", "Warning!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bUpdatePozorisniKomadActionPerformed

    private void bCreatePozorisniKomadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCreatePozorisniKomadActionPerformed
        if (!tfNazivPozorisnogKomada.getText().isEmpty() && !tfAutorPozorisnogKomada.getText().isEmpty() && tfNazivPozorisnogKomada.getText().length() < 25 && tfAutorPozorisnogKomada.getText().length() < 50) {
            PozorisniKomad pk = new PozorisniKomad();
            pk.setNaziv(tfNazivPozorisnogKomada.getText());
            pk.setAutor(tfAutorPozorisnogKomada.getText());
            pkDAO.addPozorisniKomad(pk);
            refreshPozorisniKomadi();
        } else {
            JOptionPane.showMessageDialog(null, "Polja nisu validna (prazna)", "Warning!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bCreatePozorisniKomadActionPerformed

    private void bRefreshRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefreshRActionPerformed
        refreshNarodnaPozorista();
        refreshPozorisniKomadi();
        refreshPozorisnePredstaveR();
        clearPredstaveLbl();
        clearRezervacije();
    }//GEN-LAST:event_bRefreshRActionPerformed

    private void bRefreshPretplatniciRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefreshPretplatniciRActionPerformed
        refreshPretplatnici();
    }//GEN-LAST:event_bRefreshPretplatniciRActionPerformed

    private void bDeletePretplatnikRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeletePretplatnikRActionPerformed
        if (pretplatnikR != null) {
            int result = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete pretplatnika?", "Brisanje", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                for (Rezervacija r : rezervacije) {
                    if (r.getIdPretplatnika() == pretplatnikR.getId()) {
                        for (PozorisnaPredstava pp : pozorisnePredstave) {
                            if (pp.getId() == r.getIdPozorisnePredstave()) {
                                pp.setBrojRaspolozivihMesta(pp.getBrojRaspolozivihMesta() + r.getBrojMesta());
                                ppDAO.updatePozorisnaPredstava(pp); //TODO update broj raspolozivih mesta kada obrisemo korisnina zajedno sa rezervacijama
                            }
                        }
                    }
                }
                pDAO.deletePretplatnik(pretplatnikR);
                refreshPretplatnici();
                refreshRezervacijeR();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pretplatnik nije izabran", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bDeletePretplatnikRActionPerformed

    private void bUpdatePretplatnikRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUpdatePretplatnikRActionPerformed
        if (pretplatniciR != null) {
            if (!tfAdresa.getText().isEmpty() && !tfBrojTelefona.getText().isEmpty() && !tfBrojkartice.getText().isEmpty()
                    && dpDatumVazenjaKartice.getDate() != null && tfAdresa.getText().length() < 25 && tfBrojTelefona.getText().length() < 20 && tfBrojkartice.getText().length() < 16) {
                pretplatnikR.setAdresa(tfAdresa.getText());
                pretplatnikR.setTelefon(tfBrojTelefona.getText());
                pretplatnikR.getKreditnaKartica().setBroj(tfBrojkartice.getText());
                pretplatnikR.getKreditnaKartica().setDatumVazenja((Date) dpDatumVazenjaKartice.getDate());
                pretplatnikR.getKreditnaKartica().setTip((String) cbTipKartice.getSelectedItem());
                pDAO.updatePretplatnik(pretplatnikR);
                refreshPretplatnici();
            } else {
                JOptionPane.showMessageDialog(null, "Polja nisu validna (prazna)", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pretplatnik nije izabran", "Warning", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_bUpdatePretplatnikRActionPerformed

    private void bCreatePretplatnikRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCreatePretplatnikRActionPerformed
        if (!tfAdresa.getText().isEmpty() && !tfBrojTelefona.getText().isEmpty() && !tfBrojkartice.getText().isEmpty()
                && dpDatumVazenjaKartice.getDate() != null && tfAdresa.getText().length() < 25 && tfBrojTelefona.getText().length() < 20 && tfBrojkartice.getText().length() < 16) {
            Pretplatnik p = new Pretplatnik();
            p.setKreditnaKartica(new KreditnaKartica());
            p.setAdresa(tfAdresa.getText());
            p.setTelefon(tfBrojTelefona.getText());
            p.getKreditnaKartica().setBroj(tfBrojkartice.getText());
            p.getKreditnaKartica().setDatumVazenja(dpDatumVazenjaKartice.getDate());
            p.getKreditnaKartica().setTip((String) cbTipKartice.getSelectedItem());
            pDAO.addPretplatnik(p);
            refreshPretplatnici();
            clearPretpltnikTfs();
        } else {
            JOptionPane.showMessageDialog(null, "Polja nisu validna (prazna)", "Upozorenje", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bCreatePretplatnikRActionPerformed

    private void bClearSedistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bClearSedistaActionPerformed
        if (!sedistaR.isEmpty()) {
            sedistaR.clear();
            refreshPSedista();
        }
    }//GEN-LAST:event_bClearSedistaActionPerformed

    private void bRefreshLIgracTrupaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefreshLIgracTrupaActionPerformed
        refreshTrupaIIgraci();
    }//GEN-LAST:event_bRefreshLIgracTrupaActionPerformed

    private void bDeleteIgracFromTrupaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeleteIgracFromTrupaActionPerformed
        if (!lIgraciTrupa.isSelectionEmpty() && !trupaIIgraci.isEmpty()) {
            Igrac iTmp = igraciTrupaListModel.getIgracByIndex(lIgraciTrupa.getSelectedIndex());
            tIiDAO.deleteIgraceByTrupaId(selectedTrupaTI.getId());
            for (Igrac i : trupaIIgraci) {
                if (i.getId() != iTmp.getId()) {
                    tIiDAO.addTrupaIIgrac(new TrupaIIgrac(selectedTrupaTI.getId(), i.getId()));
                }
            }
            refreshTrupaIIgraci();
        } else {
            JOptionPane.showMessageDialog(null, "Igrac nije odabran ili ne postoji.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bDeleteIgracFromTrupaActionPerformed

    private void bAddIgracToTrupaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddIgracToTrupaActionPerformed
        if (selectedTrupaTI != null && selectedIgracTI != null) {
            boolean isExist = false;
            for (Igrac ig : trupaIIgraci) {
                if (ig.getId() == selectedIgracTI.getId()) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                trupaIIgraci.add(selectedIgracTI);
                tIiDAO.deleteIgraceByTrupaId(selectedTrupaTI.getId());
                for (Igrac i : trupaIIgraci) {
                    tIiDAO.addTrupaIIgrac(new TrupaIIgrac(selectedTrupaTI.getId(), i.getId()));
                }
                refreshTrupaIIgraci();
            } else {
                JOptionPane.showMessageDialog(null, "Igrac vec postoji", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_bAddIgracToTrupaActionPerformed

    private void bUpdatePozoristeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUpdatePozoristeActionPerformed
        if (!lNarodnaPozorista.isSelectionEmpty()) {
            if (!tfNazivPozorista.getText().isEmpty() && tfNazivPozorista.getText().length() < 25) {
                npNP.setNaziv(tfNazivPozorista.getText());
                npDAO.updateNarodnoPozoriste(npNP);
                refreshNarodnaPozorista();
            } else {
                JOptionPane.showMessageDialog(null, "Polja nisu validna (prazna).", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Narodno pozoriste nije izabrano.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bUpdatePozoristeActionPerformed

    private void bUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUpdateActionPerformed
        if (!lTrupe.isSelectionEmpty()) {
            if (!tfNazivTrupe.getText().isEmpty() && tfNazivTrupe.getText().length() < 25) {
                selectedTrupaTI.setName(tfNazivTrupe.getText());
                tDAO.updateTrupa(selectedTrupaTI);
                refreshTrupa();
            } else {
                JOptionPane.showMessageDialog(null, "Polja nisu validna (prazna).", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_bUpdateActionPerformed

    private void bAQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAQueryActionPerformed
        try {
            taQuery.setText("");
            taQuery.setText(qDAO.aQuery());
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bAQueryActionPerformed

    private void bBQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBQueryActionPerformed
        try {
            taQuery.setText("");
            taQuery.setText(qDAO.bQuery());
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bBQueryActionPerformed

    private void bDQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDQueryActionPerformed
        try {
            taQuery.setText("");
            taQuery.setText(qDAO.dQuery());
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bDQueryActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("*Windows*".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    private void refreshNarodnaPozorista() {
        narodnaPozorista.clear();
        narodnaPozorista = npDAO.readFromNarodnoPozoriste();
        narodnoPozoristeListModel.updateModel(narodnaPozorista);
        lNarodnaPozorista.invalidate();
        lNarodnaPozorista.validate();
        lNarodnaPozorista.repaint();
        cbPozorista.invalidate();
        cbPozorista.validate();
        cbPozorista.repaint();
    }

    private void refreshPozorisniKomadi() {
        pozorisniKomadi.clear();
        pozorisniKomadi = pkDAO.readFromPozorisniKomad();
        pozorisniKomadListModel.updateModel(pozorisniKomadi);
        cbPozorisniKomad.invalidate();
        cbPozorisniKomad.validate();
        cbPozorisniKomad.repaint();
        lPozorisniKomadiPK.invalidate();
        lPozorisniKomadiPK.validate();
        lPozorisniKomadiPK.repaint();
    }

    private void refreshRezervacije() {
        rezervacije.clear();
        rezervacije = rDAO.readFromRezervacija();
        for (Rezervacija r : rezervacije) {
            r.setSedista(rsDAO.readFromSedistaByRezervacijaId(r.getId()));
        }
    }

    private void refreshRezervacijeR() {
        if (selectedPPR != null) {
            refreshRezervacije();
            List<Rezervacija> rezervacijeTmp = new ArrayList<>();
            for (Rezervacija r : rezervacije) {
                if (r.getIdPozorisnePredstave() == selectedPPR.getId()) {
                    rezervacijeTmp.add(r);
                }
            }
            selectedRezervacijeList = rezervacijeTmp;
            rezervacijeListModel.updateModel(rezervacijeTmp);
            lRezervacija.invalidate();
            lRezervacija.validate();
            lRezervacija.repaint();
        } else {
            rezervacijeListModel.updateModel(Collections.EMPTY_LIST);
            lRezervacija.invalidate();
            lRezervacija.validate();
            lRezervacija.repaint();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAQuery;
    private javax.swing.JButton bAddIgracToTrupa;
    private javax.swing.JButton bAddSediste;
    private javax.swing.JButton bBQuery;
    private javax.swing.JButton bClearSedista;
    private javax.swing.JButton bCreateIgraca;
    private javax.swing.JButton bCreatePozorisniKomad;
    private javax.swing.JButton bCreatePozoriste;
    private javax.swing.JButton bCreatePredstavu;
    private javax.swing.JButton bCreatePretplatnikR;
    private javax.swing.JButton bCreateRezervacija;
    private javax.swing.JButton bCreateTrupa;
    private javax.swing.JButton bDQuery;
    private javax.swing.JButton bDeleteIgracFromTrupa;
    private javax.swing.JButton bDeleteIgraci;
    private javax.swing.JButton bDeletePozorisniKomad;
    private javax.swing.JButton bDeletePozoriste;
    private javax.swing.JButton bDeletePredstavu;
    private javax.swing.JButton bDeletePretplatnikR;
    private javax.swing.JButton bDeleteRezervacije;
    private javax.swing.JButton bDeleteTrupa;
    private javax.swing.JButton bRefreshIgraci;
    private javax.swing.JButton bRefreshLIgracTrupa;
    private javax.swing.JButton bRefreshPozorisniKomadi;
    private javax.swing.JButton bRefreshPozorista;
    private javax.swing.JButton bRefreshPredstave;
    private javax.swing.JButton bRefreshPretplatniciR;
    private javax.swing.JButton bRefreshR;
    private javax.swing.JButton bRefreshRezervacije;
    private javax.swing.JButton bRefreshTrupa;
    private javax.swing.JButton bUpdate;
    private javax.swing.JButton bUpdateIgraca;
    private javax.swing.JButton bUpdatePozorisniKomad;
    private javax.swing.JButton bUpdatePozoriste;
    private javax.swing.JButton bUpdatePredstavu;
    private javax.swing.JButton bUpdatePretplatnikR;
    private javax.swing.JButton bUpdateRezervacija;
    private javax.swing.JComboBox<String> cbPozorisniKomad;
    private javax.swing.JComboBox<String> cbPozorisniKomadP;
    private javax.swing.JComboBox<String> cbPozorista;
    private javax.swing.JComboBox<String> cbTipKartice;
    private javax.swing.JComboBox<String> cbTrupaP;
    private org.jdesktop.swingx.JXDatePicker dpDatumOdrzavanja;
    private org.jdesktop.swingx.JXDatePicker dpDatumPreuzimanja;
    private org.jdesktop.swingx.JXDatePicker dpDatumRezervisanja;
    private org.jdesktop.swingx.JXDatePicker dpDatumRodjenja;
    private org.jdesktop.swingx.JXDatePicker dpDatumVazenjaKartice;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lCenaUlaznice;
    private javax.swing.JList<String> lIgraci;
    private javax.swing.JList<String> lIgraciTrupa;
    private javax.swing.JList<String> lNarodnaPozorista;
    private javax.swing.JList<String> lPozorisnePredstave;
    private javax.swing.JList<String> lPozorisnePredstaveR;
    private javax.swing.JList<String> lPozorisniKomadiPK;
    private javax.swing.JList<String> lPretplatniciR;
    private javax.swing.JList<String> lRezervacija;
    private javax.swing.JList<String> lTrupe;
    private javax.swing.JLabel lblBrMesta;
    private javax.swing.JLabel lblCena;
    private javax.swing.JLabel lblDatumOdrzavanja;
    private javax.swing.JPanel pSedistaP;
    private javax.swing.JTextArea taQuery;
    private javax.swing.JTextField tfAdresa;
    private javax.swing.JTextField tfAutorPozorisnogKomada;
    private javax.swing.JTextField tfBrojRaspolozivihMesta;
    private javax.swing.JTextField tfBrojTelefona;
    private javax.swing.JTextField tfBrojkartice;
    private javax.swing.JTextField tfCenaUlaznice;
    private javax.swing.JTextField tfImeIgraca;
    private javax.swing.JTextField tfNazivPozorisnogKomada;
    private javax.swing.JTextField tfNazivPozorista;
    private javax.swing.JTextField tfNazivTrupe;
    private javax.swing.JTextField tfPozicijaIgraca;
    private javax.swing.JTextField tfPrezimeIgraca;
    private javax.swing.JTextField tfProducent;
    // End of variables declaration//GEN-END:variables

    private void refreshPozorisnePredstave() {
        pozorisnePredstave.clear();
        pozorisnePredstave = ppDAO.readFromPozorisnaPredstava();
    }

    private void refreshPozorisnePredstaveR() {
        refreshPozorisnePredstave();
        if (selectedNPR != null && selectedPKR != null) {
            List<PozorisnaPredstava> pozorisnePredstaveTmp = new ArrayList<>();
            for (PozorisnaPredstava pp : pozorisnePredstave) {
                if (pp.getIdNarodnoPozoriste() == selectedNPR.getId() && pp.getIdPozorisniKomad() == selectedPKR.getId()) {
                    pozorisnePredstaveTmp.add(pp);
                }
            }
            pozorisnePredstaveListModelR.updateModel(pozorisnePredstaveTmp);
            lPozorisnePredstaveR.invalidate();
            lPozorisnePredstaveR.validate();
            lPozorisnePredstaveR.repaint();
        }
    }

    private void refreshTrupa() {
        trupe.clear();
        trupe = tDAO.readFromTrupa();
        trupaListModel.updateModel(trupe);
        lTrupe.invalidate();
        lTrupe.validate();
        lTrupe.repaint();
        cbTrupaP.invalidate();
        cbTrupaP.validate();
        cbTrupaP.repaint();
    }

    private void refreshIgraci() {
        igraci.clear();
        igraci = iDAO.readFromIgrac();
        igracListModel.updateModel(igraci);
        lIgraci.invalidate();
        lIgraci.validate();
        lIgraci.repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("deleteSediste")) {
            Sediste s = (Sediste) evt.getNewValue();
            for (Sediste st : sedistaR) {
                if (st.getUuid().equals(s.getUuid())) {
                    sedistaR.remove(st);
                    break;
                }
            }
            refreshPSedista();
        } else if (evt.getPropertyName().equals("updateSediste")) {
            Sediste s = (Sediste) evt.getNewValue();
            System.out.println(s);
            System.out.println("update sediste");
            System.out.println(s.toString());
            for (int i = 0; i < sedistaR.size(); i++) {
                if (sedistaR.get(i).getUuid().equals(s.getUuid())) {
                    sedistaR.set(i, s);
                    break;
                }
            }
        }
    }

    public List<Sediste> refreshDostupnaSedista() {
        if (!selectedRezervacijeList.isEmpty()) {
            List<Sediste> dostupnaSedista = sDAO.readFromSediste();
            for (Rezervacija r : selectedRezervacijeList) {
                for (Sediste s : rsDAO.readFromSedistaByRezervacijaId(r.getId())) {
                    for (Sediste ds : new ArrayList<>(dostupnaSedista)) {
                        if (ds.getId() == s.getId()) {
                            dostupnaSedista.remove(ds);
                        }
                    }
                }
            }
            return dostupnaSedista;
        }
        return null;
    }

    private void refreshPSedista() {
        List<Sediste> dostupnaSedista = refreshDostupnaSedista();
        pSedistaP.removeAll();
        if (!sedistaR.isEmpty()) {
            dostupnaSedista.addAll(sedistaR);

            for (Sediste s : sedistaR) {
                s.setUuid(UUID.randomUUID().toString());
                pSedista pSedP = new pSedista(s, dostupnaSedista);
                pSedP.addPropertyChangeListener(this);
                pSedistaP.add(pSedP);
            }
        }
        pSedistaP.invalidate();
        pSedistaP.validate();
        pSedistaP.repaint();
        jScrollPane3.invalidate();
        jScrollPane3.validate();
        jScrollPane3.repaint();
    }

    private void refreshPretplatnici() {
        pretplatniciR.clear();
        pretplatniciR = pDAO.readFromPretplatnik();
        pretplatniciListModel.updateModel(pretplatniciR);
        lPretplatniciR.invalidate();
        lPretplatniciR.validate();
        lPretplatniciR.repaint();
    }

    private void refreshAllModels() {
        refreshIgraci();
        refreshNarodnaPozorista();
        refreshPozorisnePredstave();
        refreshPozorisniKomadi();
        refreshTrupa();
        refreshPozorisnePredstave();
        refreshPretplatnici();
    }

    private void refreshPozorisnePredstaveNP() {
        refreshPozorisnePredstave();
        if (npNP != null) {
            List<PozorisnaPredstava> ppTmp = new ArrayList<>();
            for (PozorisnaPredstava pp : pozorisnePredstave) {
                if (pp.getIdNarodnoPozoriste() == npNP.getId()) {
                    ppTmp.add(pp);
                }
            }
            pozorisnePredstaveListModel.updateModel(ppTmp);
            lPozorisnePredstave.invalidate();
            lPozorisnePredstave.validate();
            lPozorisnePredstave.repaint();
        }
    }

    private void refreshTrupaIIgraci() {
        if (selectedTrupaTI != null) {
            trupaIIgraci.clear();
            trupaIIgraci = tIiDAO.readFromIgracByTrupaId(selectedTrupaTI.getId());
            igraciTrupaListModel.updateModel(trupaIIgraci);
            lIgraciTrupa.invalidate();
            lIgraciTrupa.validate();
            lIgraciTrupa.repaint();
        }
    }

    public boolean checkSedistaDuplicates() {
        for (int i = 0; i < sedistaR.size(); i++) {
            for (int j = i + 1; j < sedistaR.size(); j++) {
                if (sedistaR.get(i).getId() == sedistaR.get(j).getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void clearPretpltnikTfs() {
        tfAdresa.setText("");
        tfBrojTelefona.setText("");
        tfBrojkartice.setText("");
    }

    public void clearPredstaveLbl() {
        selectedPPR = null;
        lPozorisnePredstaveR.clearSelection();
        lblDatumOdrzavanja.setText("n/a");
        lblBrMesta.setText("n/a");
        lCenaUlaznice.setText("n/a");
    }

    public void clearRezervacije() {
        lRezervacija.clearSelection();
        selectedRezervacijaR = null;
        lblCena.setText("n/a");
        sedistaR.clear();
        refreshPSedista();
    }
}
