package net.myerichsen.hremvp.project.models;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.preference.IPreferenceStore;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

/**
 * Singleton class encapsulating a list of project model objects.
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 3. feb. 2019
 *
 */
public class ProjectList {
	@Inject
	private static IEventBroker eventBroker;

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "net.myerichsen.hremvp");
	private static List<ProjectModel> models;

	/**
	 * Exists only to defeat instantiation
	 */
	protected ProjectList() {
	}

	/**
	 * @param model
	 * @throws IOException
	 */
	public static int add(ProjectModel model) throws IOException {
		readPreferences();
		models.add(model);

		int count = store.getInt("projectcount");
		count++;

		store.setValue("project." + count + ".name", model.getName());
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		store.setValue("project." + count + ".lastupdated", df.format(model.getLastEdited()));
		store.setValue("project." + count + ".summary", model.getSummary());
		store.setValue("project." + count + ".localserver", model.getLocalServer());
		store.setValue("project." + count + ".path", model.getPath());
		store.setValue("projectcount", count);
		((ScopedPreferenceStore) store).save();
		LOGGER.info("Added " + model.getName() + " as project " + count);
		return count;
	}

	/**
	 * @return
	 */
	public static List<ProjectModel> getAllModels() {
		readPreferences();
		return models;
	}

	/**
	 * @param index
	 * @return
	 */
	public static ProjectModel getModel(int index) {
		readPreferences();
		return models.get(index);
	}

	/**
	 * @param name
	 * @return
	 */
	public static ProjectModel getModel(String name) {
		readPreferences();

		for (final ProjectModel projectModel : models) {
			if (projectModel.getName().equals(name)) {
				return projectModel;
			}
		}
		return null;
	}

	/**
	 *
	 */
	private static void readPreferences() {
		String key;
		String lastEditedString;
		String name;
		Date lastEdited;
		String summary;
		String localServer;
		String path;
		ProjectModel model;

		if (models == null) {
			models = new ArrayList<>();
		}

		final int projectCount = store.getInt("projectcount");

		try {
			for (int i = 1; i <= projectCount; i++) {
				key = new String("project." + i + ".name");
				name = store.getString(key);

				key = new String("project." + i + ".lastupdated");
				lastEditedString = store.getString(key);
				final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				lastEdited = df.parse(lastEditedString);

				key = new String("project." + i + ".summary");
				summary = store.getString(key);

				key = new String("project." + i + ".localserver");
				localServer = store.getString(key);

				key = new String("project." + i + ".path");
				path = store.getString(key);

				model = new ProjectModel(name, lastEdited, summary, localServer, path);
				models.add(model);
			}
		} catch (final ParseException e) {
			LOGGER.severe(e.getMessage());
			eventBroker.post("MESSAGE", e.getMessage());
		}
	}

	/**
	 * Remove project from model and preferences
	 * 
	 * @param index
	 * @param model
	 */
	public static void remove(int index, String dbName) {
		ProjectModel model = getModel(index);
		models.remove(model);

		int count = store.getInt("projectcount");

		for (int i = index; i < count; i++) {
			store.setValue("project." + i + ".name", store.getString("project." + (i + 1) + ".name"));
			store.setValue("project." + i + ".lastupdated", store.getString("project." + (i + 1) + ".lastupdated"));
			store.setValue("project." + i + ".summary", store.getString("project." + (i + 1) + ".summary"));
			store.setValue("project." + i + ".localserver", store.getString("project." + (i + 1) + ".localserver"));
			store.setValue("project." + i + ".path", store.getString("project." + (i + 1) + ".path"));
		}

		store.setToDefault("project." + count + ".name");
		store.setToDefault("project." + count + ".lastupdated");
		store.setToDefault("project." + count + ".summary");
		store.setToDefault("project." + count + ".localserver");
		store.setToDefault("project." + count + ".path");

		count--;
		store.setValue("projectcount", count);
	}

	/**
	 * @return
	 */
	public static boolean verify() {
		for (final ProjectModel projectModel : models) {
			if (projectModel.getLocalServer().equalsIgnoreCase("LOCAL")) {
				final String path = projectModel.getPath();
				final File file = new File(path);
				if (file.exists() == false) {
					LOGGER.severe("File " + projectModel.getName() + " does not exist");
					eventBroker.post("MESSAGE", "File " + projectModel.getName() + " does not exist");
					return false;
				}
			}
		}
		return true;
	}
}