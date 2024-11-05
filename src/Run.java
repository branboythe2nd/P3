
public class Run {

    private Record[] records;
    private Record lastElement;
    private Record currentRec;
    private int currentPos;
    
    public Run(Record[] input) {
        setRecords(input);
        lastElement = input[input.length - 1];
    }

    public Record[] getRecords() {
        return records;
    }

    public void setRecords(Record[] record) {
        records = record;
    }

    public Record getLastElement() {
        return lastElement;
    }

    public void setLastElement(Record lastEle) {
        lastElement = lastEle;
    }

    public Record getCurrentRec() {
        return currentRec;
    }

    public void setCurrentRec(Record current) {
        currentRec = current;
    }
    
    public void setCurrentPos(int pos) {
        currentPos = pos;
    }
    
    public int getCurrentPos() {
        return currentPos;
    }
}
