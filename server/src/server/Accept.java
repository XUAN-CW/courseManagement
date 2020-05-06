package server;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * 重定向的特点:redirect
 *     1. 地址栏发生变化
 *     2. 重定向可以访问其他站点(服务器)的资源
 *     3. 重定向是两次请求。不能使用request对象来共享数据
 */

@WebServlet("/server")
class Accept extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(new Date());

//        System.out.println(request.getHeader("identity"));
//        Controller c=new Controller();
//        c.parse(request.getHeader("s"));
////        c.getResult();
//
//        OutputStream os=response.getOutputStream();
//        os.write(c.getResult().getBytes());
//        os.flush();
//        os.close();

    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
