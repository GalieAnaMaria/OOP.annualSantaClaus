package database;

import java.util.ArrayList;

/**
 * Helper class (specifically for the output's format)
 * for storing the data/changes regarding the children annually
 */
public class AnnualChildren {
    private ArrayList<YearlyChildren> annualChildren = new ArrayList<>();

    public AnnualChildren() {

    }

    public final ArrayList<YearlyChildren> getAnnualChildren() {
        return annualChildren;
    }

    public final void setAnnualChildren(final ArrayList<YearlyChildren> annualChildren) {
        this.annualChildren = annualChildren;
    }
}
