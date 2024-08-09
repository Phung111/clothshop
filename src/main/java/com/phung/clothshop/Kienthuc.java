package com.phung.clothshop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Collection;

public class Kienthuc {
    int a = 0; 
    Integer b = 1;
    int[] numbers = {1, 2, 3, 4, 5};
    int[] numbers2 = new int[10];
    String str = "Hello";
        int length = str.length(); 
        char character = str.charAt(1);
        int index = str.indexOf("l");
        int index2 = str.lastIndexOf("l");
        String subStr = str.substring(1, 3);
    
    public void Void() {

        List<Integer> arrays = new ArrayList<>();
        arrays.add(1);
        arrays.remove(0);
        arrays.get(1);
        arrays.set(2, 10);
        arrays.size();
        arrays.isEmpty();
        arrays.contains(20);
        arrays.indexOf(20);
        arrays.lastIndexOf(20);

        HashMap<Integer , String > map = new HashMap<>();
        map.put(1,"apple");
        map.get(1);
        map.containsKey(1);
        map.containsValue("apple");
        map.size();
        map.isEmpty();
        Set<Integer> keys = map.keySet();
        Collection<String> values = map.values();

        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.peek();
        stack.pop();
        int positionStack = stack.search(20);
        stack.size();
        stack.clear();
        stack.isEmpty();

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(10);
        queue.peek();
        queue.poll();
        queue.size();
        queue.isEmpty();
    }
}


