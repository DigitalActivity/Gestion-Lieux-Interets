package tp2.lieuxinteretgps.database.Requetes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import tp2.lieuxinteretgps.FicheRenseignement;
import tp2.lieuxinteretgps.database.FicheRenseignement.FicheRenseignementCursorWrapper;
import tp2.lieuxinteretgps.database.FicheRenseignement.FicheRenseignementDbSchema.FicheRenseignementTable;

/**
 * Classe permettant de faire des requêtes SQL sur la table de fiches de renseignement.
 */
public class RequetesFicheRenseignement {
    /**
     * Retourne un objet qui contient un HashMap dans lequel on place la valeur de chaque colonne
     * d'un enregistrement de fiche de renseignement.
     *
     * @param p_ficheRenseignement fiche de renseignement contenant les valeurs à stocker
     * @return un objet de type ContentValues qui contient toutes les valeurs d'une fiche de renseignement
     */
    private static ContentValues getContentValues(@NonNull FicheRenseignement p_ficheRenseignement) {
        ContentValues values = new ContentValues();

        values.put(FicheRenseignementTable.Colonne.LATITUDE, Double.toString(p_ficheRenseignement.getLatitude()));
        values.put(FicheRenseignementTable.Colonne.LONGITUDE, Double.toString(p_ficheRenseignement.getLongitude()));
        values.put(FicheRenseignementTable.Colonne.ADRESSE, p_ficheRenseignement.getAdresse());
        values.put(FicheRenseignementTable.Colonne.NOM_LIEU, p_ficheRenseignement.getNomLieu());
        values.put(FicheRenseignementTable.Colonne.PERSONNE_MOBILITE_REDUITE, p_ficheRenseignement.hasPersonneMobiliteReduite());
        values.put(FicheRenseignementTable.Colonne.MATIERES_DANGEREUSES, p_ficheRenseignement.hasMatieresDangereuses());
        values.put(FicheRenseignementTable.Colonne.NB_ETAGES, Integer.toString(p_ficheRenseignement.getNbEtages()));

        return values;
    }

    /**
     * Exécute une requête SQL.
     *
     * @param p_bd base de données sur laquelle on veut exécuter la requête SQL
     * @return curseur de fiche de renseignement qui pourra parcourir chaque élément du résultat de la requête
     */
    private static FicheRenseignementCursorWrapper queryFicheRenseignements(@NonNull SQLiteDatabase p_bd) {
        @SuppressLint("Recycle") Cursor curseur = p_bd.query(   //La connection se fait fermer lors des appels.
                FicheRenseignementTable.NAME,
                null,   // colonnes (null permet de sélectionner toutes les colonnes)
                null,
                null,
                null,   // groupBy
                null,   // having
                null);    // orderBy

        return new FicheRenseignementCursorWrapper(curseur);
    }

    /**
     * Ajoute une fiche de renseignment à la base de données.
     *
     * @param p_bd                 base de données dans laquelle on veut ajouter une fiche de renseignement
     * @param p_ficheRenseignement fiche de renseignement qu'on veut ajouter à la base de données
     */
    public static void addFicheRenseignement(@NonNull SQLiteDatabase p_bd, @NonNull FicheRenseignement p_ficheRenseignement) {
        // objet contenant un HashMap rempli des valeurs qu'on insérera dans la base de donneés
        ContentValues values = getContentValues(p_ficheRenseignement);

        p_bd.insert(FicheRenseignementTable.NAME, null, values);
    }

    /**
     * Modifie une fiche de renseignement dans la base de données.
     *
     * @param p_bd                 base de données dans laquelle on veut effectuer la modification
     * @param p_ficheRenseignement fiche de reseignement ayant les nouvelles valeurs
     */
    public static void updateFicheRenseignement(@NonNull SQLiteDatabase p_bd, @NonNull FicheRenseignement p_ficheRenseignement) {
        // clé primaire de la fiche de renseignement :
        String latitude = Double.toString(p_ficheRenseignement.getLatitude());
        String longitude = Double.toString(p_ficheRenseignement.getLongitude());

        // objet contenant un HashMap rempli des valeurs qu'on insérera dans la base de donneés
        ContentValues values = getContentValues(p_ficheRenseignement);

        p_bd.update(FicheRenseignementTable.NAME, values,
                FicheRenseignementTable.Colonne.LATITUDE + " = ? AND " +
                        FicheRenseignementTable.Colonne.LONGITUDE + " = ?",
                new String[]{latitude, longitude});
    }

    /**
     * Supprime une fiche de renseigment de la base de données.
     *
     * @param p_bd                 base de données dans laquelle on veut supprimer une fiche de renseigment
     * @param p_ficheRenseignement fiche de renseignement qu'on veut supprimer
     */
    public static void deleteFicheRenseignement(@NonNull SQLiteDatabase p_bd, @NonNull FicheRenseignement p_ficheRenseignement) {
        p_bd.delete(FicheRenseignementTable.NAME,
                FicheRenseignementTable.Colonne.LATITUDE + " = ? AND " +
                        FicheRenseignementTable.Colonne.LONGITUDE + " = ?",
                new String[]{Double.toString(p_ficheRenseignement.getLatitude()),
                        Double.toString(p_ficheRenseignement.getLongitude())});
    }

    /**
     * Retourne toutes les fiches de renseignements enregistrées dans la base de données
     *
     * @param p_bd base de données dans laquelle les fiches de renseignement sont enregistrées
     * @return une liste des fiches de renseignement
     */
    public static List<FicheRenseignement> getListeFicheRenseignement(@NonNull SQLiteDatabase p_bd) {
        // liste qu'on remplira de toutes le fiches de renseignement :
        List<tp2.lieuxinteretgps.FicheRenseignement> listeFicheRenseignement = new ArrayList<>();
        try (FicheRenseignementCursorWrapper curseur = queryFicheRenseignements(p_bd)) {
            curseur.moveToFirst();

            while (!curseur.isAfterLast()) {
                listeFicheRenseignement.add(curseur.getFicheRenseignement());
                curseur.moveToNext();
            }
        }

        return listeFicheRenseignement;
    }
}