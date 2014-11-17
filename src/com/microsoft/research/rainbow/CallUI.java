package com.microsoft.research.rainbow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.binding.internal.*;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.alljoyn.bus.BusException;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.*;
import org.bytedeco.javacv.FrameGrabber.Exception;

public class CallUI implements ActionListener {
	private final EmbeddedMediaPlayerComponent mediaPlayerComponent, selfMedia;
	JPanel call_panel;
	JPanel button_panel;
	JFrame frame;
	JPanel call_button_panel, self_panel;
	JPanel incoming_panel;
	JPanel start_panel;
	JButton start = new JButton("call myself");
	JButton answer = new JButton("answer");
	JButton dismiss = new JButton("dismiss");
	JButton mute = new JButton("mute");
	JButton message = new JButton("message");
	JButton hang = new JButton("hang_up");
	JButton log = new JButton("log");
	JButton mute2 = new JButton("mute");
	
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CallUI();
			}
		});
	}

	private CallUI() {
		frame = new JFrame("Skype");
		frame.setSize(400, 500);
		frame.setLayout(null);
		// frame.setLayout(new GridLayout());
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		mediaPlayerComponent.setSize(400, 400);
		selfMedia = new EmbeddedMediaPlayerComponent();
		selfMedia.setSize(120, 100);
		// ini self_panel
		self_panel = new JPanel();
		self_panel.setBackground(new Color(255,0,0));
		// ini call panel with button and video
		call_panel = new JPanel();
		call_panel.setLayout(null);
		call_panel.add(mediaPlayerComponent);
		//call_panel.setComponentZOrder(selfMedia, 0);
		mediaPlayerComponent.setBounds(0,0,400,400);
		call_panel.add(selfMedia);
		call_panel.setComponentZOrder(selfMedia, 0);
		call_panel.setComponentZOrder(mediaPlayerComponent, 1);
		call_button_panel = new JPanel(new GridLayout(1, 1, 20, 20));
		call_button_panel.add(hang);
		call_button_panel.add(mute2);
		call_panel.add(call_button_panel);
		call_button_panel.setBounds(0,400,380,60);
		selfMedia.setBounds(0, 0, 120, 100);
		// ini button_panel of incoming call
		button_panel = new JPanel();
		button_panel.setLayout(new GridLayout(2, 2, 20, 10));
		button_panel.add(answer);
		button_panel.add(dismiss);
		button_panel.add(mute);
		button_panel.add(message);
		answer.addActionListener(this);
		hang.addActionListener(this);
		start.addActionListener(this);
		dismiss.addActionListener(this);
		mute.addActionListener(this);
		message.addActionListener(this);
		log.addActionListener(this);
		mute2.addActionListener(this);
		// ini incoming panel
		incoming_panel = new JPanel();
		incoming_panel.setLayout(null);
		incoming_panel.setName("Incoming  call");
		incoming_panel.add(button_panel);
		button_panel.setBounds(30,250,300,100);
		// ini the start panel;
		start_panel = new JPanel();
		start_panel.setBounds(50,150,200,100);
		start_panel.setSize(100, 100);
		start_panel.setLayout(null);
		start_panel.add(start);
		start_panel.add(log);
		start.setBounds(150,150,100,50);
		log.setBounds(150,210,100,50);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setContentPane(start_panel);
		// frame.setContentPane(call_panel);
		// frame.setContentPane(incoming_panel);

	}

	public void incoming_call() {
		incoming_panel.setVisible(true);
		start_panel.setVisible(false);
		call_panel.setVisible(false);
		this.frame.setContentPane(incoming_panel);
	}

	public void answer_call() {
		incoming_panel.setVisible(false);
		start_panel.setVisible(false);
		call_panel.setVisible(true);
		this.frame.setContentPane(call_panel);
		mediaPlayerComponent.getMediaPlayer().playMedia("test.mp4");
		selfMedia.getMediaPlayer().playMedia("test.mp4");
	}

	public void start() {
		incoming_panel.setVisible(false);
		call_panel.setVisible(false);
		start_panel.setVisible(true);
		frame.setContentPane(start_panel);
		

	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == answer) {
			answer_call();
		}
		if (arg0.getSource() == hang) {

			mediaPlayerComponent.getMediaPlayer().stop();
			start();
		}
		if (arg0.getSource() == start) {
			System.out.println("start a  call");
			incoming_call();
		}
		if (arg0.getSource() == mute2) {
			if (mute2.getText().equals("mute")) {
				System.out.println("mute");
				mediaPlayerComponent.getMediaPlayer().mute();
				mute2.setText("unmute");
			} else {
				mediaPlayerComponent.getMediaPlayer().mute(false);
				mute2.setText("mute");
			}
		}
		if (arg0.getSource() == mute) {
			if (mute.getText().equals("mute")) {
				System.out.println("mute");
				mute.setText("unmute");
			} else {

				System.out.println("unmute");
				mute.setText("mute");
			}
		}
	}
}
