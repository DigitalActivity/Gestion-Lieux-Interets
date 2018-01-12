package tp2.lieuxinteretgps;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import static tp2.lieuxinteretgps.Document.m_map;
import static tp2.lieuxinteretgps.Utilitaires.bougerMap;

/**
 * Classe de list item adapter pour la liste de lieux à proximité
 * @author Dracode
 */
class ListItemAdapter extends ArrayAdapter<FicheRenseignement> {
    private final List<FicheRenseignement> m_listeItem;   //La liste d'item dans le ListItemAdapter
    private final static int NOMBRE_ETAGES_MAX = 100;     //Le nombre max d'étages accepté pour un building.

    /**
     * @param context Le contexte du ListItemAdapter
     *
     */
    ListItemAdapter(Context context) {
        super(context, R.layout.list_fiche_item, Document.m_fichesProximite);
        this.m_listeItem = Document.m_fichesProximite;
    }

    @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_fiche_item, parent, false);
        }

        //Obtiens l'objet de courant dans une variable.
        FicheRenseignement i = m_listeItem.get(position);

        //Affectation des valeurs de l'objet courant.
        if (i != null) {
            TextView textViewNom = (TextView) v.findViewById(R.id.textViewNom);
            TextView textViewAdresse = (TextView) v.findViewById(R.id.textViewAdresse);
            TextView textViewEtages = (TextView) v.findViewById(R.id.textViewEtages);
            CheckBox checkBoxMobilite = (CheckBox) v.findViewById(R.id.checkBoxMobiliteReduite);
            CheckBox checkBoxMatiere = (CheckBox) v.findViewById(R.id.checkBoxMatiereDange);

            if (textViewNom != null) {
                String texte = getContext().getString(R.string.nom) + i.getNomLieu();
                textViewNom.setText(texte);
            }

            if (textViewAdresse != null) {
                textViewAdresse.setText(i.getAdresse());
            }

            if (textViewEtages != null) {
                String texte = i.getNbEtages() + getContext().getString(R.string.etages);
                textViewEtages.setText(texte);
            }


            if (checkBoxMobilite != null) {
                checkBoxMobilite.setEnabled(false);
                checkBoxMobilite.setChecked(i.hasPersonneMobiliteReduite());
            }

            if (checkBoxMatiere != null) {
                checkBoxMatiere.setEnabled(false);
                checkBoxMatiere.setChecked(i.hasMatieresDangereuses());
            }
        }

        // Click listener sur les elements de la liste
        // L'application centre la carte sur le lieu et affiche ses marqueurs
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_map.clear();
                // element selectionné
                FicheRenseignement ficheSelected = getItem(position);

                if (ficheSelected == null) {
                    return;
                }

                // Deplacer la camera sur le lieu
                MainActivity act = (MainActivity) getContext();
                Document.PlacerMaqueursListe();
                act.AfficherButtonsMarquage();
                act.setFicheSelectionne(ficheSelected);
                bougerMap(new LatLng(ficheSelected.getLatitude(), ficheSelected.getLongitude()), m_map, 18);
                // Placer les marqueurs de la ficheRenseignement sur la carte
                for (MarkerOptions m : ficheSelected.getListMarqueurs(getContext())) {
                    m_map.addMarker(m);
                }
            }
        });

        // Modification ou suppression de fiche de renseignement.
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final FicheRenseignement ficheSelected = getItem(position);
                if (ficheSelected == null) {
                    return false;
                }

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle(R.string.modification_fiche_title);

                LinearLayout linearLayout = new LinearLayout(getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                linearLayout.setLayoutParams(lp);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                final TextView textNom = new TextView(getContext());
                textNom.setText(R.string.nom_lieu);
                linearLayout.addView(textNom);

                final EditText input = new EditText(getContext());
                input.setText(ficheSelected.getNomLieu());
                input.setHint(R.string.nom_lieu_hint);
                linearLayout.addView(input);

                final TextView textEtages = new TextView(getContext());
                textEtages.setText(R.string.nombre_etages);
                linearLayout.addView(textEtages);

                final EditText editNbEtages = new EditText(getContext());
                editNbEtages.setInputType(InputType.TYPE_CLASS_NUMBER);
                editNbEtages.setText(String.valueOf(ficheSelected.getNbEtages()));
                editNbEtages.setHint(R.string.nombre_etages_hint);
                linearLayout.addView(editNbEtages);

                final CheckBox checkBoxMobilite = new CheckBox(getContext());
                checkBoxMobilite.setChecked(ficheSelected.hasPersonneMobiliteReduite());
                checkBoxMobilite.setText(R.string.mobilite_reduite);

                final CheckBox checkBoxMatiereDangereuse = new CheckBox(getContext());
                checkBoxMatiereDangereuse.setChecked(ficheSelected.hasMatieresDangereuses());
                checkBoxMatiereDangereuse.setText(R.string.matiere_dangereuse);

                linearLayout.addView(checkBoxMobilite);
                linearLayout.addView(checkBoxMatiereDangereuse);

                alertDialog.setPositiveButton(R.string.sauvegarder,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String nouveauNom = input.getText().toString();
                                int nbEtages;
                                try {
                                    nbEtages = Integer.parseInt(editNbEtages.getText().toString());
                                } catch (NumberFormatException e) {
                                    Toast.makeText(getContext(),
                                            R.string.format_error_etages,
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (nouveauNom.equals("")) {
                                    Toast.makeText(getContext(),
                                            getContext().getString(R.string.nom_vide_erreur_modif),
                                            Toast.LENGTH_SHORT).show();
                                } else if (nbEtages > NOMBRE_ETAGES_MAX || nbEtages < 1) {
                                    Toast.makeText(getContext(),
                                            getContext().getString(R.string.nb_etages_error) + NOMBRE_ETAGES_MAX + getContext().getString(R.string.nb_etages_error_second),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    ficheSelected.setNomLieu(nouveauNom);
                                    ficheSelected.setNombreEtages(nbEtages);
                                    ficheSelected.setPersonneMobiliteReduite(checkBoxMobilite.isChecked());
                                    ficheSelected.setMatieresDangereuses(checkBoxMatiereDangereuse.isChecked());
                                    new SingletonBD().get(getContext()).updateFicheRenseignement(ficheSelected);
                                    ListeFragment.notifierChagements();
                                }
                            }
                        });

                alertDialog.setNegativeButton(R.string.supprimer,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Utilitaires.dialogConfirmerOuiNon((MainActivity) getContext(),
                                        getContext().getResources().getString(R.string.confirmation_suppression_lieux),
                                        getContext().getResources().getString(R.string.oui),
                                        getContext().getResources().getString(R.string.non),
                                        new Utilitaires.DialogInputInterface() {
                                            @Override
                                            public void onResult(Boolean p_result) {
                                                if (p_result) {
                                                    Document.m_fichesProximite.remove(ficheSelected);
                                                    m_map.clear();
                                                    Document.PlacerMaqueursListe();
                                                    MainActivity act = (MainActivity) getContext();
                                                    act.deselectionnerItem();
                                                    ListeFragment.notifierChagements();
                                                    new SingletonBD().get(getContext()).deleteFicheRenseignement(ficheSelected);
                                                }
                                            }
                                        });

                            }
                        });

                alertDialog.setView(linearLayout);
                alertDialog.show();

                return true;
            }
        });

        return v;
    }
}
