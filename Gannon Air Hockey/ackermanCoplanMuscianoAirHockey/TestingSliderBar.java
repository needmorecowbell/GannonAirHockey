package ackermanCoplanMuscianoAirHockey;

import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;

public class TestingSliderBar {

	public static void main(String[] args){
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		JSlider slider = new JSlider();
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
		
		
		frame.add(slider);
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
}
