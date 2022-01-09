package database;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import common.Constants;
import lombok.SneakyThrows;

/**
 * Class designed to handle the flux of inputted data
 * from the tests along with its distribution in objects
 * with which the santa's activity and Christmas work will be
 * monitored + saved for display
 */
public class Service {
    public Service() {

    }

    /**
     * Method which simply reorientates the flow of the program,
     * forwarding the data to be altered with the anual updates
     */
    public void dataManagement(final String testId,
                               final String testPath) throws JsonProcessingException {
        Database database = Database.getInstance();
        insertData(testPath, database);
        Management.annualChangesManagement(testId, database);
    }

    /**
     * Main Method redirecting the insertion of the given information
     * to the database, where all the changes will take place
     */
    @SneakyThrows
    private void insertData(final String testPath, final Database database) {
        JsonObject fileObject = (JsonObject) new JsonParser().parse(new FileReader(testPath));

        int numberOfYears = fileObject.get(Constants.NUMBER_OF_YEARS).getAsInt();
        database.setNumberOfYears(numberOfYears);

        Double santaBudget = fileObject.get(Constants.SANTA_BUDGET).getAsDouble();
        database.setSantaBudget(santaBudget);

        JsonObject initialDataBody = (JsonObject) fileObject.get(Constants.INITIAL_DATA);
        JsonArray childrenBody = (JsonArray) initialDataBody.get(Constants.CHILDREN_LIST);
        JsonArray giftsBody = (JsonArray) initialDataBody.get(Constants.SANTA_GIFTS_LIST);

        insertBaseData(giftsBody, childrenBody, database);

        JsonArray annualChangesBody = (JsonArray) fileObject.get(Constants.ANNUAL_CHANGES_LIST);
        insertAnnualChanges(annualChangesBody, database);
    }

    /**
     * Method allocating memory for the child data to further store
     * it for future alterations
     */
    private ArrayList<Child> createChildDataList(final JsonArray childrenBody) {
        ArrayList<Child> initialList = new ArrayList<>();

        for (int i = 0; i < childrenBody.size(); i++) {
            Child child = new Child();
            JsonObject childData = childrenBody.get(i).getAsJsonObject();

            int id = childData.get(Constants.ID).getAsInt();
            String lastName = childData.get(Constants.LAST_NAME).getAsString();
            String firstName = childData.get(Constants.FIRST_NAME).getAsString();
            int age = childData.get(Constants.AGE).getAsInt();
            String city = childData.get(Constants.CITY).getAsString();
            Double niceScore = childData.get(Constants.NICE_SCORE).getAsDouble();

            List<String> preferenceList = new ArrayList<>();
            JsonArray categoryList = (JsonArray) childData.get(Constants.GIFTS_PREFERENCES_LIST);

            for (int j = 0; j < categoryList.size(); j++) {
                String category;
                category = categoryList.get(j).getAsString();
                preferenceList.add(category);
            }

            List<Double> niceScoreList = new ArrayList<>();
            niceScoreList.add(niceScore);

            child.setId(id);
            child.setLastName(lastName);
            child.setFirstName(firstName);
            child.setAge(age);
            child.setCity(city);
            child.setAverageScore(niceScore);
            child.setNiceScoreHistory(niceScoreList);
            child.setGiftsPreferences(preferenceList);

            initialList.add(child);
        }

        return initialList;
    }

    /**
     * Method which handles the insertion of data regarding the
     * original state of santa's gift list but also formatting the gifts
     * from the updates section
     */
    private ArrayList<Gift> createGiftDataList(final JsonArray giftsBody) {
        ArrayList<Gift> initialList = new ArrayList<>();

        for (int i = 0; i < giftsBody.size(); i++) {
            Gift gift = new Gift();
            JsonObject giftData = giftsBody.get(i).getAsJsonObject();

            String productName = giftData.get(Constants.PRODUCT_NAME).getAsString();
            Double price = giftData.get(Constants.PRICE).getAsDouble();
            String category = giftData.get(Constants.CATEGORY).getAsString();

            gift.setProductName(productName);
            gift.setPrice(price);
            gift.setCategory(category);

            initialList.add(gift);
        }

        return initialList;
    }

