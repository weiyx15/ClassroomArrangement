package gui;

import java.awt.event.*;
import baselineFromFile.RunArrangeFromFile;

public class ActionDo implements ActionListener{
	public void actionPerformed(ActionEvent e)
	{
		String dfn = "data.xls";
		RunArrangeFromFile.doArrange(dfn);
	}
}
