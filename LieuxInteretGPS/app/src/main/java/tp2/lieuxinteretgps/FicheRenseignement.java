package tp2.lieuxinteretgps;

import android.content.Context;

import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.List;

/**
 * Classe représentant une fiche de renseignement.
 *
 * @author Dracode
 */

public class FicheRenseignement implements Serializable {
    private final double m_latitude;  //Lattitude du lieu de la fiche de renseignement.
    private final double m_longitude; //Longitude du lieu de la fiche de renseignement.
    private final String m_adresse;   //l'adresse du lieu de la fiche de renseignement.
    private String m_nomLieu;   //Le nom du lieu de la fiche de renseignement.
    private boolean m_mobiliteReduite;      //Booléen si le lieu recueille des personnes à mobilité réduite.
    private boolean m_matiereDangeureuses;  //Booléen si le lieu recueille des matières dangereuses.
    private int m_nbEtages;     //Le nombre d'étages du building.

    /**
     * Constructeur de l'objet FicheRenseignement.
     *
     * @param p_latitude            Lattitude du lieu de la fiche de renseignement.
     * @param p_longitude           Longitude du lieu de la fiche de renseignement.
     * @param p_adresse             l'adresse du lieu de la fiche de renseignement
     * @param p_nomLieu             Le nom du lieu de la fiche de renseignement.
     * @param p_mobiliteReduite     Booléen si le lieu recueille des personnes à mobilité réduite
     * @param p_matiereDangeureuses Booléen si le lieu recueille des matières dangereuses.
     * @param p_nbEtages            Le nombre d'étages du building.
     */
    public FicheRenseignement(double p_latitude, double p_longitude, String p_adresse, String p_nomLieu,
                              boolean p_mobiliteReduite, boolean p_matiereDangeureuses, int p_nbEtages) {
        this.m_adresse = p_adresse;
        this.m_nomLieu = p_nomLieu;
        this.m_mobiliteReduite = p_mobiliteReduite;
        this.m_matiereDangeureuses = p_matiereDangeureuses;
        this.m_nbEtages = p_nbEtages;
        this.m_latitude = p_latitude;
        this.m_longitude = p_longitude;
    }

    /**
     * Getter de l'adresse.
     *
     * @return L'adresse du lieu de la fiche de renseignement.
     */
    public String getAdresse() {
        return m_adresse;
    }

    /**
     * Getter du nom du lieu.
     *
     * @return Le nom du lieu de la fiche de renseignement.
     */
    public String getNomLieu() {
        return m_nomLieu;
    }

    /**
     * Setter du nom du lieu.
     *
     * @param p_nomLieu Le nom du lieu de la fiche de renseignement.
     */
    void setNomLieu(String p_nomLieu) {
        m_nomLieu = p_nomLieu;
    }

    /**
     * Getter de si le lieu contient des personnes à mobilité réduite.
     *
     * @return True si le lieu a des personnes à mobilité réduite sinon, false.
     */
    public boolean hasPersonneMobiliteReduite() {
        return m_mobiliteReduite;
    }

    /**
     * Setter si le lieu contient des personnes à mobilité réduite.
     *
     * @param p_value True si le lieu a des personnes à mobilité réduite, sinon false.
     */
    void setPersonneMobiliteReduite(boolean p_value) {
        m_mobiliteReduite = p_value;
    }

    /**
     * Getter si le lieu contient des matières dangereuse.
     *
     * @return True si le lieu contient des matières dangereuse, sinon false.
     */
    public boolean hasMatieresDangereuses() {
        return m_matiereDangeureuses;
    }

    /**
     * Setter si le lieu contient des matières dangereuse.
     *
     * @param p_value True si le lieu contient des matières dangereuse, sinon false.
     */
    void setMatieresDangereuses(boolean p_value) {
        m_matiereDangeureuses = p_value;
    }

    /**
     * Setter du nombre d'étages du lieu de la fiche de renseignement.
     *
     * @param p_nbEtages Le nombre d'étages du lieu.
     */
    void setNombreEtages(int p_nbEtages) {
        m_nbEtages = p_nbEtages;
    }

    /**
     * Getter du nombre du lieu de la fiche de renseignement.
     *
     * @return Le nombre d'étages du lieu.
     */
    public int getNbEtages() {
        return m_nbEtages;
    }

    /**
     * Getter du la latitude du lieu de la fiche de renseignement.
     *
     * @return La latitude du lieu.
     */
    public double getLatitude() {
        return m_latitude;
    }

    /**
     * Getter du la longitude du lieu de la fiche de renseignement.
     *
     * @return La longitude du lieu.
     */
    public double getLongitude() {
        return m_longitude;
    }

    /**
     * Méthode qui ajoute dans la BD un marqueur associé à la fiche.
     *
     * @param p_context Le contexte courant de l'appel.
     * @param p_marqeur Le marqueur à ajouter dans la BD.
     */
    void ajouterMarqueur(Context p_context, MarkerOptions p_marqeur) {
        new SingletonBD().get(p_context).addMarqueur(p_marqeur, this);
    }

    /**
     * Méthode pour obtenir le liste de marqueurs associés à la fiche de renseignement
     * avec un appel à la BD.
     *
     * @param p_context Le context courant de l'appel.
     * @return La liste des marqueurs associés à la fiche de renseignement.
     */
    List<MarkerOptions> getListMarqueurs(Context p_context) {
        return new SingletonBD().get(p_context).getMarqueursDunLieu(this);
    }
}
