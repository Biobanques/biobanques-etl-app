package fr.inserm.ihm;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.inserm.Main;
import fr.inserm.bean.IssueBean;
import fr.inserm.ihm.themes.Theme_default;
import fr.inserm.transport.SFTPSender;

public class TransmissionPanelDescriptor extends WizardPanelDescriptor {

	public static final String IDENTIFIER = "TRANSMISSION_PANEL";

	TransmissionPanel mainPanel;
	private final Theme_default theme = new Theme_default();

	public TransmissionPanelDescriptor() {
		mainPanel = new TransmissionPanel();
		setPanelDescriptorIdentifier(IDENTIFIER);
		setPanelComponent(mainPanel);
	}

	@Override
	public Object getNextPanelDescriptor() {
		return IntroductionPanelDescriptor.IDENTIFIER;
	}

	@Override
	public Object getBackPanelDescriptor() {
		return ExportingPanelDescriptor.IDENTIFIER;
	}

	@Override
	public void aboutToDisplayPanel() {
		mainPanel.aboutToDisplay();
	}

	@Override
	public void displayingPanel() {
		mainPanel.display();
		getWizardModel().setNextFinishButtonText("Recommencer");
		getWizard().setNextFinishButtonEnabled(false);
		getWizard().setBackButtonEnabled(false);

		mainPanel.setProgressText("Transmission des fichiers au serveur...");
		mainPanel.setProgressValue(0);

		mainPanel.reportPanel.setVisible(false);

		Thread t = new Thread() {

			@Override
			public void run() {

				if (!Main.exportedWithWarning) {
					mainPanel.configCircle.setIcon(mainPanel.greenCircle);
					mainPanel.configLine.setIcon(mainPanel.greenLine);
					mainPanel.exportCircle.setIcon(mainPanel.greenCircle);
					mainPanel.exportLine.setIcon(mainPanel.greenLine);
					mainPanel.sendCircle.setIcon(mainPanel.blueCircle);
					mainPanel.sendLine.setIcon(mainPanel.blueLine);
					mainPanel.endCircle.setIcon(mainPanel.blueCircle);
				} else {
					mainPanel.configCircle.setIcon(mainPanel.greenCircle);
					mainPanel.configLine.setIcon(mainPanel.greenLine);
					mainPanel.exportCircle.setIcon(mainPanel.orangeCircle);
					mainPanel.exportLine.setIcon(mainPanel.orangeLine);
					mainPanel.sendCircle.setIcon(mainPanel.blueCircle);
					mainPanel.sendLine.setIcon(mainPanel.blueLine);
					mainPanel.endCircle.setIcon(mainPanel.blueCircle);

				}
				try {
					Thread.sleep(1);

					Thread.sleep(1000);
					mainPanel.setProgressValue(25);
					mainPanel.setProgressText("Connexion au serveur...");
					SFTPSender sender = new SFTPSender(Main.propertiesBean);
					Thread.sleep(500);
					mainPanel.setProgressValue(50);
					mainPanel.setProgressText("Envoi des fichiers...");

					List<IssueBean> issues = sender.pushFilesOnSent();
					mainPanel.setProgressValue(100);

					String msgConfirmation = "Transfert effectué avec succès.";
					String msgErreur;
					if (issues.size() > 0) {
						if (issues.size() == 1) {
							msgErreur = "erreur";
						} else {
							msgErreur = "erreurs";
						}
						msgConfirmation = "Le transfert a échoué suite à "
								+ issues.size() + " " + msgErreur
								+ " durant le transport!";
						// mainPanel.jLabelerr.setText(msgConfirmation);
						// mainPanel.jLabelerr.setBackground(Color.pink);
						// mainPanel.jLabelerr.setForeground(Color.red);
						// mainPanel.errorsPanel.add(mainPanel.jLabelerr);
						mainPanel.reportPanel = createAnomaliePanel(issues);
						// mainPanel.addMessageReport(issues);
						mainPanel.setProgressText(msgConfirmation);
						mainPanel.progressDescription.setForeground(Color.RED);
						mainPanel.sendCircle.setIcon(mainPanel.redCircle);
						mainPanel.sendLine.setIcon(mainPanel.redLine);
						mainPanel.endCircle.setIcon(mainPanel.redCircle);
						// mainPanel.reportBtnPanel.setVisible(true);
					} else {
						mainPanel.progressDescription
								.setForeground(Color.BLACK);
						mainPanel.setProgressText(msgConfirmation);
						mainPanel.sendCircle.setIcon(mainPanel.greenCircle);
						mainPanel.sendLine.setIcon(mainPanel.greenLine);
						if (Main.exportedWithWarning) {
							mainPanel.endCircle.setIcon(mainPanel.orangeCircle);
						} else {
							mainPanel.endCircle.setIcon(mainPanel.greenCircle);
						}
					}

					mainPanel.reportPanel.setVisible(true);
					getWizard().setNextFinishButtonEnabled(true);
					// getWizard().setBackButtonEnabled(true);

				} catch (Exception e) {
					e.printStackTrace();
					mainPanel.setProgressValue(0);
					mainPanel.setProgressText("An Error Has Occurred");
					getWizard().setNextFinishButtonEnabled(true);
					getWizard().setBackButtonEnabled(true);
				}

			}
		};

		t.start();
	}

