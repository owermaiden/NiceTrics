import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Basics {

    public static void main(String[] args) {
        // Primitive <-> String
        int i = 123;
        String iToStr1 = Integer.toString(i);
        String iToStr2 = String.valueOf(i);
        int strToI = Integer.parseInt("456");
        System.out.println("int -> String: " + iToStr1 + ", " + iToStr2);
        System.out.println("String -> int: " + strToI);

        // Integer (wrapper) <-> int (auto-box/unbox)
        Integer boxed = Integer.valueOf(789);
        int unboxed = boxed; // auto-unboxing
        System.out.println("Integer -> int: " + unboxed);

        // char <-> String
        char c = 'A';
        String cToStr = Character.toString(c);
        String cToStr2 = String.valueOf(c);
        char strToC = "Z".charAt(0);
        System.out.println("char -> String: " + cToStr + ", " + cToStr2);
        System.out.println("String -> char: " + strToC);

        // char <-> int (code point / numeric value)
        int code = (int) 'A'; // ASCII/Unicode code point
        int numeric = Character.getNumericValue('7'); // numeric value for digits
        char fromCode = (char) 66;
        System.out.println("char code of A: " + code + " (char from 66 => " + fromCode + ")");
        System.out.println("Character.getNumericValue('7') => " + numeric);

        // double/float -> String and back
        double d = 3.14159;
        String dStr = Double.toString(d);
        double parsedD = Double.parseDouble("2.71828");
        System.out.println("double -> String: " + dStr + ", parsed: " + parsedD);

        // boolean <-> String
        boolean b = true;
        String bStr = String.valueOf(b);
        boolean parsedB = Boolean.parseBoolean("false");
        System.out.println("boolean -> String: " + bStr + ", parsed: " + parsedB);

        // int -> binary/hex/octal String and back
        int val = 255;
        String bin = Integer.toBinaryString(val);
        String hex = Integer.toHexString(val);
        String oct = Integer.toOctalString(val);
        int parsedHex = Integer.parseInt("ff", 16);
        System.out.println("255 in binary/hex/octal: " + bin + ", " + hex + ", " + oct);
        System.out.println("parsed 'ff' hex => " + parsedHex);

        // BigInteger conversions
        BigInteger big = new BigInteger("12345678901234567890");
        String bigStr = big.toString();
        BigInteger parsedBig = new BigInteger("9876543210");
        System.out.println("BigInteger -> String: " + bigStr + ", parsed: " + parsedBig);

        // byte[] <-> String (encoding-aware)
        String text = "Hello, 世界";
        byte[] utf8 = text.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        String fromBytes = new String(utf8, java.nio.charset.StandardCharsets.UTF_8);
        System.out.println("String -> bytes (UTF-8) length: " + utf8.length + ", back: " + fromBytes);

        // char[] <-> String
        String hello = "Hello";
        char[] chars = hello.toCharArray();
        String helloBack = new String(chars);
        System.out.println("String -> char[]: " + Arrays.toString(chars) + ", back: " + helloBack);

        // Arrays and Collections conversions
        Integer[] boxedArr = {1, 2, 3};
        int[] primArr = Arrays.stream(boxedArr).mapToInt(Integer::intValue).toArray();
        Integer[] backBoxed = Arrays.stream(primArr).boxed().toArray(Integer[]::new);
        System.out.println("Integer[] -> int[]: " + Arrays.toString(primArr) + ", back: " + Arrays.toString(backBoxed));

        // int[] -> List<Integer> and back
        int[] nums = {4, 5, 6};
        List<Integer> listFromPrimitives = Arrays.stream(nums).boxed().collect(Collectors.toList());
        int[] primBack = listFromPrimitives.stream().mapToInt(Integer::intValue).toArray();
        System.out.println("int[] -> List<Integer>: " + listFromPrimitives + ", back: " + Arrays.toString(primBack));

        // List<String> <-> String[]
        List<String> names = List.of("Alice", "Bob", "Carol");
        String[] namesArr = names.toArray(new String[0]);
        List<String> namesBack = Arrays.asList(namesArr);
        System.out.println("List<String> -> String[]: " + Arrays.toString(namesArr) + ", back: " + namesBack);

        // Set <-> List
        Set<Integer> s = new HashSet<>(Set.of(7, 8, 9));
        List<Integer> fromSet = new ArrayList<>(s);
        Set<Integer> backSet = new HashSet<>(fromSet);
        System.out.println("Set -> List -> Set: " + fromSet + " -> " + backSet);

        // Map entries to list
        Map<String, Integer> map = Map.of("x", 1, "y", 2);
        List<String> keys = new ArrayList<>(map.keySet());
        List<Integer> values = new ArrayList<>(map.values());
        System.out.println("Map keys: " + keys + ", values: " + values);

        // Splitting and joining Strings
        String csv = "a,b,c,d";
        String[] parts = csv.split(",");
        String joined = String.join("-", parts);
        System.out.println("split -> " + Arrays.toString(parts) + ", joined -> " + joined);

        // Optional: parsing numbers safely (with default)
        String maybeNum = "1000";
        int safe = parseIntOrDefault(maybeNum, -1);
        int safeBad = parseIntOrDefault("not-a-num", -1);
        System.out.println("safe parse: " + safe + ", bad parse default: " + safeBad);

        // Widening / narrowing conversions
        short sVal = 12;
        int widened = sVal; // automatic widening
        byte narrowed = (byte) 130; // explicit narrowing (overflow)
        System.out.println("widened short->int: " + widened + ", narrowed int->byte (130 ->): " + narrowed);

        // Number wrappers: Number -> specific types
        Number anyNumber = Double.valueOf(45.67);
        int fromNumber = anyNumber.intValue();
        long fromNumberLong = anyNumber.longValue();
        System.out.println("Number(double 45.67) -> int: " + fromNumber + ", long: " + fromNumberLong);

        // Base conversions: parse and format with radix
        int n = Integer.parseInt("1011", 2); // binary -> decimal
        String asHex = Integer.toString(12345, 16);
        System.out.println("binary 1011 -> " + n + ", 12345 as hex -> " + asHex);

        // Edge: converting List of Objects to primitive arrays requires mapping
        List<Double> doubles = List.of(1.2, 3.4);
        double[] dPrims = doubles.stream().mapToDouble(Double::doubleValue).toArray();
        System.out.println("List<Double> -> double[]: " + Arrays.toString(dPrims));

        // RFC: If you need JSON <-> object conversion, use a library (Jackson/Gson) — omitted here.
    }

    private static int parseIntOrDefault(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return def;
        }
    }
}
