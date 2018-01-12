package tp2.lieuxinteretgps.database.ForceFrappe;

import android.database.Cursor;
import android.database.CursorWrapper;

import tp2.lieuxinteretgps.ForceFrappe;
import tp2.lieuxinteretgps.database.ForceFrappe.ForceFrappeDbSchema.ForceFrappeTable;

/**
 * Classe qui représente un curseur pour un enregistrement de fiche de renseignement.
 * Un curseur permet de lire un enregistrement (ou une ligne) dans une table.
 */
public class ForceFrappeCursorWrapper extends CursorWrapper {
    /**
     * Constructeur du curseur de fiche de renseignment.
     * @param p_cursor curseur de base
     */
    public ForceFrappeCursorWrapper(Cursor p_cursor) {
        super(p_cursor);
    }

    /**
     * Convertit un enregistrement dans la table force frappe en objet ForceFrappe.
     * @return la fiche de renseignement à la ligne où se trouve le curseur dans la table
     */
    public ForceFrappe getForceFrappe() {
        int nbAutoPompes = getInt(getColumnIndex(ForceFrappeTable.Colonne.NB_AUTOPOMPE));
        int nbCiternes = getInt(getColumnIndex(ForceFrappeTable.Colonne.NB_CITERNES));
        int nbEchelles = getInt(getColumnIndex(ForceFrappeTable.Colonne.NB_ECHELLES));
        int nbUnitesIntervMatDangereuses = getInt(getColumnIndex(ForceFrappeTable.Colonne.NB_UNITES_INTERV_MAT_DANGEREUSES));
        int nbUnitesSecours = getInt(getColumnIndex(ForceFrappeTable.Colonne.NB_UNITES_SECOURS));
        int nbVehiculesOfficier = getInt(getColumnIndex(ForceFrappeTable.Colonne.NB_VEHICULES_OFFICIERS));

        return new ForceFrappe(nbAutoPompes, nbCiternes, nbEchelles, nbUnitesIntervMatDangereuses, nbUnitesSecours, nbVehiculesOfficier);
    }
}
