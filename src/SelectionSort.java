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


    private Record[] populate() {
        inputBuffer = new Record[512];
        for (int i = 0; i < inputBuffer.length && curr < input.length; i++) {
            inputBuffer[i] = input[curr];
            curr++;
        }
        return inputBuffer;
    }


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
// System.out.println(outSize);
// System.out.println(heap.heapSize());
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
// System.out.println("Heap is 0");
// System.out.println(outSize);
                while (lastHeap.heapSize() > 0) {
                    output[outSize] = lastHeap.removeMin();
                    outSize++;
                }
// System.out.println(outSize);
// System.out.println(lastHeap.heapSize());
            }
        }
        System.out.println(runList.size());
        return output;

    }


    public DLList<Integer> getRunList() {
        return runList;
    }


    public void setRunList(DLList<Integer> runList) {
        this.runList = runList;
    }
}
