/*
 * �����������廪��ʵ���ݼ���
 * 1. baseline�㷨
 * 2. ����ʽ�㷨
 * �Ƚ����㷨����������ʽ�㷨������
 */

package experiment;

import java.io.*;
//import DataSet.Generate;
//import dataSet2.GenerateSparse;
import dataSet3.GenerateSlack;
import baseline.Arrange;
import algorithm1.Branch;

public class Real {
	protected static final int EXP_TIME = 1000;		// �ظ�ʵ�����
	public static void main(String args[])
	{
		long startTime = System.currentTimeMillis();// program starting time
		realExp();
//		Branch bra = new Branch();
//		bra.clearAll();
//		bra.setBuildings();
//		bra.readCourseList();
//		bra.readConflictVec();
//		bra.initCourseLeft();
//		bra.arrange1();
//		int cost_proposed = bra.computeCost();
//		System.out.println(cost_proposed);
//		Arrange arr = new Arrange();
//		arr.clearAll();
//		arr.setBuildings();
//		arr.readCourseList();
//		arr.readConflictVec();
//		arr.naiveArrange();
//		int cost_baseline = arr.computeCost();
//		System.out.println(cost_baseline);
		long endTime = System.currentTimeMillis();	// program ending time
		System.out.println("Run time: "+(endTime-startTime)/60000+" min");
	}
	
	// ����ʵѡ�����ݼ������廪��ѧ¥��ʵģ�ͣ�ֻ��baseline������ʽ�㷨
	public static void realExp()
	{
		try {
			File file = new File("RealExperiment.txt");
			OutputStream fout = new FileOutputStream(file);
			int cost_baseline, cost_proposed;			// ����ʵ���㷨1���㷨2�Ľ�
			int baseline_mean = 0, proposed_mean = 0;	// EXP_TIME��ʵ���ƽ��ֵ
			int i = 0;
			for (i=0; i<EXP_TIME; i++)
			{
				System.out.println((i+1)+"/"+EXP_TIME);
				// ����ѡ������
				GenerateSlack.generate(120,120);
				// baseline�㷨
				Arrange arr = new Arrange();
				arr.clearAll();
				arr.setBuildings();
				arr.readCourseList();
				arr.readConflictVec();
				arr.naiveArrange();
				cost_baseline = arr.computeCost();
				baseline_mean = (i*baseline_mean + cost_baseline)/(i+1);
				// ����ʽ�㷨
				Branch bra = new Branch();
				bra.clearAll();
				bra.setBuildings();
				bra.readCourseList();
				bra.readConflictVec();
				bra.initCourseLeft();
				bra.arrange1();
				cost_proposed = bra.computeCost();
				proposed_mean = (i*proposed_mean + cost_proposed)/(i+1);
//				System.out.println(cost_baseline);
//				System.out.println(cost_proposed);
				String s0 = String.valueOf(cost_baseline);
				String s1 = String.valueOf(cost_proposed);
				String sout = s0+"\t"+s1+"\r\n";
				fout.write(sout.getBytes());
			}
			System.out.println("baseline mean: "+baseline_mean);
			System.out.println("proposed mean: "+proposed_mean);
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
