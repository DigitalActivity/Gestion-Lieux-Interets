package tp2.lieuxinteretgps.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import tp2.lieuxinteretgps.FicheRenseignement;
import tp2.lieuxinteretgps.ForceFrappe;
import tp2.lieuxinteretgps.database.FicheRenseignement.FicheRenseignementBaseHelper;
import tp2.lieuxinteretgps.database.ForceFrappe.ForceFrappeBaseHelper;
import tp2.lieuxinteretgps.database.Marqueur.MarqueurBaseHelper;
import tp2.lieuxinteretgps.database.Requetes.RequetesFicheRenseignement;
import tp2.lieuxinteretgps.database.Requetes.RequetesForceFrappe;
import tp2.lieuxinteretgps.database.Requetes.RequetesMarqueur;

/**
 * Classe permettant d'effectuer des requêtes SQL sur toutes les tables de la base de donneés
 */
public class BD extends SQLiteOpenHelper {
    private static final int VERSION = 1;   // version de la base de données
    private static final String DATABASE_NAME = "FicheRenseignement.db";    // nom de la base de données

    private final SQLiteDatabase m_database;    // instance de la base de données

    /**
     * Constructeur de la classe BD qui initialise la base de donneés
     * @param p_context context à partir duquel on appelle le constructeur
     */
    public BD(@NonNull Context p_context) {
        super(p_context, DATABASE_NAME, null, VERSION);
        m_database = getWritableDatabase();
    }

    /**
     * Création de la base de données. On créé les tables de
     *    - fiches de renseignement
     *    - marqueurs
     *    - force de frappe
     * @param p_db base de données sur laquelle on veut créer les tables
     */
    @Override
    public void onCreate(SQLiteDatabase p_db) {
        FicheRenseignementBaseHelper.create(p_db);
        MarqueurBaseHelper.create(p_db);
        ForceFrappeBaseHelper.create(p_db);
    }

    /**
     * Fonction exécutée lorsqu'on met la base de données à jour
     * @param db base de données en mise à jour
     * @param oldVersion ancienne version de la base de données
     * @param newVersion nouvelle version de la base de données
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // On ne gère pas les mises à jour de BD. On enlèverait bien cette fonction,
        // mais on est obligé de l'overrider dans une classe qui dérive de SQLiteOpenHelper.
    }

    /**
     * Lorsqu'on ouvre la base de donneés, on active les clés étrangères.
     * @param db base de donneés dans laquelle on veut activer les clés étrangères
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    /**
     * Ajoute une fiche de renseignement dans la base de données.
     * @param p_ficheRenseignement fiche de reseignement à ajouter dans la base de données
     */
    public void addFicheRenseignement(@NonNull FicheRenseignement p_ficheRenseignement) {
        RequetesFicheRenseignement.addFicheRenseignement(m_database, p_ficheRenseignement);
    }

    /**
     * Modifie une fiche de renseigment dans la base de données
     * @param p_ficheRenseignement fiche de renseignement ayant les nouvelles valeurs
     */
    public void updateFicheRenseignement(@NonNull FicheRenseignement p_ficheRenseignement) {
        RequetesFicheRenseignement.updateFicheRenseignement(m_database, p_ficheRenseignement);
    }

    /**
     * Supprime une fiche de renseignement de la base de données.
     * @param p_ficheRenseignement fiche de renseignement à supprimer
     */
    public void deleteFicheRenseignement(@NonNull FicheRenseignement p_ficheRenseignement) {
        RequetesFicheRenseignement.deleteFicheRenseignement(m_database, p_ficheRenseignement);
    }

    /**
     * Récupère toutes les fiches de renseignement.
     * @return liste de toutes les fiche de renseignement enregistrées
     */
    public List<FicheRenseignement> getListeFicheRenseignement() {
        return RequetesFicheRenseignement.getListeFicheRenseignement(m_database);
    }

    /**
     * Ajoute un marqueur dans la base de données
     * @param p_marqueur marqueur enregistré dans la base de données
     * @param p_ficheRenseignement fiche de renseignement associé au même lieu d'intérêt que le marqueur
     */
    public void addMarqueur(@NonNull MarkerOptions p_marqueur,
                                   @NonNull FicheRenseignement p_ficheRenseignement) {
        RequetesMarqueur.addMarqueur(m_database, p_marqueur, p_ficheRenseignement);
    }

    /**
     * Supprimer un marqueur de la base de données
     * @param p_marqueur marqueur à supprimer de la base de données
     */
    public void deleteMarqueur(@NonNull Marker p_marqueur) {
        RequetesMarqueur.deleteMarqueur(m_database, p_marqueur);
    }

    /**
     * Retourne les marqueurs d'un lieu d'intérêt
     * @param p_ficheRenseignement fiche de renseignement associé au même lieu d'intérêt que le marqueur
     * @return liste des marqueurs d'un lieu d'intérêt
     */
    public List<MarkerOptions> getMarqueursDunLieu(@NonNull FicheRenseignement p_ficheRenseignement) {
        return RequetesMarqueur.getMarqueursDunLieu(m_database, p_ficheRenseignement);
    }

    /**
     * Ajoute une force de frappe à la base de données
     * @param p_forceFrappe force de frappe à enregistrer dans la base de données
     * @param p_ficheRenseignement fiche de renseignement associé au même lieu d'intérêt que le marqueur
     */
    public void addForceFrappe(@NonNull ForceFrappe p_forceFrappe, @NonNull FicheRenseignement p_ficheRenseignement) {
        RequetesForceFrappe.addForceFrappe(m_database, p_forceFrappe, p_ficheRenseignement);
    }

    /**
     * Modifie une force de frappe dans la base de données
     * @param p_forceFrappe force de frappe contenant les nouvelles valeurs
     * @param p_ficheRenseignement fiche de renseignement associé au même lieu d'intérêt que le marqueur
     */
    public void updateForceFrappe(@NonNull ForceFrappe p_forceFrappe, @NonNull FicheRenseignement p_ficheRenseignement) {
        RequetesForceFrappe.updateForceFrappe(m_database, p_forceFrappe, p_ficheRenseignement);
    }

    /**
     * Retourne la force de frappe d'un lieu d'intérêt
     * @param p_ficheRenseignement fiche de renseignement associé au même lieu d'intérêt que le marqueur
     * @return force de frappe d'un lieu d'intérêt
     */
    public ForceFrappe getForceFrappe(@NonNull FicheRenseignement p_ficheRenseignement) {
        return RequetesForceFrappe.getForceFrappe(m_database, p_ficheRenseignement);
    }
}
