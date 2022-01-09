package database;

import com.fasterxml.jackson.core.JsonProcessingException;
import common.Constants;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Utility class serving as a management class where
 * the information is converted/changed/added later
 * to be stored and displayed
 */
public final class Management {
    private Management() {

    }

    /**
     * Main method from where all the functional methods
     * are being called.
     * Serves at updating information in the database over the
     * years but also storing snapshots of yearly situations
     * regarding the children data.
     */
    public static void annualChangesManagement(final String testId,
                                               final Database database)
                                                throws JsonProcessingException {
        AnnualChildren memory = new AnnualChildren();

        eliminateYoungAdults(database.getInitialData().getChildrenList());
        calculateAverage(database.getInitialData().getChildrenList());
        allocateBudget(database.getInitialData().getChildrenList(),
                database.getSantaBudget());

        database.getInitialData()
                .getGiftsList().sort(Comparator.comparing(Gift::getPrice));

        giveGifts(database.getInitialData().getChildrenList(),
                database.getInitialData().getGiftsList());

        int nrOfChanges = database.getNumberOfYears();

        YearlyChildren firstYearlyKids = new YearlyChildren();
        ArrayList<Child> firstChildren = new ArrayList<>();
        ArrayList<Child> firstKidsList =
                database.getInitialData().getChildrenList();

        for (Child value : firstKidsList) {
            Child childClone;
            childClone = Child.clone(value);

            firstChildren.add(childClone);
        }

        firstYearlyKids.setChildren(firstChildren);
        memory.getAnnualChildren().add(firstYearlyKids);

        for (int i = 0; i < nrOfChanges; i++) {
            YearlyChildren yearlyKids = new YearlyChildren();
            ArrayList<Child> kidsList = database.getInitialData().getChildrenList();
            ArrayList<Gift> giftsList = database.getInitialData().getGiftsList();
            ArrayList<AnnualChanges> annualChanges = database.getAnnualChanges();

            increaseAge(kidsList);

            if (annualChanges.get(i).getNewChildren().size() != 0) {
                addNewKids(kidsList,
                        annualChanges.get(i).getNewChildren());
            }

            if (annualChanges.get(i).getChildrenUpdates().size() != 0) {
                updateKidsData(kidsList,
                        annualChanges.get(i).getChildrenUpdates());
            }

            if (annualChanges.get(i).getNewGifts().size() != 0) {
                addNewGifts(giftsList,
                        annualChanges.get(i).getNewGifts());
            }

            changeAnnualBudget(database,
                    annualChanges.get(i).getNewSantaBudget());

            eliminateYoungAdults(kidsList);
            calculateAverage(kidsList);
            allocateBudget(kidsList,
                    database.getSantaBudget());
            giveGifts(kidsList,
                    giftsList);

            ArrayList<Child> yearChildren = new ArrayList<>();
            ArrayList<Child> originalKidsList =
                    database.getInitialData().getChildrenList();

            for (Child child : originalKidsList) {
                Child childClone;
                childClone = Child.clone(child);

                yearChildren.add(childClone);
            }

            yearlyKids.setChildren(yearChildren);
            memory.getAnnualChildren().add(yearlyKids);
        }

        WriteData.writeData(testId, memory);
    }

    /**
     * Method used to increase the age of
     * every child each passing year
     */
    private static void increaseAge(final ArrayList<Child> yearlyKidsList) {
        for (Child child : yearlyKidsList) {
            int originalAge = child.getAge();
            int newAge;
            newAge = originalAge + 1;
            child.setAge(newAge);
        }
    }

    /**
     * Method with the purpose of updating the santa's annual budget
     * for the children in the current year
     */
    private static void changeAnnualBudget(final Database database, final Double newBudget) {
        database.setSantaBudget(newBudget);
    }

    /**
     * Method with main functionality to calculate the budget
     * for each child, each year according to their behavior history
     * but also with how many children they will have to share the year
     * budget with
     */
    private static void allocateBudget(final ArrayList<Child> currentKidsList,
                                      final Double santaBudget) {
        Double averageSum = 0.0;

        for (Child child : currentKidsList) {
            averageSum = averageSum + child.getAverageScore();
        }

        Double budgetUnit = santaBudget / averageSum;

        for (Child child : currentKidsList) {
            Double assignedBudget;
            assignedBudget = child.getAverageScore() * budgetUnit;

            child.setAssignedBudget(assignedBudget);
        }
    }

