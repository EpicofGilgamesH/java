package worktool;

/**
 * @author wangjie
 * @version v1.0
 * @description 123
 * @date 2020/5/3 10:16
 */
public class ParamMethod {

    public static void initializeDateSource(Boolean... isExchange) {
        Boolean current = isExchange[0];
        System.out.println(current);
    }

    public static void main(String[] args) {
        initializeDateSource(true);

    }
}
