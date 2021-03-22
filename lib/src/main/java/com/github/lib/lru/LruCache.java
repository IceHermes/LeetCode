package com.github.lib.lru;

import java.util.HashMap;

/**
 * Copyright (c) 2021 Tencent. All rights reserved.
 * 类功能描述:
 *
 * @author flynnjiao
 * @date 2021/3/22
 */
public class LruCache {
    // 双向链表节点定义
    class Node {
        int key;
        int val;
        Node prev;
        Node next;
    }

    private Node head = null;
    private Node tail = null;
    private HashMap<Integer, Node> map;
    private int capacity;

    public LruCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        }
        moveToHead(node);
        return node.val;
    }

    private void moveToHead(Node node) {
        if (node == head) {
            return;
        } else if (node == tail) {
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        node.prev = head.prev;
        node.next = head;
        head.prev = node;
        head = node;
    }

    public void put(int key, int value) {
        Node node = map.get(key);
        if (node == null) {
            node = new Node();
            node.key = key;
            node.val = value;
            if (map.size() == capacity) {
                removeLast();
            }
            addToHead(node);
            map.put(key, node);
        } else {
            node.val = value;
            moveToHead(node);
        }
    }

    private void addToHead(Node node) {
        if (map.isEmpty()) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    private void removeLast() {
        map.remove(tail.key);
        Node prev = tail.prev;
        if (prev != null) {
            prev.next = null;
            tail = prev;
        }
    }
}
