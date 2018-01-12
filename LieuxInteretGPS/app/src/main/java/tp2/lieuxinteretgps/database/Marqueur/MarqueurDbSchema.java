package tp2.lieuxinteretgps.database.Marqueur;

/**
 * Classe permettant de faire référence au nom et aux noms de colonne de la table de marqueur.
 */
public class MarqueurDbSchema {
    /**
     * Classe représentant la structure de la table de marqueurs.
     */
    public static class MarqueurTable {
        public static final String NAME = "Marqueur";

        /**
         * Classe contenant le noms des colonnes de la table de marqueurs.
         * Permet d'utiliser les constantes lorsqu'on en a besoin pour éviter des erreurs de frappe.
         */
        public static final class Colonne {
            // clé primaire :
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";

            // clé étrangère :
                public static final String LATITUDE_LIEU_INTERET = "latitude_lieu_interet";
            public static final String LONGITUDE_LIEU_INTERET = "longitude_lieu_interet";

            public static final String TYPE_MARQUEUR = "type_marqueur";
        }
    }
}
