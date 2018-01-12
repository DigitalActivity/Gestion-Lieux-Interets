package tp2.lieuxinteretgps;

/**
 * Created by Pierre on 2017-11-03.
 */
public class ForceFrappe {
    private int m_nbAutopompes;
    private int m_nbCiternes;
    private int m_nbEchelles;
    private int m_nbUnitesIntervMatDangereuses;
    private int m_nbUnitesSecours;
    private int m_nbVechiulesOfficier;

    /***
     * Constructeur force de frappe
     */
    public ForceFrappe(int p_nbAutopompes, int p_nbCiternes, int p_nbEchelles, int p_nbUnitesIntervMatDangereuses,
                       int p_nbUnitesSecours, int p_nbVechiulesOfficier) {
        m_nbAutopompes = p_nbAutopompes;
        m_nbCiternes = p_nbCiternes;
        m_nbEchelles = p_nbEchelles;
        m_nbUnitesIntervMatDangereuses = p_nbUnitesIntervMatDangereuses;
        m_nbUnitesSecours = p_nbUnitesSecours;
        m_nbVechiulesOfficier = p_nbVechiulesOfficier;
    }

    /***
     * Constructeur sans parametres force frappe
     */
    public ForceFrappe() {
        m_nbAutopompes = 0;
        m_nbCiternes = 0;
        m_nbEchelles = 0;
        m_nbUnitesIntervMatDangereuses = 0;
        m_nbUnitesSecours = 0;
        m_nbVechiulesOfficier = 0;
    }

    // Getters et Setters
    public int getNbAutopompes() {
        return m_nbAutopompes;
    }

    public void setNbAutopompes(int p_nbAutopompes) {
        m_nbAutopompes = p_nbAutopompes;
    }

    public int getNbCiternes() {
        return m_nbCiternes;
    }

    public void setNbCiternes(int p_nbCiternes) {
        m_nbCiternes = p_nbCiternes;
    }

    public int getNbEchelles() {
        return m_nbEchelles;
    }

    public void setNbEchelles(int p_nbEchelles) {
        m_nbEchelles = p_nbEchelles;
    }

    public int getNbUnitesIntervMatDangereuses() {
        return m_nbUnitesIntervMatDangereuses;
    }

    public void setNbUnitesIntervMatDangereuses(int p_nbUnitesIntervMatDangereuses) {
        m_nbUnitesIntervMatDangereuses = p_nbUnitesIntervMatDangereuses;
    }

    public int getNbUnitesSecours() {
        return m_nbUnitesSecours;
    }

    public void setNbUnitesSecours(int p_nbUnitesSecours) {
        m_nbUnitesSecours = p_nbUnitesSecours;
    }

    public int getNbVechiulesOfficier() {
        return m_nbVechiulesOfficier;
    }

    public void setNbVechiulesOfficier(int p_nbVechiulesOfficier) {
        m_nbVechiulesOfficier = p_nbVechiulesOfficier;
    }
}
