package tp2.lieuxinteretgps;

import android.location.LocationManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Document contient les données de l'application et certaines fonctionnalités générales
 * @author Dracode
 */
class Document {
    static GoogleMap m_map;
    static LatLng m_maPosition = null;
    static LocationManager locationManager;
    static final List<FicheRenseignement> m_fichesProximite = new ArrayList<>();


    /**
     * Placer les marqueurs de tous les lieux de la bd
     */
    static void PlacerMaqueursListe() {
        for (FicheRenseignement fiche : m_fichesProximite) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(fiche.getLatitude(), fiche.getLongitude()));
            markerOptions.title(fiche.getNomLieu());

            m_map.addMarker(markerOptions);
        }
    }
}
