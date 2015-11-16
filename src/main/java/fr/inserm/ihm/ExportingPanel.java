package fr.inserm.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import fr.inserm.Main;
import fr.inserm.bean.PropertiesBean;
import fr.inserm.ihm.images.ImageStaticResource;
import fr.inserm.ihm.themes.Theme_default;
import fr.inserm.log.AlertLogExporter;
import fr.inserm.log.TransformAlert;

public class ExportingPanel extends InsermPanel implements ActionListener {

	/**
	 * 
	 */

	private static Logger LOGGER = Logger.getLogger(ExportingPanel.class);
	private static final long serialVersionUID = -3965319101523320101L;
	private JLabel blankSpace;
	private JPanel jPanel1;
	public JLabel progressDescription;
	private JProgressBar progressSent;
	private JLabel welcomeTitle;
	JPanel contentPanel1;
	public List<TransformAlert> alertes;
	public JPanel reportBtnPanel;
	public Theme_default theme = new Theme_default();
	JPanel anomaliePanel;
	JLabel anomalieSource;
	JLabel anomalieDest;
	JLabel anomalieId;
	JLabel anomalieType;
	JLabel anomalieLiteral;
	JLabel nbExportedEchLbl;
	JLabel nbExportedFilesLbl;
	int nbExportedEch;
	int nbExportedFiles;
	int nbAnomalie;
	JPanel reportPanel;
	public JLabel nbAnomalieLbl;
	public JLabel nbFilesLabel;
	public JLabel nbEchLabel;
	public JLabel nbAnomLabel;

	public ExportingPanel() {
		super();
	}

	public void setProgressText(String s) {
		progressDescription.setText(s);
	}

	public void setProgressValue(int i) {
		progressSent.setValue(i);
	}

	/**
	 * methode executee à l affichage. Ne pas executer avant car objet du bean proerties a recuperer dynamiquement.
	 */
	public void display() {

		if (contentPanel1 == null) {

			contentPanel1 = new JPanel();
			contentPanel1.setBackground(theme.getFond());
			welcomeTitle = new JLabel();
			jPanel1 = new JPanel();
			blankSpace = new JLabel();
			progressSent = new JProgressBar();
			
			progressSent.setPreferredSize(new Dimension(window_width - 50, 35));
			progressDescription = new JLabel();
			contentPanel1.setLayout(new java.awt.BorderLayout());

			welcomeTitle.setText("Les données vont être récupérées et exportées dans le dossier d'export:"
					+ Main.propertiesBean.getFolderExport());
			contentPanel1.add(welcomeTitle, BorderLayout.NORTH);

			jPanel1.setLayout(new java.awt.GridLayout(0, 1));
			jPanel1.setBackground(theme.getFond());
			jPanel1.add(blankSpace);

			progressSent.setStringPainted(true);

			jPanel1.add(progressSent);

			progressDescription.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
			progressDescription.setText("Connecting to Server...");
			jPanel1.add(progressDescription);
			anomaliePanel = theme.getAnomaliePanel();

			contentPanel1.add(jPanel1, java.awt.BorderLayout.CENTER);
			contentPanel1.add(anomaliePanel, BorderLayout.SOUTH);
			reportPanel = new JPanel();

			nbExportedFilesLbl = new JLabel("Nombre de fichiers générés : ");
			nbExportedEchLbl = new JLabel("Nombre d'échantillons exportés : ");
			nbAnomalieLbl = new JLabel("Nombred 'anomalies détectées : ");
			
			reportPanel.setLayout(new GridBagLayout());
			reportPanel.setPreferredSize(new Dimension(window_width - 50, 52));
			reportPanel.setAlignmentX(CENTER_ALIGNMENT);
			reportPanel.setBorder(BorderFactory.createLineBorder(theme.getNoir(), 2));
			reportPanel.setBackground(theme.getFond());
			
			GridBagConstraints reportConstraint = new GridBagConstraints();
			reportConstraint.weightx = 0.1;
			reportConstraint.fill = GridBagConstraints.BOTH;
			//reportConstraint.anchor = GridBagConstraints.CENTER;
			reportConstraint.gridx = 0;
			reportConstraint.gridy = 0;
			reportConstraint.gridheight = 3;
			
			JLabel syntheseLabel = new JLabel("Synthèse");
			JPanel synthesePanel = theme.getAnomalieTitleFieldPanel();
			synthesePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, theme.getNoir()));
			GridBagConstraints synthConstraint = new GridBagConstraints();
			syntheseLabel.setFont(theme.getAnomalietitlefont());
			synthesePanel.add(syntheseLabel, synthConstraint);
			reportPanel.add(synthesePanel, reportConstraint);
			
