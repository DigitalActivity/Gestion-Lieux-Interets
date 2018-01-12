package tp2.lieuxinteretgps;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import static tp2.lieuxinteretgps.Document.*;
import static tp2.lieuxinteretgps.Utilitaires.*;

/***
 * Main Activity : Activité principale de l'application
 * @author Dracode
 */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private static final int ERROR_DIALOG = 9001;   //code d'erreur de dialogue
    private final static int PLACE_PICKER_REQUEST = 1;  //Code de place picker request
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2000; //Code de request de permission
    public static final String EXTRA_FORCE_FRAPPE = "FORCE_FRAPPE"; //String pour le intent de force de frappe.
    private ImageButton m_buttonQuitMarqueurs;  //Bouton pour quitter le modification d'un lieu.
    private ImageButton m_buttonForceFrappe;    //Bouton pour la force de frappe.
    private ImageButton m_buttonCamion;         //Bouton pour ajouter un camion.
    private ImageButton m_buttonSortie;         //Bouton pour ajouter une sortie.
    private ImageButton m_buttonBorne;          //Bouton pour ajouter une borne.
    private View locationButton;                //Bouton, de google map, pour centrer la map sur notre position.
    private Marker markerSelectionne = null;    //Le markeur sélectionné, pour gèrer la suppression.
    private FicheRenseignement m_ficheSelectionne = null; //La fiche de renseignement sélectionné.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Initialiser les touches
        m_buttonQuitMarqueurs = (ImageButton) findViewById(R.id.button_quit);
        m_buttonForceFrappe = (ImageButton) findViewById(R.id.button_forcefrappe);
        m_buttonCamion = (ImageButton) findViewById(R.id.button_camion);
        m_buttonSortie = (ImageButton) findViewById(R.id.button_sortie);
        m_buttonBorne = (ImageButton) findViewById(R.id.button_borne);
        InitialiserOnClickButtonsMarquage();
        if (m_fichesProximite.isEmpty()) {
            m_fichesProximite.addAll(new SingletonBD().get(getApplicationContext()).getListeFicheRenseignement());
        }
    }

    /***
     * Appeler Activity Force Frappe
     */
    private void startForceFrappeActivity() {
        Intent intent = new Intent(MainActivity.this, ForceFrappeActivity.class);
        intent.putExtra(EXTRA_FORCE_FRAPPE, m_ficheSelectionne);
        startActivity(intent);
    }

    /***
     * Initialiser les buttons sur la map.
     */
    private void InitialiserOnClickButtonsMarquage() {
        //Click du bouton quitter
        m_buttonQuitMarqueurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_buttonQuitMarqueurs.setVisibility(View.INVISIBLE);
                m_buttonCamion.setVisibility(View.INVISIBLE);
                m_buttonBorne.setVisibility(View.INVISIBLE);
                m_buttonSortie.setVisibility(View.INVISIBLE);
                m_buttonForceFrappe.setVisibility(View.INVISIBLE);
                m_ficheSelectionne = null;
            }
        });

        //Click du bouton force de frappe
        m_buttonForceFrappe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startForceFrappeActivity();
            }
        });

        //Click du bouton camion
        // Afficher dialog pour Choisir le bon type de camion à placer
        m_buttonCamion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenuTypeCamion(MainActivity.this, new DialogInputTypeCamion() {
                    @Override
                    public void onResult(MarkerOptions marqueurOptions) {
                        if (marqueurOptions != null) {
                            marqueurOptions.position(m_maPosition);
                            m_map.addMarker(marqueurOptions);
                            m_ficheSelectionne.ajouterMarqueur(getApplicationContext(), marqueurOptions);
                        }
                    }
                });
            }// Fin onclick
        }); // Fin m_buttonCamion.setOnClickListener

        //Click du bouton Borne
        m_buttonBorne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.marqueur_borne_hybride));
                markerOptions.title(TypeMarqueur.BORNE.toString());
                markerOptions.position(m_maPosition);
                m_map.addMarker(markerOptions);
                m_ficheSelectionne.ajouterMarqueur(getApplicationContext(), markerOptions);
            }
        });

        //Click du bouton sortie
        m_buttonSortie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.marqueur_sortie));
                markerOptions.title(TypeMarqueur.SORTIE.toString());
                markerOptions.position(m_maPosition);
                m_map.addMarker(markerOptions);
                m_ficheSelectionne.ajouterMarqueur(getApplicationContext(), markerOptions);
            }
        });
    }

    /**
     * Déselectionne les items.
     */
    public void deselectionnerItem() {
        m_buttonQuitMarqueurs.callOnClick();
    }

    /***
     * Afficher les buttons sur la map.
     */
    public void AfficherButtonsMarquage() {
        m_buttonQuitMarqueurs.setVisibility(View.VISIBLE);
        m_buttonForceFrappe.setVisibility(View.VISIBLE);
        m_buttonCamion.setVisibility(View.VISIBLE);
        m_buttonBorne.setVisibility(View.VISIBLE);
        m_buttonSortie.setVisibility(View.VISIBLE);
    }

    /***
     * Initialiser Google map
     */
    private void initialiserMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Callback de map ready.
     * Utilisé pour appeler la fonction du bouton pour centrer la caméra
     * sur la postion de l'appareil.
     */
    private final GoogleMap.OnMapLoadedCallback call = new GoogleMap.OnMapLoadedCallback() {
        @Override
        public void onMapLoaded() {
            if (locationButton != null) {
                locationButton.callOnClick();   //Une fois la map loadé, pèse sur le bouton my position.
            }
        }
    };

    /***
     * Map is ready callback. Fonction déclanchée quand la carte est prête
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        //Enregistrer l'objet GoogleMap
        m_map = googleMap;

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            m_map.setMyLocationEnabled(true);
            locationButton = ((View) this.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            m_map.setOnMapLoadedCallback(call);
            PlacerMaqueursListe();
        }

        // Long Click Listener
        m_map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (m_maPosition != null) {
                    // Pick Place using google Activity API
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                    StartPlacePicker(builder);
                }
            }
        });

        // Set a listener for marker click.
        m_map.setOnMarkerClickListener(this);
    }

    /***
     * Choisir une place en utilisant google places API
     */
    private void StartPlacePicker(PlacePicker.IntentBuilder p_intent) {
        try {
            Intent intent = p_intent.build(this);

            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    /***
     * Retourn la place choisie : Google places API
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                final Place place = PlacePicker.getPlace(this, data);
                // ajouter nouvelle place
                String nomPlace = place.getName().equals("") ? (String) place.getAddress() : (String) place.getName();
                dialogConfirmerOuiNon(this, "Ajouter cette place : \n".concat(nomPlace),
                    "Ajouter", "Annuler", new DialogInputInterface() {
                        @Override
                        public void onResult(Boolean b) {
                        if (b) {
                            ajouterNouvellePlace(place);
                        }
                        }
                    });
            }
        }
    }

    /***
     * Ajouter une nouvelle place
     */
    private void ajouterNouvellePlace(Place p_place) {
        // verifier si le lieu n'exist pas
        if (!placeEstInscrite(p_place)) {
            // Ajouter
            FicheRenseignement ficheRenseignement = new FicheRenseignement((float) p_place.getLatLng().latitude,
                    (float) p_place.getLatLng().longitude, p_place.getAddress().toString(),
                    p_place.getName().toString(), false, false, 1);

            m_fichesProximite.add(ficheRenseignement);
            m_map.clear();
            PlacerMaqueursListe();
            ListeFragment.notifierChagements();
            m_buttonQuitMarqueurs.callOnClick();
            //Ajout de la fiche dans la BD
            new SingletonBD().get(getApplicationContext()).addFicheRenseignement(ficheRenseignement);
            Toast.makeText(this, p_place.getAddress(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Le lieux est déjà enregistrer.", Toast.LENGTH_LONG).show();
        }
    }

    /***
     * Demander les droits necessaires pour la localisation et Internet
     */
    private boolean demanderDroits() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, locationListenerGPS);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 0, locationListenerGPS);
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return false;
        }
    }

    /***
     * Obtenir les permission de localisation et internet
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, locationListenerGPS);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 0, locationListenerGPS);
                    initialiserMap();
                } else {
                    Toast.makeText(this, R.string.autorisation_localisation, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * locationListenerGPS : Gère l'affichage des deplacements
     */
    private final LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            m_maPosition = new LatLng(latitude, longitude);
            ListeFragment.notifierChagements();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {
            Toast.makeText(getApplicationContext(), R.string.autorisation_localisation, Toast.LENGTH_LONG).show();
            Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(callGPSSettingIntent);
        }
    }; // Fin locationListenerGPS

    /**
     * fonction qui permet de set la fiche sélectionné.
     * @param p_fiche la FicheRenseignement à mettre courrante.
     */
    public void setFicheSelectionne(FicheRenseignement p_fiche) {
        m_ficheSelectionne = p_fiche;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isServiceOK(this, ERROR_DIALOG) && demanderDroits()) {
            initialiserMap();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListenerGPS);
    }

    /**
     * Supprimer un marqeur
     * Appelé quand l'utilisateur click sur un marker.
     * https://developers.google.com/maps/documentation/android-api/marker
     *
     * @param p_marker le marker cliqué.
     */
    @Override
    public boolean onMarkerClick(final Marker p_marker) {
        //Affiche l'information en haut du marqueur.
        p_marker.showInfoWindow();

        //Prompt de suppression du marqueur clické.
        if (m_ficheSelectionne != null && markerSelectionne != null && markerSelectionne.equals(p_marker)) {
            Utilitaires.dialogConfirmerOuiNon(this, getString(R.string.confirmation_supprimer_marqueur), getString(R.string.oui), getString(R.string.non), new DialogInputInterface() {
                @Override
                public void onResult(Boolean b) {
                    if (b) {
                        markerSelectionne = null;
                        //Supprime le marqueur
                        new SingletonBD().get(getApplicationContext()).deleteMarqueur(p_marker);
                        //Clear la map
                        m_map.clear();
                        //Replace tout les lieux
                        Document.PlacerMaqueursListe();
                        //Replace les marqueurs du lieu selectionné.
                        for (MarkerOptions m : m_ficheSelectionne.getListMarqueurs(getApplicationContext())) {
                            m_map.addMarker(m);
                        }
                    }
                }
            });
        } else {    //Set le marker selectionné au marquer clické si le dernier n'était pas identique.
            markerSelectionne = p_marker;
        }

        return true;
    }
}