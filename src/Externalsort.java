import java.io.*;
import java.nio.ByteBuffer;
import java.util.Random;

/**
 * External Sort of heaps
 */

/**
 * The class containing the main method.
 *
 * @author Brantson and Adarsh
 * @version 10/23/2024
 */

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

public class Externalsort {

    private static final int RECORD_SIZE = 16;
    private static final int BLOCK_SIZE = 512;
    private static final int BLOCK_BYTE_SIZE = BLOCK_SIZE * RECORD_SIZE;
    private static Record[] records;
    private static int totalRecords;

    /**
     * @param args
     *            Command line parameters
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        totalRecords = 0;
        records = new Record[BLOCK_BYTE_SIZE];
        String filePath = args[0];
        readBinaryFile(filePath);
        SelectionSort sortedArray = new SelectionSort(records);
        System.out.println(totalRecords);
        records = sortedArray.externalSort();
        System.out.println(sortedArray.getRunList());
        String result = "";
        for (int i = 0; i < BLOCK_BYTE_SIZE; i+=BLOCK_SIZE) {
            result += records[i] + "\n";
        }
        System.out.println(result);
    }


    /**
     * Reads a binary file containing blocks of records.
     *
     * @param filePath The path to the .bin file to read.
     */
    public static void readBinaryFile(String filePath) {
        File file = new File(filePath);
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] blockBuffer = new byte[BLOCK_BYTE_SIZE];

            int bytesRead;

            while ((bytesRead = fis.read(blockBuffer)) != -1) {
                if (bytesRead < BLOCK_BYTE_SIZE) {
                    System.out.println(
                        "Partial block read. File may be incomplete.");
                }
                processBlock(blockBuffer, bytesRead);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Processes a block of records from the given byte array.
     * 
     * @param blockBuffer The byte array containing the block of data.
     * @param bytesRead   The number of bytes read into the blockBuffer.
     */
    private static void processBlock(byte[] blockBuffer, int bytesRead) {
        int numRecords = bytesRead / RECORD_SIZE;
        for (int i = 0; i < numRecords; i++) {
            int recordStart = i * RECORD_SIZE;
            byte[] record = new byte[RECORD_SIZE];

            System.arraycopy(blockBuffer, recordStart, record, 0, RECORD_SIZE);

            records[totalRecords] = processRecord(record);
            totalRecords++;
        }
    }


    /**
     * Processes the byte block and make it into a record
     * 
     * @param record
     *            the byte block that will be split
     */
    private static Record processRecord(byte[] record) {
        ByteBuffer byteBuff = ByteBuffer.wrap(record, 0, 8);
        long id = byteBuff.getLong();
        byteBuff = ByteBuffer.wrap(record, 8, 8);
        double key = byteBuff.getDouble();
        return new Record(id, key);
    }
    

}
