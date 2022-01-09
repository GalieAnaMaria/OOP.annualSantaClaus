package database;

import java.util.ArrayList;

/**
 * Helper class (specifically for the output's format)
 * for storing the data/changes regarding the children annually
 */
public class YearlyChildren {
    private ArrayList<Child> children = new ArrayList<>();

    public YearlyChildren() {

    }

    public final ArrayList<Child> getChildren() {
        return children;
    }

    public final void setChildren(final ArrayList<Child> children) {
        this.children = children;
    }
}