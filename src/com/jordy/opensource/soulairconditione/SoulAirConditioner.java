package com.jordy.opensource.soulairconditione;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.awt.AWTUtilities;

/**
 * 灵魂空调
 * SoulAirConditioner.java
 * @author ming
 * @date 2023年6月8日
 */
public class SoulAirConditioner extends JFrame{

	private int minWindth;
	
	private int minHeight;
	
	private SoulAirConditionePanel mRootPanel;
	
	public SoulAirConditioner() {
		super("灵魂桌面空调");
		setUndecorated(true);
		setBounds(new Rectangle(40,40));
		setSize(400, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		mRootPanel = new SoulAirConditionePanel(this);
		add(mRootPanel);
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_O) {
					mRootPanel.open();
				}else if (e.getKeyCode() == KeyEvent.VK_S) {
					mRootPanel.close();
				}else if (e.getKeyCode() == KeyEvent.VK_UP) {
					mRootPanel.setTemperatureReduce();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					mRootPanel.setTemperatureIncrease();
				} else if (e.getKeyCode() == KeyEvent.VK_0) {
					mRootPanel.setModel(0);
				} else if (e.getKeyCode() == KeyEvent.VK_1) {
					mRootPanel.setModel(1);
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		AWTUtilities.setWindowShape(this,new RoundRectangle2D.Double(0.0D, 0.0D, 400, 200, 30.0D, 30.0D));
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new SoulAirConditioner();
	}
}
