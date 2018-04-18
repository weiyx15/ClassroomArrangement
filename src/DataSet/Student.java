package DataSet;

import java.util.Map;
import java.util.Random;
import java.util.Vector;

// һ��ѧ�������ѡ����ſΣ��ȵ��ȵ�
public class Student {
	public Student(Integer ID)
	{
		sID = ID;
	}
	
	public Integer sID;											// ѧ��ID
	protected Integer course1,course2;							// ��ѧ��ѡ��2�ſ�
	// ���protected������Aclass����ʹ��
 
	public int getCourse1()
	{
		return course1;
	}
	public int getCourse2()
	{
		return course2;
	}
	public void registCourse(Map<Integer,Course> firstCourse, Map<Integer,Course> secondCourse, 
			Vector<Integer> firstID, Vector<Integer> secondID)	// һ��ѧ��ѡ2�ſ�
	{
		Random rand = new Random();								// ��������ӣ�������ɿ����
		int rand1,rand2,key1,key2;
		rand1 = rand.nextInt(firstID.size());					// ��������һ�ڿε��±�
		rand2 = rand.nextInt(secondID.size());					// �������ڶ��ڿε��±�
		key1 = firstID.get(rand1);								// �����һ�ڿεļ�ֵ
		key2 = secondID.get(rand2);								// ����ڶ��ڿεļ�ֵ
		Course c1 = firstCourse.get(key1);						// �ӿα����ҳ���������һ�ڿ�
		Course c2 = secondCourse.get(key2);						// �ӿα����ҳ��������ڶ��ڿ�
		while(c1.vetifyFull())									// ���������ѡ������һֱѭ�����
		{
			rand1 = rand.nextInt(firstID.size());					// ��������һ�ڿε��±�
			key1 = firstID.get(rand1);								// �����һ�ڿεļ�ֵ
			c1 = firstCourse.get(key1);								// �ӿα����ҳ���������һ�ڿ�
		}
		c1.beChosen();
		course1 = key1;
		while(c2.vetifyFull())										// ���������ѡ������һֱѭ�����
		{
			rand2 = rand.nextInt(secondID.size());					// ��������һ�ڿε��±�
			key2 = secondID.get(rand2);								// �����һ�ڿεļ�ֵ
			c2 = secondCourse.get(key2);							// �ӿα����ҳ���������һ�ڿ�
		}
		c2.beChosen();
		course2 = key2;
	}
}