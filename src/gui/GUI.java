package gui;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import baselineFromFile.CourseLoad;

public class GUI {
	static final int WIDTH = 1000;								// 页面宽度
	static final int HEIGHT = 1200;								// 页面高度
	static public String ctime = "11";							// 当前显示的课表的时间段
	static public final String[] head = {"课程名","教学楼","教室号"};// 表头
	static public DefaultTableModel model = new DefaultTableModel();	// 表格内容
	public static void main(String args[])
	{
		JFrame jf = new JFrame
		("基于二分图染色的教室集中化排课系统  魏宇轩 2018年4月17日");	// 顶层容器
		jf.setSize(WIDTH, HEIGHT);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel contentPane = new JPanel();						// 中间容器
		contentPane.setLayout(new GridLayout(1,2));				// 总体横向布局
		jf.setContentPane(contentPane);							// 设置内容面板
		// 页面1：左侧页面
		JPanel jp1 = new JPanel();								// 左侧页面，放按钮
		jp1.setLayout(new GridLayout(1,2));						// 分左右2列					
		// 左侧页面的子页面
		JPanel jp11 = new JPanel();
		JPanel jp12 = new JPanel();
		jp11.setLayout(new GridLayout(11,1));
		jp12.setLayout(new GridLayout(11,1));
		jp1.add(jp11);
		jp1.add(jp12);
		// 文字
		JLabel jl = new JLabel("2017-2018春季学期");				// 固定的文字标签
		jl.setFont(new Font(null, Font.PLAIN, 25));				// 设置字体
		jp11.add(jl);
		// 按钮
		JButton b_do = new JButton("排课");						// "排课"按钮
		b_do.setFont(new Font(null, Font.PLAIN, 20));			// 设置按钮上文字的字体
		jp12.add(b_do);
		ActionListener ac_do = new ActionDo();					// "排课"按钮事件监听器
		b_do.addActionListener(ac_do);							// 向事件源"排课"按钮注册
		// 单选按钮
		int i = 0, j1Num = CourseLoad.FIRSTTABLE.length, 
		j2Num = CourseLoad.SECONDTABLE.length;
		ButtonGroup bg = new ButtonGroup();						// 互斥单选按钮组
		JRadioButton[] jr = new JRadioButton[j1Num+j2Num];		// 单选按钮数组
		ActionListener[] ac_jr = new ActionSwitchTime[j1Num+j2Num];// 单选按钮事件监听器
		for (i=0; i<j1Num; i++)
		{
			jr[i] = new JRadioButton(CourseLoad.FIRSTTABLE[i]);	// 设置单选按钮文本
			bg.add(jr[i]);										// 加入互斥按钮组
			jp11.add(jr[i]);									// 放在左侧
			ac_jr[i] = new ActionSwitchTime(i);					// 互斥按钮事件监听器,参数为i
			jr[i].addActionListener(ac_jr[i]);					// 向事件源注册
		}
		for (i=0; i<j2Num; i++)
		{
			jr[i+j1Num] = new JRadioButton(CourseLoad.SECONDTABLE[i]);// 设置单选按钮文本
			bg.add(jr[i+j1Num]);							// 加入互斥按钮组
			jp12.add(jr[i+j1Num]);							// 放在右侧
			ac_jr[i+j1Num] = new ActionSwitchTime(i+j1Num);	// 互斥按钮事件监听器,参数为i+j1Num
			jr[i+j1Num].addActionListener(ac_jr[i+j1Num]);	// 向事件源注册
		}	
		JTable disp = new JTable(model);		// 表格组件，显示"课程：教学楼：教室"	
		disp.setSize(400,1200);
		// 页面2：右侧页面
		JScrollPane scroll = new JScrollPane(disp);				// 把表格放在滚动条组件里
		scroll.setBounds(20,20,400,1000);							
		contentPane.add(jp1);
		contentPane.add(scroll);
		jf.setVisible(true);				// 所有组件加载好了再写jf.setVisible(true)
	}
}