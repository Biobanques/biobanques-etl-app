package fr.inserm.ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.inserm.Main;
import fr.inserm.bean.FileInputBean;
import fr.inserm.bean.PropertiesBean;
import fr.inserm.exporter.JSONExporter;
import fr.inserm.exporter.XMLExporter;
import fr.inserm.extractor.Extractor;
import fr.inserm.ihm.themes.Theme_default;
import fr.inserm.log.AlertLogExporter;
import fr.inserm.log.TransformAlert;

/**
 * panel d 'export des données.
 * 
 * @author nicolas
 * 
 */
public class ExportingPanelDescriptor extends WizardPanelDescriptor {

	public static final String IDENTIFIER = "EXPORT_PANEL";
	public List<TransformAlert> alertes;
	ExportingPanel mainPanel;
	int nbExportedFiles;
	int nbExportedEch;
	int nbAnomalie;

	private Theme_default theme = new Theme_default();

	public List<TransformAlert> getAlertes() {
		return alertes;
	}

	public ExportingPanelDescriptor() {
		mainPanel = new ExportingPanel();
		setPanelDescriptorIdentifier(IDENTIFIER);
		setPanelComponent(mainPanel);
	}

	@Override
	public Object getNextPanelDescriptor() {
		return TransmissionPanelDescriptor.IDENTIFIER;
	}

	@Override
	public Object getBackPanelDescriptor() {
		return IntroductionPanelDescriptor.IDENTIFIER;
	}

	@Override
	public void aboutToDisplayPanel() {

	}

