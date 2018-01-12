package tp2.lieuxinteretgps.database.FicheRenseignement;

import android.database.sqlite.SQLiteDatabase;

import tp2.lieuxinteretgps.database.FicheRenseignement.FicheRenseignementDbSchema.FicheRenseignementTable;

/**
 * Classe permettant de faire la création de la table de fiches de renseignement.
 */

public class FicheRenseignementBaseHelper {
    /**
     * Exécute la création de la table de fiche de renseignement.
     */
    public static void create(SQLiteDatabase p_db) {
        p_db.execSQL("create table " + FicheRenseignementTable.NAME + "( " +
                        FicheRenseignementTable.Colonne.LATITUDE + ", " +
                        FicheRenseignementTable.Colonne.LONGITUDE + ", " +
                        FicheRenseignementTable.Colonne.ADRESSE + ", " +
                        FicheRenseignementTable.Colonne.NOM_LIEU + ", " +
                        FicheRenseignementTable.Colonne.PERSONNE_MOBILITE_REDUITE + ", " +
                        FicheRenseignementTable.Colonne.MATIERES_DANGEREUSES + ", " +
                        FicheRenseignementTable.Colonne.NB_ETAGES +

                        ", PRIMARY KEY( " +
                            FicheRenseignementTable.Colonne.LATITUDE + ", " +
                            FicheRenseignementTable.Colonne.LONGITUDE  + " )" +
                    ")"
        );
    }
}
