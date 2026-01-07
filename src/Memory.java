import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Memory {

    public static void main(String[] args) {
        System.out.println(getCountOfCharacters("Hello"));
        System.out.println(removeNonrepeatingCharacter("Hello"));
        System.out.println(getSumOfDigitsOfANumber(123));
        System.out.println(getDigitsSumWithStream(123));
        System.out.println(getNumberFromString("1234"));
        System.out.println(camelCase("camelCase"));
        System.out.println(hasDistinctDigits(12458));
        System.out.println(hasDistinctDigits2(12458));
        System.out.println(findFactorial(6));
        String[] strings = {"Apple", "Banana", "apple", "Cherry", "Apple"};
        System.out.println(getCountOfElementsInArray(strings));
        List<String> words = new ArrayList<>(Arrays.asList(strings));
        System.out.println(lenghtOfWords(words));
        int[] nums = {0,1,1,2,3,4};
        System.out.println(findMissingNumber(nums));
        System.out.println(getNumberOfDublicatedLetters("Hello"));
        System.out.println(Arrays.toString(increaseArrayAndReturnNewArray(nums)));
        System.out.println(removeDuplicatesFromArray(nums));
        int[] numbers = {2, 4, 9, 16};
        System.out.println(findWholeSquareNumbers(numbers));
        System.out.println(reverseStringInPlace("Hello"));
        System.out.println(reduceString("Hello"));
        System.out.println(stringCompare("Hello"));
    }

    private static boolean stringCompare(String input) {
        return IntStream.range(1, input.length())
                .anyMatch(i -> input.charAt(i) < input.charAt(i - 1));
    }

    private static Map<Character, Integer> getCountOfCharacters(String s) {
        Map<Character, Integer> sCountMap = new HashMap<>();
        for (char c : s.toCharArray()) {
            sCountMap.put(c, sCountMap.getOrDefault(c, 0) + 1);
        }
        return sCountMap;
    }

    static Map<String, Long> getCountOfElementsInArray(String[] strings) {
        if (strings == null) throw new IllegalArgumentException("Input should not be null");
        return Arrays.stream(strings)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private static Map<Character, Integer> removeNonrepeatingCharacter(String given){
        Map<Character, Integer> pairs= getCountOfCharacters(given);
        Iterator<Map.Entry<Character, Integer>> iterator = pairs.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Character, Integer> entry = iterator.next();
            if (entry.getValue() == 1){
                iterator.remove();
            }
        }
        return pairs;
    }

    private static int getSumOfDigitsOfANumber(int num){
        // Convert the number to a string to get its getLength
        String numStr = Integer.toString(num);
        int n = numStr.length();

        // Compute the sum of the nth powers of the digits
        int sum = 0;
        for (int i = 0; i < n; i++) {
            int digit = Character.getNumericValue(numStr.charAt(i));
//            sum += Math.pow(digit, n);  // nth power
            sum += digit;  // digit itself
        }

        return sum;
    }

    private static int getDigitsSumWithStream(int number) {
        return Integer.toString(number)
                .chars()
                .map(Character::getNumericValue)    //  .map(c -> Character.getNumericValue(c)) //same
                .reduce(0, Integer::sum);
//                .toArray();
    }

    private static int getNumberFromString(String given){
        return Integer.parseInt(given);
    }

    public static String camelCase(String str) {
        if (str == null || str.isEmpty()) return "";
        return str.chars()
                .mapToObj(ch -> Character.isUpperCase((char) ch) ? " " + (char) ch : String.valueOf((char) ch))
                .collect(Collectors.joining());
    }

    public static boolean hasDistinctDigits(int num) {
        boolean[] digitOccurred = new boolean[10]; // Assuming numbers are less than 10 digits
        // Check for distinct digits
        while (num > 0) {
            int digit = num % 10;
            if (digitOccurred[digit]) {
                return false; // Digit repeated
            }
            digitOccurred[digit] = true;
            num /= 10;
        }
        return true; // No repeating digits
    }

    public static boolean hasDistinctDigits2(int num) {
        int[] digits = String.valueOf(num).chars().map(Character::getNumericValue).toArray();
        return IntStream.range(0, digits.length)
                .noneMatch(i -> IntStream.range(i + 1, digits.length).anyMatch(j -> digits[i] == digits[j]));
    }

    private static long findFactorial(int num){
        if (num < 0) throw new IllegalArgumentException("input should be positive number or zero");
        return IntStream.range(1, num + 1)//returns IntStream
                .reduce(1, (x, y) -> x * y);
    }

    private static List<Integer> lenghtOfWords(List<String> words) {
        return words.stream()
                .map(String::length)
                .collect(Collectors.toList());

//        String s = words.stream()
//                .map(w -> w.length()+"")
//                .collect(Collectors.joining(", "));

    }

    static int findMissingNumber(int[] nums) {
        int n = nums.length;
//        int expected = Stream.iterate(0, i -> i <= n, i -> i + 1).reduce(0, Integer::sum);
//        int expected = IntStream.iterate(0, i -> i <= n, i -> i + 1).sum();
        int expected = IntStream.rangeClosed(0, n).sum();
        int actual = Arrays.stream(nums).sum();
        return expected - actual;
    }

    public static int getNumberOfDublicatedLetters(String str) { // Lambda Functions

        return (int) str.toLowerCase().chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), HashMap::new, Collectors.counting()))
                .values().stream()
                .filter(l -> l > 1)
                .count();
    }

    static int[] increaseArrayAndReturnNewArray(int[] digits) {
        StringBuilder number = new StringBuilder();
        for (int eachNum : digits) {
            number.append(eachNum);
        }

        int num2 = Integer.parseInt(number.toString()) + 1;
        return Integer.toString(num2).chars().map(Character::getNumericValue).toArray();
    }

    public static int removeDuplicatesFromArray(int[] nums) {
        Arrays.sort(nums);
        if (nums.length < 2) return nums.length;
        int slow = 0;
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[slow] != nums[fast]) {
                slow++;
                nums[slow] = nums[fast];
            }
        }
        return ++slow; // to find the getLength of result array
    }

    static List<Integer> findWholeSquareNumbers(int[] numbers) {
        List<Integer> result = new ArrayList<>();
        for (Integer num: numbers) {
            double sqrt = Math.sqrt(num);
            if (Math.floor(sqrt) == sqrt){
                result.add(num);
            }
        }
        return result;
    }

    public static String reverseStringInPlace(String given) {
        char[] s = given.toCharArray();
        for (int i = 0, j = s.length-1; i < s.length/2; i++, j--) {
            char ch = s[i];
            s[i] = s[j];
            s[j] = ch;
        }
        return new String(s);
    }


    public static String reduceString(String str){
        if (str.isEmpty()) return "";
        System.out.println(str);
        return reduceString(str.substring(0, str.length() - 1));
    }



}
