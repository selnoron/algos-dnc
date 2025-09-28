package main.java.algos;

import java.util.Arrays;

public class Deterministic_Select {
    private static long comparisons = 0; // количество сравнений
    private static int maxDepth = 0;     // максимальная глубина рекурсии

    // Главный метод: найти k-й элемент (0-based)
    public static int select(int[] arr, int k) {
        return select(arr, 0, arr.length - 1, k, 0);
    }

    private static int select(int[] arr, int left, int right, int k, int depth) {
        maxDepth = Math.max(maxDepth, depth);

        // если маленький массивв → сортировка и возврат k-го
        if (right - left + 1 <= 5) {
            insertionSort(arr, left, right);
            return arr[left + k];
        }

        // шаг 1: делим на группы по 5 и ищем медианы
        int numGroups = (int) Math.ceil((right - left + 1) / 5.0);
        int[] medians = new int[numGroups];

        for (int i = 0; i < numGroups; i++) {
            int gLeft = left + i * 5;
            int gRight = Math.min(gLeft + 4, right);

            insertionSort(arr, gLeft, gRight);
            medians[i] = arr[gLeft + (gRight - gLeft) / 2]; // медиана группы
        }

        // шаг 2: рекурсивно находим медиану медиан
        int medianOfMedians = select(medians, 0, numGroups - 1, numGroups / 2, depth + 1);

        // шаг 3: partition вокруг медианы медиан
        int pivotIndex = partition(arr, left, right, medianOfMedians);

        int length = pivotIndex - left;
        if (k == length) {
            return arr[pivotIndex];
        } else if (k < length) {
            return select(arr, left, pivotIndex - 1, k, depth + 1);
        } else {
            return select(arr, pivotIndex + 1, right, k - length - 1, depth + 1);
        }
    }

    // partition (in-place)
    private static int partition(int[] arr, int left, int right, int pivot) {
        int i = left;
        for (int j = left; j <= right; j++) {
            comparisons++;
            if (arr[j] < pivot) {
                swap(arr, i, j);
                i++;
            }
        }

        // ставим pivot в позицию
        int pivotIndex = i;
        for (int j = i; j <= right; j++) {
            if (arr[j] == pivot) {
                swap(arr, j, pivotIndex);
                break;
            }
        }
        return pivotIndex;
    }

    // сортировка вставками
    private static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= left) {
                comparisons++;
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = key;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // Тестовый запуск
    public static void main(String[] args) {
        int[] arr = {12, 3, 5, 7, 4, 19, 26};
        int k = 3; // ищем 4-й по величине элемент (0-based → 3-й индекс)

        int result = select(arr, k);

        System.out.println("Массив: " + Arrays.toString(arr));
        System.out.println(k + "-й элемент (0-based) / k-th element: " + result);
        System.out.println("Сравнений / Comparisons: " + comparisons);
        System.out.println("Максимальная глубина рекурсии / Max recursion depth: " + maxDepth);
    }
}
