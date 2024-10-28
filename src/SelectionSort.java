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
        inputBuffer = new Record[512];
        outBuffer = new Record[512];
        input = inputArray;
        output = new Record[input.length];
        curr = 0;
        outSize = 0;
        runs = 0;
    }

    private Record[] populate() {
        int i = 0;
        while (i < inputBuffer.length && curr < input.length) {
            inputBuffer[i] = input[curr];
            curr++;
            i++;
        }
        return inputBuffer;
    }

    public void initialLoad() {
        int count = 0;
        while (count < 8 && curr < input.length) {
            inputBuffer = populate();
            for (Record r : inputBuffer) {
                if (r != null) {
                    heap.insert(r);
                }
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

                if (inputBuffer[i] != null) {
                    heap.insert(inputBuffer[i]);
                }
            }

            for (Record r : outBuffer) {
                if (r != null && outSize < output.length) {
                    output[outSize] = r;
                    outSize++;
                }
            }
            outBuffer = new Record[512]; 
        }

        
        while (heap.heapSize() > 0 && outSize < output.length) {
            output[outSize] = heap.removeMin();
            outSize++;
        }
        //System.out.println(outSize);
    }

    
}
