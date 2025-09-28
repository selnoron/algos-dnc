# Report on Assignment 1

## Architecture Notes
Our implementation includes three core algorithms: **QuickSort, Deterministic Select, and Closest Pair of Points**.  
To control recursion depth, QuickSort explicitly tracks the number of recursive calls and enforces the constraint `depth ≤ 2*floor(log₂ n) + O(1)`.  
Memory allocation was minimized by reusing arrays where possible (e.g., temporary arrays in MergeSort, buffer for Closest Pair). For `Closest Pair`, the divide-and-conquer implementation carefully avoids unnecessary array copying by passing indices instead of creating new subarrays.  

---

## Recurrence Analysis

### QuickSort
QuickSort uses a randomized pivot strategy.  
- Recurrence: T(n) = T(k) + T(n−k−1) + Θ(n).  
- Expected depth: Θ(log n), worst-case depth: Θ(n).  
- By applying the Master theorem intuition, the average runtime is **Θ(n log n)**.  
- Depth bound was checked against `2*log₂ n`, confirming theoretical expectations.  

### MergeSort
MergeSort always splits the array evenly.  
- Recurrence: T(n) = 2T(n/2) + Θ(n).  
- Using the Master theorem, this resolves to **Θ(n log n)**.  
- Memory allocations are Θ(n) due to the auxiliary array.  
- Depth is exactly log₂ n.  

### Deterministic Select (Median of Medians)
The algorithm ensures linear worst-case runtime.  
- Recurrence: T(n) = T(n/5) + T(7n/10) + Θ(n).  
- By Akra–Bazzi analysis, the result is **Θ(n)**.  
- The method guarantees balanced partitions and prevents QuickSelect’s unlucky deep recursions.  

### Closest Pair of Points
Divide-and-conquer approach splits points along x-axis.  
- Recurrence: T(n) = 2T(n/2) + Θ(n).  
- By Master theorem, the runtime is **Θ(n log n)**.  
- For small subproblems (n ≤ 2000), a naive **Θ(n²)** approach is used for validation.  
- Memory usage is linear, recursion depth is log₂ n.  

---

## Plots and Constant-Factor Discussion
We tested arrays of increasing size (n up to ~100,000).  
- **QuickSort** performed fastest on average, but recursion depth varied depending on pivot choices.  
- **MergeSort** had stable performance with predictable depth, but required more memory allocations.  
- **Deterministic Select** was slower in practice than randomized QuickSelect, due to constant factors from median-of-medians.  
- **Closest Pair** showed excellent scaling, though for small n the O(n²) solution was sometimes faster due to lower constant factors.  

Practical effects:  
- Cache locality favored MergeSort over QuickSort in some mid-sized tests.  
- Java’s garbage collector (GC) introduced minor noise in timings for large n due to temporary arrays.  

---

## Summary
- **Theoretical vs Experimental:**  
  - QuickSort and MergeSort both showed **Θ(n log n)** scaling as predicted.  
  - Deterministic Select behaved as **Θ(n)**, but constants made it slower than built-in `Arrays.sort` in practice.  
  - Closest Pair matched **Θ(n log n)** scaling, validating the divide-and-conquer recurrence.  

- **Alignment:** Theory and measurements were consistent in terms of asymptotic growth.  
- **Mismatch:** Constant factors (cache, GC, array copying) explained minor deviations.  

Overall, the project confirms the theoretical complexity bounds and highlights the practical trade-offs of algorithm design in Java.  
