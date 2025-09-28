package main.java.algos;

import java.util.*;

public class AlgoTester {

    // ---------- Helpers ----------
    // Генератор случайного массива
    static int[] randomArray(int n, int bound) {
        Random rand = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rand.nextInt(bound);
        return arr;
    }

    // Проверка, отсортирован ли массив
    static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) return false;
        }
        return true;
    }

    // ---------- Тест MergeSort & QuickSort ----------
    static void testSorts() {
        System.out.println("=== Тест MergeSort и QuickSort ===");
        int[] sizes = {10, 100, 1000, 10000};

        for (int n : sizes) {
            int[] arr1 = randomArray(n, 10000);
            int[] arr2 = Arrays.copyOf(arr1, arr1.length);

            // MergeSort
            MergeSort.sort(arr1);
            System.out.println("MergeSort n=" + n + " корректен? " + isSorted(arr1));

            // QuickSort
            QuickSort.sort(arr2);
            System.out.println("QuickSort n=" + n + " корректен? " + isSorted(arr2));
        }

        // Проверка глубины рекурсии для QuickSort
        int n = 10000;
        int[] arr = randomArray(n, 100000);
        QuickSort.sort(arr);
        int depth = QuickSort.getMaxDepth();
        System.out.println("QuickSort рекурсия: " + depth + " (ограничение ~2*log2(n))");
    }

    // ---------- Тест Deterministic Select ----------
    static void testSelect() {
        System.out.println("\n=== Тест Deterministic Select ===");
        Random rand = new Random();
        boolean allCorrect = true;

        for (int t = 0; t < 100; t++) {
            int[] arr = randomArray(50, 1000);
            int k = rand.nextInt(arr.length);

            int expected;
            int[] sorted = Arrays.copyOf(arr, arr.length);
            Arrays.sort(sorted);
            expected = sorted[k];

            int result = Deterministic_Select.select(arr.clone(), k);
            if (result != expected) {
                allCorrect = false;
                System.out.println("Ошибка на тесте " + t);
                break;
            }
        }
        System.out.println("Select корректен на 100 тестах? " + allCorrect);
    }

    // ---------- Тест Closest Pair ----------
    static void testClosestPair() {
        System.out.println("\n=== Тест Closest Pair ===");
        Random rand = new Random();

        // Маленькие n → сравнение с O(n^2)
        for (int n = 10; n <= 200; n *= 2) {
            Closest_Pair_of_Points.Point[] pts = new Closest_Pair_of_Points.Point[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new Closest_Pair_of_Points.Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
            }

            double fast = Closest_Pair_of_Points.closestPair(pts);

            // O(n^2) проверка
            double brute = Double.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    double dx = pts[i].x - pts[j].x;
                    double dy = pts[i].y - pts[j].y;
                    brute = Math.min(brute, Math.sqrt(dx * dx + dy * dy));
                }
            }

            if (Math.abs(fast - brute) > 1e-9) {
                System.out.println("Ошибка при n=" + n);
                return;
            }
        }
        System.out.println("Closest Pair корректен для маленьких n");

        // Большие n → только быстрая вверсия
        int n = 10000;
        Closest_Pair_of_Points.Point[] big = new Closest_Pair_of_Points.Point[n];
        for (int i = 0; i < n; i++) {
            big[i] = new Closest_Pair_of_Points.Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
        }
        double dist = Closest_Pair_of_Points.closestPair(big);
        System.out.println("Closest Pair для n=" + n + " -> " + dist);
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {
        testSorts();
        testSelect();
        testClosestPair();
    }
}
