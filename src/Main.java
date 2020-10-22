import java.util.concurrent.atomic.AtomicLong;

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
    }
}
