package tp2.lieuxinteretgps.database.FicheRenseignement;

import android.database.Cursor;
import android.database.CursorWrapper;

import tp2.lieuxinteretgps.FicheRenseignement;

/**
 * Classe qui représente un curseur pour un enregistrement de fiche de renseignement.
 * Un curseur permet de lire un enregistrement (ou une ligne) dans une table.
 */
public class FicheRenseignementCursorWrapper extends CursorWrapper {
    /**
     * Constructeur du curseur de fiche de renseignment.
     * @param cursor curseur de base
     */
    public FicheRenseignementCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /**
     * Convertit un enregistrement dans la table fiche renseignement en objet FicheRenseignement.
     * @return la fiche de renseignement à la ligne où se trouve le curseur dans la table
     */
    public FicheRenseignement getFicheRenseignement() {
        String adresse = getString(getColumnIndex(FicheRenseignementDbSchema.FicheRenseignementTable.Colonne.ADRESSE));
        double latitude = getDouble(getColumnIndex(FicheRenseignementDbSchema.FicheRenseignementTable.Colonne.LATITUDE));
        double longitude = getDouble(getColumnIndex(FicheRenseignementDbSchema.FicheRenseignementTable.Colonne.LONGITUDE));
        String nomLieu = getString(getColumnIndex(FicheRenseignementDbSchema.FicheRenseignementTable.Colonne.NOM_LIEU));
        boolean personneMobiliteReduite = getInt(getColumnIndex(FicheRenseignementDbSchema.FicheRenseignementTable.Colonne.PERSONNE_MOBILITE_REDUITE)) != 0;
        boolean matieresDangereuse = getInt(getColumnIndex(FicheRenseignementDbSchema.FicheRenseignementTable.Colonne.MATIERES_DANGEREUSES)) != 0;
        int nbEtages = getInt(getColumnIndex(FicheRenseignementDbSchema.FicheRenseignementTable.Colonne.NB_ETAGES));

        return new FicheRenseignement(latitude, longitude, adresse, nomLieu, personneMobiliteReduite, matieresDangereuse, nbEtages);
    }
}
