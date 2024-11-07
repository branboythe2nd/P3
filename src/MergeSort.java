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

    public MergeSort(DLList<Integer> indexes, Record[] inputArray) {
        //System.out.println(indexes.size() + 1);
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


    private Record[] populate(Run run) {
        inputBuffer = new Record[512];
        int count = 0;
        for (int i = 0; i < inputBuffer.length && run.getCurrentPos() < run
            .getRecords().length; i++) {
            inputBuffer[i] = run.getRecords()[run.getCurrentPos()];
            run.setCurrentPos(run.getCurrentPos() + 1);
            count++;
        }
        run.setCurrentRec(run.getRecords()[run.getCurrentPos() - 1]);
        curr += count;
        return inputBuffer;
    }


    private DLList<Run> mergeHelper(DLList<Run> runs) {
        if (runs.size() == 1) {
            return runs;
        }
        initialLoad();
        int total = 0;
        int size = 0;
        int rand = 0;
        if (runList.size() >= 8) {
            rand = 8;
        }
        else {
            rand = runList.size();
        }
        for (int i = 0; i < rand; i++) {
            if (runs.get(i) != null) {
                total += runs.get(i).getRecords().length;
            }
        }
        Record[] temp = new Record[total];
        System.out.println(total);
        while (curr < total) {
            for (int i = 0; i < outBuffer.length; i++) {
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


    public Record[] mergeSort() {
        runList = mergeHelper(runList);
        return runList.get(0).getRecords();
    }


    private int isLastElement(Record rec) {
        int count = 0;
        for (Run run : runList) {

            if (rec.compareTo(run.getCurrentRec()) == 0) {
                return count;
            }
            count++;
        }
        return -1;
    }


    private boolean runOver(Record rec) {

        for (Run run : runList) {
            if (rec.compareTo(run.getLastElement()) == 0) {
                return true;
            }
        }

        return false;
    }

}