	@Override
	public void displayingPanel() {

		mainPanel.display();
		mainPanel.progressDescription.setForeground(Color.BLACK);
		mainPanel.anomaliePanel.setVisible(false);
		mainPanel.reportBtnPanel.setVisible(false);
		mainPanel.reportPanel.setVisible(false);
		mainPanel.setProgressValue(0);
		mainPanel.setProgressText("Export des informations...");
		getWizard().setNextFinishButtonEnabled(false);
		getWizard().setBackButtonEnabled(false);

		Thread t = new Thread() {

			@Override
			public void run() {
				/**
				 * Affichage de la ligne de progression ("petit train")
				 */
				mainPanel.configCircle.setIcon(mainPanel.greenCircle);
				mainPanel.configLine.setIcon(mainPanel.greenLine);
				mainPanel.exportCircle.setIcon(mainPanel.blueCircle);
				mainPanel.exportLine.setIcon(mainPanel.blueLine);
				mainPanel.sendCircle.setIcon(mainPanel.blackCircle);
				mainPanel.sendLine.setIcon(mainPanel.blackLine);
				mainPanel.endCircle.setIcon(mainPanel.blackCircle);

				try {
					Thread.sleep(500);
					mainPanel.setProgressValue(25);
					mainPanel.setProgressText("Connexion au serveur de base de données");
					Extractor ex = new Extractor(Main.propertiesBean);
					Thread.sleep(500);
					mainPanel.setProgressValue(50);
					mainPanel.setProgressText("Extraction et Traduction des données...");
					FileInputBean input = ex.extract();
					Thread.sleep(500);
					mainPanel.setProgressValue(75);
					if (Main.propertiesBean.exportMode == 1) {
						mainPanel.setProgressText("Enregistrement dans fichier JSON...");
						JSONExporter jsonExporter = new JSONExporter(Main.propertiesBean);
						jsonExporter.exportFile(input);
					} else {
						mainPanel.setProgressText("Enregistrement dans fichier XML...");
						XMLExporter xmlExporter = new XMLExporter(Main.propertiesBean);
						int resEx = xmlExporter.exportFile(input);

						if (resEx < 0) {
							mainPanel.setProgressValue(0);
							mainPanel.progressDescription.setForeground(Color.RED);
							mainPanel.progressDescription.setFont(new Font("MS Sans Serif", Font.BOLD, 12));
							mainPanel.setProgressText("Une erreur est apparue dans l'extraction/export.");
							mainPanel.exportCircle.setIcon(mainPanel.redCircle);
							mainPanel.exportLine.setIcon(mainPanel.redLine);
							getWizard().setBackButtonEnabled(true);
						} else {
							mainPanel.setProgressValue(90);
							mainPanel.setProgressText("Construction du rapport d'anomalies...");
							Thread.sleep(500);
							alertes = ex.getAlertes();
							mainPanel.alertes = alertes;
							String progresText = "";
							nbExportedFiles = xmlExporter.nbFichiers;
							nbExportedEch = xmlExporter.nbEchantillons;
							nbAnomalie = 0;
							exporter();
							if (alertes.size() > 0) {
								nbAnomalie = alertes.size();
								mainPanel.anomaliePanel = createAnomaliePanel(alertes);
								mainPanel.doLayout();
								mainPanel.anomaliePanel.setVisible(true);
								mainPanel.reportBtnPanel.setVisible(true);
								mainPanel.progressDescription.setForeground(Color.RED);
								mainPanel.progressDescription.setFont(new Font("MS Sans Serif", Font.ITALIC, 14));
								progresText = "Données extraites avec succès mais des anomalies de traduction sont présentes : ";

								AlertLogExporter.exportInFile(alertes, Main.propertiesBean);

								mainPanel.exportCircle.setIcon(mainPanel.orangeCircle);
								mainPanel.exportLine.setIcon(mainPanel.orangeLine);
								Main.exportedWithWarning = true;
							} else {
								progresText = "Données extraites avec succès";
								mainPanel.exportCircle.setIcon(mainPanel.greenCircle);
								mainPanel.exportLine.setIcon(mainPanel.greenLine);
							}
							mainPanel.setProgressValue(100);

							mainPanel.setProgressText(progresText);

							mainPanel.nbExportedFiles = nbExportedFiles;
							mainPanel.nbAnomLabel.setText(Integer.toString(nbAnomalie));
							// mainPanel.nbExportedFilesLbl.setText("Nombre de fichiers générés : ");

							mainPanel.nbExportedEch = nbExportedEch;
							mainPanel.nbEchLabel.setText(Integer.toString(nbExportedEch));
							// mainPanel.nbExportedEchLbl.setText("Nombre d'échantillons extraits : ");
							mainPanel.nbAnomalie = nbAnomalie;
							mainPanel.nbFilesLabel.setText(Integer.toString(nbExportedFiles));
							mainPanel.reportPanel.setVisible(true);
							getWizard().setNextFinishButtonEnabled(true);
							getWizard().setBackButtonEnabled(true);

						}
					}
				} catch (InterruptedException e) {

					mainPanel.setProgressValue(0);
					mainPanel.setProgressText("Une erreur est apparue.");

					getWizard().setBackButtonEnabled(true);
				}

			}
		};

		t.start();
	}

	/**
	 * exporte les anomalies vers un fichier xml de rapport
	 */
	public void exporter() {

		PropertiesBean properties = Main.propertiesBean;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("hh_mm_ddMMyyyy");
		String suffixDate = sdf.format(date);
		String nomFichier = properties.getFolderExport() + "anomalies_export_" + properties.getIdSite() + "_"
				+ suffixDate + ".xml.report";
		AlertLogExporter.exportAlertesInXMLFile(Integer.toString(nbExportedFiles), Integer.toString(nbExportedEch),

		nomFichier, alertes, properties);
	}

	/**
	 * Recupere les anomalies (limitées à 10) et construit le tableau d'anomalies
	 * 
	 * @param listAlertes
	 * @return
	 */

