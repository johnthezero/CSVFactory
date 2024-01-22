package views;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
/**
 * @author JonTheZero!!!
 */
public class MonthContainer extends Container {
	private JButton[] buttons ;
	private String[] months= {"January","February","March","April","May","June","July","August","September","October","November","December"};
	public MonthContainer() {
		super();
		buttons=new JButton[12];

		this.setLayout(new GridLayout(4,3));
		for(int i=0;i<buttons.length;i++) {
			buttons[i]=new JButton(months[i]);
			buttons[i].setActionCommand(months[i]);
			//useful?
			buttons[i].setToolTipText(months[i]);
			Container x=new Container();
			x.setLayout(new BorderLayout());

			x.add(buttons[i]);
			this.add(x);
		}
	}
	public JButton[] getButtons() {
		return this.buttons;
	}
}
