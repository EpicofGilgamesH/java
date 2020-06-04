package six.practice;

/**
 * 回文算法
 * 思路：1.使用链表来判断回文,将字符串放入单向链表中
 * 2.使用快慢指(p,q)针的方式来判断链表的中点,区分奇偶.
 * 2.1.如果q指针的next为null,则链长为奇数:left linked为p指针向左移动一位后反转,right linked为p指针向右移动一位
 * 2.2.如果q指针为null,则链长为偶数:left linked为p指针向左移动一位后反转,right linked 为p指针开始
 *
 * @Author lizzy
 * @Date 2020/6/2 11:10
 * @Version 1.0
 */
public class Palindrome {


    public static boolean isPalindrome(String str) {
        MyLinked<String> myLinked = new MyLinked<>();
        for (int i = 0; i < str.length(); i++) {
            myLinked.add(str.substring(i, i + 1));
        }
        MyLinked.Node p = myLinked.head;
        MyLinked.Node q = myLinked.head;
        if (p.next == null) {
            System.out.println("只有一个元素");
        }
        while (q.next != null && q.next.next != null) {
            p = p.next;
            q = q.next.next;
        }
        MyLinked.Node leftLink;
        MyLinked.Node rightLink;
        //偶数链
        if (q.next == null) {
            leftLink = myLinked.inverseLinkList(p, false);
            rightLink = p.next;
        } else {
            leftLink = myLinked.inverseLinkList(p, true);
            rightLink = p.next;
        }
        return myLinked.compare(leftLink, rightLink);
    }

    public static void main(String[] args) {
        String palindrome = "aabcbaa";
        boolean flag = isPalindrome(palindrome);
        System.out.println(flag);

        //字符串比较
        Object a="a";
        Object b="a";
        System.out.println(a==b);
        System.out.println(a.equals(b));
    }

}