    /**
     * Method which distributes gifts from santa's list to each
     * child according to their gift preferences/preferred gift category.
     * Each child will get as many presents from as many categories as
     * it is permitted by the personal budget and santa's gift list
     */
    private static void giveGifts(final ArrayList<Child> kidsList,
                                 final ArrayList<Gift> currentGifts) {
        for (Child child : kidsList) {
            int sizeChildCateg = child.getGiftsPreferences().size();
            Double kidBudget = child.getAssignedBudget();

            child.getReceivedGifts().clear();

            for (int j = 0; j < sizeChildCateg; j++) {
                String category = child.getGiftsPreferences().get(j);
                Gift giftToReceive = new Gift();

                for (Gift giftToInsert : currentGifts) {
                    if (giftToInsert.getCategory().equals(category)) {
                        Double giftPrice = giftToInsert.getPrice();

                        if (giftPrice <= kidBudget) {

                            giftToReceive.setProductName(giftToInsert.getProductName());
                            giftToReceive.setPrice(giftToInsert.getPrice());
                            giftToReceive.setCategory(giftToInsert.getCategory());

                            child.getReceivedGifts().add(giftToReceive);
                            kidBudget -= giftPrice;
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * Method which mainly add new kids each year to the database
     */
    private static void addNewKids(final ArrayList<Child> originalKids,
                                  final ArrayList<Child> newKids) {
        originalKids.addAll(newKids);
    }

    /**
     * Method for adding new unique gifts in santa's previous list
     * (It will also sort according to the price tag in ascending order)
     */
    private static void addNewGifts(final ArrayList<Gift> newGifts,
                                   final ArrayList<Gift> originalGifts) {
        ArrayList<Gift> uniqueNewGifts = new ArrayList<>();

        for (Gift gift : newGifts) {
            if (!uniqueNewGifts.contains(gift)) {
                uniqueNewGifts.add(gift);
            }
        }

        for (Gift newGift : uniqueNewGifts) {
            if (!originalGifts.contains(newGift)) {
                originalGifts.add(newGift);
            }
        }

        originalGifts.sort(Comparator.comparing(Gift::getPrice));
    }

    /**
     * Method which is used to update a child's niceScore and/or
     * add new gift preferences to their old list
     */
    private static void updateKidsData(final ArrayList<Child> kidsList,
                                      final ArrayList<ChildUpdate> kidsUpdates) {
        for (ChildUpdate kidsUpdate : kidsUpdates) {
            int idToSearch = kidsUpdate.getChildId();

            for (Child child : kidsList) {
                int kidId = child.getId();

                if (kidId == idToSearch) {
                    Double changedScore = kidsUpdate.getChangedNiceScore();

                    if (changedScore != null) {
                        child.getNiceScoreHistory().add(changedScore);
                    }

                    if (kidsUpdate.getNewGiftsPreferences().size() != 0) {
                        List<String> uniqueNewPref = new ArrayList<>();

                        for (String p : kidsUpdate.getNewGiftsPreferences()) {
                            if (!uniqueNewPref.contains(p)) {
                                uniqueNewPref.add(p);
                            }
                        }

                        List<String> changedPreferences =
                                new ArrayList<>(uniqueNewPref);
                        int sizeOrigPref = child.getGiftsPreferences().size();

                        for (int k = 0; k < sizeOrigPref; k++) {
                            String preference = child.getGiftsPreferences().get(k);

                            if (!changedPreferences.contains(preference)) {
                                changedPreferences.add(preference);
                            }
                        }

                        child.setGiftsPreferences(changedPreferences);
                    }
                }
            }
        }
    }

    /**
     * Method used for eliminating a young adult from santa's
     * children list once their age is over (18)
     */
    private static void eliminateYoungAdults(final ArrayList<Child> yearlyKidsList) {
        for (int i = yearlyKidsList.size() - 1; i >= 0; i--) {
            int kidAge = yearlyKidsList.get(i).getAge();

            if (kidAge > Constants.YOUNG_ADULT_AGE) {
                yearlyKidsList.remove(i);
            }
        }
    }

    /**
     * Method with main functionality to calculate the weighted average
     * for the age group represented by postteens/almost young adults (12-18)
     */
    private static Double weightedAverage(final Child postTeen) {
        int sizeHistory = postTeen.getNiceScoreHistory().size();
        Double sum = 0.0;
        Double indexSum = 0.0;

        for (int j = 0; j < sizeHistory; j++) {
            Double score = postTeen.getNiceScoreHistory().get(j);
            indexSum = indexSum + (j + 1.0);

            if (score != null) {
                score = score * (j + 1.0);
                sum += score;
            }
        }

        Double average = 0.0;
        average = sum / indexSum;
        return average;
    }

    /**
     * Method with main functionality to calculate teh normal average
     * for the age group represented by preteens (5-12)
     */
    private static Double normalAverage(final Child preTeen) {
        int sizeHistory = preTeen.getNiceScoreHistory().size();
        Double sum = 0.0;

        for (int j = 0; j < sizeHistory; j++) {
            Double score = preTeen.getNiceScoreHistory().get(j);

            if (score != null) {
                sum += score;
            }
        }

        Double average = 0.0;
        average = sum / sizeHistory;
        return average;
    }

    /**
     * Method with the functionality of calculating the average score
     * of each child based on the age group they are part of in the
     * current year
     */
    private static void calculateAverage(final ArrayList<Child> yearlyKidsList) {
        for (Child child : yearlyKidsList) {
            int kidAge = child.getAge();

            if (kidAge < Constants.BABY_AGE) {
                child.setAverageScore(Constants.BABY_SCORE);

            } else if (kidAge < Constants.TEEN_AGE) {
                Child preTeen = child;
                preTeen.setAverageScore(normalAverage(preTeen));

            } else if (kidAge <= Constants.YOUNG_ADULT_AGE) {
                Child postTeen = child;
                postTeen.setAverageScore(weightedAverage(postTeen));
            }
        }
    }
}
