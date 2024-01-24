import java.util.*;
import java.util.stream.Collectors;

public class Basics {

    public static void main(String[] args) {
         // convert array to arraylist
        int[] nums = {1,2,3,4};
        List<Integer> numsList = Arrays.stream(nums).boxed().collect(Collectors.toList());
        System.out.println(numsList);

        // convert arraylist to int[]
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1,2,3,4));
        int[] numsArray = numbers.stream().mapToInt(Integer::intValue).toArray();
        System.out.println(Arrays.toString(numsArray));

        // convert list to Integer[]
        List<Integer> list = new ArrayList<>(Arrays.asList(1,2,3,4));
        Integer[] array = list.toArray(new Integer[0]);
        System.out.println(Arrays.toString(array));

        // convert array to hashset
        int[] nums2 = {1,2,3,4};
        Set<Integer> set = Arrays.stream(nums2).boxed().collect(Collectors.toSet());
        System.out.println(set);

        // convert set to list
        List<Integer> fromSet = set.stream().toList();
        List<Integer> fromSet2 = new ArrayList<>(fromSet);
        System.out.println(fromSet2);

        // convert set to int[]
        Set<Integer> set2 = new HashSet<>(Set.of(1, 2, 3, 4));
        int[] array2 = set2.stream().mapToInt(Integer::intValue).toArray();
        System.out.println(Arrays.toString(array2));

        // convert char[] to string vice versa
        String given = "Hello";
        char[] chars = given.toCharArray();
        String newString = new String(chars);

    }
}
