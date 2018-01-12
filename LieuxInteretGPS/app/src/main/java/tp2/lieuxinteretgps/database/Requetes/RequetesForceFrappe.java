package tp2.lieuxinteretgps.database.Requetes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import tp2.lieuxinteretgps.FicheRenseignement;
import tp2.lieuxinteretgps.ForceFrappe;
import tp2.lieuxinteretgps.database.ForceFrappe.ForceFrappeCursorWrapper;
import tp2.lieuxinteretgps.database.ForceFrappe.ForceFrappeDbSchema.ForceFrappeTable;

/**
 * Classe permettant de faire des requêtes SQL sur la table de forces de frappe.
 */
public class RequetesForceFrappe {
    /**
     * Retourne un objet qui contient un HashMap dans lequel on place la valeur de chaque colonne
     * d'un enregistrement de fiche de renseignement.
     *
     * @param p_forceFrappe        force de frappe contenant les valeurs à stocker
     * @param p_ficheRenseignement fiche de renseignement associé au même lieu d'intérêt que la force de fappe
     * @return un objet de type ContentValues qui contient toutes les valeurs d'une force de frappe
     */
    private static ContentValues getContentValues(@NonNull ForceFrappe p_forceFrappe, @NonNull FicheRenseignement p_ficheRenseignement) {
        ContentValues values = new ContentValues();

        values.put(ForceFrappeTable.Colonne.LATITUDE_LIEU_INTERET, Double.toString(p_ficheRenseignement.getLatitude()));
        values.put(ForceFrappeTable.Colonne.LONGITUDE_LIEU_INTERET, Double.toString(p_ficheRenseignement.getLongitude()));
        values.put(ForceFrappeTable.Colonne.NB_AUTOPOMPE, Double.toString(p_forceFrappe.getNbAutopompes()));
        values.put(ForceFrappeTable.Colonne.NB_CITERNES, Double.toString(p_forceFrappe.getNbCiternes()));
        values.put(ForceFrappeTable.Colonne.NB_ECHELLES, Double.toString(p_forceFrappe.getNbEchelles()));
        values.put(ForceFrappeTable.Colonne.NB_UNITES_INTERV_MAT_DANGEREUSES, Double.toString(p_forceFrappe.getNbUnitesIntervMatDangereuses()));
        values.put(ForceFrappeTable.Colonne.NB_UNITES_SECOURS, Double.toString(p_forceFrappe.getNbUnitesSecours()));
        values.put(ForceFrappeTable.Colonne.NB_VEHICULES_OFFICIERS, Double.toString(p_forceFrappe.getNbVechiulesOfficier()));

        return values;
    }

    /**
     * Exécute une requête SQL.
     *
     * @param p_bd          base de données sur laquelle on veut exécuter la requête SQL
     * @param p_whereClause restrictions de la clause where
     * @param p_whereArgs   valeurs des restrictions de la clause where
     * @return curseur de force de frappe qui pourra parcourir chaque élément du résultat de la requête
     */
    private static ForceFrappeCursorWrapper queryForceFrappe(@NonNull SQLiteDatabase p_bd, String p_whereClause, String[] p_whereArgs) {
        @SuppressLint("Recycle") Cursor curseur = p_bd.query(  //La connection se fait fermer lors des appels.
                ForceFrappeTable.NAME,
                null,   // colonnes (null permet de sélectionner toutes les colonnes)
                p_whereClause,
                p_whereArgs,
                null,   // groupBy
                null,   // having
                null);   // orderBy

        return new ForceFrappeCursorWrapper(curseur);
    }

    /**
     * Ajoute une force de frappe à la base de donneés.
     *
     * @param p_bd                 base de données à laquelle on veut ajouter une force de frappe
     * @param p_forceFrappe        force de frappe qu'on veut ajouter à la base de donneés
     * @param p_ficheRenseignement fiche de renseignement en lien avec
     *                             le même lieu d'intérêt que la force de frappe
     */
    public static void addForceFrappe(@NonNull SQLiteDatabase p_bd, @NonNull ForceFrappe p_forceFrappe,
                                      @NonNull FicheRenseignement p_ficheRenseignement) {
        // objet contenant un HashMap rempli des valeurs qu'on insérera dans la base de donneés
        ContentValues values = getContentValues(p_forceFrappe, p_ficheRenseignement);

        p_bd.insert(ForceFrappeTable.NAME, null, values);
    }

    /**
     * Modifie les valeurs d'une force de frappe dans la base de données.
     *
     * @param p_bd                 base de données dans laquelle on veut modifier une force de frappe
     * @param p_forceFrappe        force de frappe ayant les nouvelles valeurs
     * @param p_ficheRenseignement fiche de renseignement en lien avec
     *                             le même lieu d'intérêt que la force de frappe
     */
    public static void updateForceFrappe(@NonNull SQLiteDatabase p_bd, @NonNull ForceFrappe p_forceFrappe,
                                         @NonNull FicheRenseignement p_ficheRenseignement) {
        // clé primaire de la force de frappe :
        String latitudeLieu = Double.toString(p_ficheRenseignement.getLatitude());
        String longitudeLieu = Double.toString(p_ficheRenseignement.getLongitude());

        // objet contenant un HashMap rempli des valeurs qu'on insérera dans la base de donneés
        ContentValues values = getContentValues(p_forceFrappe, p_ficheRenseignement);

        p_bd.update(ForceFrappeTable.NAME, values,
                ForceFrappeTable.Colonne.LATITUDE_LIEU_INTERET + " = ? AND " +
                        ForceFrappeTable.Colonne.LONGITUDE_LIEU_INTERET + " = ?",
                new String[]{latitudeLieu, longitudeLieu});
    }

    /**
     * Retourne une force de frappe stockée dans la base de données
     *
     * @param p_bd                 base de données dans laquelle est stockée la force de frappe à récupérer
     * @param p_ficheRenseignement fiche de renseignement en lien avec le même lieu d'intérêt
     *                             que la force de frappe. La fiche de renseignement contient la clé primaire
     *                             de l'enregistrement d'une force de frappe dans la base de données.
     * @return une force de frappe
     */
    public static ForceFrappe getForceFrappe(@NonNull SQLiteDatabase p_bd, @NonNull FicheRenseignement p_ficheRenseignement) {
        ForceFrappe forceFrappe; // force de frappe qu'on retournera

        try (ForceFrappeCursorWrapper curseur = queryForceFrappe(
                p_bd, ForceFrappeTable.Colonne.LATITUDE_LIEU_INTERET + " = ? AND " +
                        ForceFrappeTable.Colonne.LONGITUDE_LIEU_INTERET + " = ?",
                new String[]{Double.toString(p_ficheRenseignement.getLatitude()), Double.toString(p_ficheRenseignement.getLongitude())})) {
            // Le lieu d'intérêt en lien avec la fiche de renseigment reçue en paramètre n'a pas de force
            // de frappe.
            if (curseur.getCount() == 0) {
                return null;
            }

            curseur.moveToFirst();
            forceFrappe = curseur.getForceFrappe();
        }

        return forceFrappe;
    }
}