package database;

import java.util.ArrayList;

/**
 * Class for retaining the base original information
 * coming from the input test files but also the
 * updated data over the years
 */
public final class Database {
    private static Database singleton;
    private int numberOfYears;
    private Double santaBudget;
    private InitialData initialData;
    private ArrayList<AnnualChanges> annualChanges = new ArrayList<>();

    private Database() {

    }

    /**
     * Singleton Design Pattern used to ensure
     * the creation of a single database for every
     * data bundle
     */
    public static Database getInstance() {
        if (singleton == null) {
            singleton = new Database();
        }

        return singleton;
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public void setNumberOfYears(final int numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public Double getSantaBudget() {
        return santaBudget;
    }

    public void setSantaBudget(final Double santaBudget) {
        this.santaBudget = santaBudget;
    }

    public InitialData getInitialData() {
        return initialData;
    }

    public void setInitialData(final InitialData initialData) {
        this.initialData = initialData;
    }

    public ArrayList<AnnualChanges> getAnnualChanges() {
        return annualChanges;
    }

    public void setAnnualChanges(final ArrayList<AnnualChanges> annualChanges) {
        this.annualChanges = annualChanges;
    }
}