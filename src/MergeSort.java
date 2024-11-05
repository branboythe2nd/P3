
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

    public MergeSort(DLList<Integer> indexes, Record[] inputArray) {
        runList = new DLList<Record[]>();
        curr = 0;
         for(int i = 0; i < indexes.size(); i++) {
             Record[] run = new Record[indexes.get(i) - curr];
             System.arraycopy(inputArray, curr, run, 0, indexes.get(i) - curr);
             runList.add(run);
             curr += indexes.get(i) - curr;
         }
         Record[] run = new Record[inputArray.length - curr];
         System.arraycopy(inputArray, curr, run, 0, inputArray.length - curr);
         runList.add(run);
         curr += inputArray.length - curr;
         System.out.println(runList.size());
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
        while (heap.heapSize() != 4096) {
            
        }
    }


    public Record[] mergeSort() {
        
        return null;
    }
}
