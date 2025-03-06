package com.siddhatech.AESUtil;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;


public class TestClass {

    private static final Logger log = LoggerFactory.getLogger(TestClass.class);

    public static void main(String[] args) throws NoSuchAlgorithmException {

        char character = 'H';
        int asciiValue = character;
        String binaryString = Integer.toBinaryString(asciiValue);
        String paddedBinary = String.format("%8s", binaryString).replace(' ', '0');

        int decimalValue = Integer.parseInt(paddedBinary, 2);

        String binary6Bit = String.format("%6s", Integer.toBinaryString(decimalValue & 0x3F)).replace(' ', '0');
        int decimal6Bit = Integer.parseInt(binary6Bit, 2);
        System.out.println("decimal value of given binary "+decimal6Bit);
        log.info("decimal value of given binary{}", decimal6Bit);

        /*
        char character = 'h';
        int asciiValue = character;
        String binaryString = Integer.toBinaryString(asciiValue);
        String paddedBinary = String.format("%8S", binaryString).replcase('','0');

        int decimalValue = integer.partInt(paddedBinary,2);

        String binary16Bit = String.format("6%", Integer.toBianryString(decimalValue & 0x3f)).replace('','0');
        int decimal16Bit = Integer.parseInt(binary6Bit, 2);
        System.out.println("decimal value of given binary "+decimal16Bit);
        log.info("decimal value of given binary{}", decimal16Bit);

        Solr is an open-source search platform built on top of Apache Lucene, just like Elasticsearch. It is used for
        implementing powerful search and indexing capabilities, making it easier to search through large volume of data.
        Solr is widely known for its flexibility, scalability, and enterprise-grade feature, making it popular choice for search
        engines and analytics platform.


        */
    }
}
