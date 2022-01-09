package database;

import java.util.ArrayList;

/**
 * Class which will retain the inputData regarding
 * the changes over each year to further be used
 * in the implementation
 */
public class AnnualChanges {
    private Double newSantaBudget;
    private ArrayList<Gift> newGifts = new ArrayList<>();
    private ArrayList<Child> newChildren = new ArrayList<>();
    private ArrayList<ChildUpdate> childrenUpdates = new ArrayList<>();

    public AnnualChanges() {

    }

    public final Double getNewSantaBudget() {
        return newSantaBudget;
    }

    public final void setNewSantaBudget(final Double newSantaBudget) {
        this.newSantaBudget = newSantaBudget;
    }

    public final ArrayList<Gift> getNewGifts() {
        return newGifts;
    }

    public final void setNewGifts(final ArrayList<Gift> newGifts) {
        this.newGifts = newGifts;
    }

    public final ArrayList<Child> getNewChildren() {
        return newChildren;
    }

    public final void setNewChildren(final ArrayList<Child> newChildren) {
        this.newChildren = newChildren;
    }

    public final ArrayList<ChildUpdate> getChildrenUpdates() {
        return childrenUpdates;
    }

    public final void setChildrenUpdates(final ArrayList<ChildUpdate> childrenUpdates) {
        this.childrenUpdates = childrenUpdates;
    }
}
