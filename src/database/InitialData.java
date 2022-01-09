package database;

import java.util.ArrayList;

/**
 * Class used for storing the base given information about the
 * children and santa's gifts list before the changes in
 * the following rounds
 */
public class InitialData {
    private ArrayList<Child> childrenList = new ArrayList<>();
    private ArrayList<Gift> giftsList = new ArrayList<>();

    public InitialData() {

    }

    public final ArrayList<Child> getChildrenList() {
        return childrenList;
    }

    public final void setChildrenList(final ArrayList<Child> childrenList) {
        this.childrenList = childrenList;
    }

    public final ArrayList<Gift> getGiftsList() {
        return giftsList;
    }

    public final void setGiftsList(final ArrayList<Gift> giftsList) {
        this.giftsList = giftsList;
    }
}
