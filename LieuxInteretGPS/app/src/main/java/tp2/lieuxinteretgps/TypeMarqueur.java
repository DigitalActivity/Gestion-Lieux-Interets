package tp2.lieuxinteretgps;

/**
 * Enum du type de marqueur
 *
 * @author Dracode
 */
public enum TypeMarqueur {
    BORNE("Borne hydrique"),    //Type borne.
    SORTIE("Sortie de secours"),    //Type Sortie.
    AUTOPOMPE("Autopompe"),         //Type Autopompe.
    CITERNE("Citerne"),             //Type Citerne.
    ECHELLE("Échelle"),             //Type Échelle.
    UNITE_INTERVENTION_MATIERE_DANGEUREUSE("Unité interv. matière dangereuse"), //Type Unité d'intervention de matière dangereuse.
    UNITE_SECOURS("Unité de secours"), //Type Unité de secours.
    VEHICULE_OFFICIER("Véhicule officier"); //Type Véhicule officier.

    private final String m_stringValue; //La valeur de l'enum en String.

    /**
     * Constructeur des valeur enum TypeMarqueur
     *
     * @param p_stringValue La valeur de l'enum en String
     */
    TypeMarqueur(final String p_stringValue) {
        m_stringValue = p_stringValue;
    }

    /**
     * Getter de la valeur de l'enum en String
     *
     * @return La valeur de l'enum en string
     */
    public String toString() {
        return m_stringValue;
    }
}