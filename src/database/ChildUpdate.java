package database;

import java.util.ArrayList;
import java.util.List;

/**
 * Class storing the data needed for an
 * update regarding a child's information/wishes
 */
public class ChildUpdate {
    private int childId;
    private Double changedNiceScore;
    private List<String> newGiftsPreferences = new ArrayList<>();

    public ChildUpdate() {

    }

    public final int getChildId() {
        return childId;
    }

    public final void setChildId(final int childId) {
        this.childId = childId;
    }

    public final Double getChangedNiceScore() {
        return changedNiceScore;
    }

    public final void setChangedNiceScore(final Double changedNiceScore) {
        this.changedNiceScore = changedNiceScore;
    }

    public final List<String> getNewGiftsPreferences() {
        return newGiftsPreferences;
    }

    public final void setNewGiftsPreferences(final List<String> newGiftsPreferences) {
        this.newGiftsPreferences = newGiftsPreferences;
    }
}
