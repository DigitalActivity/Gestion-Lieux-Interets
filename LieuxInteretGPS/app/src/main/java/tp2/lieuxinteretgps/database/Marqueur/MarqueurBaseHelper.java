package tp2.lieuxinteretgps.database.Marqueur;

import android.database.sqlite.SQLiteDatabase;

import tp2.lieuxinteretgps.database.FicheRenseignement.FicheRenseignementDbSchema.FicheRenseignementTable;
import tp2.lieuxinteretgps.database.Marqueur.MarqueurDbSchema.MarqueurTable;

/**
 * Classe permettant de faire la création de la table de marqueurs.
 */

public class MarqueurBaseHelper {
    /**
     * Exécute la création de la table de fiche de marqueurs.
     */
    public static void create(SQLiteDatabase p_db) {
        p_db.execSQL("create table " + MarqueurTable.NAME + "( " +
                        MarqueurTable.Colonne.LATITUDE + ", " +
                        MarqueurTable.Colonne.LONGITUDE + ", " +
                        MarqueurTable.Colonne.TYPE_MARQUEUR + ", "+
                        MarqueurTable.Colonne.LATITUDE_LIEU_INTERET + ", " +
                        MarqueurTable.Colonne.LONGITUDE_LIEU_INTERET + ", " +

                        "FOREIGN KEY(" +
                            MarqueurTable.Colonne.LATITUDE_LIEU_INTERET + ", " +
                            MarqueurTable.Colonne.LONGITUDE_LIEU_INTERET
                        + ")" +
                        "REFERENCES " +
                            FicheRenseignementTable.NAME + " (" +
                            FicheRenseignementTable.Colonne.LATITUDE + ", " +
                            FicheRenseignementTable.Colonne.LONGITUDE +
                        ") " +
                        "ON DELETE CASCADE " +
                    ")"
        );
    }
}