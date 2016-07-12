package ackermanCoplanMuscianoAirHockey;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Hashtable;

public class SettingsWindow {
	
	private JFrame frame;
	private ButtonGroup colorsGroup;
	private JSlider slider;
	private boolean doneClicked = false;
	private String colorChoice = "Red";
	private int difficulty = 0;

	public SettingsWindow(){
	
	}
	
	public void setUp(){
		
		frame = new JFrame("Settings");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new GridLayout(3, 0));
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(null);
		JLabel colorsLabel = new JLabel("Choose your paddle color:", JLabel.CENTER);
		colorsLabel.setBounds(0, 0, 150, 100);
		JRadioButton color1 = new JRadioButton("Red");
		color1.setActionCommand("Red");
		color1.setSelected(true);
		color1.setBounds(150, 0, 70, 100);
		color1.setBackground(Color.cyan);
		JRadioButton color2 = new JRadioButton("Blue");
		color2.setActionCommand("Blue");
		color2.setBounds(220, 0, 70, 100);
		color2.setBackground(Color.cyan);
		JRadioButton color3 = new JRadioButton("Green");
		color3.setActionCommand("Green");
		color3.setBounds(290, 0, 70, 100);
		color3.setBackground(Color.cyan);
		JRadioButton color4 = new JRadioButton("Purple");
		color4.setActionCommand("Purple");
		color4.setBounds(360, 0, 70, 100);
		color4.setBackground(Color.cyan);
		JRadioButton color5 = new JRadioButton("Pink");
		color5.setActionCommand("Pink");
		color5.setBounds(430, 0, 70, 100);
		color5.setBackground(Color.cyan);
		JRadioButton color6 = new JRadioButton("Orange");
		color6.setActionCommand("Orange");
		color6.setBounds(500, 0, 70, 100);
		color6.setBackground(Color.cyan);
		colorsGroup = new ButtonGroup();
		colorsGroup.add(color1);
		colorsGroup.add(color2);
		colorsGroup.add(color3);
		colorsGroup.add(color4);
		colorsGroup.add(color5);
		colorsGroup.add(color6);
		panel1.add(colorsLabel);
		panel1.add(color1);
		panel1.add(color2);
		panel1.add(color3);
		panel1.add(color4);
		panel1.add(color5);
		panel1.add(color6);
		panel1.setBackground(Color.cyan);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(2, 0));
		JLabel sliderLabel = new JLabel("Choose your difficulty (for computer game only)", JLabel.CENTER);
		slider = new JSlider();
		slider.setValue(0);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(1);
		slider.setMaximum(4);
		Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
		table.put(0, new JLabel("Easy"));
		table.put(1, new JLabel("Medium"));
		table.put(2, new JLabel("Hard"));
		table.put(3, new JLabel("Very Hard"));
		table.put(4, new JLabel("God"));
		slider.setLabelTable(table);
		slider.setPaintLabels(true);
		slider.setBackground(Color.pink);
		panel2.add(sliderLabel);
		panel2.add(slider);
		panel2.setBackground(Color.pink);
		
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BorderLayout());
		JButton doneButton = new JButton("Save Settings");
		doneButton.setFont(new Font("Arial Bold", Font.BOLD, 40));
		doneButton.addActionListener(new AL());
		panel3.add(doneButton, BorderLayout.CENTER);
		doneButton.setBackground(Color.cyan);
		
		frame.add(panel1);
		frame.add(panel2);
		frame.add(panel3);
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private class AL implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			colorChoice = colorsGroup.getSelection().getActionCommand();
			difficulty = slider.getValue();
			frame.setVisible(false);
			doneClicked = true;
		}
	}
	
	public boolean isDoneClicked(){return doneClicked;}
	public String getSettings(){return colorChoice + " " + difficulty;}
}
