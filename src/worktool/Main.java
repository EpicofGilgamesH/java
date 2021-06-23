package worktool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        long mask = (1L << 48); //48位全为1的二进制数
        System.out.println(mask);
        int test = (1 << 31);
        //System.out.println(test);
        double double_unit = 0x1.0p-23;//十六进制1.0*2的-23次方
        System.out.println(double_unit);

        AtomicLong atomicLong = new AtomicLong(8682522807148012L);
        //按位异或 相同取0,不同取1
        //0异或其他数=其他数,1异或其他数=其k他数取反
        Long value = atomicLong.get() ^ System.nanoTime();
        System.out.println(value);

        //二进制运算的5中运算方式 与,或,异或,左移,右移


        /*List<String> list = new ArrayList<>();
        list.add("/bc");
        list.add("/bc/abc");
        boolean bc = list.contains("abc");

        String url = "/bc/deliverTask/page,/bc/waybillOrder/getDetailByNo,/bc/order/detail,/bc/index/taskPickUp,/bc/index/taskReceipt";
        boolean contains = url.contains("/index/taskPickUp");
        System.out.println(contains);*/
        String requestURI = "/outer/out/login";
        String substring = requestURI.substring(requestURI.indexOf("/", 1), requestURI.length());
        System.out.println("url:" + substring);

        Integer i = 71;
        String format = String.format("%04d", i);
        System.out.println(format);

        Long start = System.currentTimeMillis();

        String name1 = " 古 力 娜 扎 ";
        String name2 = "刘 德华";
        String name3 = "";

        String name4 = "James";
        String name5 = "Lebron Raymore James";

        String name6 = "James张";
        String name7 = "James 张";

        System.out.println("原姓名:" + name1);
        System.out.println("脱敏后:" + desensitization(name1));
        System.out.println("原姓名:" + name2);
        System.out.println("脱敏后:" + desensitization(name2));
        System.out.println("原姓名:" + name3);
        System.out.println("脱敏后:" + desensitization(name3));

        System.out.println("原姓名:" + name4);
        System.out.println("脱敏后:" + desensitization(name4));
        System.out.println("原姓名:" + name5);
        System.out.println("脱敏后:" + desensitization(name5));

        System.out.println("原姓名:" + name6);
        System.out.println("脱敏后:" + desensitization(name6));
        System.out.println("原姓名:" + name7);
        System.out.println("脱敏后:" + desensitization(name7));

        System.out.println("耗时:" + (System.currentTimeMillis() - start));

        List<String> list = new ArrayList<>(Arrays.asList("pickNetworkName "
                , "deliveryTime    "
                , "collectStaffCode"
                , "collectStaffName"
                , "collectTime     "
                , "customerId      "
                , "customerCode    "
                , " customerName    "
                , " expressTypeId   "
                , "expressTypeCode "
                , "expressTypeName "
                , "dispatchCode    "));
        /*List<String> collect = list.stream().map(x -> trim(x))
                .map(x -> test(x)).collect(Collectors.toList());*/


        int cap = 256;
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;

        System.out.println(n);
    }


    private static String trim(String str) {
        String trim = str.trim();
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("trim时间:" + System.currentTimeMillis());
        return trim;
    }

    private static String test(String str) {
        str += ";";
        System.out.println("test时间:" + System.currentTimeMillis());
        return str;
    }

    private static String desensitization(String str) {
        if (str == null || str == "")
            return "";
        str = str.trim();
        if (isAlpha(str)) {
            //a-z
            int index = str.trim().indexOf(" ");
            if (index == -1)
                return str.substring(0, 1) + linkAsterisk(str.length() - 1);
            else {
                String span = " ";
                String[] s = str.split(span);
                String s1 = s[0];
                for (int i = 1; i < s.length; ++i) {
                    s1 += span + linkAsterisk(s[i].length());
                }
                return s1;
            }
        } else
            return str.substring(0, 1) + linkAsterisk(str.length() - 1);
    }

    private static boolean isAlpha(String str) {
        str = str.replaceAll(" ", "");
        if (str == null) return false;
        return str.matches("[a-zA-Z]+");
    }

    private static String linkAsterisk(int count) {
        String asterisk = "*";
        String result = "";
        for (int i = 0; i < count; ++i) {
            result += asterisk;
        }
        return result;
    }
}
