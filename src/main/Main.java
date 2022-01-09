package main;

import checker.Checker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonSyntaxException;
import common.Constants;
import database.Service;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class used to run the code
 */
public final class Main {

    private Main() {
        ///constructor for checkstyle
    }
    /**
     * This method is used to call the checker which calculates the score
     * @param args
     *          the arguments used to call the main method
     */
    public static void main(final String[] args) throws JsonProcessingException,
            FileNotFoundException, JsonSyntaxException {
        File dir = new File("tests");
        File[] directoryListing = dir.listFiles();

        if (directoryListing != null) {
            for (File child : directoryListing) {
                Pattern p = Pattern.compile(Constants.FILE_NAME_PATTERN);
                Matcher m = p.matcher(child.getName());

                String numberExt = "";
                if (m.find()) {
                    numberExt = m.group(1);
                }

                Service input = new Service();
                String filePath = child.getPath();

                input.dataManagement(numberExt, filePath);
            }
        }

        Checker.calculateScore();
    }
}
