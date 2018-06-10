package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Filip
 */
public class QueryDAO {

    private Connection con = null;
    private String url = "jdbc:mysql://localhost:3306/predstave";
    private String username = "root";
    private String password = "";

    public QueryDAO() {
    }

    public Connection openConnection() {
        try {
            con = DriverManager.getConnection(url, username, password);
            return con;
        } catch (SQLException ex) {
            Logger.getLogger(QueryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public Connection closeConnection() {
        try {
            con.close();
            return con;
        } catch (SQLException ex) {
            Logger.getLogger(QueryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public String aQuery() throws SQLException {
        con = openConnection();
        StringBuilder sb = new StringBuilder();
        String query = "SELECT * \n"
                + "FROM pretplatnik p \n"
                + "INNER JOIN \n"
                + "	(SELECT * \n"
                + "	FROM kreditna_kartica kr \n"
                + "	ORDER BY datum_vazenja \n"
                + "	LIMIT 1) AS kr \n"
                + "	ON p.id_kreditne_kartice = kr.id";
        PreparedStatement st = con.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        sb.append("id_kartice");
        sb.append("\t");
        sb.append("broj_kartice");
        sb.append("\t");
        sb.append("tip");
        sb.append("\t");
        sb.append("datum_vazenja");
        sb.append("\n");
        while (rs.next()) {
            sb.append(String.valueOf(rs.getInt("id")));
            sb.append("\t");
            sb.append(String.valueOf(rs.getInt("broj_kartice")));
            sb.append("\t");
            sb.append(rs.getString("tip"));
            sb.append("\t");
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            sb.append(df.format(rs.getDate("datum_vazenja")));
            sb.append("\n");
        }
        con.close();
        return sb.toString();
    }

    public String bQuery() throws SQLException {
        con = openConnection();
        StringBuilder sb = new StringBuilder();
        String query = "SELECT r.id, r.ukupan_iznos, p.id as `id_pretplatnika`, kk.id as `id_kartice`, kk.datum_vazenja, kk.broj_kartice, kk.tip\n"
                + "	FROM pretplatnik p, kreditna_kartica kk, rezervacija r\n"
                + "	INNER JOIN (SElECT id, id_pretplatnika, MAX(r.ukupan_iznos) as ukupan_iznoss \n"
                + "		FROM rezervacija r ) as rez\n"
                + "             WHERE r.ukupan_iznos = rez.ukupan_iznoss AND r.id_pretplatnika = p.id AND p.id_kreditne_kartice = kk.id;";
        PreparedStatement st = con.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        sb.append("id_rezervacija");
        sb.append("\t");
        sb.append("ukupan_iznos");
        sb.append("\t");
        sb.append("id_pretplatnik");
        sb.append("\t");
        sb.append("id_kartice");
        sb.append("\t");
        sb.append("datum_vazenja");
        sb.append("\t");
        sb.append("broj_kartice");
        sb.append("\t");
        sb.append("tip");
        sb.append("\n");
        while (rs.next()) {
            sb.append(String.valueOf(rs.getInt("id")));
            sb.append("\t");
            sb.append(String.valueOf(rs.getDouble("ukupan_iznos")));
            sb.append("\t");
            sb.append(String.valueOf(rs.getInt("id_pretplatnika")));
            sb.append("\t");
            sb.append(String.valueOf(rs.getInt("id_kartice")));
            sb.append("\t");
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            sb.append(df.format(rs.getDate("datum_vazenja")));
            sb.append("\t");
            sb.append(String.valueOf(rs.getInt("broj_kartice")));
            sb.append("\t");
            sb.append(rs.getString("tip"));
            sb.append("\n");
        }
        con.close();
        return sb.toString();
    }

    public String dQuery() throws SQLException {
        con = openConnection();
        StringBuilder sb = new StringBuilder();
        String query = "SELECT *\n"
                + "FROM pozorisna_predstava pp\n"
                + "ORDER BY cena_ulaznice DESC";
        PreparedStatement st = con.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        sb.append("id_predstave");
        sb.append("\t");
        sb.append("id_pozorista");
        sb.append("\t");
        sb.append("id_pozorisni_komad");
        sb.append("\t");
        sb.append("id_trupa");
        sb.append("\t");
        sb.append("datum_odrzavanja");
        sb.append("\t");
        sb.append("broj_raspolozivih_mesta");
        sb.append("\t");
        sb.append("cena_ulaznice");
        sb.append("\t");
        sb.append("producent");
        sb.append("\n");
        while (rs.next()) {
            sb.append(String.valueOf(rs.getInt("id")));
            sb.append("\t");
            sb.append(String.valueOf(rs.getInt("id_narodnog_pozorista")));
            sb.append("\t");
            sb.append(String.valueOf(rs.getInt("id_pozorisnog_komada")));
            sb.append("\t");
            sb.append(String.valueOf(rs.getInt("id_trupe")));
            sb.append("\t");
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            sb.append(df.format(rs.getDate("datum_odrzavanja")));
            sb.append("\t");
            sb.append(String.valueOf(rs.getInt("broj_raspolozivih_mesta")));
            sb.append("\t");
            sb.append(String.valueOf(rs.getDouble("cena_ulaznice")));
            sb.append("\t");
            sb.append(rs.getString("producent"));
            sb.append("\n");
        }
        con.close();
        return sb.toString();
    }
}
