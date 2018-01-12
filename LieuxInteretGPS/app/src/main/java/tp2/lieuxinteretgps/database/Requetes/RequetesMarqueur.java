package tp2.lieuxinteretgps.database.Requetes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import tp2.lieuxinteretgps.database.Marqueur.MarqueurCursorWrapper;
import tp2.lieuxinteretgps.database.Marqueur.MarqueurDbSchema.MarqueurTable;
import tp2.lieuxinteretgps.FicheRenseignement;

/**
 * Classe permettant de faire des requêtes SQL sur la table de marqueurs.
 */
public class RequetesMarqueur {
    /**
     * Retourne un objet qui contient un HashMap dans lequel on place la valeur de chaque colonne
     * d'un enregistrement de marqueur.
     *
     * @param p_marqueur           marqueur contenant les valeurs à stocker
     * @param p_ficheRenseignement fiche de renseignement associé au même lieu d'intérêt que le marqueur
     * @return un objet de type ContentValues qui contient toutes les valeurs d'un marqueur
     */
    private static ContentValues getContentValues(@NonNull MarkerOptions p_marqueur, @NonNull FicheRenseignement p_ficheRenseignement) {
        ContentValues values = new ContentValues();

        values.put(MarqueurTable.Colonne.LATITUDE, String.valueOf(p_marqueur.getPosition().latitude));
        values.put(MarqueurTable.Colonne.LONGITUDE, String.valueOf(p_marqueur.getPosition().longitude));
        values.put(MarqueurTable.Colonne.LATITUDE_LIEU_INTERET, String.valueOf(p_ficheRenseignement.getLatitude()));
        values.put(MarqueurTable.Colonne.LONGITUDE_LIEU_INTERET, String.valueOf(p_ficheRenseignement.getLongitude()));
        values.put(MarqueurTable.Colonne.TYPE_MARQUEUR, p_marqueur.getTitle());

        return values;
    }

    /**
     * Exécute une requête SQL.
     *
     * @param p_bd          base de données sur laquelle on veut exécuter la requête SQL
     * @param p_whereClause restrictions de la clause where
     * @param p_whereArgs   valeurs des restrictions de la clause where
     * @return curseur de marqueur qui pourra parcourir chaque élément du résultat de la requête
     */
    private static MarqueurCursorWrapper queryMarqueur(@NonNull SQLiteDatabase p_bd, String p_whereClause, String[] p_whereArgs) {
        // Donne un avertissement indiquant qu'il faut fermer le curseur, mais on le ferme dans
        // la fonction qui appelle cette fonction.
        @SuppressLint("Recycle") Cursor curseur = p_bd.query(  //La connection se fait fermer lors des appels.
                MarqueurTable.NAME,
                null,   // colonnes (null permet de sélectionner toutes les colonnes)
                p_whereClause,
                p_whereArgs,
                null,   // groupBy
                null,   // having
                null);    // orderBy

        return new MarqueurCursorWrapper(curseur);
    }

    /**
     * Ajoute un marqueur dans la base de données
     *
     * @param p_bd                 base de données dans laquelle on veut ajouter le marqueur
     * @param p_marqueur           marqueur qu'on veut enregistrer dans la base de données
     * @param p_ficheRenseignement fiche de renseignement associé au même lieu d'intérêt que le marqueur
     */
    public static void addMarqueur(@NonNull SQLiteDatabase p_bd, @NonNull MarkerOptions p_marqueur, @NonNull FicheRenseignement p_ficheRenseignement) {
        ContentValues values = getContentValues(p_marqueur, p_ficheRenseignement);
        p_bd.insert(MarqueurTable.NAME, null, values);
    }

    /**
     * Supprimer un marqueur de la base de donneés
     *
     * @param p_bd       base de données contenant le marqueur à supprimer
     * @param p_marqueur marqueur à supprimer
     */
    public static void deleteMarqueur(@NonNull SQLiteDatabase p_bd, @NonNull Marker p_marqueur) {
        p_bd.delete(MarqueurTable.NAME,
                MarqueurTable.Colonne.LATITUDE + " = ? AND " +
                        MarqueurTable.Colonne.LONGITUDE + " = ?",
                new String[]{Double.toString(p_marqueur.getPosition().latitude),
                        Double.toString(p_marqueur.getPosition().longitude)});
    }

    /**
     * Retourne les marqueurs d'un lieu d'intérêt
     *
     * @param p_bd                 base de données contenant les marqueurs
     * @param p_ficheRenseignement fiche de renseignement associé au même lieu d'intérêt que le marqueur
     * @return liste des marqueurs du même lieu d'intérêt que la fiche de renseigment
     */
    public static List<MarkerOptions> getMarqueursDunLieu(@NonNull SQLiteDatabase p_bd, @NonNull FicheRenseignement p_ficheRenseignement) {
        // sera remplie des marqueurs du lieu d'intérêt associé à la fiche de renseignement reçu en paramètre
        List<MarkerOptions> listeMarqueur = new ArrayList<>();

        try (MarqueurCursorWrapper curseur = queryMarqueur(p_bd, MarqueurTable.Colonne.LATITUDE_LIEU_INTERET + " = ? AND " +
                        MarqueurTable.Colonne.LONGITUDE_LIEU_INTERET + " = ?",
                new String[]{String.valueOf(p_ficheRenseignement.getLatitude()),
                        String.valueOf(p_ficheRenseignement.getLongitude())})) {
            curseur.moveToFirst();
            while (!curseur.isAfterLast()) {
                listeMarqueur.add(curseur.getMarqueur());
                curseur.moveToNext();
            }
        }

        return listeMarqueur;
    }
}