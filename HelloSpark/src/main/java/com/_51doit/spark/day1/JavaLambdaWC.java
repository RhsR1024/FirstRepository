package com._51doit.spark.day1;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

public class JavaLambdaWC {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("JavaLambdaWC");
        JavaSparkContext jsc = new JavaSparkContext(conf);

        JavaRDD<String> lines = jsc.textFile(args[0]);

        //切分压平
        JavaRDD<String> words = lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator());

        //将单词和一组合
        JavaPairRDD<String, Integer> wordAndOne = words.mapToPair(w -> new Tuple2<>(w, 1));

        //聚合
        JavaPairRDD<String, Integer> reduced = wordAndOne.reduceByKey((x, y) -> x + y);


        JavaPairRDD<Integer, String> countAndWords = reduced.mapToPair(x -> x.swap());


        JavaPairRDD<Integer, String> sorted = countAndWords.sortByKey(false);


        JavaPairRDD<String, Integer> result = sorted.mapToPair(x -> x.swap());


        //写入HDFS
        result.saveAsTextFile(args[1]);

        //释放资源
        jsc.stop();

    }
}
