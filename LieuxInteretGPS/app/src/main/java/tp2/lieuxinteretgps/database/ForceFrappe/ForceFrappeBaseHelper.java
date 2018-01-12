package tp2.lieuxinteretgps.database.ForceFrappe;

import android.database.sqlite.SQLiteDatabase;

import tp2.lieuxinteretgps.database.FicheRenseignement.FicheRenseignementDbSchema;
import tp2.lieuxinteretgps.database.ForceFrappe.ForceFrappeDbSchema.ForceFrappeTable;

/**
 * Classe permettant de faire la création de la table des force de frappe.
 */

public class ForceFrappeBaseHelper {
    /**
     * Exécute la création de la table de force de frappe.
     */
    public static void create(SQLiteDatabase p_db) {
        p_db.execSQL("create table " + ForceFrappeTable.NAME + "( " +
                        ForceFrappeTable.Colonne.LATITUDE_LIEU_INTERET + ", " +
                        ForceFrappeTable.Colonne.LONGITUDE_LIEU_INTERET + ", " +
                        ForceFrappeTable.Colonne.NB_AUTOPOMPE + ", " +
                        ForceFrappeTable.Colonne.NB_CITERNES + ", " +
                        ForceFrappeTable.Colonne.NB_ECHELLES + ", " +
                        ForceFrappeTable.Colonne.NB_UNITES_INTERV_MAT_DANGEREUSES + ", " +
                        ForceFrappeTable.Colonne.NB_UNITES_SECOURS + ", " +
                        ForceFrappeTable.Colonne.NB_VEHICULES_OFFICIERS + ", " +

                        "PRIMARY KEY(" +
                            ForceFrappeTable.Colonne.LATITUDE_LIEU_INTERET + ", " +
                            ForceFrappeTable.Colonne.LONGITUDE_LIEU_INTERET  +
                        "), " +

                        "FOREIGN KEY (" +
                            ForceFrappeTable.Colonne.LATITUDE_LIEU_INTERET + ", " +
                            ForceFrappeTable.Colonne.LONGITUDE_LIEU_INTERET  +
                        " )" +
                        "REFERENCES " +
                            FicheRenseignementDbSchema.FicheRenseignementTable.NAME + " (" +
                            FicheRenseignementDbSchema.FicheRenseignementTable.Colonne.LATITUDE + ", " +
                            FicheRenseignementDbSchema.FicheRenseignementTable.Colonne.LONGITUDE +
                        ") " +
                        "ON DELETE CASCADE " +
                    " )"
        );
    }
}
