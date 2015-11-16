package fr.inserm.ihm.images;

import javax.swing.ImageIcon;

/**
 * classe de chargement statique des images.
 * 
 * @author nicolas
 * 
 */
public class ImageStaticResource {
	static ImageResource imgRes = new ImageResource();

	public static ImageIcon getImageCloudsIcon() {
		return imgRes.getImageIcon("clouds.jpg");
	}

	public static ImageIcon getImageCancelIcon() {
		return imgRes.getImageIcon("cancelIcon.png");
	}

	public static ImageIcon getImageFinishIcon() {
		return imgRes.getImageIcon("finishIcon.png");
	}

	public static ImageIcon getImageNextIcon() {
		return imgRes.getImageIcon("nextIcon.png");
	}

	public static ImageIcon getImageBackIcon() {
		return imgRes.getImageIcon("backIcon.png");
	}

	public static ImageIcon getImageInsermIcon() {
		return imgRes.getImageIcon("inserm.fr.gif");
	}

	public static ImageIcon getImageBlackLineIcon() {
		return imgRes.getImageIcon("line_black.gif");
	}

	public static ImageIcon getImageBlueLineIcon() {
		return imgRes.getImageIcon("line_blue.gif");
	}

	public static ImageIcon getImageGreenLineIcon() {
		return imgRes.getImageIcon("line_green.gif");
	}

	public static ImageIcon getImageOrangeLineIcon() {
		return imgRes.getImageIcon("line_orange.gif");
	}

	public static ImageIcon getImageRedLineIcon() {
		return imgRes.getImageIcon("line_red.gif");
	}

	public static ImageIcon getImageBlackCircleIcon() {
		return imgRes.getImageIcon("circle_black.gif");
	}

	public static ImageIcon getImageBlueCircleIcon() {
		return imgRes.getImageIcon("circle_blue.gif");
	}

	public static ImageIcon getImageGreenCircleIcon() {
		return imgRes.getImageIcon("circle_green.gif");
	}

	public static ImageIcon getImageRedCircleIcon() {
		return imgRes.getImageIcon("circle_red.gif");
	}

	public static ImageIcon getImageOrangeCircleIcon() {
		return imgRes.getImageIcon("circle_orange.gif");
	}
	public static ImageIcon getImageLogoInserm() {
		return imgRes.getImageIcon("logo_inserm200-54.gif");
	}
	public static ImageIcon getImageLogoBiobanques() {
		return imgRes.getImageIcon("logobb125-100.png");
	}
	public static ImageIcon getSaveIcon() {
		return imgRes.getImageIcon("saveIcon.png");
	}
	public static ImageIcon getLoadIcon() {
		return imgRes.getImageIcon("loadIcon.png");
	}
}
