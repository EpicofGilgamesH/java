package controller;

import annotation.MyAutowired;
import annotation.MyController;
import annotation.MyRequestMapping;
import annotation.MyRequsetParam;
import service.TestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author wangjie
 * @version v1.0
 * @description
 * @date 2020/5/11 10:45
 */
@MyController
@MyRequestMapping("/my")
public class Contorller {

    @MyAutowired
    private TestService testService;

    @MyRequestMapping("/test")
    public String testActino(HttpServletRequest request, HttpServletResponse response,
                             @MyRequsetParam("a") String a, @MyRequsetParam("b") Integer b) {
        String result = "";
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            result = testService.getInfo(a, b);
            pw.write(result);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
