package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import baselineFromFile.CourseLoad;

public class ActionSwitchTime implements ActionListener			
// ���ݰ��µĵ�ѡ��ť�ı�Ÿı�ctime����ʾ�Ŀα��ʱ���
{
	public int i;					// ��ѡ��ť���
	public ActionSwitchTime(int ii)	// ���õ�ѡ��ť�ı��
	{
		i = ii;
	}
	public void actionPerformed(ActionEvent e)	// ���ݰ�ť��Ÿı�α�ʱ����ַ���
	{
		int len1 = CourseLoad.FIRSTTABLE.length;
		if (i < len1)
		{
			GUI.ctime = CourseLoad.FIRSTTABLE[i];// ���һ�а�ť
		}
		else
		{
			GUI.ctime = CourseLoad.SECONDTABLE[i-len1];	// �ұ�һ�а�ť
		}
		loadResult();							// ���¼��ص�ǰʱ��ε��ſν��
	}
	public void loadResult()					// ���ݵ�ǰctime��ֵ��excel�ļ��м����ſ�����
	{
		String fn = GUI.ctime + ".xls";			// ctime��Ӧ�Ľ�����ļ���
		int i = 0;
		try {
			File ff = new File(fn);
			FileInputStream fis = new FileInputStream(ff);
			Workbook wb = Workbook.getWorkbook(fis);	// Excel Workbook��
			Sheet sheet = wb.getSheet(0);	// ��ǩҳ��getSheet()���Ǳ�ǩҳ��
			String result[][] = new String[sheet.getRows()][3];	// ��ʼ��result�����С
			for (i=0; i<sheet.getRows(); i++)
			{
				result[i][0] = sheet.getCell(1,i).getContents();			// �γ���
				result[i][1] = sheet.getCell(4,i).getContents();			// ��ѧ¥��
				result[i][2] = sheet.getCell(5,i).getContents();			// ���Һ�
			}
			wb.close();
			GUI.model.setDataVector(result, GUI.head);	// �޸�model�е����ݣ��������¼�������
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		catch (BiffException e)
		{
			e.printStackTrace();			// Biff error
		}
        catch (IOException e) {
            e.printStackTrace();
        }
	}
}