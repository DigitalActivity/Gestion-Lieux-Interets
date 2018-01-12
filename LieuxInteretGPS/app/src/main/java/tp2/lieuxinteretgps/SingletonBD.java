package tp2.lieuxinteretgps;

import android.content.Context;
import android.support.annotation.NonNull;

import tp2.lieuxinteretgps.database.BD;

/**
 * Classe permettant d'utiliser la base de données avec une instance unique.
 */
class SingletonBD {
    private static BD m_singletonBD;    // instance unique de la base de données

    /**
     * Créé la base de données si elle n'est pas créé avant de la retourner.
     * @param p_context context à partir duquel on a besoin de la base de données
     * @return instance unique de la base de données
     */
    BD get(@NonNull Context p_context) {
        if (m_singletonBD == null) {
            m_singletonBD = new BD(p_context);
        }

        return m_singletonBD;
    }
}
