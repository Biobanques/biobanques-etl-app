package fr.inserm.ihm;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import fr.inserm.bean.IssueBean;

//import javax.swing.JSeparator;

/**
 * panel de transmission des informations au serveur.
 * 
 * @author nicolas
 * 
 */
public class TransmissionPanel extends InsermPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9200510273741962018L;
	private JLabel blankSpace;
//	public JLabel jLabel1 = new JLabel();
	private JPanel jPanel1;
	public JLabel progressDescription;
	private JProgressBar progressSent;
	private JLabel welcomeTitle;

	// private JPanel contentPanel;
	// private JLabel iconLabel;
	// private JSeparator separator;
	// private JLabel textLabel;
	// private JPanel titlePanel;
	JPanel progressBarPanel;
	/**
	 * panel de reporting pour remonter les anomalies
	 */
	public JPanel reportPanel;

	JPanel secondaryPanel;
	//public JLabel jLabelerr;

	public TransmissionPanel() {

		super();
	}

	public void setProgressText(String s) {
		progressDescription.setText(s);
	}

	public void setProgressValue(int i) {
		progressSent.setValue(i);
	}

	public void aboutToDisplay() {

	}

	/**
	 * action executee a l affichage
	 */
	public void display() {
		if (secondaryPanel == null) {
			JPanel mainPanel = new JPanel(new BorderLayout());
			secondaryPanel = new JPanel();
		
			progressBarPanel = new JPanel();
			reportPanel = theme.getAnomaliePanel();
		//	jLabelerr = new JLabel();
			welcomeTitle = new JLabel();
			jPanel1 = new JPanel();
			blankSpace = new JLabel();
			progressSent = new JProgressBar();
			progressSent.setPreferredSize(new Dimension(window_width - 50, 35));
			progressDescription = new JLabel();
			setProgressValue(0);
			setProgressText("Transmission des fichiers au serveur...");
			progressBarPanel.setLayout(new java.awt.BorderLayout());
			progressBarPanel.setBackground(theme.getFond());
			welcomeTitle.setText("Envoi des fichiers XML au serveur d'application distant...");
			progressBarPanel.add(welcomeTitle, java.awt.BorderLayout.NORTH);
			jPanel1.setBackground(theme.getFond());
			jPanel1.setLayout(new java.awt.GridLayout(0, 1));
			jPanel1.add(blankSpace);
			progressSent.setStringPainted(true);
			jPanel1.add(progressSent);
			progressDescription.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
			progressDescription.setText("Connexion au serveur...");
			jPanel1.add(progressDescription);
			progressBarPanel.add(jPanel1, java.awt.BorderLayout.CENTER);



			secondaryPanel.setLayout(new BorderLayout());
//			GridBagConstraints progressBarPanelConstraints = new GridBagConstraints();
//			progressBarPanelConstraints.gridx = 0;
//			progressBarPanelConstraints.gridy = 0;
//			progressBarPanelConstraints.weighty = 4;
//			progressBarPanelConstraints.fill = GridBagConstraints.HORIZONTAL;

//			GridBagConstraints errorsPanelConstraints = new GridBagConstraints();
//			errorsPanelConstraints.gridx = 0;
//			errorsPanelConstraints.gridy = 1;
//			errorsPanelConstraints.weighty = 2;
//			errorsPanelConstraints.fill = GridBagConstraints.HORIZONTAL;

//			GridBagConstraints reportPanelConstraints = new GridBagConstraints();
//			reportPanelConstraints.gridx = 0;
//			reportPanelConstraints.gridy = 2;
//			reportPanelConstraints.weighty = 4;
//			reportPanelConstraints.fill = GridBagConstraints.HORIZONTAL;

//			secondaryPanel.setBackground(Main.getBleupastel());
//			secondaryPanel.add(progressBarPanel, progressBarPanelConstraints);
//			secondaryPanel.add(errorsPanel, errorsPanelConstraints);
//			secondaryPanel.add(reportPanel, reportPanelConstraints);
			secondaryPanel.setBackground(theme.getFond());
			secondaryPanel.add(progressBarPanel,BorderLayout.NORTH);
			secondaryPanel.add(reportPanel, BorderLayout.CENTER);
			//secondaryPanel.add(reportPanel, BorderLayout.SOUTH);

			mainPanel.add(secondaryPanel,BorderLayout.CENTER);
add(mainPanel, BorderLayout.CENTER);
		}
	}

	/**
	 * ajout de message de reporting
	 */
//	public void addMessageReport(List<IssueBean> messages) {
//
//		reportPanel.setLayout(new GridBagLayout());
//		reportPanel.setBackground(theme.getBleupastel());
//		reportPanel.setBorder(BorderFactory.createLineBorder(theme.getVioletlogo(), 2));
//		GridBagConstraints c = new GridBagConstraints();
//		c.gridx=0;
//		c.weightx = 1.0;
//		reportPanel.add(new JLabel("Severity"), c);
//		reportPanel.add(new JLabel("Categorie"), c);
//		GridBagConstraints c2 = new GridBagConstraints();
//		c2.weightx = 5.0;
//		c2.gridwidth = GridBagConstraints.REMAINDER;
//		reportPanel.add(new JLabel("Message"), c2);
//		for (IssueBean message : messages) {
//			reportPanel.add(new JLabel(message.getSeverity() + ""), c);
//			reportPanel.add(new JLabel(message.getCategorie() + ""), c);
//			reportPanel.add(new JLabel(message.getMessage()), c2);
//		}
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("exportRapport")) {

		}

	}

}
