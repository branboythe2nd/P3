
/**
 * The Run class represents a run in the merge sort algorithm,
 * containing a series of records and managing the current position and elements
 * of the run during the merge process.
 * 
 * @author Brantson and Adarsh
 * @version 11/06/2024
 */
public class Run {

    private Record[] records;
    private Record lastElement;
    private Record currentRec;
    private int currentPos;

    /**
     * Constructs a Run with a specified array of records.
     * The last element of the array is stored for easy access.
     *
     * @param input
     *            the array of records representing the run
     */
    public Run(Record[] input) {
        setRecords(input);
        lastElement = input[input.length - 1];
    }


    /**
     * Returns the array of records in this run.
     *
     * @return the array of Record objects in the run
     */
    public Record[] getRecords() {
        return records;
    }


    /**
     * Sets the array of records in this run.
     *
     * @param record
     *            the array of Record objects to set as this run's records
     */
    public void setRecords(Record[] record) {
        records = record;
    }


    /**
     * Returns the last element in this run, as defined during initialization.
     *
     * @return the last Record in the run
     */
    public Record getLastElement() {
        return lastElement;
    }


    /**
     * Sets the last element in this run.
     *
     * @param lastEle
     *            the Record to set as the last element of the run
     */
    public void setLastElement(Record lastEle) {
        lastElement = lastEle;
    }


    /**
     * Returns the current record in the run, representing the most recently
     * accessed element.
     *
     * @return the current Record in the run
     */
    public Record getCurrentRec() {
        return currentRec;
    }


    /**
     * Sets the current record in the run, usually the last accessed or inserted
     * record.
     *
     * @param current
     *            the Record to set as the current record
     */
    public void setCurrentRec(Record current) {
        currentRec = current;
    }


    /**
     * Sets the current position within the run, typically pointing to the next
     * record to be accessed.
     *
     * @param pos
     *            the integer position to set as the current position in the run
     */
    public void setCurrentPos(int pos) {
        currentPos = pos;
    }


    /**
     * Returns the current position within the run.
     *
     * @return the integer index representing the current position in the run
     */
    public int getCurrentPos() {
        return currentPos;
    }
}
