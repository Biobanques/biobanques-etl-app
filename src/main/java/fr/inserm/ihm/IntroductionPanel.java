package fr.inserm.ihm;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import fr.inserm.DbDefinitionType;
import fr.inserm.Main;
import fr.inserm.OuiNonType;
import fr.inserm.bean.PropertiesBean;
import fr.inserm.ihm.images.ImageStaticResource;
import fr.inserm.tools.loader.ApplicationLoader;

public class IntroductionPanel extends InsermPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 96802265009891915L;

	private static Logger LOGGER = Logger.getLogger(IntroductionPanel.class);
	JLabel blankSpace;
	JTextField siteTF;
	JTextField idSiteTF;
	JTextField finessSiteTF;
	/**
	 * champs pour la base
	 */
	// JComboBox<DbDefinitionType> choixDbTF;
	JComboBox choixDbTF;
	JTextField hostTF;
	JTextField portTF;
	JTextField dbNameTF;
	JTextField userNameTF;
	JPasswordField passwordTF;
	// JTextField passwordTF;
	JTextField sqlFilterTF;
	JTextField datasourceFolderTableurs;
	/**
	 * champs pour l export
	 */
	JTextField pathExportTF;
	JTextField pathSentTF;
	// JComboBox<OuiNonType> cryptTF;
	JComboBox cryptTF;
	JComboBox compressTF;
	/**
	 * champs pour la transmission
	 */
	JTextField serverReceptionTF;
	JTextField pathReceptionTF;
	JTextField loginFTPTF;
	// JTextField passwordFTPTF;
	JPasswordField passwordFTPTF;

	IntroductionPanelDescriptor descParent;

	public IntroductionPanel(IntroductionPanelDescriptor descriptorParent) {
		super();
		descParent = descriptorParent;
	}

	// JPanel contentPanel1;

	public JPanel errorsPanel;

	public JPanel mainPanel;
	public JScrollPane scroller;

	/**
	 * methode executee a l affichage du panel.
	 */
	public void display() {
		if (mainPanel == null) {
			// Border blueLineBorder =
			// BorderFactory.createLineBorder(Color.BLUE);
			mainPanel = new JPanel();
			blankSpace = new JLabel();
			JPanel contentPanel1 = new JPanel();
			JLabel welcomeTitle = new JLabel();
			// JLabel jLabelClickNext = new JLabel();
			errorsPanel = new JPanel();
			errorsPanel.setLayout(new GridLayout(0, 1));
			contentPanel1.setLayout(new GridBagLayout());
			welcomeTitle.setFont(new java.awt.Font("MS Sans Serif", Font.BOLD,
					11));
			welcomeTitle
					.setText("Bienvenue dans l'outil express d'export de données pour biobanques!");

			mainPanel.setLayout(new GridLayout(2, 1));
			mainPanel.setMaximumSize(new Dimension(window_width - 10, 60));
			mainPanel.add(welcomeTitle);
			scroller = new JScrollPane(errorsPanel);
			Dimension dim = new Dimension(window_width - 10, 30);
			scroller.setMaximumSize(dim);
			scroller.setPreferredSize(dim);
			scroller.setMinimumSize(dim);
			mainPanel.add(scroller);
			mainPanel.setBackground(theme.getFond());

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridy = 0;
			gbc.weighty = 0.1;
			contentPanel1.add(mainPanel, gbc);
			gbc.gridy = 1;
			gbc.weighty = 1;
			contentPanel1.add(getConfigurationPanel(), gbc);
			contentPanel1
					.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
			contentPanel1.setBackground(theme.getFond());
			add(contentPanel1);
		}
	}

	/**
	 * supprime les erreurs du errorPanel.
	 */
	public void resetErrors() {
		errorsPanel = new JPanel();
		errorsPanel.setLayout(new GridLayout(1, 0));
	}

	JPanel dbPanel = new JPanel();

	public JPanel getConfigurationPanel() {
		JPanel result = new JPanel();
		siteTF = new JTextField();
		idSiteTF = new JTextField();
		finessSiteTF = new JTextField();

		pathExportTF = new JTextField();
		pathSentTF = new JTextField();
		choixDbTF = new JComboBox();
		hostTF = new JTextField();
		portTF = new JTextField();
		dbNameTF = new JTextField();
		userNameTF = new JTextField();
		// passwordTF = new JTextField();
		passwordTF = new JPasswordField();

		sqlFilterTF = new JTextField();
		datasourceFolderTableurs = new JTextField(14);
		cryptTF = new JComboBox();
		compressTF = new JComboBox();
		serverReceptionTF = new JTextField();
		pathReceptionTF = new JTextField();
		loginFTPTF = new JTextField();
		// passwordFTPTF = new JTextField();
		passwordFTPTF = new JPasswordField();

		BoxLayout frameLayout = new BoxLayout(result, BoxLayout.Y_AXIS);
		result.setLayout(frameLayout);
		result.setBackground(theme.getVioletpastel());
		JPanel sitePanel = new JPanel();
		sitePanel.setBorder(BorderFactory
				.createTitledBorder("Configuration du site local"));

		sitePanel.add(new JLabel("Nom du site:"));
		sitePanel.add(siteTF);
		sitePanel.add(new JLabel("Id du site(unique):"));
		sitePanel.add(idSiteTF);
		sitePanel.add(new JLabel("Numéro Finess du site:"));
		sitePanel.add(finessSiteTF);
		GridLayout siteLayout;
		dbPanel.setBorder(BorderFactory
				.createTitledBorder("Configuration de la base de données"));
		if (Main.propertiesBean.getFolderDatasourceTableurs() == null
				|| Main.propertiesBean.getFolderDatasourceTableurs().equals("")
				|| Main.propertiesBean.getFolderDatasourceTableurs().equals(
						"${folder.datasource.tableurs}")) {
			dbPanel.setLayout(new GridLayout(7, 2));

			siteLayout = new GridLayout(7, 2);
			sitePanel.add(new JLabel(""));
			sitePanel.add(new JLabel(""));
			sitePanel.add(new JLabel(""));
			sitePanel.add(new JLabel(""));
			sitePanel.add(new JLabel(""));
			sitePanel.add(new JLabel(""));
			sitePanel.add(new JLabel(""));
			sitePanel.add(new JLabel(""));
			dbPanel.add(new JLabel("Choix de la base de données:"));
			dbPanel.add(choixDbTF);
			dbPanel.add(new JLabel("Hôte de la base de données :"));
			dbPanel.add(hostTF);
			dbPanel.add(new JLabel("Port de la base de données :"));
			dbPanel.add(portTF);
			dbPanel.add(new JLabel("Nom de la base de données :"));
			dbPanel.add(dbNameTF);
			dbPanel.add(new JLabel("Nom d'utilisateur de la bdd :"));
			dbPanel.add(userNameTF);
			dbPanel.add(new JLabel("mot de passe de la bdd :"));
			dbPanel.add(passwordTF);
			dbPanel.add(new JLabel("Filtre SQL appliqué à la requête :"));
			dbPanel.add(sqlFilterTF);
		} else {

			siteLayout = new GridLayout(3, 2);
			dbPanel.setLayout(new GridLayout(3, 2));
			dbPanel.add(new JLabel("Dossier source du fichier Excel :"));
			dbPanel.add(datasourceFolderTableurs);
			dbPanel.add(new JLabel(""));
			dbPanel.add(new JLabel(""));
			dbPanel.add(new JLabel(""));
			dbPanel.add(new JLabel(""));

		}
		sitePanel.setLayout(siteLayout);

		// export panel
		JPanel exportPanel = new JPanel();
		exportPanel.setBorder(BorderFactory
				.createTitledBorder("Configuration de l'export"));
		exportPanel.setLayout(new GridLayout(4, 2));
		exportPanel.add(new JLabel("Chemin du dossier d'export:"));
		exportPanel.add(pathExportTF);
		exportPanel.add(new JLabel("Chemin du dossier d'envoi:"));
		exportPanel.add(pathSentTF);
		exportPanel.add(new JLabel("Cryptage des fichiers:"));
		exportPanel.add(cryptTF);
		exportPanel.add(new JLabel("Compression des fichiers:"));
		exportPanel.add(compressTF);

		// transmission panel
		JPanel transmissionPanel = new JPanel();
		TitledBorder transmissionPanelTitle = BorderFactory
				.createTitledBorder("Configuration de l'envoi");
		transmissionPanel.setBorder(transmissionPanelTitle);
		transmissionPanel.setLayout(new GridLayout(4, 2));
		transmissionPanel.add(new JLabel("Serveur inserm de reception:"));
		transmissionPanel.add(serverReceptionTF);
		transmissionPanel.add(new JLabel("Dossier de reception:"));
		transmissionPanel.add(pathReceptionTF);
		transmissionPanel.add(new JLabel("Nom d'utilisateur:"));
		transmissionPanel.add(loginFTPTF);
		transmissionPanel.add(new JLabel("Mot de passe:"));
		transmissionPanel.add(passwordFTPTF);

		JButton saveButton = new JButton("Tester la configuration");
		saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		saveButton.setActionCommand("testConfig");

		saveButton.addActionListener(this);

		JButton exportButton = new JButton();
		exportButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exportButton.setActionCommand("exportParam");
		exportButton.setIcon(ImageStaticResource.getSaveIcon());

		exportButton.setToolTipText("enregistrer les paramètres");
		exportButton.addActionListener(this);

		JButton importButton = new JButton();
		importButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		importButton.setActionCommand("importParam");
		importButton.setIcon(ImageStaticResource.getLoadIcon());
		importButton.addActionListener(this);
		importButton.setToolTipText("importer les paramètres enregistrés");

		JPanel configurationGrid = new JPanel();
		configurationGrid.setBackground(theme.getBleupastel());
		configurationGrid.setLayout(new GridBagLayout());

		GridBagConstraints dbPanelConstraints = new GridBagConstraints();
		dbPanelConstraints.gridx = 0;
		dbPanelConstraints.gridy = 0;
		dbPanelConstraints.gridwidth = 1;
		dbPanelConstraints.gridheight = 1;
		dbPanelConstraints.fill = GridBagConstraints.BOTH;
		dbPanel.setBackground(theme.getBleupastel());
		configurationGrid.add(dbPanel, dbPanelConstraints);

		GridBagConstraints sitePanelConstraints = new GridBagConstraints();
		sitePanelConstraints.gridx = 1;
		sitePanelConstraints.gridy = 0;
		sitePanelConstraints.gridwidth = 1;
		sitePanelConstraints.gridheight = 1;
		sitePanelConstraints.fill = GridBagConstraints.BOTH;
		sitePanel.setBackground(theme.getBleupastel());
		configurationGrid.add(sitePanel, sitePanelConstraints);

		GridBagConstraints exportPanelConstraints = new GridBagConstraints();
		exportPanelConstraints.gridx = 0;
		exportPanelConstraints.gridy = 1;
		exportPanelConstraints.gridwidth = 1;
		exportPanelConstraints.gridheight = 1;
		exportPanelConstraints.fill = GridBagConstraints.BOTH;
		exportPanel.setBackground(theme.getBleupastel());
		configurationGrid.add(exportPanel, exportPanelConstraints);

		GridBagConstraints transmissionPanelConstraints = new GridBagConstraints();
		transmissionPanelConstraints.gridx = 1;
		transmissionPanelConstraints.gridy = 1;
		transmissionPanelConstraints.gridwidth = 1;
		transmissionPanelConstraints.gridheight = 1;
		transmissionPanelConstraints.fill = GridBagConstraints.BOTH;
		transmissionPanel.setBackground(theme.getBleupastel());
		configurationGrid.add(transmissionPanel, transmissionPanelConstraints);
		result.add(configurationGrid, BorderLayout.CENTER);

		JPanel btns = new JPanel();
		btns.setBackground(theme.getBleupastel());
		btns.setLayout(new GridBagLayout());

		GridBagConstraints btnSaveConstraint = new GridBagConstraints();
		btnSaveConstraint.gridy = 0;
		btnSaveConstraint.weightx = 96;
		btnSaveConstraint.anchor = GridBagConstraints.FIRST_LINE_END;

		GridBagConstraints btnExportConstraint = new GridBagConstraints();
		btnExportConstraint.gridy = 0;
		btnExportConstraint.weightx = 2;
		GridBagConstraints btnImportConstraint = new GridBagConstraints();
		btnImportConstraint.gridy = 0;
		btnImportConstraint.weightx = 2;

		btns.add(exportButton, btnExportConstraint);
		btns.add(importButton, btnImportConstraint);
		btns.add(saveButton, btnSaveConstraint);

		result.add(btns, BorderLayout.SOUTH);
		initFilling(true);
		return result;
	}

	/**
	 * remplit les forms avec les infos des properties.
	 * 
	 * @return
	 */

	private void initFilling(boolean first) {
		idSiteTF.setText("" + Main.propertiesBean.getIdSite());
		finessSiteTF.setText(Main.propertiesBean.getFinessSite());
		siteTF.setText("" + Main.propertiesBean.getNameSite());
		if (first) {
			for (DbDefinitionType db : DbDefinitionType.values()) {
				choixDbTF.addItem(db);
			}
		}
		DbDefinitionType dbChoice = Main.propertiesBean.getDbChoice();
		/*
		 * 
		 * 
		 */
		choixDbTF.setSelectedItem(dbChoice);
		choixDbTF.setEnabled(false);
		pathExportTF.setText(Main.propertiesBean.getFolderExport());
		pathSentTF.setText(Main.propertiesBean.getFolderExportSent());
		sqlFilterTF.setText(Main.propertiesBean.getSqlFilter());
		if (!Main.propertiesBean.getFolderDatasourceTableurs().equals(
				"${folder.datasource.tableurs}")) {
			datasourceFolderTableurs.setText(Main.propertiesBean
					.getFolderDatasourceTableurs());
		} else {
			datasourceFolderTableurs.setText("");
		}
		if (Main.propertiesBean != null) {
			dbNameTF.setText(Main.propertiesBean.getDbname());
			hostTF.setText(Main.propertiesBean.getHost());
			portTF.setText(Main.propertiesBean.getPort());
			userNameTF.setText(Main.propertiesBean.getLogin());
			passwordTF.setText(Main.propertiesBean.getPassword());
		}
		/**
		 * init des infos d envoi ftp/sftp
		 */
		serverReceptionTF.setText(Main.propertiesBean.getServer());
		pathReceptionTF.setText(Main.propertiesBean.getDirectory());
		loginFTPTF.setText(Main.propertiesBean.getUsername());
		passwordFTPTF.setText(Main.propertiesBean.getPasswordFTP());
		if (first) {
			cryptTF.addItem(OuiNonType.OUI);
			cryptTF.addItem(OuiNonType.NON);
		}
		if (first) {
			compressTF.addItem(OuiNonType.OUI);
			compressTF.addItem(OuiNonType.NON);
		}
		if ((Main.propertiesBean.crypt)) {
			cryptTF.setSelectedItem(OuiNonType.OUI);
		} else {
			cryptTF.setSelectedItem(OuiNonType.NON);
		}
		if ((Main.propertiesBean.compress)) {
			compressTF.setSelectedItem(OuiNonType.OUI);
		} else {
			compressTF.setSelectedItem(OuiNonType.NON);
		}
		repaint();
	}

	/**
	 * enregsitre dans le bean de proprietes les informations utiles<br>
	 * N'enregistre pas toutes les infos pour ne pas afficher les données en dev
	 * beta et sensibles.
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("testConfig")) {
			tester();

		}

		/*
		 * Commande du bouton exporter Teste la validité des infos, puis
		 * enregistre les propriétés dans un fichier .properties, à un
		 * emplacement choisi par l'utilisateur
		 */
		if (e.getActionCommand().equals("exportParam")) {
			boolean err = tester();
			if (!err) {
				exporter();
			} else {
				JOptionPane
						.showMessageDialog(this,
								"Des erreurs sont présentes,\nimpossible d'exporter les paramètres!");
			}

		}
		/*
		 * Commande du bouton importer. importe des propriétés depuis un fichier
		 * sur le disque
		 */
		if (e.getActionCommand().equals("importParam")) {
			importer();
		}
	}

	public boolean tester() {

		boolean isErrors = false;
		try {
			DbDefinitionType choix = (DbDefinitionType) choixDbTF
					.getSelectedItem();
			Main.propertiesBean.setDbChoice(choix);
		} catch (Exception ex) {
			LOGGER.warn("pb parsing choix db" + ex.getMessage());
			isErrors = true;
		}
		/**
		 * proprietes de la base de données
		 */
		try {
			Main.propertiesBean.setDbname(dbNameTF.getText());
			Main.propertiesBean.setHost(hostTF.getText());
			Main.propertiesBean.setPort(portTF.getText());
			Main.propertiesBean.setLogin(userNameTF.getText());
			Main.propertiesBean.setPassword(passwordTF.getText());
			Main.propertiesBean.setSqlFilter(sqlFilterTF.getText());
			Main.propertiesBean
					.setFolderDatasourceTableurs(datasourceFolderTableurs
							.getText());

			/**
			 * proprietes d export
			 */
			try {
				OuiNonType choix = (OuiNonType) cryptTF.getSelectedItem();
				if (choix.equals(OuiNonType.OUI)) {
					Main.propertiesBean.setCrypt(true);
				} else {
					Main.propertiesBean.setCrypt(false);
				}
			} catch (Exception ex) {
				LOGGER.warn("pb parsing choix db" + ex.getMessage());
				isErrors = true;
			}
			try {
				OuiNonType choix = (OuiNonType) compressTF.getSelectedItem();
				if (choix.equals(OuiNonType.OUI)) {
					Main.propertiesBean.setCompress(true);
				} else {
					Main.propertiesBean.setCompress(false);
				}
			} catch (Exception ex) {
				LOGGER.warn("pb parsing choix db" + ex.getMessage());
				isErrors = true;
			}
			Main.propertiesBean.setFolderExport(pathExportTF.getText());
			Main.propertiesBean.setFolderExportSent(pathSentTF.getText());
			/**
			 * proprietes de transfert
			 */
			Main.propertiesBean.setServer(serverReceptionTF.getText());
			Main.propertiesBean.setDirectory(pathReceptionTF.getText());
			Main.propertiesBean.setUsername(loginFTPTF.getText());
			Main.propertiesBean.setPasswordFTP(passwordFTPTF.getText());
			isErrors = descParent.checkDependencies();

		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return isErrors;
	}

	public void importer() {
		JFileChooser propsFile = new JFileChooser();
		int isfileSelected = propsFile.showOpenDialog(this);
		if (isfileSelected == JFileChooser.APPROVE_OPTION) {
			File fileSelected = propsFile.getSelectedFile();
			Main.propertiesBean = new PropertiesBean(new ApplicationLoader(
					fileSelected));
			initFilling(false);
		}
	}

	public void exporter() {
		PropertiesBean pb = Main.propertiesBean;
		Properties props = new Properties();
		String booleanToString = "";
		if (pb.compress) {
			booleanToString = "1";
		} else {
			booleanToString = "0";
		}
		props.put("application.export.compress", booleanToString);

		if (pb.crypt) {
			booleanToString = "1";
		} else {
			booleanToString = "0";
		}
		props.put("application.export.crypt", booleanToString);

		if (pb.debugMode) {
			booleanToString = "1";
		} else {
			booleanToString = "0";
		}
		props.put("application.debug.mode", booleanToString);
		props.put("db.choix", Integer.toString(pb.dbChoice.getValue()));
		props.put("application.export.cryptphrase", pb.cryptphrase);
		props.put("db.name", pb.dbname);
		props.put("inserm.ftp.path", pb.directory);
		props.put("application.export.cron", pb.exportCron);
		props.put("application.export.mode", Integer.toString(pb.exportMode));
		props.put("site.finess", pb.finessSite);
		props.put("folder.export", pb.folderExport);
		props.put("folder.export.sent", pb.folderExportSent);
		props.put("folder.report", pb.folderReport);
		props.put("db.host", pb.host);
		props.put("site.id", pb.idSite);
		props.put("db.user.login", pb.login);
		props.put("site.nom", pb.nameSite);
		props.put("db.user.password", pb.password);
		props.put("inserm.ftp.password", pb.passwordFTP);
		props.put("db.port", pb.port);
		props.put("inserm.ftp.server", pb.server);
		props.put("application.sql.filter", pb.sqlFilter);
		props.put("inserm.ftp.login", pb.username);
		props.put("application.version", pb.versionNumber.substring(8));
		props.put("folder.datasource.tableurs", pb.folderDatasourceTableurs);
		JFileChooser propsFile = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				".properties", "properties");
		propsFile.setFileFilter(filter);
		int isFileSelected = propsFile.showSaveDialog(this);
		if (isFileSelected == JFileChooser.APPROVE_OPTION) {
			File fileSelected = propsFile.getSelectedFile();

			try {
				if (!fileSelected.getName().endsWith(".properties")) {
					FileWriter fw = new FileWriter(fileSelected.getPath()
							+ ".properties");
					props.store(fw, null);
				} else {
					FileWriter fw = new FileWriter(fileSelected);
					props.store(fw, null);
				}

			} catch (Exception ex) {
				LOGGER.error("erreur d'enregistrement : " + ex);
			}
		}
	}
}
