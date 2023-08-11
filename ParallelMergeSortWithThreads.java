import java.util.Arrays;

public class ParallelMergeSortWithThreads {

    private static final int THREAD_COUNT = 8; // Adjust as needed

    public static void main(String[] args) {
        try {
            int[] inputArray = { 11, 5, 2, 12, 9, 14, 1, 15, 5, 6, 8, 3, 10, 13, 20 };
            parallelMergeSort(inputArray);
            System.out.println("Sorted Array: " + Arrays.toString(inputArray));
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void parallelMergeSort(int[] arr) {
        int[] aux = new int[arr.length];
        parallelMergeSort(arr, aux, 0, arr.length - 1, THREAD_COUNT);
    }

    private static void parallelMergeSort(int[] arr, int[] aux, int low, int high, int threadCount) {
        if (threadCount <= 1) {
            mergeSort(arr, aux, low, high);
            return;
        }

        int mid = (low + high) / 2;

        Thread leftThread = new Thread(() -> parallelMergeSort(arr, aux, low, mid, threadCount / 2));
        Thread rightThread = new Thread(() -> parallelMergeSort(arr, aux, mid + 1, high, threadCount / 2));

        leftThread.start();
        rightThread.start();

        try {
            leftThread.join();
            rightThread.join();
        } catch (InterruptedException e) {
            System.err.println("Thread execution interrupted: " + e.getMessage());
            e.printStackTrace();
        }

        merge(arr, aux, low, mid, high);
    }

    private static void mergeSort(int[] arr, int[] aux, int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSort(arr, aux, low, mid);
            mergeSort(arr, aux, mid + 1, high);
            merge(arr, aux, low, mid, high);
        }
    }

    private static void merge(int[] arr, int[] aux, int low, int mid, int high) {
        int n1 = mid - low + 1;
        int n2 = high - mid;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        for (int i = 0; i < n1; i++) {
            leftArray[i] = arr[low + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0, k = low;

        // Merge the two subarrays
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                arr[k++] = leftArray[i++];
            } else {
                arr[k++] = rightArray[j++];
            }
        }

        // Copy remaining elements from left and right subarrays
        while (i < n1) {
            arr[k++] = leftArray[i++];
        }
        while (j < n2) {
            arr[k++] = rightArray[j++];
        }
    }
}