	public JPanel createAnomaliePanel(List<IssueBean> alertes) {

		JPanel anomaliePanel = mainPanel.reportPanel;
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = 0;

		JPanel idTitlePanel = theme.getAnomalieTitleFieldPanel();
		JPanel sourceTitlePanel = theme.getAnomalieTitleFieldPanel();
		JPanel destTitlePanel = theme.getAnomalieTitleFieldPanel();

		JLabel anomalieIdTitle = theme.getAnomalieTitleLabel("Severity");
		JLabel anomalieSourceTitle = theme.getAnomalieTitleLabel("Categorie");
		JLabel anomalieDestTitle = theme.getAnomalieTitleLabel("Message");

		gbc.gridx = 0;
		gbc.weightx = 0.2;
		anomaliePanel.add(idTitlePanel, gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.2;
		anomaliePanel.add(sourceTitlePanel, gbc);
		gbc.gridx = 2;
		gbc.weightx = 1;
		anomaliePanel.add(destTitlePanel, gbc);

		GridBagConstraints anomalieConstraint = new GridBagConstraints();
		anomalieConstraint.anchor = GridBagConstraints.CENTER;
		idTitlePanel.add(anomalieIdTitle, anomalieConstraint);
		sourceTitlePanel.add(anomalieSourceTitle, anomalieConstraint);
		destTitlePanel.add(anomalieDestTitle, anomalieConstraint);

		int nbMax;
		if (alertes.size() >= 10) {
			nbMax = 11;
		} else {
			nbMax = alertes.size() + 1;
		}
		gbc.weightx = 0;

		for (int i = 0; i < nbMax; i++) {
			gbc.gridy = i + 1;
			int[] nbPairs = { 0, 2, 4, 6, 8, 10 };
			boolean alternance = false;
			for (int nb : nbPairs) {
				if (nb == i)
					alternance = true;
			}

			JPanel idSamplePanel = theme
					.getAnomalieSampleFieldPanel(alternance);
			JPanel sourceSamplePanel = theme
					.getAnomalieSampleFieldPanel(alternance);
			JPanel destSamplePanel = theme
					.getAnomalieSampleFieldPanel(alternance);
			JLabel anomalieSource = theme.getAnomalieSampleLabel();
			// JLabel anomalieDest = theme.getAnomalieSampleLabel();
			JLabel anomalieDest = theme.getAnomalieSampleLabel();
			JLabel anomalieId = theme.getAnomalieSampleLabel();

			if (i != nbMax - 1) {

				anomalieId.setText(alertes.get(i).severity.name());
				anomalieSource.setText(alertes.get(i).categorie.name());
				// anomalieDest.setText(alertes.get(i).message.substring(0,100)+"...");
				anomalieDest.setText("<html>" + alertes.get(i).message
						+ "</html>");

				idSamplePanel.add(anomalieId, anomalieConstraint);
				sourceSamplePanel.add(anomalieSource, anomalieConstraint);
				destSamplePanel.add(anomalieDest, anomalieConstraint);

			} else {
				idSamplePanel.setBackground(theme.getFond());
				sourceSamplePanel.setBackground(theme.getFond());
				destSamplePanel.setBackground(theme.getFond());

				gbc.fill = GridBagConstraints.BOTH;
				gbc.weighty = 1;
			}

			gbc.gridx = 0;
			anomaliePanel.add(idSamplePanel, gbc);
			gbc.gridx = 1;

			anomaliePanel.add(sourceSamplePanel, gbc);
			gbc.gridx = 2;
			anomaliePanel.add(destSamplePanel, gbc);

		}
		return anomaliePanel;
	}

	@Override
	public void aboutToHidePanel() {
		// Can do something here, but we've chosen not not.
	}

}
