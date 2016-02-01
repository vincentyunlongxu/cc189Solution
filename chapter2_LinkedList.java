// 2.1 Remove Dups: write code to remove duplicates from an unsorted linked list;
// first intuition: use hashset to remove duplicates
// Time Complexity is O(n)
private class ListNode {
	private int val;
	ListNode next;
	public ListNode(int val) {
		this.val = val;
		this.next = null;
	}
}
public void removeDuplicate(ListNode head) {
	if (head == null) {
		return;
	}
	Set<Integer> hset = new HashSet<>();
	hset.add(head.val);
	while (head.next != null) {
		if (hset.contains(head.next.val)) {
			head.next = head.next.next;
		} else {
			hset.add(head.next.val);
			head = head.next;
		}
	}
}
// CC189 solution: same as me, but extra pointer
public void deleteDups(ListNode n) {
	HashSet<Integer> set = new HashSet<Integer>();
	ListNode previous = null;
	while (n != null) {
		if (set.contains(n.data)) {
			previous.next = n.next;
		} else {
			set.add(n.data);
			previous = n;
		}
		n = n.next;
	}
}
// 2.1 Follow up: how would you solve this problem if a temporary buffer is not allowed?
// if no buffer available, then it will takes N^2 time to finish
public void deleteDups(ListNode head) {
	if (head == null) {
		return;
	}
	ListNode curNode = head;
	while (curNode != null) {
		ListNode checkNode = curNode;
		while (checkNode.next != null) {
			if (checkNode.next.val == curNode.val) {
				checkNode.next = checkNode.next.next;
			} else {
				checkNode = checkNode.next;
			}
		}
		curNode = curNode.next;
	}
}

// 2.2 Return Kth to last : Implement an algorithm to find the kth to last element of a singly linked list.
// idea: will have two pointers, the different between first pointer and second pointer will be k, and return second pointer will be the kth
public ListNode kthToLast(ListNode head, int k) {
	if (head == null || k < 0) {
		return null;
	}
	ListNode dummy = new ListNode(0);
	dummy.next = head;
	head = dummy;
	ListNode preNode = dummy;
	for (int i = 0; i < k; i++) {
		head = head.next;
	}
	while (head != null) {
		head = head.next;
		preNode = preNode.next;
	}
	return preNode;
}

// 2.3 Delete Middle Node: Implement an algorithm to delete a node in the middle of a singly linked list. given only access to that node
// Example: 
// Input: a -> b -> c -> d -> e
// Output: nothing returned, but the new linked list will look like: a -> b -> d -> e
public void deleteMiddle(ListNode node) {
	if (node == null) {
		return;
	}
	if (node.next != null) {
		node.val = node.next.val;
		node.next = node.next.next;
	}
}

// 2.4 Partition: Write code to partition a linked list around a value x, such that all nodes less than x come before all nodes greater than
// or equal to x. if x is contained within the list, the values of x only need to be after the elements less than x
// Example:
// Input: 3 -> 5 -> 8 -> 5 -> 10 -> 2 -> 1 [partition = 5]
// Output: 3 -> 1 -> 2 -> 10 -> 5 -> 5 -> 8
// set up two list, and combine them later
public ListNode partition(ListNode head, int x) {
	if (head == null) {
		return null;
	}
	ListNode leftDummy = new ListNode(0);
	ListNode rightDummy = new ListNode(0);
	ListNode left = leftDummy;
	ListNode right = rightDummy;
	while (head != null) {
		if (head.val < x) {
			left.next = head;
			// left = left.next;
			left = head;
		} else {
			right.next = head;
			// right = right.next;
			right = head;
		}
		head = head.next;
	}
	left.next = rightDummy.next;
	right.next = null;
	return leftDummy.next;
}

