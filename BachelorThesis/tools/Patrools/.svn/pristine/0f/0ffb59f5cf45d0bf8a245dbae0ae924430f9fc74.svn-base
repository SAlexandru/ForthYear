package lrg.patrools.init;

import java.net.URL;
import java.util.Iterator;

import java.io.File;

import lrg.common.abstractions.managers.EntityTypeManager;
import lrg.common.abstractions.plugins.AbstractPlugin;
import lrg.common.metamodel.Loader;
import lrg.patrools.wala.extensions.listeners.WalaRepresentationBuilder;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import startup.EarlyStart;

import codepro.preferences.PreferenceInitializer;

public class Activator extends AbstractUIPlugin implements IStartup {

	public static final String PLUGIN_ID = "Patrools";
	
	private static String ICO_PATH;

	private static Activator plugin;
	
	public Activator() {}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}
	
	public static String getIconPath() {
		return ICO_PATH;
	}
	
	private void init() throws Exception {
		
		//Find bundle path
		URL url = Platform.getBundle(PLUGIN_ID).getEntry(File.separator);
		url = FileLocator.resolve(url);
		String pluginPath = url.getPath();
		ICO_PATH=pluginPath + "res" + File.separator + "ico" + File.separator;

		//Subsystems preferences
		EarlyStart.setPreferenceStoreId(pluginPath+"codepro.preferece");
		PreferenceInitializer prefInit= new PreferenceInitializer();
		prefInit.initializeDefaultPreferences(); 

		//Set-up entity types
		EntityTypeManager.registerEntityType("result", "");
		EntityTypeManager.registerEntityType("numerical", "");
		EntityTypeManager.registerEntityType("boolean", "");
		EntityTypeManager.registerEntityType("string", "");
		EntityTypeManager.registerEntityType("group", "");
		EntityTypeManager.registerEntityType("system", "");
		EntityTypeManager.registerEntityType("workspace", "");
		EntityTypeManager.registerEntityType("system", "workspace");
		EntityTypeManager.registerEntityType("package root", "system");
		EntityTypeManager.registerEntityType("subsystem", "package root");
		EntityTypeManager.registerEntityType("package", "subsystem");
		EntityTypeManager.registerEntityType("compilation unit", "package");
		EntityTypeManager.registerEntityType("class", "compilation unit");
		EntityTypeManager.registerEntityType("method", "class");
		EntityTypeManager.registerEntityType("attribute", "class");
		EntityTypeManager.registerEntityType("local variable", "method");
		EntityTypeManager.registerEntityType("local variable","global function");
		EntityTypeManager.registerEntityType("parameter", "method");
		EntityTypeManager.registerEntityType("parameter", "global function");
		EntityTypeManager.registerEntityType("inheritance relation", "system");
		
		//Set-up plugins	
		String scanPaths[] = {
				"lib"+File.separator+"codepro"+File.separator+"codepro.core",
				"bin"
		};
		for(String scanPath : scanPaths) {
			Loader loader = new Loader(pluginPath + File.separator + scanPath);
			Iterator<String> it = loader.getNames().iterator();
			while (it.hasNext()) {
				String s = it.next();
				AbstractPlugin someCommand = loader.buildFrom(s, "plugins");
				if (someCommand != null) {
					EntityTypeManager.attach(someCommand);
				}
			}
		}
	}

	public void earlyStartup() {
		try {
			init();
			JavaCore.addElementChangedListener(WalaRepresentationBuilder.getInstance());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
