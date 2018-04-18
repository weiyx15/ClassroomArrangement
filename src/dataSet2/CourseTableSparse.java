/*
 * DataSet.CourseTable���ɵĿα�����ÿ��ѧ����������������ѡ�Σ�һ�ſεĿ�������30~200֮�䣬
 * ��ÿ��ʱ����ܹ��н���200�ſΣ���������ʱ������ſ��ص����������󲿷�ֻ��1�������3
 * �������������ӷǳ�dense����ÿ�����ӵ�Ȩֵ�ǳ�light; # ����ʵ������ܲ����
 * �����ɸ�sparse, heavy-weighted�����磬���á�ѧ����ѡ�Σ�����ѡ�ν��ȷ��ÿ�ſεĿ���������ѡ�η���
 * �½�Aclass��ģ�⡰�༶����һ���༶��ѧ��ѡһ����2�ſγ̣����ư༶�������ܿ���2�ſγ��ص�������
 * 
 */

package dataSet2;

import java.util.*;
import DataSet.CourseTable;

public class CourseTableSparse extends CourseTable {
	public CourseTableSparse(int n1, int n2)
	{
		super(n1, n2);						// n1�� �����һ�ڿ�����; n2: ����ڶ��ڿ�����
	}
	
	private Vector<Integer> ClassTable = new Vector<Integer>();	// �༶��������
	
	// ����ѧ��ѡ�κ���
	public void students()										// ѧ����ѡ��
	{
		generateClassTable();
		int i = 0;												// counter������
		for (i=0; i<ClassTable.size(); i++)
		{
			Aclass ac = new Aclass(i,ClassTable.get(i));
			ac.registCourse(firstCourse, secondCourse, conflictMap, firstID, secondID);
		}
	}
	private void generateClassTable()		// ���ɰ༶ѧ����������
	{
		int sRemain = snum;					// ʣ�»�ûѡ�ε�ѧ����
		Integer acNum = 70;// acNum: �༶��������70��ʼÿ��ѭ���ݼ�ֱ��1,���ʣ�����Ķ���һ����һ����
		int times = 0;		// ͨ��times������acNum
		while(sRemain>=acNum)
		{
			ClassTable.add(acNum);
			sRemain -= acNum;
			if(acNum>1)			// acNum��1��Ͳ����ˣ�һֱ��1
			{
				if(times < 3)	// ��ͬ�����İ༶�ظ�����3��
				{
					times ++;
				}
				else
				{
					times = 0;
					acNum --;
				}
			}
		}
	}
	
}
