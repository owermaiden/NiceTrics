import java.util.*;
import java.util.stream.Collectors;

public class CollectionsExamples {

    public static void main(String[] args) {
        System.out.println("=== Lists ===");
        listExamples();

        System.out.println("\n=== Sets ===");
        setExamples();

        System.out.println("\n=== Maps ===");
        mapExamples();

        System.out.println("\n=== Queues & Deques ===");
        queueAndDequeExamples();

        System.out.println("\n=== Collections utilities & Streams ===");
        utilitiesAndStreams();
    }

    private static void listExamples() {
        // ArrayList - dynamic array
        List<String> arrayList = new ArrayList<>();
        arrayList.add("Alice");
        arrayList.add("Bob");
        arrayList.add("Carol");
        System.out.println("ArrayList: " + arrayList);

        // Random access and set
        arrayList.set(1, "Bobby");
        System.out.println("after set(1, 'Bobby'): " + arrayList + ", get(2) => " + arrayList.get(2));

        // LinkedList - efficient insert/remove at ends
        List<String> linkedList = new LinkedList<>(arrayList);
        linkedList.add(0, "Zed");
        ((LinkedList<String>) linkedList).addFirst("Start");
        System.out.println("LinkedList: " + linkedList);

        // Convert between arrays and lists
        String[] arr = arrayList.toArray(new String[0]);
        System.out.println("toArray => " + Arrays.toString(arr));

        List<String> immutable = Collections.unmodifiableList(new ArrayList<>(arrayList));
        System.out.println("unmodifiableList (read-only view): " + immutable);

        // Iteration: forEach, iterator remove
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (s.startsWith("C")) it.remove();
        }
        System.out.println("after iterator remove items starting with 'C': " + arrayList);

        // ReplaceAll and sort
        arrayList.replaceAll(String::toUpperCase);
        Collections.sort(arrayList);
        System.out.println("uppercased + sorted: " + arrayList);
    }

    private static void setExamples() {
        // HashSet - no guaranteed order, fast operations (O(1) average)
        Set<String> hashSet = new HashSet<>();
        hashSet.add("apple");
        hashSet.add("banana");
        hashSet.add("apple"); // duplicate ignored
        System.out.println("HashSet: " + hashSet);

        // LinkedHashSet - preserves insertion order
        Set<String> linked = new LinkedHashSet<>();
        linked.add("one"); linked.add("two"); linked.add("three");
        System.out.println("LinkedHashSet (insertion order): " + linked);

        // TreeSet - sorted set
        SortedSet<Integer> tree = new TreeSet<>(Arrays.asList(5, 3, 9, 1));
        System.out.println("TreeSet (sorted): " + tree + ", first: " + tree.first() + ", last: " + tree.last());

        // Common operations
        System.out.println("contains 'banana'?: " + hashSet.contains("banana"));
        hashSet.remove("banana");
        System.out.println("after remove 'banana': " + hashSet);
    }

    private static void mapExamples() {
        // HashMap - unsorted mapping
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        System.out.println("HashMap: " + map);

        // getOrDefault
        System.out.println("getOrDefault('X', 0): " + map.getOrDefault("X", 0));

        // putIfAbsent / computeIfAbsent
        map.putIfAbsent("B", 20); // won't overwrite existing
        map.putIfAbsent("D", 4);
        map.computeIfAbsent("E", k -> 5);
        System.out.println("after putIfAbsent/computeIfAbsent: " + map);

        // merge (useful for counting)
        Map<String, Integer> counts = new HashMap<>();
        String[] words = {"apple", "banana", "apple", "pear"};
        for (String w : words) {
            counts.merge(w, 1, Integer::sum);
        }
        System.out.println("counts using merge: " + counts);

        // iteration patterns
        System.out.println("iterate keys:");
        for (String k : map.keySet()) System.out.print(k + " ");
        System.out.println();

        System.out.println("iterate entries:");
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            System.out.println("  " + e.getKey() + " -> " + e.getValue());
        }

        // LinkedHashMap preserves insertion order; TreeMap sorts by key
        Map<String, Integer> linkedMap = new LinkedHashMap<>();
        linkedMap.put("x", 10); linkedMap.put("y", 20);
        Map<String, Integer> treeMap = new TreeMap<>(linkedMap);
        System.out.println("LinkedHashMap: " + linkedMap + ", TreeMap(sorted): " + treeMap);
    }

    private static void queueAndDequeExamples() {
        // PriorityQueue - min-heap by default
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(5); pq.offer(1); pq.offer(3);
        System.out.println("PriorityQueue poll order:");
        while (!pq.isEmpty()) System.out.print(pq.poll() + " ");
        System.out.println();

        // ArrayDeque used as stack and queue
        Deque<String> dq = new ArrayDeque<>();
        dq.addLast("first"); dq.addLast("second"); dq.addFirst("zero");
        System.out.println("Deque (as queue) pollFirst: " + dq.pollFirst() + ", pollLast: " + dq.pollLast());

        // LinkedList as deque
        Deque<Integer> ll = new LinkedList<>();
        ll.push(10); ll.push(20); // push = addFirst
        System.out.println("LinkedList as stack pop: " + ll.pop());
    }

    private static void utilitiesAndStreams() {
        List<String> items = new ArrayList<>(Arrays.asList("b","a","c","a","b","a"));
        System.out.println("original items: " + items);

        // Collections utilities
        Collections.sort(items);
        System.out.println("sorted: " + items);
        Collections.reverse(items);
        System.out.println("reversed: " + items);
        Collections.shuffle(items, new Random(0)); // deterministic shuffle for example
        System.out.println("shuffled: " + items);

        // frequency
        System.out.println("frequency of 'a' => " + Collections.frequency(items, "a"));

        // removeIf
        items.removeIf(s -> s.equals("b"));
        System.out.println("after removeIf 'b': " + items);

        // Streams: groupingBy and collectors
        List<String> fruits = Arrays.asList("apple","banana","apple","pear","banana");
        Map<String, Long> grouped = fruits.stream()
                .collect(Collectors.groupingBy(f -> f, Collectors.counting()));
        System.out.println("grouped counts (stream): " + grouped);

        // Collect to immutable list (Java 10+)
        List<String> copy = List.copyOf(fruits);
        System.out.println("immutable copy: " + copy);

        // Convert stream to map safely (avoid duplicate keys by merging)
        Map<String, Integer> safeMap = fruits.stream()
                .collect(Collectors.toMap(f -> f, f -> 1, Integer::sum));
        System.out.println("toMap with merge function: " + safeMap);
    }
}

