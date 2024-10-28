public class SelectionSort {
    private MinHeap<Record> heap;
    private Record[] inputBuffer;
    private Record[] outBuffer;
    private Record[] input;
    private Record[] output;
    private int curr;
    private int outSize;
    private int runs;

    public SelectionSort(Record[] inputArray) {
        heap = new MinHeap<>(new Record[4096], 0, 4096);
        inputBuffer = new Record[512];  // 1 block as per input size
        outBuffer = new Record[512];    // Buffer to store sorted output temporarily
        input = inputArray;
        output = new Record[input.length];
        curr = 0;
        outSize = 0;
        runs = 0;
    }

    private Record[] populate() {
        for (int i = 0; i < inputBuffer.length; i++) {
            inputBuffer[i] = input[curr];
            curr++;
        }
        return inputBuffer;
    }

    public void initialLoad() {
        int count = 0;
        while (count < 8 && curr < input.length) {
            inputBuffer = populate();
            for (Record r : inputBuffer) {
                heap.insert(r);
            }
            count++;
        }
        heap.buildHeap(); 
    }

    public void externalSort() {
        initialLoad();

        while (curr < input.length) {
            inputBuffer = populate(); 

            for (int i = 0; i < outBuffer.length && heap.heapSize() > 0; i++) {
                outBuffer[i] = heap.removeMin();

                if (i < inputBuffer.length) {
                    heap.insert(inputBuffer[i]);
                }
            }

            for (Record r : outBuffer) {
                output[outSize] = r;
                outSize++;
            }
            outBuffer = new Record[512]; 
        }


        while (heap.heapSize() > 0) {
            output[outSize] = heap.removeMin();
            outSize++;
        }
    }
}
