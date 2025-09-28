package main.java.algos;

import java.util.Random;

public class QuickSort {

    private static int maxDepth;       // максимум глубины рекурсии
    private static int currentDepth;   // текущая глубина рекурсии

    // 🔹 Удобный метод сортировки (для тестов)
    public static void sort(int[] arr) {
        maxDepth = 0;
        currentDepth = 0;
        quickSort(arr, 0, arr.length - 1);
    }

    // Основной алгоритм QuickSort
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            currentDepth++;
            maxDepth = Math.max(maxDepth, currentDepth);

            int pivotIndex = partition(arr, low, high);

            // рекурсивно вызываем на меньшей части
            if (pivotIndex - low < high - pivotIndex) {
                quickSort(arr, low, pivotIndex - 1);
                quickSort(arr, pivotIndex + 1, high);
            } else {
                quickSort(arr, pivotIndex + 1, high);
                quickSort(arr, low, pivotIndex - 1);
            }

            currentDepth--;
        }
    }

    // Разделение массива вокруг pivot
    private static int partition(int[] arr, int low, int high) {
        Random rand = new Random();
        int pivotIndex = rand.nextInt(high - low + 1) + low;
        int pivot = arr[pivotIndex];
        swap(arr, pivotIndex, high);

        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    // Обмен элементов
    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 🔹 Вернуть глубину рекурсии
    public static int getMaxDepth() {
        return maxDepth;
    }

    // 🔹 Пример локального теста
    public static void main(String[] args) {
        int[] arr = {10, 7, 8, 9, 1, 5};
        System.out.println("До сортировки:");
        for (int x : arr) System.out.print(x + " ");
        System.out.println();

        sort(arr);

        System.out.println("После сортировки:");
        for (int x : arr) System.out.print(x + " ");
        System.out.println("\nМаксимальная глубина рекурсии: " + getMaxDepth());
    }
}
