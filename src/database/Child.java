package database;

import java.util.ArrayList;
import java.util.List;

/**
 * Class storing both the inputData about
 * the children in the year (0) but also the changes
 * in the following years
 */
public class Child {
    private int id;
    private String lastName;
    private String firstName;
    private String city;
    private int age;
    private List<String> giftsPreferences = new ArrayList<>();
    private Double averageScore;
    private List<Double> niceScoreHistory = new ArrayList<>();
    private Double assignedBudget;
    private ArrayList<Gift> receivedGifts = new ArrayList<>();

    public Child() {

    }

    /**
     * Method used to create Deep Copies/Clones of
     * the annual data regarding the children
     */
    public static Child clone(final Child original) {
        Child newObj = new Child();

        newObj.setId(original.getId());
        newObj.setLastName(original.getLastName());
        newObj.setFirstName(original.getFirstName());
        newObj.setCity(original.getCity());
        newObj.setAge(original.getAge());
        newObj.setAverageScore(original.getAverageScore());
        newObj.setAssignedBudget(original.getAssignedBudget());

        List<String> copyPref = new ArrayList<>();
        for (int i = 0; i < original.getGiftsPreferences().size(); i++) {
            copyPref.add(original.getGiftsPreferences().get(i));
        }

        newObj.setGiftsPreferences(copyPref);

        List<Double> copyHistory = new ArrayList<>();
        for (int i = 0; i < original.getNiceScoreHistory().size(); i++) {
            copyHistory.add(original.getNiceScoreHistory().get(i));
        }

        newObj.setNiceScoreHistory(copyHistory);

        ArrayList<Gift> copyReceived = new ArrayList<>();
        for (int i = 0; i < original.getReceivedGifts().size(); i++) {
            Gift copyGift = new Gift();
            Gift origGift = original.getReceivedGifts().get(i);

            copyGift.setProductName(origGift.getProductName());
            copyGift.setPrice(origGift.getPrice());
            copyGift.setCategory(origGift.getCategory());

            copyReceived.add(copyGift);
        }

        newObj.setReceivedGifts(copyReceived);
        return newObj;
    }

    public final int getId() {
        return id;
    }

    public final void setId(final int id) {
        this.id = id;
    }

    public final String getLastName() {
        return lastName;
    }

    public final void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public final String getFirstName() {
        return firstName;
    }

    public final void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public final int getAge() {
        return age;
    }

    public final void setAge(final int age) {
        this.age = age;
    }

    public final String getCity() {
        return city;
    }

    public final void setCity(final String city) {
        this.city = city;
    }

    public final List<String> getGiftsPreferences() {
        return giftsPreferences;
    }

    public final void setGiftsPreferences(final List<String> giftsPreferences) {
        this.giftsPreferences = giftsPreferences;
    }

    public final Double getAverageScore() {
        return averageScore;
    }

    public final void setAverageScore(final Double averageScore) {
        this.averageScore = averageScore;
    }

    public final List<Double> getNiceScoreHistory() {
        return niceScoreHistory;
    }

    public final void setNiceScoreHistory(final List<Double> niceScoreHistory) {
        this.niceScoreHistory = niceScoreHistory;
    }

    public final Double getAssignedBudget() {
        return assignedBudget;
    }

    public final void setAssignedBudget(final Double assignedBudget) {
        this.assignedBudget = assignedBudget;
    }

    public final ArrayList<Gift> getReceivedGifts() {
        return receivedGifts;
    }

    public final void setReceivedGifts(final ArrayList<Gift> receivedGifts) {
        this.receivedGifts = receivedGifts;
    }
}