package fr.inserm.ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import fr.inserm.ihm.images.ImageStaticResource;
import fr.inserm.ihm.themes.Theme_default;


/**
 * panel to get teh same presentation on each screen with custom layout.
 * 
 * @author nicolas
 * 
 */
public class InsermPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8838829739265871166L;

	public static int window_width = 900;
	public int window_height = 600;
	public WizardModel wm = new WizardModel();

	// private JLabel iconLabel;

	// private ImageIcon icon;
	/*
	 * Panel de contenu
	 */
	JLabel configLine = new JLabel();
	JLabel exportLine = new JLabel();
	JLabel sendLine = new JLabel();
	JLabel configCircle = new JLabel();
	JLabel exportCircle = new JLabel();
	JLabel sendCircle = new JLabel();
	JLabel endCircle = new JLabel();
	JLabel endLine = new JLabel();
	JLabel logoInsermLabel = new JLabel();
	JLabel logoBiobanquesLabel = new JLabel();
	ImageIcon blackLine = ImageStaticResource.getImageBlackLineIcon();
	ImageIcon blueLine = ImageStaticResource.getImageBlueLineIcon();
	ImageIcon greenLine = ImageStaticResource.getImageGreenLineIcon();
	ImageIcon orangeLine = ImageStaticResource.getImageOrangeLineIcon();
	ImageIcon redLine = ImageStaticResource.getImageRedLineIcon();
	ImageIcon blackCircle = ImageStaticResource.getImageBlackCircleIcon();
	ImageIcon blueCircle = ImageStaticResource.getImageBlueCircleIcon();
	ImageIcon greenCircle = ImageStaticResource.getImageGreenCircleIcon();
	ImageIcon orangeCircle = ImageStaticResource.getImageOrangeCircleIcon();
	ImageIcon redCircle = ImageStaticResource.getImageRedCircleIcon();
	ImageIcon logoInserm = ImageStaticResource.getImageLogoInserm();
	ImageIcon logoBiobanques = ImageStaticResource.getImageLogoBiobanques();
	ImageIcon saveIcon = ImageStaticResource.getSaveIcon();
	ImageIcon loadIcon = ImageStaticResource.getLoadIcon();

	public Theme_default theme = new Theme_default();

	public InsermPanel() {
		setLayout(new java.awt.BorderLayout());

		/*
		 * Partie Nord : affichage de la progression dans l'appli
		 */
JPanel northPanel = new JPanel(new GridBagLayout());
northPanel.setBackground(theme.getVioletpastel());
		JPanel trainWithTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		trainWithTitlePanel.setBackground(theme.getVioletpastel());
		JPanel trainPanel = new JPanel();
		trainPanel.setBackground(theme.getVioletpastel());
		trainPanel.setLayout(new BoxLayout(trainPanel, BoxLayout.X_AXIS));

		JPanel confPanel = new JPanel();
		confPanel.setBackground(theme.getVioletpastel());
		confPanel.setLayout(new BoxLayout(confPanel, BoxLayout.Y_AXIS));
		JPanel confPanelTrain = new JPanel();
		confPanelTrain.setBackground(theme.getVioletpastel());
		confPanelTrain.setLayout(new BoxLayout(confPanelTrain, BoxLayout.X_AXIS));
		JPanel confPanelTitle = new JPanel();
		confPanelTitle.setBackground(theme.getVioletpastel());
		confPanelTitle.setLayout(new FlowLayout(FlowLayout.LEFT));

		JPanel transportPanel = new JPanel();
		transportPanel.setBackground(theme.getVioletpastel());
		transportPanel.setLayout(new BoxLayout(transportPanel, BoxLayout.Y_AXIS));
		JPanel transportPanelTrain = new JPanel();
		transportPanelTrain.setBackground(theme.getVioletpastel());
		transportPanelTrain.setLayout(new BoxLayout(transportPanelTrain, BoxLayout.X_AXIS));
		JPanel transportPanelTitle = new JPanel();
		transportPanelTitle.setBackground(theme.getVioletpastel());
		transportPanelTitle.setLayout(new FlowLayout(FlowLayout.LEFT));

		JPanel exportPanel = new JPanel();
		exportPanel.setBackground(theme.getVioletpastel());
		exportPanel.setLayout(new BoxLayout(exportPanel, BoxLayout.Y_AXIS));
		JPanel exportPanelTrain = new JPanel();
		exportPanelTrain.setBackground(theme.getVioletpastel());
		exportPanelTrain.setLayout(new BoxLayout(exportPanelTrain, BoxLayout.X_AXIS));
		JPanel exportPanelTitle = new JPanel();
		exportPanelTitle.setBackground(theme.getVioletpastel());
		exportPanelTitle.setLayout(new FlowLayout(FlowLayout.LEFT));

		JPanel finPanel = new JPanel();
		finPanel.setBackground(theme.getVioletpastel());
		finPanel.setLayout(new BoxLayout(finPanel, BoxLayout.Y_AXIS));
		JPanel finPanelTrain = new JPanel();
		finPanelTrain.setBackground(theme.getVioletpastel());
		finPanelTrain.setLayout(new BoxLayout(finPanelTrain, BoxLayout.X_AXIS));
		JPanel finPanelTitle = new JPanel();
		finPanelTitle.setBackground(theme.getVioletpastel());
		finPanelTitle.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel conf = new JLabel("Configuration");
		JLabel export = new JLabel("Export");
		JLabel transport = new JLabel("Envoi");
		JLabel fin = new JLabel("Fin");

		confPanelTitle.add(conf);
		confPanelTrain.add(configCircle);
		confPanelTrain.add(configLine);
		confPanel.add(confPanelTitle);
		confPanel.add(confPanelTrain);
		trainPanel.add(confPanel);

		exportPanelTitle.add(export);
		exportPanelTrain.add(exportCircle);
		exportPanelTrain.add(exportLine);
		exportPanel.add(exportPanelTitle);
		exportPanel.add(exportPanelTrain);
		trainPanel.add(exportPanel);

		transportPanelTitle.add(transport);
		transportPanelTrain.add(sendCircle);
		transportPanelTrain.add(sendLine);
		transportPanel.add(transportPanelTitle);
		transportPanel.add(transportPanelTrain);
		trainPanel.add(transportPanel);

		finPanelTitle.add(fin);
		finPanelTrain.add(endCircle);
		endLine.setText("      ");
		finPanelTrain.add(endLine);
		finPanel.add(finPanelTitle);
		finPanel.add(finPanelTrain);
		trainPanel.add(finPanel);
		trainWithTitlePanel.add(trainPanel);
		GridBagConstraints trainConstraints = new GridBagConstraints();
		GridBagConstraints insermLogoConstraints = new GridBagConstraints();
		GridBagConstraints biobanqueLogoConstraints = new GridBagConstraints();
		biobanqueLogoConstraints.gridx=0;
		biobanqueLogoConstraints.gridy=0;
		biobanqueLogoConstraints.gridwidth=5;
		biobanqueLogoConstraints.weightx=20;
		biobanqueLogoConstraints.anchor=GridBagConstraints.FIRST_LINE_START;
		insermLogoConstraints.gridx=35;
		insermLogoConstraints.gridy=0;
		insermLogoConstraints.gridwidth=5;
		insermLogoConstraints.weightx=5;
		insermLogoConstraints.anchor=GridBagConstraints.FIRST_LINE_END;
		trainConstraints.gridx=5;
		trainConstraints.gridy=0;
		trainConstraints.gridwidth=30;
		trainConstraints.weightx=60;
		trainConstraints.anchor=GridBagConstraints.PAGE_START;
		logoBiobanquesLabel.setIcon(logoBiobanques);
		logoInsermLabel.setIcon(logoInserm);
		

		northPanel.add(logoBiobanquesLabel,biobanqueLogoConstraints);
		northPanel.add(trainWithTitlePanel, trainConstraints);
		northPanel.add(logoInsermLabel, insermLogoConstraints);
		
		JPanel northPanelWithSep = new JPanel((new GridBagLayout()));

		GridBagConstraints logoConstraints = new GridBagConstraints();
		logoConstraints.gridx=0;
		logoConstraints.gridy=0;
		logoConstraints.weightx=1;
		logoConstraints.weighty=7;
		logoConstraints.fill=GridBagConstraints.HORIZONTAL;
		GridBagConstraints sepConstraints = new GridBagConstraints();
		sepConstraints.gridy=1;
		sepConstraints.weighty=3;
		sepConstraints.fill=GridBagConstraints.HORIZONTAL;
		northPanelWithSep.add(northPanel,logoConstraints);
		northPanelWithSep.add(new JSeparator(JSeparator.HORIZONTAL),sepConstraints);
		add(northPanelWithSep, BorderLayout.NORTH);
		
		
		//add(southPanel, BorderLayout.SOUTH);
		Dimension dimension = new Dimension(window_width, window_height);
		setPreferredSize(dimension);

	}

	/**
	 * add always on the center panel.
	 * 
	 * @param obj
	 */
	public void add(Object obj) {

	}
}
