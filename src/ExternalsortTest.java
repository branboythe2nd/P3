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
        //nothing to set up.
    }
    
    /**
     * T
     * @throws IOException 
     */
    public void testExternalsort() throws IOException {
        Random rand = new Random();
        ByteFile file = new ByteFile("text.bin", 8);
        file.writeRandomRecords(rand);
        String[] args = {"sampleInput16.bin"};
        Externalsort.main(args);
        
    }

}
