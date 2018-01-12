package tp2.lieuxinteretgps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import static tp2.lieuxinteretgps.Utilitaires.isValidInteger;

/**
 * Created by 0821450 on 2017-11-03.
 */
public class ForceFrappeActivity extends AppCompatActivity {
    private EditText uniteHazardous;
    private EditText echelle;
    private EditText uniteSecours;
    private EditText autoPompes;
    private EditText vehiculeOfficier;
    private EditText citerne;
    private FicheRenseignement ficheRenseignement;
    private ForceFrappe forceFrappe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.force_frappe_activity);

        uniteHazardous = (EditText) findViewById(R.id.editTextUniteHazardous);
        echelle = (EditText) findViewById(R.id.editTextEchelle);
        uniteSecours = (EditText) findViewById(R.id.editTextUniteSecours);
        autoPompes = (EditText) findViewById(R.id.editTextAutopompe);
        vehiculeOfficier = (EditText) findViewById(R.id.editTextVehiculeOfficier);
        citerne = (EditText) findViewById(R.id.editTextCiterne);
        TextView nomlieu = (TextView) findViewById(R.id.forcefrappenomlieu);
        Button sauvegarder = (Button) findViewById(R.id.buttonEnregistrer);

        // Get the Intent that started this activity and extract FicheRenseignement
        Intent intent = getIntent();
        ficheRenseignement = (FicheRenseignement) intent.getSerializableExtra(MainActivity.EXTRA_FORCE_FRAPPE);

        // Ecrire nom lieu
        nomlieu.setText(ficheRenseignement.getNomLieu());

        // Remplir les champs si des valeurs existent
        forceFrappe = new SingletonBD().get(getApplicationContext()).getForceFrappe(ficheRenseignement);
        if(forceFrappe != null) {
            initialiserLesChamps();
        }

        // Boutton enregistrer est cliqué
        sauvegarder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enregistrerLesChangements();
            }
        });
    }

    /***
     * Initialiser les chmaps de l'activité
     */
    private void initialiserLesChamps() {
        uniteHazardous.setText(String.valueOf(forceFrappe.getNbUnitesIntervMatDangereuses()));
        echelle.setText(String.valueOf(forceFrappe.getNbEchelles()));
        uniteSecours.setText(String.valueOf(forceFrappe.getNbUnitesSecours()));
        autoPompes.setText(String.valueOf(forceFrappe.getNbAutopompes()));
        vehiculeOfficier.setText(String.valueOf(forceFrappe.getNbVechiulesOfficier()));
        citerne.setText(String.valueOf(forceFrappe.getNbCiternes()));
    }

    /***
     * Enregistrer les données
     */
    private void enregistrerLesChangements() {
        ForceFrappe forceFrappe = new ForceFrappe();

        // Enregirdter les champs dans la bd apres validation
        if(isValidInteger(uniteHazardous.getText().toString()))
            forceFrappe.setNbUnitesIntervMatDangereuses(Integer.parseInt(uniteHazardous.getText().toString()));
        if(isValidInteger(echelle.getText().toString()))
            forceFrappe.setNbEchelles(Integer.parseInt(echelle.getText().toString()));
        if(isValidInteger(uniteSecours.getText().toString()))
            forceFrappe.setNbUnitesSecours(Integer.parseInt(uniteSecours.getText().toString()));
        if(isValidInteger(autoPompes.getText().toString()))
            forceFrappe.setNbAutopompes(Integer.parseInt(autoPompes.getText().toString()));
        if(isValidInteger(vehiculeOfficier.getText().toString()))
            forceFrappe.setNbVechiulesOfficier(Integer.parseInt(vehiculeOfficier.getText().toString()));
        if(isValidInteger(citerne.getText().toString()))
            forceFrappe.setNbCiternes(Integer.parseInt(citerne.getText().toString()));

        // Verifier si mise a jour ou ajout d'une nouvelle donnée
        ForceFrappe f = new SingletonBD().get(getApplicationContext()).getForceFrappe(ficheRenseignement);
        if(f == null)
            new SingletonBD().get(getApplicationContext()).addForceFrappe(forceFrappe, ficheRenseignement);
        else
            new SingletonBD().get(getApplicationContext()).updateForceFrappe(forceFrappe, ficheRenseignement);

        finish();
    }
}
