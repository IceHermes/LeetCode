package com.github.leetcode.linked;

/**
 * Copyright (c) 2021 Tencent. All rights reserved.
 * 类功能描述:
 *
 * @author flynnjiao
 * @date 2021/3/15
 */
public class TanXin {
    public static void main(String[] args) {
        canJump(nums);
    }

    //给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
    //数组中的每个元素代表你在该位置可以跳跃的最大长度。
    //判断你是否能够到达最后一个下标。
    //示例 1：输入：nums = [2,3,1,1,4] 输出：true
    //解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
    //示例 2：输入：nums = [3,2,1,0,4] 输出：false
    //解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标。
    static int[] nums = {2, 3, 2, 1, 4};

    public static boolean canJump(int[] nums) {
        //能到达的最大位置k
        int k = 0;
        //遍历数组
        for (int i = 0; i < nums.length; i++) {
            //如果达不到i位置，就直接返回false
            if (k < i) return false;
            k = Math.max(k, i + nums[i]);
            System.out.println(i + ":" + k);
        }
        return true;
    }
}
