import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DSAExamples {

    public static void main(String[] args) {

//        int[] arr = {1, 2, 3, 4, 5};
//        int[] prod = productExceptSelf(arr);
//        System.out.println("productExceptSelf: " + Arrays.toString(prod));
//
//        int[] nums = {2, 17, 11, 7};
//        int target = 9;
//        int[] pos = twoSumProblem(nums, target);
//        System.out.println("twoSumProblem: " + Arrays.toString(pos));

//        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
//        int[][] result = mergeIntervals(intervals);
//
//        System.out.println("Merged Intervals:");
//        for (int[] interval : result) {
//            System.out.println(Arrays.toString(interval));
//        }

//        int[] nums1 = {1, 3};
//        int[] nums2 = {2};
//        System.out.println("Median: " + findMedianSortedArrays(nums1, nums2));  // Output: 2.0
//
//
//        int[] nums3 = {1, 2};
//        int[] nums4 = {3, 4};
//        System.out.println("Median: " + findMedianSortedArrays(nums3, nums4));  // Output: 2.5

//        int[] nums = {3,2,1,5,6,4};
//        int k = 2;
//        System.out.println("The " + k + "-th largest element is: " + findKthLargest(nums, k));  // Output: 5

        int[] topKFrequentElements = topKFrequentElements(new int[]{1, 1, 1, 2, 2, 3}, 3);
        System.out.println("topKFrequentElements : " + Arrays.toString(topKFrequentElements));

    }

    /*Given an array arr[] of n integers, construct a Product Array prod[] (of the same size)
    such that prod[i] is equal to the product of all the elements of arr[] except arr[i]
    without using the division.*/
    private static int[] productExceptSelf(int[] inputArr) {

        int n = inputArr.length;
        int[] prod = new int[n];
        Arrays.fill(prod, 1);

        int leftProd = 1;
        for (int i=0; i<n; i++) {
            prod[i] = leftProd;
            leftProd *= inputArr[i];
        }
//        System.out.println("leftProd: " + Arrays.toString(prod));

        int rightProd = 1;
        for(int i=n-1; i>= 0; i--) {
            prod[i] *= rightProd;
            rightProd *= inputArr[i];
        }
//        System.out.println("rightProd: " + Arrays.toString(prod));

        return prod;


    }

    /*Given an array of integers nums and an integer target,
    return indices of the two numbers such that they add up to target.
    You may assume that each input would have exactly one solution, and you may not use the same element twice.
    int[] nums = {2, 7, 11, 15};
    int target = 9;
    Output: [0, 1]  // Because nums[0] + nums[1] = 2 + 7 = 9*/
    private static int[] twoSumProblem(int[] nums, int target) {
        int[] result = new int[2];
//        Map<Integer, Integer> store = new HashMap<>();
//        Arrays.fill(result,1);
//
//        for (int i=0; i< nums.length; i++) {
//
//            Integer complement = Math.subtractExact(target, nums[i]);
//
//            if(store.containsKey(complement)) {
//                result = new int[] { store.get(complement), i };
//            }
//
//            store.put(nums[i], i);
//
//        }

        for (int pos1=0; pos1< nums.length; pos1++) {
            for (int pos2=0; pos2< nums.length; pos2++) {
                if(pos1 != pos2 && Math.addExact(nums[pos1], nums[pos2]) == target) {
                    return new int[]{pos1, pos2};
                }
            }
        }


        return result;
    }

    /*
    * Given a string, find the length of the longest substring without repeating characters.
    * sliding window technique
    * */
    public static int lengthOfLongestSubstring(String s) {
        // Map to store the last seen index of characters
        Map<Character, Integer> map = new HashMap<>();
        int maxLength = 0;
        int start = 0; // start index of the current window

        // Iterate over the characters in the string
        for (int end = 0; end < s.length(); end++) {
            char currentChar = s.charAt(end);

            // If the character is already in the map and within the current window, move the start
            if (map.containsKey(currentChar) && map.get(currentChar) >= start) {
                start = map.get(currentChar) + 1;
            }

            // Update the last seen index of the current character
            map.put(currentChar, end);

            // Update the maximum length of the substring found so far
            maxLength = Math.max(maxLength, end - start + 1);
        }

        return maxLength;
    }

    /*
    Given a collection of intervals, merge all overlapping intervals.
    int[][] intervals = {{1,3},{2,6},{8,10},{15,18}};
    Output: [[1,6],[8,10],[15,18]]
     */
    public static int[][] mergeIntervals(int[][] intervals) {
        // Edge case: if there are no intervals, return an empty array
        if (intervals.length == 0) {
            return new int[0][];
        }

        // Sort the intervals based on the start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // List to store the merged intervals
        List<int[]> merged = new ArrayList<>();

        // Start with the first interval
        int[] currentInterval = intervals[0];
        merged.add(currentInterval);

        // Iterate through the intervals
        for (int[] interval : intervals) {
            int currentStart = currentInterval[0];
            int currentEnd = currentInterval[1];
            int nextStart = interval[0];
            int nextEnd = interval[1];

            // If the current interval overlaps with the next interval, merge them
            if (currentEnd >= nextStart) {
                currentInterval[1] = Math.max(currentEnd, nextEnd);
            } else {
                // If not, add the next interval as a new interval
                currentInterval = interval;
                merged.add(interval);
            }
        }

        // Convert the list of merged intervals to a 2D array and return it
        return merged.toArray(new int[merged.size()][]);
    }

    /*
     * Find the Median of Two Sorted Arrays
     * Problem: Given two sorted arrays nums1 and nums2 of size m and n respectively,
     * return the median of the two sorted arrays.
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {

        Double result = 0d;
        List<Integer> merged = Stream.concat(IntStream.of(nums1).boxed(), IntStream.of(nums2).boxed())
                .sorted().toList();

        int mergedListLength = merged.size();
        int medianPosition = 0;
        if(mergedListLength % 2 == 0) { // Even
            medianPosition = Math.floorDiv(mergedListLength, 2);
            System.out.println("even medianPosition : " + medianPosition);
            result = Math.addExact(merged.get(medianPosition-1), merged.get(medianPosition)) / 2.0;
        } else { // Odd
            medianPosition = Math.floorDiv(mergedListLength, 2);
            System.out.println("odd medianPosition : " + medianPosition);
            result = Double.valueOf(merged.get(medianPosition));
        }

        return result;

    }

    /*
     * The problem of finding the number of distinct ways to climb a staircase
     * where you can take 1 or 2 steps at a time is a classic dynamic programming problem.
     * The key insight is that the number of ways to reach step n is the sum of the ways
     * to reach step n-1 and the ways to reach step n-2.
     */
    public static int climbStairs(int n) {
        // Base cases
        if (n == 1) return 1;
        if (n == 2) return 2;

        // Variables to store the number of ways to reach the last two steps
        int oneStepBefore = 2;
        int twoStepsBefore = 1;
        int allWays = 0;

        // Loop to calculate the number of ways for each step from 3 to n
        for (int i = 3; i <= n; i++) {
            allWays = oneStepBefore + twoStepsBefore;
            twoStepsBefore = oneStepBefore;
            oneStepBefore = allWays;
        }

        return allWays;
    }

    /*
    Given an integer array nums and an integer k, return the kth largest element in the array.
    int[] nums = {3,2,1,5,6,4};
    int k = 2;
    Output: 5  // Because the second largest element is 5.
     */
    public static int findKthLargest(int[] nums, int k) {
        List<Integer> numsSorted = IntStream.of(nums).boxed().sorted().distinct().toList();
        return numsSorted.get(numsSorted.size() - k);

    }

    public static int[] topKFrequentElements(int[] nums, int k) {

        return IntStream.of(nums)
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() >= k)
                .map(Map.Entry::getKey)
                .mapToInt(Integer::valueOf)
                .toArray();

    }

    public static boolean isValidParenthesis(String s) {
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            if(c == '[' || c == '{' || c == '(') {
                stack.push(c);
            } else if(c == ']' || c == '}' || c == ')') {
                if(stack.isEmpty()) {
                    return false;
                }

                char top = stack.pop();
                if((c == ']' && top != '[')
                    || (c == '}' && top != '{')
                    || (c == ')' && top != '(')) {

                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public int lengthOfLongestSubstring2(String s) {
        int n = s.length();
        int maxLen = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0, j = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)) + 1, i);
            }
            maxLen = Math.max(maxLen, j - i + 1);
            map.put(s.charAt(j), j);
        }
        return maxLen;
    }
}