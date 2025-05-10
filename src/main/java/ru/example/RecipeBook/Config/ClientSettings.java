package ru.example.RecipeBook.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.example.RecipeBook.models.DTO.Settings;

import java.io.*;

@Component
public class ClientSettings {

	private static Settings settings;
	private final ObjectMapper mapper;

	public ClientSettings(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	public void saveFile() {

		if (settings == null) {
			settingsВefault();
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("settings.bin"))) {
			String json = mapper.writeValueAsString(settings);
			writer.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadFile() {
		StringBuilder json = new StringBuilder();

		try (BufferedReader reader = new BufferedReader(new FileReader("settings.bin"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				json.append(line);
			}

			settings = mapper.readValue(json.toString(), Settings.class);

			System.out.println("Настройки загружены: " + settings);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void settingsВefault() {
		settings = new Settings();
	}

	/**
	 * @return the settings
	 */
	public static Settings getSettings() {
		return settings;
	}

	
	
	/**
	 * @param settings the settings to set
	 */
	public static void setSettings(Settings settings) {
		ClientSettings.settings = settings;
	}

}
