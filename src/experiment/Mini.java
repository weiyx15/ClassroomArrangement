/*
 * ������mini���ݼ�:(7,7:3,3,2)�������ݼ���
 * 1. baseline�㷨
 * 2. ����ʽ�㷨
 * 3. ���Ž�
 * �Ƚ��㷨����
 */

package experiment;

import java.io.*;
import dataSet2.GenerateSparse;
import baseline.Arrange;
import algorithm1.Branch;
import optimal.Optimal;

public class Mini {
	protected static final int EXP_TIME = 30;		// �ظ�ʵ�����
	public static void main(String args[])
	{
		long startTime = System.currentTimeMillis();// program starting time
		miniExp();
		long endTime = System.currentTimeMillis();	// program ending time
		System.out.println("Run time: "+(endTime-startTime)/60000+" min");
	}
	
	// ��(7,7:3,3,2)��������baseline
	public static void miniExp()
	{
		try {
			File file = new File("MiniExperiment.txt");
			OutputStream fout = new FileOutputStream(file);
			int cost_baseline, cost_proposed, cost_optimal;
			int i = 0;
			for (i=0; i<EXP_TIME; i++)
			{
				Arrange arr = new Arrange();
				arr.clearAll();
				// ����ѡ������]
				GenerateSparse.generate(7, 7);
				// baseline�㷨
				arr.setBuildings2();
				arr.readCourseList();
				arr.readConflictVec();
				arr.naiveArrange();
				cost_baseline = arr.computeCost();
				// ����ʽ�㷨
				Branch bra = new Branch();
				bra.clearAll();
				bra.setBuildings2();
				bra.readCourseList();
				bra.readConflictVec();
				bra.initCourseLeft();
				bra.arrange1();
				cost_proposed = bra.computeCost();
				// ö�ٷ�
				Optimal opt = new Optimal();
				opt.clearAll();
				opt.readCourseList();
				opt.readConflictVec();
				opt.setBuildings2();
				cost_optimal = opt.arrange2();
				System.out.println(cost_baseline);
				System.out.println(cost_proposed);
				System.out.println(cost_optimal);
				String s0 = String.valueOf(cost_baseline);
				String s1 = String.valueOf(cost_proposed);
				String s2 = String.valueOf(cost_optimal);
				String sout = s0+"\t"+s1+"\t"+s2+"\r\n";
				fout.write(sout.getBytes());
			}
			fout.close();
		}
		catch (FileNotFoundException nfx)
		{
			nfx.printStackTrace();
			return;
		}
		catch (IOException iox)
		{
			iox.printStackTrace();
			return;
		}
	}
}
