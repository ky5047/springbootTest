package com.example.demo.util;


import com.example.demo.pojo.User;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by 92992 on 2018/12/10.
 * 测试了解java 8 新特性
 */
public class TestJava8 {

    public static  void main(String[] args) {
        /**
         * 特点：
         Stream不存储数据
         Stream不会修改源数据，无论怎么操作源数据并不会改变
         Stream是单向的，不可以重复使用
         Stream的部分操作是延迟的
         只要Stream的方法返回的对象是Stream，这些方法就是延迟执行的方法
         延迟执行的方法，一定要等到一个迫切方法执行的时候，才会执行。一般在Stream流中，一个方法返回的不是Stream，基本就是迫切方法
         Stream可以执行并行操作
         * */
//      清单 1. Java 7 的排序、取值实现
        List<User> groceryTransactions = new ArrayList<>();
        int i = 50;
        do {
            String uuid = Utils.getUUID();
            Random random = new Random();
            groceryTransactions.add(new User(i, "wxy" + uuid, random.nextInt(100), uuid + "@163.com", "uuid:" + uuid));
            i--;
        } while (i > 1);


        for (User t : groceryTransactions) {
            if ("WXY".equals(t.getId())) {
                groceryTransactions.add(t);
            }
        }
        Collections.sort(groceryTransactions, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getNickName().compareTo(o2.getNickName());
            }
        });
        List<Integer> transactionIds = new ArrayList<>();
        for (User t : groceryTransactions) {
            transactionIds.add(t.getId());
        }

//       清单 2. Java 8 的排序、取值实现
        transactionIds = groceryTransactions.parallelStream()
                .filter(u -> "WXY".equals(u.getEmail()))
                .sorted(Comparator.comparing(User::getNickName).reversed())
                .map(User::getId)
                .collect(Collectors.toList());
        System.out.println(transactionIds);

        /*Stream 就如同一个迭代器（Iterator），单向，不可往复，
        数据只能遍历一次，遍历过一次后即用尽了，就好比流水从面前流过，一去不复返。

        和迭代器又不同的是，Stream 可以并行化操作，迭代器只能命令式地、串行化操作
        Stream 的并行操作依赖于 Java7 中引入的 Fork/Join 框架（JSR166y）来拆分任务和加速处理过程


        使用一个流的时候，通常包括三个基本步骤：
        获取一个数据源（source）→ 数据转换→执行操作获取想要的结果，
        每次转换原有 Stream 对象不改变，返回一个新的 Stream 对象（可以有多次转换），
        这就允许对其操作可以像链条一样排列，变成一个管道
        */

        /**
         * 流的操作类型分为两种：
         * Intermediate：
         * 一个流可以后面跟随零个或多个 intermediate 操作。
         * 其目的主要是打开流，做出某种程度的数据映射/过滤，然后返回一个新的流，交给下一个操作使用。
         * 这类操作都是惰性化的（lazy），就是说，仅仅调用到这类方法，并没有真正开始流的遍历。
         *
         *Terminal：一个流只能有一个 terminal 操作，当这个操作执行后，流就被使用“光”了，无法再被操作。
         *所以这必定是流的最后一个操作。
         *Terminal 操作的执行，才会真正开始流的遍历，并且会生成一个结果，或者一个 side effect。
         *
         *
         * 还有一种操作被称为 short-circuiting。用以指：
         *对于一个 intermediate 操作，如果它接受的是一个无限大（infinite/unbounded）的 Stream，但返回一个有限的新 Stream。
         *对于一个 terminal 操作，如果它接受的是一个无限大的 Stream，但能在有限的时间计算出结果。
         *当操作一个无限大的 Stream，而又希望在有限时间内完成操作，则在管道内拥有一个 short-circuiting 操作是必要非充分条件。
         * */
//        清单 3. 一个流操作的示例
        /*
        * filter 和 mapToInt 为 intermediate 操作
        * 进行数据筛选和转换，最后一个 sum() 为 terminal 操作，对符合条件的全部小物件作重量求和
        * */
        System.out.println("清单 3:" + groceryTransactions.parallelStream().filter(g -> g.getMark() % 2 == 0).mapToInt(num -> num.getId()).sum());
        ;


        //数值流的构造
        IntStream.of(new int[]{1, 2, 3}).forEach(System.out::print);
        IntStream.range(1, 3).forEach(System.out::print);//右边界 为 开区间
        IntStream.rangeClosed(1, 3).forEach(System.out::print);//左右为闭合区间