    /**
     * Method used for inserting the start input data on which
     * all the updates will be made on later on
     */
    private void insertBaseData(final JsonArray giftsBody,
                                final JsonArray childrenBody, final Database database) {
        ArrayList<Child> initialChildList = createChildDataList(childrenBody);
        ArrayList<Gift> initialGiftList = createGiftDataList(giftsBody);

        InitialData initialData = new InitialData();
        initialData.setChildrenList(initialChildList);
        initialData.setGiftsList(initialGiftList);

        database.setInitialData(initialData);
    }

    /**
     * Method to retrieve the format of the future children updates
     * regarding their history and new gift preferences
     */
    private ArrayList<ChildUpdate> createCDUpdatesList(final JsonArray childrenUpdatesBody) {
        ArrayList<ChildUpdate> initialList = new ArrayList<>();

        for (int i = 0; i < childrenUpdatesBody.size(); i++) {
            ChildUpdate childUpdate = new ChildUpdate();
            JsonObject childData = childrenUpdatesBody.get(i).getAsJsonObject();

            int id = childData.get(Constants.ID).getAsInt();

            Double niceScore = null;
            if (!childData.get(Constants.NICE_SCORE).isJsonNull()) {
                niceScore = childData.get(Constants.NICE_SCORE).getAsDouble();
            }

            List<String> preferenceList = new ArrayList<>();
            JsonArray categoryList = (JsonArray) childData.get(Constants.GIFTS_PREFERENCES_LIST);

            for (int j = 0; j < categoryList.size(); j++) {
                String category;
                category = categoryList.get(j).getAsString();
                preferenceList.add(category);
            }

            childUpdate.setChildId(id);
            childUpdate.setChangedNiceScore(niceScore);
            childUpdate.setNewGiftsPreferences(preferenceList);

            initialList.add(childUpdate);
        }

        return initialList;
    }

    /**
     * Method used for storing the changes that will need to be
     * applied over the years to the base original data
     */
    private void insertAnnualChanges(final JsonArray annualChangesBody,
                                     final Database database) {
        ArrayList<AnnualChanges> initialList = new ArrayList<>();

        for (int i = 0; i < annualChangesBody.size(); i++) {
            AnnualChanges change = new AnnualChanges();

            JsonObject changeData = annualChangesBody.get(i).getAsJsonObject();

            Double newBudget = changeData.get(Constants.NEW_SANTA_BUDGET).getAsDouble();

            JsonArray newGifts = (JsonArray) changeData.get(Constants.NEW_SANTA_GIFTS_LIST);
            ArrayList<Gift> newGiftsList = new ArrayList<>();

            if (newGifts.size() != 0) {
                newGiftsList = createGiftDataList(newGifts);
            }

            JsonArray newChildren = (JsonArray) changeData.get(Constants.NEW_CHILDREN);
            ArrayList<Child> newChildrenList = new ArrayList<>();

            if (newChildren.size() != 0) {
                newChildrenList = createChildDataList(newChildren);
            }

            JsonArray childrenUpdates = (JsonArray) changeData.get(Constants.CHILDREN_UPDATES_LIST);
            ArrayList<ChildUpdate> childrenUpdatesList = new ArrayList<>();

            if (childrenUpdates.size() != 0) {
                childrenUpdatesList = createCDUpdatesList(childrenUpdates);
            }

            change.setNewSantaBudget(newBudget);
            change.setNewGifts(newGiftsList);
            change.setNewChildren(newChildrenList);
            change.setChildrenUpdates(childrenUpdatesList);

            initialList.add(change);
        }

        database.setAnnualChanges(initialList);
    }
}
