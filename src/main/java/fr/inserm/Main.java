package fr.inserm;


import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import fr.inserm.bean.PropertiesBean;
import fr.inserm.ihm.ExportingPanelDescriptor;
import fr.inserm.ihm.IntroductionPanelDescriptor;
import fr.inserm.ihm.TransmissionPanelDescriptor;
import fr.inserm.ihm.Wizard;
import fr.inserm.ihm.WizardPanelDescriptor;
import fr.inserm.tools.loader.ApplicationLoader;

/**
 * classe de lancement.
 * 
 * @author nicolas
 * 
 */
public class Main {

	/**
	 * bean qui contient les proprietes du site, utile dans toute l application.
	 */
	public static PropertiesBean propertiesBean;
	public static boolean exportedWithWarning = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					UIManager.getLookAndFeelDefaults().put("nimbusOrange", (new Color(127, 255, 191)));
					break;
				}
			}
		} catch (Exception e) {
			// handle exception
			System.out.println("pb look and feel:" + e.getMessage());
		}

		ApplicationLoader loader = new ApplicationLoader();
		propertiesBean = new PropertiesBean(loader);
		Wizard wizard = new Wizard();
		wizard.getDialog().setTitle("Biobanques-connecteur");

		WizardPanelDescriptor intro = new IntroductionPanelDescriptor();
		wizard.registerWizardPanel(IntroductionPanelDescriptor.IDENTIFIER, intro);

		WizardPanelDescriptor export = new ExportingPanelDescriptor();
		wizard.registerWizardPanel(ExportingPanelDescriptor.IDENTIFIER, export);

		WizardPanelDescriptor transmission = new TransmissionPanelDescriptor();
		wizard.registerWizardPanel(TransmissionPanelDescriptor.IDENTIFIER, transmission);

		wizard.setCurrentPanel(IntroductionPanelDescriptor.IDENTIFIER);

		wizard.showModalDialog();
		System.exit(0);

	}

}
