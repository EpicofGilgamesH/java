package eight.practice;

/**
 * 栈的实现
 *
 * @Author lizzy
 * @Date 2020/6/3 10:48
 * @Version 1.0
 */
public class ArrayStack {
    //基于数组实现栈功能
    //1.先进后出原则 2.出栈和入栈在同一侧进行操作
    /**
     * 栈中的数组集合
     */
    private String[] items;

    /**
     * 栈中元素的个数
     */
    private int count;

    /**
     * 栈的大小
     */
    private int size;

    public ArrayStack(int size) {
        //初始化栈的大小
        items = new String[size];
        this.size = size;
        this.count = 0;
    }

    /**
     * 入栈操作 考虑栈满情况
     *
     * @param item
     * @return
     */
    public boolean push(String item) {
        if (this.count >= size) return false;
        //应该入栈的序列
        int index = count;
        items[index] = item;
        count++;
        return true;
    }

    /**
     * 出栈操作 出栈的数据为最后入栈的数据 考虑栈空情况
     *
     * @return
     */
    public String pop() {
        if (count == 0) return null;
        String result = items[count - 1];
        count--;
        return result;
    }

    public static void main(String[] args) {
        ArrayStack arrayStack = new ArrayStack(10);
        arrayStack.push("a");
        arrayStack.push("b");
        arrayStack.push("c");
        arrayStack.push("d");
        arrayStack.push("e");
        arrayStack.push("f");
        arrayStack.push("g");
        arrayStack.push("h");

        while (arrayStack.count > 0) {
            System.out.println(arrayStack.pop());
        }

    }
}
