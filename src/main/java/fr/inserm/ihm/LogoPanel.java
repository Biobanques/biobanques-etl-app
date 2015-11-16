package fr.inserm.ihm;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import fr.inserm.ihm.images.ImageStaticResource;

/**
 * panel de presentation du logo
 * 
 * @author nicolas
 * 
 */
public class LogoPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4207741104093333906L;

	public LogoPanel() {
		JLabel logoLabel;
		ImageIcon logo;
		logo = ImageStaticResource.getImageInsermIcon();
		logoLabel = new JLabel();
		if (logo != null)
			logoLabel.setIcon(logo);
		logoLabel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		add(logoLabel);
	}
}
