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
            for (int i = 0; i < outBuffer.length && heap.heapSize() > 0; i++) {
                outBuffer[i] = heap.removeMin();
                if (inputBuffer[i] != null) {
                    heap.insert(inputBuffer[i]);
                }
                heap.buildHeap();
                if (heap.getRoot().compareTo(outBuffer[i]) < 0) {
                    heap.remove(0);
                }
                buffer = i;
            }

            for (Record r : outBuffer) {
                output[outSize] = r;
                outSize++;
            }
            outBuffer = new Record[512];

            if (heap.heapSize() == 0) {
                if (buffer != inputBuffer.length) {
                    curr = curr - (inputBuffer.length - buffer);
                }
                heap.setHeapSize(4096);
                heap.buildHeap();
                getRunList().add(outSize - 1);
                //System.out.println("Heap is 0");
            }
        }
        heap.setHeapSize(4096);
        while (heap.heapSize() > 0) {
            output[outSize] = heap.removeMin();
            heap.buildHeap();
            outSize++;
        }
        //System.out.println(outSize);
        return output;
        

    }


    public DLList<Integer> getRunList() {
        return runList;
    }


    public void setRunList(DLList<Integer> runList) {
        this.runList = runList;
    }
}
