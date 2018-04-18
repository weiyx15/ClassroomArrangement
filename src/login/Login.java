package login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 使用Jsoup模拟登陆清华大学教学门户
 * 
 * 
 * 大体思路如下:
 * 
 * 第一次请求登陆页面，获取页面信息，包含表单信息，和cookie（这个很重要），拿不到，会模拟登陆不上
 * ## 清华大学教学门户没有cookie ^_^
 * 
 * 
 * 第二次登陆，设置用户名，密码，把第一次的cookie，放进去，即可
 * 
 * 怎么确定是否登陆成功？
 * 
 * 登陆后，打印页面，会看到账户的详细信息。
 *  
 * **/

public class Login {
	public static void main(String[] args) throws Exception {
        Login loginDemo = new Login();
        loginDemo.login("da_kao_la", "System.out.println()");// 教学门户的用户名密码
    }
    /**
     * 模拟登陆教学门户
     * 
     * @param userName
     *            用户名
     * @param pwd
     *            密码
     * 
     * **/
    public void login(String userName, String pwd) throws Exception {
    	
    	String targetString = "http://info.tsinghua.edu.cn/tag.a7da6bcaf7985278.render.userLayoutRootNode.uP?uP_sparam=focusedTabID&focusedTabID=3&uP_sparam=mode&mode=view";
        // 第一次请求
        Connection con = Jsoup
                .connect(targetString);// 获取连接
        con.header("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");// 配置模拟浏览器
        Response rs = con.execute();// 获取响应
//        System.out.println(rs.body());
        Document d1 = Jsoup.parse(rs.body());// 转换为Dom树
        List<Element> et = d1.select("#login-form");// 获取form表单，可以通过F12查看页面源码代码得知
        // 获取，cooking和表单属性，下面map存放post时的数据
        Map<String, String> datas = new HashMap<String, String>();
        for (Element e : et.get(0).getAllElements()) {
            if (e.attr("name").equals("username")) {
                e.attr("value", userName);// 设置用户名
            }
            if (e.attr("name").equals("password")) {
                e.attr("value", pwd); // 设置用户密码
            }
            if (e.attr("name").length() > 0) {// 排除空值表单属性
                datas.put(e.attr("name"), e.attr("value"));
            }
        }
        /**
         * 第二次请求，post表单数据，以及cookie信息
         * 
         * **/
        Connection con2 = Jsoup
                .connect(targetString);
        con2.header("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        // 设置cookie和post上面的map数据
        Response login = con2.ignoreContentType(true).method(Method.POST)
                .data(datas).cookies(rs.cookies()).execute();
        // 打印，登陆成功后的信息
        System.out.println(login.body());
        // 登陆成功后的cookie信息，可以保存到本地，以后登陆时，只需一次登陆即可
        // 清华大学教学门户没有cookie,这段代码不会有输出
        Map<String, String> map = login.cookies();
        for (String s : map.keySet()) {
            System.out.println(s + "      " + map.get(s));
        

        }
    }
}
