/*
 * �ӿ�����Ϣ����data.xls�ļ��ж�ȡÿһ��ʱ��(11,12,...)�Ŀγ����Ϳγ�����
 */

package loadExcel;

import java.io.*;
import jxl.Sheet;  
import jxl.Workbook;  
import jxl.read.biff.BiffException;

public class LoadExcel {
	public int loadExcel(String ctime)	// ����������Ͽ�ʱ��Σ���"11"��ʾ�����һ��
	{
		try {
			int cnt = 0;				// ��ʱ��εĿγ�����
			InputStream fis = new FileInputStream(new File("data.xls"));// �����ֽ���
			Workbook wb = Workbook.getWorkbook(fis);					// Excel Workbook��
			Sheet sheet = wb.getSheet(0);	// ��ǩҳ��getSheet()���Ǳ�ǩҳ��
			int i = 0;
			for (i=0; i<sheet.getRows(); i++)
			{
				String depart = sheet.getCell(0,i).getContents();	// ������(column,row)
				String courseName = sheet.getCell(3,i).getContents();// �����Ľ�����ַ���
				String ttime = sheet.getCell(7,i).getContents();
				if (ttime.contains(ctime) && !depart.contains("����ѧԺ"))
					// ����ѧԺ���ڽ�ѧ¥�ϿΣ�Ҫȥ��
				{
					System.out.println(depart+"\t"+courseName+"\t"+ttime);
					cnt++;
				}
			}
			return cnt;
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
			return -1;						// file error ����-1��ʾ��ȡ����
		}
		catch (BiffException e)
		{
			e.printStackTrace();			// Biff error
			return -1;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return -1;						// IO error
		}
	}
}