//      清单 4. 构造流的几种常见方法
        // 1. Individual values
        Stream stream = Stream.of("a", "b", "c");
        // 2. Arrays
        String[] strArray = new String[]{"a", "b", "c"};
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);

        //流转换为其它数据结构
        //1、Array
        String[] strArray1 = (String[]) stream.toArray(String[]::new);


        // 3. Collections  一个 Stream 只可以使用一次 ==> 代码为了简洁而重复使用了数次
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();
        List<String> list1 = (List<String>) stream.collect(Collectors.toList());
//        List<String> list2 = (List<String>)stream.collect(Collectors.toCollection(ArrayList::new));
       /* Set set1 = (Set) stream.collect(Collectors.toSet());
        Stack stack1 = stream.collect(Collectors.toCollection(Stack::new));*/
        // 3. String
//        String str = stream.collect(Collectors.joining()).toString();


        /*流的操作
        *Intermediate：
        *map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、
        *limit、 skip、 parallel、 sequential、 unordered
        *
        *Terminal：
        *forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、
        * anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator
        *
        * Short-circuiting：
        * anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit
        * */

//      map/flatMap
//      清单 7. 转换大写
        List<String> output = groceryTransactions.parallelStream()
                .map(User::getEmail)
                .collect(Collectors.toList());
        output.stream().map(String::toUpperCase).collect(Collectors.toList()).forEach(System.out::println);

//        清单 9. 一对多
        /**
         * flatMap 把 input Stream 中的层级结构扁平化，就是将最底层元素抽出来放到一起，最终 output 的新 Stream 里面已经没有 List 了，都是直接的数字
         * */
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6)
        );
        Stream<Integer> outputStream = inputStream.
                flatMap((childList) -> childList.stream());
        outputStream.forEach(System.out::println);
//        清单 10. 留下偶数
        Integer[] sixNums = {1, 2, 3, 4, 5, 6};
//        List<Integer> sixNumList = Stream.of(sixNums).filter(x -> x % 2 == 0).collect(Collectors.toList());
        sixNums = Stream.of(sixNums).filter(x -> x % 2 == 0).toArray(Integer[]::new);
        System.out.println(sixNums);

//        forEach 方法接收一个 Lambda 表达式，然后在 Stream 的每一个元素上执行该表达式。
        /*Lambda 表达式本身是可以重用的，非常方便。
        当需要为多核系统优化时，可以 parallelStream().forEach()，只是此时原有元素的次序没法保证，
        并行的情况下将改变串行时操作的行为，此时 forEach 本身的实现不需要调整，
        而 Java8 以前的 for 循环 code 可能需要加入额外的多线程逻辑。

        一般认为，forEach 和常规 for 循环的差异不涉及到性能，它们仅仅是函数式风格与传统 Java 风格的差别
        forEach 是 terminal 操作，因此它执行后，Stream 的元素就被“消费”掉了，你无法对一个 Stream 进行两次 terminal 运算。

        forEach 不能修改自己包含的本地变量值，也不能用 break/return 之类的关键字提前结束循环。
        */

//        清单 13. peek 对每个元素执行操作并返回一个新的 Stream
        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());

     /*   findFirst
        这是一个 termimal 兼 short-circuiting 操作，它总是返回 Stream 的第一个元素，或者空。
        这里比较重点的是它的返回值类型：Optional。这也是一个模仿 Scala 语言中的概念，作为一个容器，它可能含有某值，或者不包含。
        使用它的目的是尽可能避免 NullPointerException。


        使用 Optional 代码的可读性更好，而且它提供的是编译时检查，
        能极大的降低 NPE 这种 Runtime Exception 对程序的影响，
        或者迫使程序员更早的在编码阶段处理空值问题，而不是留到运行时再发现和调试。
        Stream 中的 findAny、max/min、reduce 等方法等返回 Optional 值。
        */

        String strA = " abcd ", strB = null;
        print(strA);//abcd
        print("");//没有输出结果
        print(strB);//没有输出结果
        getLength(strA);//6
        getLength("");//0
        getLength(strB);//-1

        // 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        System.out.println(minValue);

    }

    //        清单 14. Optional 的两个用例
    public static void print(String text) {
        // Java 8
        Optional.ofNullable(text).ifPresent(System.out::println);
        // Pre-Java 8
        if (text != null) {
            System.out.println(text);
        }
    }
    public static void getLength(String text) {
        // Java 8
        System.out.println(Optional.ofNullable(text).map(String::length).orElse(-1));
        // Pre-Java 8
        System.out.println(text!=null?text.length():-1);
    };



}
