import java.io.IOException;
import java.util.Random;
import student.TestCase;

/**
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class ExternalsortTest extends TestCase {

    /**
     * set up for tests
     */
    public void setUp() {
        // nothing to set up.
    }

    /* *//**
          * Tests the main method for External Sort
          * 
          * @throws IOException
          *//*
             * public void testExternalsort() throws IOException {
             * Random rand = new Random();
             * ByteFile file = new ByteFile("text.bin", 8);
             * file.writeRandomRecords(rand);
             * String[] args = { "text.bin" };
             * Externalsort.main(args);
             * 
             * }
             */


    /**
     * Tests the main method for External Sort
     * 
     * @throws IOException
     */
    public void testExternalsort2() throws IOException {
        String[] args = { "sampleInput16_sorted.bin" };
        Externalsort.main(args);

    }

}
