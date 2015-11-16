package fr.inserm.ihm.themes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import fr.inserm.ihm.InsermPanel;

public class Theme_default extends JComponent {

	private static final Font anomalieSampleFont = new Font("MS Sans Serif", Font.PLAIN, 12);
	private static final Font AnomalieTitleFont = new Font("MS Sans Serif", Font.BOLD, 14);
	/**
	 * 
	 */
	private static final long serialVersionUID = 2594640551301340389L;

	/**
	 * 
	 */

	public Theme_default() {

	}

	private static final Color noir = Color.BLACK;


	private static final Color bleuPastel = new Color(204, 248, 231);
	private static final Color bleuLogo = new Color(117, 228, 193);
	private static final Color violetPastel = new Color(225, 210, 225);


	private static final Color Fond = new Color(255, 255, 255);
	private static final Color violetLogo = new Color(215, 136, 241);

	/**
	 * Jeu de couleurs utilisées dans le thème
	 * 
	 * @param color
	 * @return
	 */
//	public static Color getLogoColors(int color) {
//		switch (color) {
//		case 1:
//			return new Color(117, 228, 193);
//		case 11:
//			return new Color(204, 248, 231);
//		case 2:
//			return new Color(215, 136, 241);
//		case 21:
//			return new Color(225, 210, 225);
//		default:
//			return null;
//		}
//
//	}

	
	/**
	 * Définition des styles de label pour les tableaux d'anomalies (sample et title)
	 * 
	 * @return
	 */
	public JLabel getAnomalieSampleLabel() {
		JLabel label = new JLabel();
		label.setFont(anomalieSampleFont);
		return label;
	}

	public JLabel getAnomalieTitleLabel(String title) {
		JLabel label = new JLabel(title);
		label.setFont(AnomalieTitleFont);
		return label;
	}

	/**
	 * Définition des styles de panel pour les tableaux d'anomalies (title et sample)
	 * 
	 * @return
	 */
	public JPanel getAnomalieTitleFieldPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBackground(bleuPastel);
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, noir));
		return panel;
	}

	public JPanel getAnomalieSampleFieldPanel(boolean b) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		if (b)
			panel.setBackground(Fond);
		else
			panel.setBackground(bleuPastel);
		panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, noir));

		return panel;
	}

	/**
	 * Définition du style de panel pour les tableaux d'anomalies
	 * 
	 * @return
	 */

	public JPanel getAnomaliePanel() {
		JPanel panel = new JPanel();
		panel.setBackground(Fond);
		panel.setPreferredSize(new Dimension(InsermPanel.window_width - 50, 280));
		panel.setBorder(BorderFactory.createLineBorder(noir, 2));
		panel.setLayout(new GridBagLayout());
		return panel;
	}
	
	public Font getAnomaliesamplefont() {
		return anomalieSampleFont;
	}

	public Font getAnomalietitlefont() {
		return AnomalieTitleFont;
	}

	public  Color getBleupastel() {
		return bleuPastel;
	}

	public  Color getBleulogo() {
		return bleuLogo;
	}

	public  Color getFond() {
		return Fond;
	}

	public  Color getVioletlogo() {
		return violetLogo;
	}
	
	public Color getNoir() {
		return noir;
	}
	
	public Color getVioletpastel() {
		return violetPastel;
	}
}
