public class MergeSort {
    private MinHeap<Record> heap;
    private DLList<Run> runList;
    private Record[] inputBuffer;
    private Record[] outBuffer;
    private Record[] input;
    private Record[] output;
    private int[] positions;
    private int curr;

    public MergeSort(DLList<Integer> indexes, Record[] inputArray) {
        input = inputArray;
        output = new Record[inputArray.length];
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
    }


    public void initialLoad() {
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
            }
        }
        curr = count;
        for (int i = 0; i < positions.length; i++) {
            runList.get(i).setCurrentRec(runList.get(i)
                .getRecords()[positions[i] - 1]);
            runList.get(i).setCurrentPos(positions[i]);
        }
        heap.buildHeap();
    }

    private Record[] populate(Run run) {
        inputBuffer = new Record[512];
        for (int i = 0; i < inputBuffer.length && run.getCurrentPos() < run
            .getRecords().length; i++) {
            inputBuffer[i] = run.getRecords()[run.getCurrentPos()];
            run.setCurrentPos(run.getCurrentPos() + 1);
        }
        return inputBuffer;
    }

    public Record[] mergeSort() {
        initialLoad();
        while (curr < input.length) {
            for (int i = 0; i < outBuffer.length && heap.heapSize() > 0
                && inputBuffer[i] != null; i++) {
                outBuffer[i] = heap.getRoot();
                if (isLastElement(outBuffer[i])) {
                    if(runOver(outBuffer[i])) {
                        
                    }
                }
            }
        }
        return null;
    }
    
    private boolean isLastElement(Record rec) {
        for (Run run : runList) {
            if(rec.compareTo(run.getCurrentRec()) == 0) {
                return true;
            }
        }
        return false;
    }
    
    private boolean runOver(Record rec) {
        for (Run run : runList) {
            if(rec.compareTo(run.getLastElement()) == 0) {
                return true;
            }
        }
        return false;
    }

}
