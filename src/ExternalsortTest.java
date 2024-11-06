import java.io.IOException;
import java.util.Random;
import student.TestCase;

/**
 * @author Brantson and Adarsh
 * @version 10/29/2024
 */
public class ExternalsortTest extends TestCase {

    /**
     * set up for tests
     */
    public void setUp() {
        // nothing to set up.
    }


    /**
     * Tests the main method for External Sort
     * 
     * @throws IOException
     */
    public void testExternalsort() throws IOException {
        Random rand = new Random();
        ByteFile file = new ByteFile("text.bin", 64);
        file.writeRandomRecords(rand);
        String[] args = { "text.bin" };
        Externalsort.main(args);

    }
}
