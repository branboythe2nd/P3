/**
 * The Merge sort class implements an multiway merge algorithm for the external
 * sort algorithm to handle
 * large data sets using a minimum heap and list of runs. This class manages
 * sorting
 * by breaking down data into smaller runs and then merging them using a heap.
 * 
 * @author Brantson and Adarsh
 * @version 11/06/2024
 */
public class MergeSort {

    private MinHeap<Record> heap;
    private DLList<Run> runList;
    private Record[] inputBuffer;
    private Record[] outBuffer;
    private Record[] input;
    private Record[] output;
    private int[] positions;
    private int curr;
    private int outSize;

    /**
     * Constructs a new {@code MergeSort} instance, initializing buffers, heap,
     * and
     * organizing input data into runs based on provided indexes.
     *
     * @param indexes
     *            the list of indices marking the end of each run in the input
     *            data
     * @param inputArray
     *            the input array of records to be sorted
     */
    public MergeSort(DLList<Integer> indexes, Record[] inputArray) {
        input = inputArray;
        output = new Record[inputArray.length];
        outBuffer = new Record[512];
        inputBuffer = new Record[512];
        heap = new MinHeap<>(new Record[4096], 0, 4096);
        runList = new DLList<>();
        curr = 0;
        for (int i = 0; i < indexes.size(); i++) {
            Record[] run = new Record[indexes.get(i) - curr];
            System.arraycopy(inputArray, curr, run, 0, indexes.get(i) - curr);
            Run r = new Run(run);
            runList.add(r);
            curr += indexes.get(i) - curr;
        }
        Record[] run = new Record[inputArray.length - curr];
        System.arraycopy(inputArray, curr, run, 0, inputArray.length - curr);
        Run r = new Run(run);
        runList.add(r);
        curr += inputArray.length - curr;
        outSize = 0;
    }


    /**
     * Performs the initial load of records into the heap from each run in
     * {@code runList},
     * populating the heap up to its capacity or until all runs are loaded.
     */
    public void initialLoad() {
        heap = new MinHeap<>(new Record[4096], 0, 4096);
        int count = 0;
        positions = new int[runList.size()];
        while (count < 4096) {
            for (int i = 0; i < runList.size(); i++) {
                Record[] array = runList.get(i).getRecords();
                for (int j = 0; j < 512 && positions[i] < array.length; j++) {
                    heap.insert(array[positions[i]]);
                    positions[i]++;
                    count++;
                    if (count >= 4096) {
                        break;
                    }
                }
                if (count >= 4096) {
                    break;
                }
            }
        }
        curr = count;
        for (int i = 0; i < positions.length; i++) {
            if (positions[i] != 0) {
                runList.get(i).setCurrentRec(runList.get(i)
                    .getRecords()[positions[i] - 1]);
                runList.get(i).setCurrentPos(positions[i]);
            }
        }
        heap.buildHeap();
    }


    /**
     * Populates the input buffer with records from the specified run until the
     * buffer
     * reaches its capacity or the run is exhausted.
     *
     * @param run
     *            the run to populate from
     * @return an array of records loaded from the run into the input buffer
     */
    private Record[] populate(Run run) {
        inputBuffer = new Record[512];
        int count = 0;
        for (int i = 0; i < inputBuffer.length && run.getCurrentPos() < run
            .getRecords().length && i < (4096 - heap.heapSize()); i++) {
            inputBuffer[i] = run.getRecords()[run.getCurrentPos()];
            run.setCurrentPos(run.getCurrentPos() + 1);
            count++;
        }
        run.setCurrentRec(run.getRecords()[run.getCurrentPos() - 1]);
        curr += count;
        return inputBuffer;
    }


    /**
     * Recursively merges runs by continually populating the heap and output
     * buffer,
     * creating a sorted run each time. Combines runs until a single sorted run
     * remains.
     *
     * @param runs
     *            the list of runs to merge
     * @return a single list of runs containing one sorted run
     */
    private DLList<Run> mergeHelper(DLList<Run> runs) {
        if (runs.size() == 1) {
            return runs;
        }
        initialLoad();
        int total = 0;
        int size = 0;
        int rand = (runList.size() >= 8) ? 8 : runList.size();
        for (int i = 0; i < rand; i++) {
            if (runs.get(i) != null) {
                total += runs.get(i).getRecords().length;
            }
        }
        Record[] temp = new Record[total];
        while (curr < total) {
            for (int i = 0; i < outBuffer.length && heap.heapSize() > 0; i++) {
                outBuffer[i] = heap.removeMin();
                int index = isLastElement(outBuffer[i]);
                if (index >= 0) {
                    if (runOver(outBuffer[i])) {
                        runs.remove(index);
                    }
                    else {
                        populate(runList.get(index));
                        for (int j = 0; j < inputBuffer.length
                            && inputBuffer[j] != null; j++) {
                            heap.insert(inputBuffer[j]);
                        }
                    }
                }
            }
            for (Record r : outBuffer) {
                if (r != null) {
                    temp[size] = r;
                    size++;
                }
            }
            outBuffer = new Record[512];
        }
        while (heap.heapSize() > 0) {
            temp[size] = heap.removeMin();
            int index = isLastElement(temp[size]);
            if (index >= 0) {
                if (runOver(temp[size])) {
                    runs.remove(index);
                }
                else {
                    populate(runList.get(index));
                    for (int j = 0; j < inputBuffer.length
                        && inputBuffer[j] != null; j++) {
                        heap.insert(inputBuffer[j]);
                    }
                }
            }
            size++;
        }
        Run run = new Run(temp);
        runs.add(run);
        return mergeHelper(runs);
    }


    /**
     * Performs the merge sort on the input data, merging runs until a single
     * sorted run
     * is obtained, which contains all sorted records.
     *
     * @return an array of sorted records
     */
    public Record[] mergeSort() {
        runList = mergeHelper(runList);
        return runList.get(0).getRecords();
    }


    /**
     * Checks if the specified record is the last element in any of the current
     * runs
     * and returns the index of the run it belongs to, or -1 if not found.
     *
     * @param rec
     *            the record to check
     * @return the index of the run containing the last element equal to
     *         {@code rec}, or -1 if not found
     */
    private int isLastElement(Record rec) {
        int count = 0;
        for (Run run : runList) {
            if (run.getCurrentRec() != null) {
                if (rec.compareTo(run.getCurrentRec()) == 0) {
                    return count;
                }
            }
            count++;
        }
        return -1;
    }


    /**
     * Determines if the specified record is the last element of its run.
     *
     * @param rec
     *            the record to check
     * @return {@code true} if {@code rec} is the last element in its run,
     *         otherwise {@code false}
     */
    private boolean runOver(Record rec) {
        for (Run run : runList) {
            if (rec.compareTo(run.getLastElement()) == 0) {
                return true;
            }
        }
        return false;
    }

}
