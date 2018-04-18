/*
 * ��Լ���ɳڻ�
 * ����CourseTable/CourseTableSparse��generate������
 * >150, >60, >30�Ŀβ�Ҫ���޶������ʽ�㷨���������
 */

package dataSet3;

import java.util.Random;
import DataSet.Course;
import dataSet2.CourseTableSparse;

public class CourseTableSlack extends CourseTableSparse {
	public CourseTableSlack(int n1, int n2)
	{
		super(n1, n2);						// n1�� �����һ�ڿ�����; n2: ����ڶ��ڿ�����
	}
	// ����CourseTable/CourseTableSparse��generate������>150, >60, >30�Ŀβ�Ҫ���޶�
	public int generateTable()// randomly create course and add to list��������������ѡ��ѧ��
	{
		Random rand = new Random();			// ���������
		int i=0;
		int larger150 = 0;										// >150�˵Ŀ�
		int larger60 = 0;										// >60�˵Ŀ�
		int larger30 = 0;										// >30�˵Ŀ�
		int getRand = 0;	// ���ݿ����������������ƴ������Ŀη�ֹ���ҷŲ���,���Ҫ��¼ÿ�����ֵ
		int totalFirst = 0;			// �����һ�ڵ�������
		int totalSecond = 0;		// ����ڶ��ڵ�������
		for (i=0; i<num1; i++)
		{
			Course c = new Course(i);
			if(larger30==164/2)						// ��>30�˵Ŀγ����޼�Ϊһ��
			{
				getRand = 0;
			}
			else if(larger60==52/2)					// ��>60�˵Ŀγ����޼�Ϊһ��
			{
				getRand = rand.nextInt(2);
			}
			else if(larger150==6/2)					// ��>150�˵Ŀγ����޼�Ϊһ��
			{
				getRand = rand.nextInt(4);
			}
			else
			{
				getRand = rand.nextInt(6);
			}
			switch(getRand)
			{
			case 4:
			case 5:
				larger150++;
			case 2:
			case 3:
				larger60++;
			case 1:
				larger30++; break;
			default: break;
			}
			c.setCapacity(CAPACITY[getRand]);		// 6: length of const array CAPACITY
			c.setTime(0);
			firstCourse.put(i,c);					// 0: �����һ��,1: ����ڶ���
			firstID.add(i);
			totalFirst += c.getCapacity();
			courseMap.put(i,c);	
		}
		larger30 = 0;
		larger60 = 0;
		larger150 = 0;
		for (i=num1; i<num2+num1; i++)
		{
			Course c = new Course(i);
			if(larger30==164/2)									// ��>30�˵Ŀγ����޼�Ϊһ��
			{
				getRand = 0;
			}
			else if(larger60==52/2)								// ��>60�˵Ŀγ����޼�Ϊһ��
			{
				getRand = rand.nextInt(2);
			}
			else if(larger150==6/2)								// ��>150�˵Ŀγ����޼�Ϊһ��
			{
				getRand = rand.nextInt(4);
			}
			else
			{
				getRand = rand.nextInt(6);
			}
			switch(getRand)
			{
			case 4:
			case 5:
				larger150++;
			case 2:
			case 3:
				larger60++;
			case 1:
				larger30++; break;
			default: break;
			}
			c.setCapacity(CAPACITY[getRand]);			// 6: length of const array CAPACITY
			c.setTime(1);
			secondCourse.put(i,c);							// 0: �����һ��
			secondID.add(i);
			totalSecond += c.getCapacity();
			courseMap.put(i,c);	
		}
		return totalFirst<totalSecond? totalFirst:totalSecond;	// ��������֮���С��
	}
	
}