// 2.5 Sum List: You have two numbers represented by a linked list, where each node contains a single digits. The digits are stored in reverse
// order, such that 1's digit at the head of the list. Write a function that adds the two numbers and returns the sum as a linked list
// Example:
// Input: (7 -> 1 -> 6) + (5 -> 9 -> 2). That is 617 + 295
// Output: 2 -> 1 -> 9. that is 912
public ListNode sumTwoLists(ListNode head1, ListNode head2) {
	if (head1 == null && head2 == null) {
		return null;
	} else if (head1 == null) {
		return head2;
	} else if (head2 == null) {
		return head1;
	}
	int carry = 0;
	ListNode dummy = new ListNode(0);
	ListNode head = dummy;
	while (head1 != null && head2 != null) {
		int sum = head1.val + head2.val + carry;
		ListNode newNode = new ListNode(sum % 10);
		head.next = newNode;
		head = newNode;
		carry = sum / 10;
		head1 = head1.next;
		head2 = head2.next;
	}
	while (head1 != null) {
		int sum = head1.val + carry;
		ListNode newNode = new ListNode(sum % 10);
		head.next = newNode;
		head = newNode;
		carry = sum / 10;
		head1 = head1.next;
	}
	while (head2 != null) {
		int sum = head2.val + carry;
		ListNode newNode = new ListNode(sum % 10);
		head.next = newNode;
		head = newNode;
		carry = sum / 10;
		head2 = head2.next;
	}
	head.next = null;
	return dummy.next;
}

// 2.6 Palindrome: Implement a function to check if a linked list is a palindrome
public boolean isPalindrome(ListNode head) {
	if (head == null) {
		return true;
	}
	ListNode middle = findMiddle(head);
	middle.next = reverse(middle.next);
	ListNode p1 = head, p2 = middle.next;
	while (p1 != null && p2 != null) {
		p1 = p1.next;
		p2 = p2.next;
	}
	return p2 == null;
}
public ListNode findMiddle(ListNode head) {
	if (head == null) {
		return null;
	}
	ListNode slow = head, fast = head.next;
	while (fast != null && fast.next != null) {
		slow = slow.next;
		fast = fast.next.next;
	}
	return slow;
}
private ListNode reverse(ListNode head) {
	ListNode newNode = null;
	while (head != null) {
		ListNode next = head.next;
		head.next = newNode;
		newNode = head;
		head = next;
	}
	return newNode;
}

// 2.7 Intersection: Given two(singly) linked lists, determine if the two lists intersect. Return the intersecting node. Note that the intersection is
// defined based on reference, not value. That is, if the kth node of the first linked list is the exact same node (by reference) as the jth node of the
// second linked list, then they are intersecting
public ListNode findIntersection(ListNode l1, ListNode l2) {
	if (l1 == null || l2 == null) {
		return null;
	}
	ListNode node1 = l1;
	ListNode node2 = l2;
	while (node1.next != null) {
		node1 = node1.next;
	}
	node1.next = node2;
	ListNode result = helper(l1);
	node1.next = null;
	return result;
}
public ListNode helper(ListNode head) {
	ListNode slow = head;
	ListNode fast = head.next;
	while (slow != fast) {
		if (fast == null || fast.next == null) {
			return null;
		}
		slow = slow.next;
		fast = fast.next.next;
	}
	slow = head;
	fast = fast.next;
	while (slow != fast) {
		slow = slow.next;
		fast = fast.next;
	}
	return slow;
}

// 2.8 Loop Detection: Given a circular linked List, implement an algorithm that returns the node at the beginning of the loop
public ListNode findNode(ListNode head) {
	if (head == null) {
		return head;
	}
	ListNode slow = head;
	ListNode fast = head.next;
	while (slow != fast) {
		if (fast == null || fast.next == null) {
			return null;
		}
		slow = slow.next;
		fast = fast.next;
	}
	slow = head;
	fast = fast.next;
	while (slow != fast) {
		slow = slow.next;
		fast = fast.next;
	}
	return slow;
}