package net.myerichsen.hremvp.providers;

import java.util.List;

import org.eclipse.jface.viewers.LabelProvider;

/**
 * Default JFace combo label provider
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2019
 * @version 4. mar. 2019
 *
 */
public class HREComboLabelProvider extends LabelProvider {
	/**
	 * @param element
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getText(Object element) {
		final List<String> list = (List<String>) element;
		return list.get(2);
	}
}