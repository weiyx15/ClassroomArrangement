package connection;

public class RunConnection {
	public static void main(String args[])
	{
		String url = "http://academic.tsinghua.edu.cn/";
		String creeped = ConnectionUtil.Connect(url);
		System.out.println(creeped);
	}
}
