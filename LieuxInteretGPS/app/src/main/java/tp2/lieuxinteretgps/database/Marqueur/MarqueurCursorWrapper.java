package tp2.lieuxinteretgps.database.Marqueur;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import tp2.lieuxinteretgps.R;
import tp2.lieuxinteretgps.TypeMarqueur;

/**
 * Classe qui représente un curseur pour un enregistrement de marqueur.
 * Un curseur permet de lire un enregistrement (ou une ligne) dans une table.
 */
public class MarqueurCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public MarqueurCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /**
     * Convertit un enregistrement dans la table marqueur en objet MarkerOptions.
     * @return la fiche de renseignement à la ligne où se trouve le curseur dans la table
     */
    public MarkerOptions getMarqueur() {
        double latitude = getDouble(getColumnIndex(MarqueurDbSchema.MarqueurTable.Colonne.LATITUDE));
        double longitude = getDouble(getColumnIndex(MarqueurDbSchema.MarqueurTable.Colonne.LONGITUDE));

        String typeMarqueur = getString(getColumnIndex(MarqueurDbSchema.MarqueurTable.Colonne.TYPE_MARQUEUR));

        MarkerOptions marqueur = new MarkerOptions();
        marqueur.position(new LatLng(latitude, longitude));
        marqueur.title(typeMarqueur);

        if(marqueur.getTitle().equals(TypeMarqueur.BORNE.toString())) {
            marqueur.icon(BitmapDescriptorFactory.fromResource(R.mipmap.marqueur_borne_hybride));
        }
        else if (marqueur.getTitle().equals(TypeMarqueur.AUTOPOMPE.toString())) {
            marqueur.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ffmarqueurautopompe));
        }
        else if (marqueur.getTitle().equals(TypeMarqueur.CITERNE.toString())) {
            marqueur.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ffmarqueurciterne));
        }
        else if (marqueur.getTitle().equals(TypeMarqueur.ECHELLE.toString())) {
            marqueur.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ffmarqueurechelle));
        }
        else if (marqueur.getTitle().equals(TypeMarqueur.UNITE_INTERVENTION_MATIERE_DANGEUREUSE.toString())) {
            marqueur.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ffinterventionhazardous));
        }
        else if (marqueur.getTitle().equals(TypeMarqueur.UNITE_SECOURS.toString())) {
            marqueur.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ffmarqueurunitesecours));
        }
        else if (marqueur.getTitle().equals(TypeMarqueur.VEHICULE_OFFICIER.toString())) {
            marqueur.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ffmarqueurofficier));
        }
        else {
            marqueur.icon(BitmapDescriptorFactory.fromResource(R.mipmap.marqueur_sortie));
        }

        return marqueur;
    }
}