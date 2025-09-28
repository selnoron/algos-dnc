package main.java.algos;

public class MergeSort {

    // Рекурсивная сортировка (основной алгоритм)
    private static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    // Слияние двух отсортированных половин
    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) arr[k++] = L[i++];
            else arr[k++] = R[j++];
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    // 🔹 Удобный метод для вызова в тестах
    public static void sort(int[] arr) {
        mergeSort(arr, 0, arr.length - 1);
    }

    // 🔹 Пример локального теста
    public static void main(String[] args) {
        int[] arr = {5, 2, 9, 1, 7};
        System.out.println("До сортировки:");
        for (int x : arr) System.out.print(x + " ");
        System.out.println();

        sort(arr);

        System.out.println("После сортировки:");
        for (int x : arr) System.out.print(x + " ");
    }
}
