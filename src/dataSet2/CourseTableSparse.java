/*
 * DataSet.CourseTable生成的课表由于每个学生都可以自主独立选课，一门课的课容量在30~200之间，
 * 而每个时间段总共有将近200门课，导致两个时间段两门课重叠的人数绝大部分只有1，最多有3
 * 这样的网络连接非常dense，但每条连接的权值非常light; # 这与实际情况很不相符
 * 欲生成更sparse, heavy-weighted的网络，采用“学生先选课，根据选课结果确定每门课的课容量”的选课方法
 * 新建Aclass类模拟“班级”，一个班级的学生选一样的2门课程，控制班级人数就能控制2门课程重叠的人数
 * 
 */

package dataSet2;

import java.util.*;
import DataSet.CourseTable;

public class CourseTableSparse extends CourseTable {
	public CourseTableSparse(int n1, int n2)
	{
		super(n1, n2);						// n1： 上午第一节课门数; n2: 上午第二节课门数
	}
	
	private Vector<Integer> ClassTable = new Vector<Integer>();	// 班级人数向量
	
	// 重载学生选课函数
	public void students()										// 学生们选课
	{
		generateClassTable();
		int i = 0;												// counter计数器
		for (i=0; i<ClassTable.size(); i++)
		{
			Aclass ac = new Aclass(i,ClassTable.get(i));
			ac.registCourse(firstCourse, secondCourse, conflictMap, firstID, secondID);
		}
	}
	private void generateClassTable()		// 生成班级学生人数向量
	{
		int sRemain = snum;					// 剩下还没选课的学生数
		Integer acNum = 70;// acNum: 班级人数，从70开始每次循环递减直到1,最后剩下来的都是一个人一个班
		int times = 0;		// 通过times计数让acNum
		while(sRemain>=acNum)
		{
			ClassTable.add(acNum);
			sRemain -= acNum;
			if(acNum>1)			// acNum到1后就不动了，一直是1
			{
				if(times < 3)	// 相同人数的班级重复出现3次
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
