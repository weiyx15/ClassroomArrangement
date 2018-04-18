package lineCounter;

public class RunCounter {
	public static void main(String args[])
	{
		CountLine cl = new CountLine();
		cl.Dir("D:\\eclipse_workspace\\ClassroomArrangement\\src");	// 工程代码路径
		cl.countLine();												// 计算.java代码总行数
		System.out.println(cl.getLines());							// 把行数输出到屏幕
	}
}