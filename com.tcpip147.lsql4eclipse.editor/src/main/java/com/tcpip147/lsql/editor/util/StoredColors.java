package com.tcpip147.lsql.editor.util;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.editors.text.EditorsUI;

public class StoredColors {

	public static Color get(String key) {
		RGB rgb = null;
		IPreferenceStore store = EditorsUI.getPreferenceStore();
		if (store.contains(key)) {
			if (store.isDefault(key)) {
				rgb = PreferenceConverter.getDefaultColor(store, key);
			} else {
				rgb = PreferenceConverter.getColor(store, key);
			}
		}
		return EditorsUI.getSharedTextColors().getColor(rgb);
	}
}
