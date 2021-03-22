package com.github.lib.lru;

import java.util.HashMap;

/**
 * Copyright (c) 2021 Tencent. All rights reserved.
 * 类功能描述:
 *
 * @author flynnjiao
 * @date 2021/3/22
 */
public class LruCache1 {
    // 双向链表节点定义
    class Node {
        int key;
        int val;
        Node prev;
        Node next;
    }

    private Node first;
    private Node last;
    private int max;
    private HashMap<Integer, Node> map;

    public LruCache1(int max) {
        this.max = max;
        map = new HashMap<>();
    }

    /**
     * 新数据插入连表头部
     * 最大容量,移除最后一个元素
     * 命中缓存,缓存移到头部
     *
     * @param key
     * @param value
     */
    public void put(int key, int value) {
        Node node = map.get(key);
        if (node == null) {
            node = new Node();
            node.key = key;
            node.val = value;
            if (map.size() == max) {
                removeLast();
            }
            addToHead(node);
        } else {
            moveToHead(node);
            node.val = value;
        }
    }

    private void moveToHead(Node node) {

        node.prev.next = node.next;
        node.next.prev = node.prev;

        node.next = first;
        first.prev = node;
        first = node;


    }

    private void removeLast() {
        map.remove(last.key);
        last.prev.next = null;
        last = last.prev;

    }

    private void addToHead(Node node) {
        if (map.isEmpty()) {
            first = node;
            last = node;
        } else {
            node.next = first;
            first.prev = node;
            first = node;
        }

    }
}
