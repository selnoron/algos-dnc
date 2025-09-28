package main.java.algos;

import java.util.Arrays;
import java.util.Comparator;

public class Closest_Pair_of_Points {
    private static long comparisons = 0; // количество сравнений
    private static int maxDepth = 0;     // максимальная глубина рекурсии

    // Точка в 2D
    static class Point {
        double x, y;
        Point(double x, double y) { this.x = x; this.y = y; }
        public String toString() { return "(" + x + "," + y + ")"; }
    }

    // Главный метод: найти минимальное расстояние
    public static double closestPair(Point[] points) {
        Point[] sortedByX = points.clone();
        Arrays.sort(sortedByX, Comparator.comparingDouble(p -> p.x));
        return closest(sortedByX, 0, points.length - 1, 0);
    }

    private static double closest(Point[] pts, int left, int right, int depth) {
        maxDepth = Math.max(maxDepth, depth);

        if (right - left <= 3) {
            return bruteForce(pts, left, right);
        }

        int mid = (left + right) / 2;
        double midX = pts[mid].x;

        // рекурсивно в левой и правой половине
        double d1 = closest(pts, left, mid, depth + 1);
        double d2 = closest(pts, mid + 1, right, depth + 1);
        double d = Math.min(d1, d2);

        // собираем точки в "полосе"
        Point[] strip = new Point[right - left + 1];
        int count = 0;
        for (int i = left; i <= right; i++) {
            if (Math.abs(pts[i].x - midX) < d) {
                strip[count++] = pts[i];
            }
        }

        Arrays.sort(strip, 0, count, Comparator.comparingDouble(p -> p.y));

        // проверяем только ближайших 7 соседей
        for (int i = 0; i < count; i++) {
            for (int j = i + 1; j < count && (strip[j].y - strip[i].y) < d; j++) {
                comparisons++;
                d = Math.min(d, distance(strip[i], strip[j]));
            }
        }

        return d;
    }

    // "тупой" поиск для маленьких массивов (<=3)
    private static double bruteForce(Point[] pts, int left, int right) {
        double minDist = Double.MAX_VALUE;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                comparisons++;
                minDist = Math.min(minDist, distance(pts[i], pts[j]));
            }
        }
        return minDist;
    }

    // расстояние между двумя точками
    private static double distance(Point a, Point b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Тестовый запуск
    public static void main(String[] args) {
        Point[] points = {
                new Point(2, 3), new Point(12, 30), new Point(40, 50),
                new Point(5, 1), new Point(12, 10), new Point(3, 4)
        };

        double result = closestPair(points);

        System.out.println("Минимальное расстояние / Closest distance: " + result);
        System.out.println("Сравнений / Comparisons: " + comparisons);
        System.out.println("Максимальная глубина рекурсии / Max recursion depth: " + maxDepth);
    }
}
