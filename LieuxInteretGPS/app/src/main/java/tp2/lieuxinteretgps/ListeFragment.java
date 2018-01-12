package tp2.lieuxinteretgps;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.Comparator;

import static tp2.lieuxinteretgps.Document.m_maPosition;

/**
 * Classe du fragment de la liste de fiche de renseignement.
 *
 * @author Dracode
 */
public class ListeFragment extends Fragment {
    private static ArrayAdapter<FicheRenseignement> arrayAdapter;    //L'array adapter associé au fragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.liste_fragment, container, false);
        ListView list = (ListView) view.findViewById(R.id.list_view);

        arrayAdapter = new ListItemAdapter(view.getContext()
        );

        list.setAdapter(arrayAdapter);
        return view;
    }

    /***
     * Notifier des changements et reordonner la liste des elements
     */
    public static void notifierChagements() {
        arrayAdapter.sort(new Comparator<FicheRenseignement>() {
            @Override
            public int compare(FicheRenseignement place1, FicheRenseignement place2) {
                LatLng currentLoc = m_maPosition;
                double lat1 = place1.getLatitude();
                double lon1 = place1.getLongitude();
                double lat2 = place2.getLatitude();
                double lon2 = place2.getLongitude();

                double distanceToPlace1 = distance(currentLoc.latitude, currentLoc.longitude, lat1, lon1);
                double distanceToPlace2 = distance(currentLoc.latitude, currentLoc.longitude, lat2, lon2);
                return (distanceToPlace1 - distanceToPlace2 < 0) ? -1 : 1;
            }
        });

        arrayAdapter.notifyDataSetChanged();
    }

    /***
     * Calculer la distance entre deux positions
     */
    private static float distance(double lat_a, double lng_a, double lat_b, double lng_b) {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b - lat_a);
        double lngDiff = Math.toRadians(lng_b - lng_a);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff / 2) * Math.sin(lngDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return (float) (distance * meterConversion);
    }
}