	public JPanel createAnomaliePanel(List<TransformAlert> alertes) {

		JPanel anomaliePanel = mainPanel.anomaliePanel;
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = 0;

		JPanel idTitlePanel = theme.getAnomalieTitleFieldPanel();
		JPanel sourceTitlePanel = theme.getAnomalieTitleFieldPanel();
		JPanel destTitlePanel = theme.getAnomalieTitleFieldPanel();
		JPanel typeTitlePanel = theme.getAnomalieTitleFieldPanel();
		JPanel typeLiteralTitlePanel = theme.getAnomalieTitleFieldPanel();
		JLabel anomalieIdTitle = theme.getAnomalieTitleLabel("Id");
		JLabel anomalieSourceTitle = theme.getAnomalieTitleLabel("Source");
		JLabel anomalieDestTitle = theme.getAnomalieTitleLabel("Destination");
		JLabel anomalieTypeTitle = theme.getAnomalieTitleLabel("Type");
		JLabel anomalieLiteralTitle = theme.getAnomalieTitleLabel("Type litéral");

		gbc.gridx = 0;
		gbc.weightx = 0.15;
		anomaliePanel.add(idTitlePanel, gbc);
		gbc.gridx = 1;
		gbc.weightx = 1;
		anomaliePanel.add(sourceTitlePanel, gbc);
		gbc.gridx = 2;
		gbc.weightx = 1;
		anomaliePanel.add(destTitlePanel, gbc);
		gbc.gridx = 3;
		gbc.weightx = 0.8;

		anomaliePanel.add(typeTitlePanel, gbc);
		gbc.gridx = 4;
		gbc.weightx = 0.8;

		anomaliePanel.add(typeLiteralTitlePanel, gbc);

		GridBagConstraints anomalieConstraint = new GridBagConstraints();
		anomalieConstraint.anchor = GridBagConstraints.CENTER;
		idTitlePanel.add(anomalieIdTitle, anomalieConstraint);
		sourceTitlePanel.add(anomalieSourceTitle, anomalieConstraint);
		destTitlePanel.add(anomalieDestTitle, anomalieConstraint);
		typeTitlePanel.add(anomalieTypeTitle, anomalieConstraint);
		typeLiteralTitlePanel.add(anomalieLiteralTitle, anomalieConstraint);

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

			JPanel idSamplePanel = theme.getAnomalieSampleFieldPanel(alternance);
			JPanel sourceSamplePanel = theme.getAnomalieSampleFieldPanel(alternance);
			JPanel destSamplePanel = theme.getAnomalieSampleFieldPanel(alternance);
			JPanel typeSamplePanel = theme.getAnomalieSampleFieldPanel(alternance);
			JPanel typeLiteralSamplePanel = theme.getAnomalieSampleFieldPanel(alternance);
			JLabel anomalieSource = theme.getAnomalieSampleLabel();
			JLabel anomalieDest = theme.getAnomalieSampleLabel();
			JLabel anomalieId = theme.getAnomalieSampleLabel();
			JLabel anomalieType = theme.getAnomalieSampleLabel();
			JLabel anomalieLiteral = theme.getAnomalieSampleLabel();
			if (i != nbMax - 1) {
				anomalieId.setText(alertes.get(i).idElement);
				anomalieSource.setText(alertes.get(i).fieldSourceName);
				anomalieDest.setText(alertes.get(i).fieldDestName);
				anomalieType.setText(alertes.get(i).alertType.toString());
				anomalieLiteral.setText(alertes.get(i).literalAlert);
				idSamplePanel.add(anomalieId, anomalieConstraint);
				sourceSamplePanel.add(anomalieSource, anomalieConstraint);
				destSamplePanel.add(anomalieDest, anomalieConstraint);
				typeSamplePanel.add(anomalieType, anomalieConstraint);
				typeLiteralSamplePanel.add(anomalieLiteral, anomalieConstraint);
			} else {
				gbc.fill = GridBagConstraints.BOTH;
				gbc.weighty = 1;
			}

			gbc.gridx = 0;
			anomaliePanel.add(idSamplePanel, gbc);
			gbc.gridx = 1;

			anomaliePanel.add(sourceSamplePanel, gbc);
			gbc.gridx = 2;

			anomaliePanel.add(destSamplePanel, gbc);
			gbc.gridx = 3;

			anomaliePanel.add(typeSamplePanel, gbc);
			gbc.gridx = 4;

			anomaliePanel.add(typeLiteralSamplePanel, gbc);

		}
		return anomaliePanel;
	}

	@Override
	public void aboutToHidePanel() {
		// Can do something here, but we've chosen not not.
	}

}
