/*
 * һ����ѧ����ɵİ༶�������ѧ����ѡһ���Ŀ�
 * ȡ�ɣ� Aclass����Դ�DataSet.Student�̳�
 */

package dataSet2;

import java.util.Map;
import java.util.Random;
import java.util.TreeSet;
import java.util.Vector;

import DataSet.Course;
import DataSet.Student;

public class Aclass extends Student{
	public Aclass(Integer ID, Integer sNum)
	{
		super(ID);
		studentNum = sNum;
	}
	
	private Integer studentNum;					// һ���༶��ѧ������
	
	public Integer getStudentNum()
	{
		return studentNum;
	}
	// ����registCourse����
	public void registCourse(Map<Integer,Course> firstCourse, 
			Map<Integer,Course> secondCourse, Map<TreeSet<Integer>,Integer> conflictMap,
			Vector<Integer> firstID, Vector<Integer> secondID)	// һ���༶ͬʱѡ2�ſ�
	{
		Random rand = new Random();								// ��������ӣ�������ɿ����
		int rand1,rand2,key1,key2;
		int i=0;	// i: �༶ѧ����������
		Vector<Integer> courses1 = new Vector<Integer>();	// ������ѧ��ѡ�������һ�ڿ��б�
		Vector<Integer> courses2 = new Vector<Integer>();	// ������ѧ��ѡ������ڶ��ڿ��б� 
		while(i<studentNum)									// ���������ѡ������һֱѭ�����
		{
			rand1 = rand.nextInt(firstID.size());				// ��������һ�ڿε��±�
			key1 = firstID.get(rand1);							// �����һ�ڿεļ�ֵ
			Course c1 = firstCourse.get(key1);					// �ӿα����ҳ���������һ�ڿ�
			label1: for (; i<studentNum; i++)					// һ����һ��ѡ��
			{
				if(!c1.beChosen())								// һ����ѡ��һ�룬��ѡ����
				{
					break label1;
				}
				else
				{
					courses1.add(key1);							// ѡ��key1
				}
			}
//			System.out.println(i);
		}
		i=0;	// �༶ѧ�������������¹���
		while(i<studentNum)									// ���������ѡ������һֱѭ�����
		{
			rand2 = rand.nextInt(secondID.size());			// ��������һ�ڿε��±�
			key2 = secondID.get(rand2);						// �����һ�ڿεļ�ֵ
			Course c2 = secondCourse.get(key2);				// �ӿα����ҳ���������һ�ڿ�
			label2: for (; i<studentNum; i++)				// һ����һ��ѡ��
			{
				if(!c2.beChosen())					// һ����ѡ��һ��ѡ����
				{
					break label2;
				}
				else
				{
					courses2.add(key2);				// ѡ��key2
				}
			}
		}
		// ����conflictMap
		Integer oldValue = 0;
		for (i=0; i< studentNum; i++)
		{
			TreeSet<Integer> con = new TreeSet<Integer>();
			con.add(courses1.get(i));
			con.add(courses2.get(i));
			if(conflictMap.get(con)==null)						// ��ѯ������ֵ�����½�һ����Ŀ
			{
				conflictMap.put(con,1);
			}
			else												// ��ֵ������value+1
			{
				oldValue = conflictMap.get(con);
				conflictMap.put(con,oldValue+1);
			}
		}
	}
}