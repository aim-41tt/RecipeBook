package ru.example.RecipeBook.models.DTO;

import java.awt.Color;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.example.RecipeBook.models.enums.Themes;

public class Settings {

	private Themes theme = Themes.LIGHT;
	private String font = "Arial";

	// Геттер/сеттер для сериализации Jackson
	public Themes getThemeEnum() {
		return theme;
	}

	public void setThemeEnum(Themes theme) {
		this.theme = theme;
	}

	/**
	 * @return the font
	 */
	public String getFont() {
		return font;
	}

	/**
	 * @param font the font to set
	 */
	public void setFont(String font) {
		this.font = font;
	}
	

	/**
	 * @return the theme
	 */
	@JsonIgnore
	public Themes getThemeInver() {
		return theme.equals(Themes.DARK) ? Themes.LIGHT : Themes.DARK;
	}

	// Метод только для отображения, не сериализуется
	@JsonIgnore
	public Color getThemeColor() {
		return theme.equals(Themes.DARK) ? Color.BLACK : Color.WHITE;
	}

	@JsonIgnore
	public Color getThemeTextColor() {
		return theme.equals(Themes.DARK) ? Color.WHITE : Color.BLACK;
	}
}
