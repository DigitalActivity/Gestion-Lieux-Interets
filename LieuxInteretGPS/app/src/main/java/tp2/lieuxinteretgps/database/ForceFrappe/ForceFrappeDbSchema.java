package tp2.lieuxinteretgps.database.ForceFrappe;

/**
 * Classe permettant de faire référence au nom et aux noms de colonne de la table de force de frappe.
 */
public class ForceFrappeDbSchema {
    /**
     * Classe représentant la structure de la table de forces de frappe.
     */
    public static final class ForceFrappeTable {
        public static final String NAME = "ForceFrappe";

        /**
         * Classe contenant le noms des colonnes de la table de forces de frappe.
         * Permet d'utiliser les constantes lorsqu'on en a besoin pour éviter des erreurs de frappe.
         */
        public static final class Colonne {
            // clé primaire et étrangère
            public static final String LATITUDE_LIEU_INTERET = "latitude_lieu_interet";
            public static final String LONGITUDE_LIEU_INTERET = "longitude_lieu_interet";

            public static final String NB_AUTOPOMPE = "autopompe";
            public static final String NB_CITERNES = "nb_citernes";
            public static final String NB_ECHELLES = "nb_echelles";
            public static final String NB_UNITES_INTERV_MAT_DANGEREUSES = "nb_unites_interv_mat_dangereuse";
            public static final String NB_UNITES_SECOURS = "nb_unites_secours";
            public static final String NB_VEHICULES_OFFICIERS = "nb_vechicules_officier";
        }
    }
}