			reportConstraint.fill = GridBagConstraints.HORIZONTAL;
			reportConstraint.weightx = 0;
			reportConstraint.gridheight = 1;
			reportConstraint.gridx = 1;
			reportConstraint.gridy = 0;
			synthConstraint.weightx=0.01;
			synthConstraint.anchor = GridBagConstraints.FIRST_LINE_START;		
			
			JPanel nbFilesLblPanel = theme.getAnomalieSampleFieldPanel(true);
			nbFilesLblPanel.setBorder(null);
			nbFilesLblPanel.add(nbExportedFilesLbl, synthConstraint);
			
			JPanel nbEchLblPanel = theme.getAnomalieSampleFieldPanel(false);
			nbEchLblPanel.add(nbExportedEchLbl, synthConstraint);
			
			JPanel nbAnomalieLblPanel = theme.getAnomalieSampleFieldPanel(true);
			nbAnomalieLblPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, theme.getNoir()));
			nbAnomalieLblPanel.add(nbAnomalieLbl, synthConstraint);
			
			reportPanel.add(nbFilesLblPanel, reportConstraint);
			
			reportConstraint.gridy = 1;
			reportPanel.add(nbEchLblPanel, reportConstraint);
			reportConstraint.gridy = 2;
			reportPanel.add(nbAnomalieLblPanel, reportConstraint);
			reportConstraint.weightx = 1;
			synthConstraint.weightx=1;
			synthConstraint.anchor = GridBagConstraints.FIRST_LINE_START;
			JPanel nbFilesPanel = theme.getAnomalieSampleFieldPanel(true);
			nbFilesPanel.setBorder(null);
			nbFilesPanel.setAlignmentY(LEFT_ALIGNMENT);
			nbFilesLabel = new JLabel(Integer.toString(nbExportedFiles));
			nbFilesPanel.add(nbFilesLabel, synthConstraint);
			
			JPanel nbEchPanel = theme.getAnomalieSampleFieldPanel(false);
			nbEchLabel = new JLabel(Integer.toString(nbExportedEch));
			nbEchPanel.add(nbEchLabel, synthConstraint);
			
			JPanel nbAnomPanel = theme.getAnomalieSampleFieldPanel(true);
			nbAnomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, theme.getNoir()));
			nbAnomLabel = new JLabel(Integer.toString(nbAnomalie));
			nbAnomPanel.add(nbAnomLabel, synthConstraint);
			
			reportConstraint.gridx = 2;
			reportConstraint.gridy = 0;
			reportPanel.add(nbFilesPanel, reportConstraint);

			reportConstraint.gridy = 1;
			reportPanel.add(nbEchPanel, reportConstraint);

			reportConstraint.gridy = 2;
			reportPanel.add(nbAnomPanel, reportConstraint);

			JPanel sec = new JPanel();
			sec.setBackground(theme.getFond());
			sec.add(contentPanel1, BorderLayout.CENTER);
			sec.add(reportPanel, BorderLayout.SOUTH);
			add(sec, BorderLayout.CENTER);
			reportBtnPanel = new JPanel();
			reportBtnPanel.setPreferredSize(new Dimension(Component.WIDTH, 35));

			reportBtnPanel.setBackground(theme.getBleupastel());
			JButton reportBtn = new JButton();
			reportBtn.setIcon(ImageStaticResource.getSaveIcon());
			reportBtn.setToolTipText("Enregistrer le rapport d'export");
			reportBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
			reportBtn.setActionCommand("exportRapport");
			reportBtn.addActionListener(this);
			reportBtnPanel.setBackground(theme.getFond());
			reportBtnPanel.add(reportBtn);
			reportBtnPanel.setAlignmentY(Component.TOP_ALIGNMENT);

			add(reportBtnPanel, BorderLayout.SOUTH);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("exportRapport")) {
			PropertiesBean properties = Main.propertiesBean;
			JFileChooser exportFile = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(".xml", "xml");
			exportFile.setFileFilter(filter);
			int isFileSelected = exportFile.showSaveDialog(this);
			if (isFileSelected == JFileChooser.APPROVE_OPTION) {
				File fileSelected = exportFile.getSelectedFile();
				String fileName;

				if (!fileSelected.getName().endsWith(".xml")) {
					fileName = fileSelected.getPath() + ".xml.report";

				} else {
					fileName = fileSelected.getPath() + ".report";

				}
				AlertLogExporter.exportAlertesInXMLFile(Integer.toString(nbExportedFiles),
						Integer.toString(nbExportedEch), fileName, alertes, properties);
			}
		}

	}

}
