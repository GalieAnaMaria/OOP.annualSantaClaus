package main;

import checker.Checker;
import com.fasterxml.jackson.core.JsonProcessingException;
import common.Constants;
import database.Service;
import java.io.File;
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
    public static void main(final String[] args) throws JsonProcessingException {

        Checker.calculateScore();
    }
}
