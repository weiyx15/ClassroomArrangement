package gui;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import baselineFromFile.CourseLoad;

public class GUI {
	static final int WIDTH = 1000;								// ҳ����
	static final int HEIGHT = 1200;								// ҳ��߶�
	static public String ctime = "11";							// ��ǰ��ʾ�Ŀα��ʱ���
	static public final String[] head = {"�γ���","��ѧ¥","���Һ�"};// ��ͷ
	static public DefaultTableModel model = new DefaultTableModel();	// �������
	public static void main(String args[])
	{
		JFrame jf = new JFrame
		("���ڶ���ͼȾɫ�Ľ��Ҽ��л��ſ�ϵͳ  κ���� 2018��4��17��");	// ��������
		jf.setSize(WIDTH, HEIGHT);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel contentPane = new JPanel();						// �м�����
		contentPane.setLayout(new GridLayout(1,2));				// ������򲼾�
		jf.setContentPane(contentPane);							// �����������
		// ҳ��1�����ҳ��
		JPanel jp1 = new JPanel();								// ���ҳ�棬�Ű�ť
		jp1.setLayout(new GridLayout(1,2));						// ������2��					
		// ���ҳ�����ҳ��
		JPanel jp11 = new JPanel();
		JPanel jp12 = new JPanel();
		jp11.setLayout(new GridLayout(11,1));
		jp12.setLayout(new GridLayout(11,1));
		jp1.add(jp11);
		jp1.add(jp12);
		// ����
		JLabel jl = new JLabel("2017-2018����ѧ��");				// �̶������ֱ�ǩ
		jl.setFont(new Font(null, Font.PLAIN, 25));				// ��������
		jp11.add(jl);
		// ��ť
		JButton b_do = new JButton("�ſ�");						// "�ſ�"��ť
		b_do.setFont(new Font(null, Font.PLAIN, 20));			// ���ð�ť�����ֵ�����
		jp12.add(b_do);
		ActionListener ac_do = new ActionDo();					// "�ſ�"��ť�¼�������
		b_do.addActionListener(ac_do);							// ���¼�Դ"�ſ�"��ťע��
		// ��ѡ��ť
		int i = 0, j1Num = CourseLoad.FIRSTTABLE.length, 
		j2Num = CourseLoad.SECONDTABLE.length;
		ButtonGroup bg = new ButtonGroup();						// ���ⵥѡ��ť��
		JRadioButton[] jr = new JRadioButton[j1Num+j2Num];		// ��ѡ��ť����
		ActionListener[] ac_jr = new ActionSwitchTime[j1Num+j2Num];// ��ѡ��ť�¼�������
		for (i=0; i<j1Num; i++)
		{
			jr[i] = new JRadioButton(CourseLoad.FIRSTTABLE[i]);	// ���õ�ѡ��ť�ı�
			bg.add(jr[i]);										// ���뻥�ⰴť��
			jp11.add(jr[i]);									// �������
			ac_jr[i] = new ActionSwitchTime(i);					// ���ⰴť�¼�������,����Ϊi
			jr[i].addActionListener(ac_jr[i]);					// ���¼�Դע��
		}
		for (i=0; i<j2Num; i++)
		{
			jr[i+j1Num] = new JRadioButton(CourseLoad.SECONDTABLE[i]);// ���õ�ѡ��ť�ı�
			bg.add(jr[i+j1Num]);							// ���뻥�ⰴť��
			jp12.add(jr[i+j1Num]);							// �����Ҳ�
			ac_jr[i+j1Num] = new ActionSwitchTime(i+j1Num);	// ���ⰴť�¼�������,����Ϊi+j1Num
			jr[i+j1Num].addActionListener(ac_jr[i+j1Num]);	// ���¼�Դע��
		}	
		JTable disp = new JTable(model);		// ����������ʾ"�γ̣���ѧ¥������"	
		disp.setSize(400,1200);
		// ҳ��2���Ҳ�ҳ��
		JScrollPane scroll = new JScrollPane(disp);				// �ѱ����ڹ����������
		scroll.setBounds(20,20,400,1000);							
		contentPane.add(jp1);
		contentPane.add(scroll);
		jf.setVisible(true);				// ����������غ�����дjf.setVisible(true)
	}
}