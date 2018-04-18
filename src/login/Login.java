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
 * ʹ��Jsoupģ���½�廪��ѧ��ѧ�Ż�
 * 
 * 
 * ����˼·����:
 * 
 * ��һ�������½ҳ�棬��ȡҳ����Ϣ����������Ϣ����cookie���������Ҫ�����ò�������ģ���½����
 * ## �廪��ѧ��ѧ�Ż�û��cookie ^_^
 * 
 * 
 * �ڶ��ε�½�������û��������룬�ѵ�һ�ε�cookie���Ž�ȥ������
 * 
 * ��ôȷ���Ƿ��½�ɹ���
 * 
 * ��½�󣬴�ӡҳ�棬�ῴ���˻�����ϸ��Ϣ��
 *  
 * **/

public class Login {
	public static void main(String[] args) throws Exception {
        Login loginDemo = new Login();
        loginDemo.login("da_kao_la", "System.out.println()");// ��ѧ�Ż����û�������
    }
    /**
     * ģ���½��ѧ�Ż�
     * 
     * @param userName
     *            �û���
     * @param pwd
     *            ����
     * 
     * **/
    public void login(String userName, String pwd) throws Exception {
    	
    	String targetString = "http://info.tsinghua.edu.cn/tag.a7da6bcaf7985278.render.userLayoutRootNode.uP?uP_sparam=focusedTabID&focusedTabID=3&uP_sparam=mode&mode=view";
        // ��һ������
        Connection con = Jsoup
                .connect(targetString);// ��ȡ����
        con.header("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");// ����ģ�������
        Response rs = con.execute();// ��ȡ��Ӧ
//        System.out.println(rs.body());
        Document d1 = Jsoup.parse(rs.body());// ת��ΪDom��
        List<Element> et = d1.select("#login-form");// ��ȡform��������ͨ��F12�鿴ҳ��Դ������֪
        // ��ȡ��cooking�ͱ����ԣ�����map���postʱ������
        Map<String, String> datas = new HashMap<String, String>();
        for (Element e : et.get(0).getAllElements()) {
            if (e.attr("name").equals("username")) {
                e.attr("value", userName);// �����û���
            }
            if (e.attr("name").equals("password")) {
                e.attr("value", pwd); // �����û�����
            }
            if (e.attr("name").length() > 0) {// �ų���ֵ������
                datas.put(e.attr("name"), e.attr("value"));
            }
        }
        /**
         * �ڶ�������post�����ݣ��Լ�cookie��Ϣ
         * 
         * **/
        Connection con2 = Jsoup
                .connect(targetString);
        con2.header("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        // ����cookie��post�����map����
        Response login = con2.ignoreContentType(true).method(Method.POST)
                .data(datas).cookies(rs.cookies()).execute();
        // ��ӡ����½�ɹ������Ϣ
        System.out.println(login.body());
        // ��½�ɹ����cookie��Ϣ�����Ա��浽���أ��Ժ��½ʱ��ֻ��һ�ε�½����
        // �廪��ѧ��ѧ�Ż�û��cookie,��δ��벻�������
        Map<String, String> map = login.cookies();
        for (String s : map.keySet()) {
            System.out.println(s + "      " + map.get(s));
        

        }
    }
}
