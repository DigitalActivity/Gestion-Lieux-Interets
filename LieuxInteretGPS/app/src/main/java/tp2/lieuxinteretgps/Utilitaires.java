package tp2.lieuxinteretgps;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static tp2.lieuxinteretgps.Document.m_fichesProximite;

/**
 * Created by 0821450 on 2017-10-25.
 * Class Utilitaires contient des fonctions utiles pour l'application
 * @author Dracode
 */
class Utilitaires {

    /**
     * Verifier si un endroit est deja inscrit dans
     * FicheRenseignement, basé sur latLng et adress et nom du lieu
     * passer des parametres vides "" ou 0 pour les ignorer
     */
    private static boolean placeEstInscrite(float p_latitude, float p_longitude, String p_adresse, String p_nom) {
        for (FicheRenseignement fr : m_fichesProximite) {
            if (p_nom.equals(fr.getNomLieu()) && p_adresse.equals(fr.getAdresse())) {
                if (p_latitude == fr.getLatitude() && p_longitude == fr.getLongitude()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifier si la palce est deja inscrite
     */
    static boolean placeEstInscrite(Place place) {
        return placeEstInscrite((float) place.getLatLng().latitude,
                (float) place.getLatLng().longitude,
                (String) place.getAddress(),
                (String) place.getName());
    }

    /**
     * Deplacer la camera de la carte sur l'endroit en parameters
     */
    static void bougerMap(LatLng latLng, GoogleMap p_map, int p_zoom) {
        CameraUpdate CamUpdate = CameraUpdateFactory.newLatLngZoom(latLng, p_zoom);
        p_map.animateCamera(CamUpdate);
        // NE PLUS HIDE CE LAYOUT LA -> findViewById(R.id.layout_edit_fiche).setEnabled(true);
        // Faire apparaitre des boutons pour ajouter des bornes, portes, camions et bouton pour quiter l'état edit
    }

    /**
     * Afficher un dialog de confirmation
     */
    interface DialogInputInterface {
        // onResult(View v) is called when the user clicks on 'Ok'
        void onResult(Boolean b); // fonction qui va etre appelée une fois que user saisie une input dans le dialog
    }

    /**
     * Méthode pour afficher un dialogue pour avoir une confirmation
     * @param p_context      Le context à appeler le dialogue
     * @param p_message      Le message de l'alerte
     * @param p_txtButtonOui Le texte du boutton à gauche
     * @param p_txtButtonNon Le texte du boutton à droite
     * @param p_dlgResult    Le dialogue input inteface à éxécuter au click.
     */
    static void dialogConfirmerOuiNon(Activity p_context, String p_message,
                                      String p_txtButtonOui, String p_txtButtonNon,
                                      final DialogInputInterface p_dlgResult) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        p_dlgResult.onResult(true);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        p_dlgResult.onResult(false);
                        break;
                    default:
                        p_dlgResult.onResult(false);
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(p_context);
        builder.setMessage(p_message).setPositiveButton(p_txtButtonOui, dialogClickListener)
                .setNegativeButton(p_txtButtonNon, dialogClickListener).show();
    } // fin dialogConfirmerOuiNon


    /***
     * Dialog choisir un type de camion, retourn un marqueur avec le bon titre et la bonne icone
     *  selon le camion choisi
     */
    interface DialogInputTypeCamion {
        // onResult(View v) appelé quand l'utilisateur choisi un element de la liste
        void onResult(MarkerOptions p_marker); // fonction qui va etre appelée une fois que user saisie une input dans le dialog
    }
    static void showMenuTypeCamion(Activity p_context, final DialogInputTypeCamion p_callback) {
        final MarkerOptions myMarkerOptions = new MarkerOptions();
        AlertDialog.Builder builder = new AlertDialog.Builder(p_context);
        builder.setTitle("Type de lieu");
        builder.setItems(new CharSequence[]{
                        TypeMarqueur.AUTOPOMPE.toString(), TypeMarqueur.UNITE_INTERVENTION_MATIERE_DANGEUREUSE.toString(),
                        TypeMarqueur.CITERNE.toString(), TypeMarqueur.ECHELLE.toString(),
                        TypeMarqueur.VEHICULE_OFFICIER.toString(), TypeMarqueur.UNITE_SECOURS.toString(),
                },

                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                myMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ffmarqueurautopompe));
                                myMarkerOptions.title(TypeMarqueur.AUTOPOMPE.toString());
                                break;
                            case 1:
                                myMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ffinterventionhazardous));
                                myMarkerOptions.title(TypeMarqueur.UNITE_INTERVENTION_MATIERE_DANGEUREUSE.toString());
                                break;
                            case 2:
                                myMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ffmarqueurciterne));
                                myMarkerOptions.title(TypeMarqueur.CITERNE.toString());
                                break;
                            case 3:
                                myMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ffmarqueurechelle));
                                myMarkerOptions.title(TypeMarqueur.ECHELLE.toString());
                                break;
                            case 4:
                                myMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ffmarqueurofficier));
                                myMarkerOptions.title(TypeMarqueur.VEHICULE_OFFICIER.toString());
                                break;
                            case 5:
                                myMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ffmarqueurunitesecours));
                                myMarkerOptions.title(TypeMarqueur.UNITE_SECOURS.toString());
                                break;
                        }
                        p_callback.onResult(myMarkerOptions);
                    }
                });
        builder.create().show();
    }

    /***
     * Verifier si Google play services, et les permissions sont obtenuent.
     */
    static boolean isServiceOK(Activity p_context, int p_errorId) {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(p_context);

        if (available == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Dialog dia = GoogleApiAvailability.getInstance().getErrorDialog(p_context, available, p_errorId);
            dia.show();
        } else {
            Toast.makeText(p_context, "This device can\'t use google maps requests", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    /**
     * Fonction pour savoir si une string est un chiffre valide.
     *
     * @param value La string a évaluer.
     * @return true si la string est un chiffre valide, sinon false.
     */
    static Boolean isValidInteger(String value) {
        try {
            Integer val = Integer.valueOf(value);
            return val != null;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
