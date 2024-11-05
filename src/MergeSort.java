public class MergeSort {
    private MinHeap<Record> heap;
    private DLList<Record[]> runList;
    private Record[] inputBuffer;
    private Record[] outBuffer;
    private Record[] input;
    private Record[] output;
    private int curr;
    private int outSize;
    private int buffer;
    
    // Constructor: Initializes the run list based on the indexes of sorted runs.
    public MergeSort(DLList<Integer> indexes, Record[] inputArray) {
        runList = new DLList<>();
        curr = 0;
        for (int i = 0; i < indexes.size(); i++) {
            Record[] run = new Record[indexes.get(i) - curr];
            System.arraycopy(inputArray, curr, run, 0, indexes.get(i) - curr);
            runList.add(run);
            curr += indexes.get(i) - curr;
        }
        // Adding the last segment as a run
        Record[] run = new Record[inputArray.length - curr];
        System.arraycopy(inputArray, curr, run, 0, inputArray.length - curr);
        runList.add(run);
        curr += inputArray.length - curr;
    }

    // Populate initial records from each run to the MinHeap
    public void initialLoad() {
        heap = new MinHeap<>(new Record[runList.size()], 0, runList.size());
        for (int i = 0; i < runList.size(); i++) {
            Record[] run = runList.get(i);
            if (run.length > 0) {
                heap.insert(run[0]); // Insert the first record of each run into the heap
                run[0] = null; // Mark this as consumed in the run
            }
        }
    }

    // The main multiway merge process using the initialized heap and runs
    public Record[] mergeSort() {
        initialLoad(); // Populate the heap with the first record from each run
        outBuffer = new Record[512]; // Output buffer to accumulate sorted records
        int outIndex = 0;

        while (heap.heapSize()>0) {
            Record smallest = heap.removeMin(); // Get the smallest record
            outBuffer[outIndex++] = smallest; // Add to output buffer

            if (outIndex == outBuffer.length) {
                // Output buffer is full, flush it out
                flushOutputBuffer();
                outIndex = 0; // Reset output buffer index
            }

            // Fetch the next record from the respective run of the smallest record
            Record[] currentRun = findRunForRecord(smallest);
            Record nextRecord = getNextRecord(currentRun);
            if (nextRecord != null) {
                heap.insert(nextRecord); // Insert the next record from this run into the heap
            }
        }

        // Flush any remaining records in the output buffer
        if (outIndex > 0) {
            flushOutputBuffer(outIndex);
        }

        return output; // Assuming output holds the fully merged result
    }

    // Helper method to flush the output buffer to the final output array or file
    private void flushOutputBuffer() {
        System.arraycopy(outBuffer, 0, output, buffer, outBuffer.length);
        buffer += outBuffer.length;
    }

    // Overloaded method to handle partial flush when buffer isn't full
    private void flushOutputBuffer(int count) {
        System.arraycopy(outBuffer, 0, output, buffer, count);
        buffer += count;
    }

    // Helper method to find the run from which the given record originated
    private Record[] findRunForRecord(Record record) {
        for (Record[] run : runList) {
            for (Record rec : run) {
                if (rec != null && rec.equals(record)) {
                    return run;
                }
            }
        }
        return null;
    }

    // Helper method to get the next record from a given run and mark it as consumed
    private Record getNextRecord(Record[] run) {
        for (int i = 0; i < run.length; i++) {
            if (run[i] != null) {
                Record nextRecord = run[i];
                run[i] = null; // Mark this as consumed
                return nextRecord;
            }
        }
        return null; // No more records in this run
    }
}
