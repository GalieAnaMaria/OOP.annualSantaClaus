package database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Constants;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class used to translate the children's information
 * and be displayed in a .json format
 */
public final class WriteData {
    private WriteData() {

    }

    /**
     * Method where the files are created for displaying the
     * output data at the end of all the years/annual updates
     */
    public static void writeData(final String testId,
                                 final AnnualChildren memory) throws JsonProcessingException {
        String fullPath = Constants.OUTPUT_PATH.concat(testId);
        String fullPathFile = fullPath.concat(Constants.FILE_EXTENSION);

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(memory);

        try (FileWriter file = new FileWriter(fullPathFile)) {
            file.write(jsonStr);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
