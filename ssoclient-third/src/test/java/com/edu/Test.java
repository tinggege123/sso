package com.edu;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static ListNode ReverseList(ListNode head) {
        if(head == null){
            return null;
        }
        List data = new ArrayList<Integer>();
        while(head.next != null){
            data.add(head.val);
            head = head.next;
        }
        ListNode result = new ListNode(head.val);
        ListNode lastResult = result;
        int size = data.size();
        for(int i = size;i>0;i--){
            result.next =  new ListNode((Integer) data.get(size-1));
            result = result.next;
        }
        return lastResult;
    }

    public static void main(String []args){
        ListNode data = new ListNode(1);
        ListNode d = data;
        data.next = new ListNode(2);
        data = data.next;
        data.next = new ListNode(3);
        data = data.next;
        data.next = new ListNode(4);
        data = data.next;
        data.next = new ListNode(5);
        data = data.next;
        Test.ReverseList(d);

    }
}