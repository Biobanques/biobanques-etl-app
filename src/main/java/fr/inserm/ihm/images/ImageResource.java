package fr.inserm.ihm.images;

import java.net.URL;

import javax.swing.ImageIcon;

public class ImageResource {

	public ImageIcon getImageIcon(String name) {
		ImageIcon res = null;
		URL url = getClass().getResource(name);
		if (url != null) {
			res = new ImageIcon(url);
		}
		return res;
	}
}
