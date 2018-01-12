package tp2.lieuxinteretgps.database.FicheRenseignement;

/**
 * Classe permettant de faire référence au nom et aux noms de colonne de la table de fiche de renseignement.
 */
public class FicheRenseignementDbSchema {
    /**
     * Classe représentant la structure de la table de fiches de renseignement.
     */
    public static final class FicheRenseignementTable {
        public static final String NAME = "FicheRenseignement";

        /**
         * Classe contenant le noms des colonnes de la table de fiches de renseignement.
         * Permet d'utiliser les constantes lorsqu'on en a besoin pour éviter des erreurs de frappe.
         */
        public static final class Colonne {
            // clé primaire de la fiche de renseignement :
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";

            public static final String ADRESSE = "adresse";
            public static final String NOM_LIEU = "nom_lieu";
            public static final String PERSONNE_MOBILITE_REDUITE = "personne_mobilite_reduite";
            public static final String MATIERES_DANGEREUSES = "matieres_dangereuses";
            public static final String NB_ETAGES = "nb_etages";
        }
    }
}