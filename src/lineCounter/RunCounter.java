package lineCounter;

public class RunCounter {
	public static void main(String args[])
	{
		CountLine cl = new CountLine();
		cl.Dir("D:\\eclipse_workspace\\ClassroomArrangement\\src");	// ���̴���·��
		cl.countLine();												// ����.java����������
		System.out.println(cl.getLines());							// �������������Ļ
	}
}