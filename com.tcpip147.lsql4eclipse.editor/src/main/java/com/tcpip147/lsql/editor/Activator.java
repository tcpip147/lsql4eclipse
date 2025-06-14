package com.tcpip147.lsql.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "com.tcpip147.lsql4eclipse.editor";
	private static Activator plugin;

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	public static Activator getDefault() {
		return plugin;
	}

	@Override
	protected void initializeImageRegistry(ImageRegistry registry) {
		ImageDescriptor plus = imageDescriptorFromPlugin(PLUGIN_ID, "icons/add_obj.png");
		registry.put("plus", plus);
		ImageDescriptor minus = imageDescriptorFromPlugin(PLUGIN_ID, "icons/delete_obj.png");
		registry.put("minus", minus);
		ImageDescriptor pencil = imageDescriptorFromPlugin(PLUGIN_ID, "icons/text_edit.gif");
		registry.put("pencil", pencil);
		ImageDescriptor copy = imageDescriptorFromPlugin(PLUGIN_ID, "icons/copy_edit.png");
		registry.put("copy", copy);
		ImageDescriptor arrowUp = imageDescriptorFromPlugin(PLUGIN_ID, "icons/prev_nav.png");
		registry.put("arrow-up", arrowUp);
		ImageDescriptor arrowDown = imageDescriptorFromPlugin(PLUGIN_ID, "/icons/next_nav.png");
		registry.put("arrow-down", arrowDown);
	}

	public static Image getImage(String key) {
		return getDefault().getImageRegistry().get(key);
	}
}
