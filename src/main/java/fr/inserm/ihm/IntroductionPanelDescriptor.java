package fr.inserm.ihm;

import java.awt.Color;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

import fr.inserm.Main;
import fr.inserm.tools.loader.DependeciesChecker;

public class IntroductionPanelDescriptor extends WizardPanelDescriptor {
	String nbErrorMessage;
	public static final String IDENTIFIER = "INTRODUCTION_PANEL";

	IntroductionPanel introPanel;

	public IntroductionPanelDescriptor() {
		introPanel = new IntroductionPanel(this);
		setPanelDescriptorIdentifier(IDENTIFIER);
		setPanelComponent(introPanel);
	}

	@Override
	public void displayingPanel() {
		introPanel.display();
		checkDependencies();
	}

	public boolean checkDependencies() {
		boolean isErrors = false;
		introPanel.errorsPanel.removeAll();
		introPanel.errorsPanel.revalidate();
		Border redline = BorderFactory.createLineBorder(Color.red);
		introPanel.errorsPanel.setBorder(redline);
		List<String> errors = DependeciesChecker.checkDependencies(Main.propertiesBean);
		// disable suivant
		if (errors.size() > 0) {
			isErrors = true;
			introPanel.scroller.setVisible(true);
			getWizard().setNextFinishButtonEnabled(false);
			introPanel.errorsPanel.setVisible(true);
			if (errors.size() == 1)
				nbErrorMessage = " erreur";
			else
				nbErrorMessage = " erreurs";
			JLabel nbErrors = new JLabel("Il y a " + errors.size() + nbErrorMessage
					+ ", merci de vérifier votre saisie. (Faire défiler pour voir les erreurs)");
			nbErrors.setForeground(Color.red);
			introPanel.errorsPanel.add(nbErrors);
		} else {
			getWizard().setNextFinishButtonEnabled(true);
			introPanel.errorsPanel.setVisible(false);
			introPanel.scroller.setVisible(false);
		}
		for (String error : errors) {

			JLabel jLabelerr = new JLabel(error);
			jLabelerr.setForeground(Color.red);
			Color colorbg = new Color(255, 226, 226);
			introPanel.errorsPanel.setBackground(colorbg);
			introPanel.errorsPanel.add(jLabelerr);
		}
		if (!isErrors) {
			introPanel.configCircle.setIcon(introPanel.greenCircle);
			introPanel.configLine.setIcon(introPanel.greenLine);
			introPanel.exportCircle.setIcon(introPanel.blackCircle);
			introPanel.exportLine.setIcon(introPanel.blackLine);
			introPanel.sendCircle.setIcon(introPanel.blackCircle);
			introPanel.sendLine.setIcon(introPanel.blackLine);
			introPanel.endCircle.setIcon(introPanel.blackCircle);
			// introPanel.endLine.setIcon(introPanel.blackLine);

		} else {
			introPanel.configCircle.setIcon(introPanel.redCircle);
			introPanel.configLine.setIcon(introPanel.redLine);
			introPanel.exportCircle.setIcon(introPanel.blackCircle);
			introPanel.exportLine.setIcon(introPanel.blackLine);
			introPanel.sendCircle.setIcon(introPanel.blackCircle);
			introPanel.sendLine.setIcon(introPanel.blackLine);
			introPanel.endCircle.setIcon(introPanel.blackCircle);
			// introPanel.endLine.setIcon(introPanel.blackLine);
		}
		return isErrors;
	}

	@Override
	public Object getNextPanelDescriptor() {
		return ExportingPanelDescriptor.IDENTIFIER;
	}

	@Override
	public Object getBackPanelDescriptor() {
		return null;
	}

}
