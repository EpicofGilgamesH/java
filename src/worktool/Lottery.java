package worktool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * @author wangjie
 * @version v1.0
 * @description 摇号 模拟摇号软件
 * @date 2020/4/10 15:02800
 */
public class Lottery {


    public static void main(String[] args) {
        //1.随机生成固定数量(count)的标编号
        //2.每次进行摇号的时候在号池中抽取一个编号作为自己的编号
        //3.摇号时从号池中随机获取一个编号,与持有编号对比
        //池量
        //Integer count = 1000000;
        //指标量
        //Integer indicatorsCount = 2200;

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入总摇号数...");
        int count = scanner.nextInt();
        System.out.println("请输入总指标数...");
        int indicatorsCount = scanner.nextInt();
        //连续10次
        int totaltime = 0;
        for (int i = 0; i < 10; i++) {
            int value = test(count, indicatorsCount);
            totaltime += value;
            System.out.println("第" + (i + 1) + "次摇中期数:" + value);
        }
        System.out.println("10次摇中平均期数为:" + totaltime / 10);
    }

    private static int test(int total, int get) {
        //List<Integer> pool = new ArrayList<>();
        //伪随机池编号
        //获取1-int的最大值范围内的随机数,不能重复
        /*Random random = new Random();
        for (int i = 0; i < count; i++) {

            Integer rv = random.nextInt(Integer.MAX_VALUE - 1 + 1) + 1;
            if (!pool.contains(rv)) {
                pool.add(rv);
            } else {
                i--;
            }
        }
        Integer poolSize = pool.size();*/
        System.out.println("号码池生成成功,池数量为:" + total);

        Random random1 = new Random();
        Integer currentIndex = random1.nextInt(total + 1);
        //Integer currentValue = pool.get(currentIndex);
        System.out.println("您已取到号码:" + currentIndex);

        int j = 1;
        while (true) {
            List<Integer> getIndexList = new ArrayList<>();
            for (int i = 0; i < get; i++) {
                Random random2 = new Random();
                Integer getIndex = random2.nextInt(total + 1);
                if (!getIndexList.contains(getIndex)) {
                    getIndexList.add(getIndex);
                } else {
                    i--;
                }
            }
            boolean success = getIndexList.contains(currentIndex);
            j++;
            String msg = success ? "恭喜,您已中签" : "未中签";
            //System.out.println("第" + j + "次摇号已完成," + msg);
            if (success)
                return j;
            /*try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }
}
