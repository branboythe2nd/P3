/**
 * The SelectionSort class implements the replacement selection for an external sorting algorithm using a min-heap to sort a large dataset.
 * It uses input and output buffers to manage records efficiently, processing chunks at a time.
 */
public class SelectionSort {
    private MinHeap<Record> heap;
    private DLList<Integer> runList;
    private Record[] inputBuffer;
    private Record[] outBuffer;
    private Record[] input;
    private Record[] output;
    private int curr;
    private int outSize;
    private int buffer;

    /**
     * Constructs a SelectionSort instance with a given array of records to be sorted.
     *
     * @param inputArray The array of records to sort
     */
    public SelectionSort(Record[] inputArray) {
        heap = new MinHeap<>(new Record[4096], 0, 4096);
        setRunList(new DLList<Integer>());
        inputBuffer = new Record[512];
        outBuffer = new Record[512];
        input = inputArray;
        output = new Record[input.length];
        curr = 0;
        outSize = 0;
        buffer = 0;
    }

    /**
     * Fills the input buffer with the next set of records from the input array.
     *
     * @return An array populated with records from the input array
     */
    private Record[] populate() {
        inputBuffer = new Record[512];
        for (int i = 0; i < inputBuffer.length && curr < input.length; i++) {
            inputBuffer[i] = input[curr];
            curr++;
        }
        return inputBuffer;
    }

    /**
     * Initializes the heap by loading the first 8 blocks (buffers) of records from the input array
     * into the heap and building the heap structure.
     */
    public void initialLoad() {
        int count = 0;
        while (count < 8) {
            inputBuffer = populate();
            for (Record r : inputBuffer) {
                heap.insert(r);
            }
            count++;
        }
        heap.buildHeap();
    }

    /**
     * Performs an external sort on the input records using the min-heap.
     * Populates the output array with sorted records and maintains a list of sorted runs.
     *
     * @return The sorted array of records
     */
    public Record[] externalSort() {
        initialLoad();
        while (curr < input.length) {
            inputBuffer = populate();
            for (int i = 0; i < outBuffer.length && heap.heapSize() > 0
                && inputBuffer[i] != null; i++) {
                outBuffer[i] = heap.getRoot();
                heap.modify(0, inputBuffer[i]);
                if (inputBuffer[i].compareTo(outBuffer[i]) < 0) {
                    heap.remove(0);
                }
                buffer = i;
            }

            for (Record r : outBuffer) {
                if (r != null) {
                    output[outSize] = r;
                    outSize++;
                }
            }
            outBuffer = new Record[512];

            if (heap.heapSize() == 0) {
                if (buffer != inputBuffer.length) {
                    curr = curr - (inputBuffer.length - buffer - 1);
                }
                heap.setHeapSize(4096);
                heap.buildHeap();
                getRunList().add(outSize - 1);
            }
        }
        if (heap.heapSize() > 0) {
            if (heap.heapSize() == 4096) {
                while (heap.heapSize() > 0) {
                    output[outSize] = heap.removeMin();
                    outSize++;
                }
            }
            else {
                int rest = 4096 - heap.heapSize();
                Record[] lastRecords = new Record[rest];
                System.arraycopy(heap.getHeap(), heap.heapSize(), lastRecords,
                    0, rest);
                MinHeap<Record> lastHeap = new MinHeap<>(lastRecords, rest,
                    rest);
                lastHeap.buildHeap();
                while (heap.heapSize() > 0) {
                    output[outSize] = heap.removeMin();
                    outSize++;
                }
                getRunList().add(outSize - 1);
                while (lastHeap.heapSize() > 0) {
                    output[outSize] = lastHeap.removeMin();
                    outSize++;
                }
            }
        }
        return output;

    }

    /**
     * Retrieves the list of indices where each sorted run ends. This list can be used for
     * external merging of sorted runs.
     *
     * @return The list of end indices of sorted runs
     */
    public DLList<Integer> getRunList() {
        return runList;
    }

    /**
     * Sets the list that tracks the end indices of each sorted run.
     *
     * @param runList The list to track sorted run end indices
     */
    public void setRunList(DLList<Integer> runList) {
        this.runList = runList;
    }
}
