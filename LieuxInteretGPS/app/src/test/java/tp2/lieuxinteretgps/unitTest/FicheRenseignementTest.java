package tp2.lieuxinteretgps.unitTest;

import org.junit.Test;

import tp2.lieuxinteretgps.FicheRenseignement;

import static org.junit.Assert.assertEquals;

public class FicheRenseignementTest {

    private float latitudeTest = 45.306059f;
    private float latitudeAttendue = latitudeTest;
    private float longitudeTest = -73.252403f;
    private float longitudeAttendue = longitudeTest;
    private String adresseTest = "54 Rue Saint-Jacques, Saint-Jean-sur-Richelieu, QC J3B 2J9";
    private String adresseAttendue = adresseTest;
    private String nomLieuTest = "Nova DBA";
    private String nomLieuAttendue = nomLieuTest;
    private boolean mobiliteReduiteTest = false;
    private boolean mobiliteReduiteAttendue = mobiliteReduiteTest;
    private boolean matiereDangeureusesTest = false;
    private boolean matiereDangeureusesAttendue = matiereDangeureusesTest;
    private int nbEtagesTest = 2;
    private int nbEtagesAttendue = nbEtagesTest;
    //As déjà un Test, on le restest ?
    //private ForceFrappe forceFrappeAttendue = new ForceFrappe(TypeCamion.AUTOPOMPE, 2, "960 3e Rue, Richelieu");

    @Test
    public void testFicheRenseignement() {
        System.out.println("Test de FicheRenseignement : ");

        FicheRenseignement ficheDeTest = new FicheRenseignement(latitudeTest, longitudeTest, adresseTest, nomLieuTest, mobiliteReduiteTest, matiereDangeureusesTest,
                                                                nbEtagesTest);
        assertEquals(ficheDeTest.getLatitude(), latitudeAttendue, 0.00f);
        assertEquals(ficheDeTest.getLongitude(), longitudeAttendue, 0.00f);
        assertEquals(ficheDeTest.getAdresse(), adresseAttendue);
        assertEquals(ficheDeTest.getNomLieu(), nomLieuAttendue);
        assertEquals(ficheDeTest.hasPersonneMobiliteReduite(), mobiliteReduiteAttendue);
        assertEquals(ficheDeTest.hasMatieresDangereuses(), matiereDangeureusesAttendue);
        assertEquals(ficheDeTest.getNbEtages(), nbEtagesAttendue);
        //assertEquals(forceFrappeTest, forceFrappeAttendue);

        //TODO tester les exceptions.
    }
}