/*
 * ����ö�ٷ���cost��С�Ľ⣺
 * �����㷨ʱ�临�Ӷȵ����ƣ�ֻ����5�ڵ�һ�ڿΡ�5�ڵڶ��ڿΡ�3��ѧ¥6���ҵ�miniģ��
 * ����setBuildings2()��������builings���COST����
 */

package optimal;

import java.util.*;
import baseline.Arrange;
import baseline.CourseRead;

public class Optimal extends Arrange{
//	public static void main(String args[])
//	{
//		int minCost = 0;					// ��Сcost����
//		setBuildings2();
//		readCourseList();
//		readConflictVec();
//		long startTime = System.currentTimeMillis();	// program starting time
//		minCost = arrange2();
//		long endTime = System.currentTimeMillis();	// program ending time
//		System.out.println("Run time: "+(endTime-startTime)/1000+" s");
//		System.out.println("Cost function: "+minCost);
//	}
	
	public int arrange2()
	/*
	 * �ſ��㷨2��
	 * ����ö�������Ž�
	 * ������С��costֵ
	 */
	{
		Vector<CourseRead> allSet = new Vector<CourseRead>();	// �ϲ�firstSet��secondSet
		for (CourseRead c1 : firstSet)
		{
			allSet.add(c1);
		}
		for (CourseRead c2 : secondSet)
		{
			allSet.add(c2);
		}
		// enu: �����������������, enu[i] = 0~4����5����ѧ¥
		// pnu: enu��ǰһ������enuһ����Կ̻�������
		int enu[] = new int[firstSet.size()+secondSet.size()];
		int i = 0;
		int len = enu.length;			// ��һ�ڿκ͵ڶ��ڿογ�����
		int b_len = buildings.size();	// ��ѧ¥����
		for (i=0; i<len; i++)
		{
			enu[i] = 0;
		}
		int costMin = Integer.MAX_VALUE;	// ά����С��costֵ
		int costThis = Integer.MAX_VALUE;	// ��ǰ���ŷ�ʽ�µ�costֵ
		boolean feasible = true;			// �ý��Ƿ��ǿ��н�
		int cnt = 0;						// ö�ټ�����
		int divider = 1;
		int total = 1;						// �ܵ��������
		for (i=0; i<len; i++)
		{
			total *= b_len;
		}
		while (cnt < total)					// �������п��ܵ����
		{
			divider = 1;
			for (i=0; i<len; i++)
			{
				enu[i] = (cnt%(divider*b_len))/divider;
				divider *= b_len;
			}
			cnt++;
			System.out.println(cnt+"/"+total);
			// ������н�ѧ¥��ԭ�пγ�, Ϊ��ѧ¥�ָ�ԭ״
			buildings.clear();				// ���buildings
			COST.clear();					// ���COST
			setBuildings2();				// �������ɽ�ѧ¥
			feasible = true;				// һ���½���Ĭ�����ǿ��н�
			for (i=0; i<len; i++)
			{
				CourseRead cr = allSet.get(i);
				if(!buildings.get(enu[i]).useRoom(cr))
				{
					feasible = false;		// �ýⲻ����
					break;
				}
			}
			if (feasible)					// ����ǿ��н�
			{
				costThis = computeCost();	// ���㵱ǰ���costֵ
				System.out.println("current cost: "+costThis);
				costMin = costMin < costThis? costMin : costThis;	// ������С��costֵ
			}
		}
		return costMin;
	}
}
