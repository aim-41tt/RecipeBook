package ru.example.RecipeBook.ui;

import javax.swing.*;

import dev.aim_41tt.sling.core.Page;
import ru.example.RecipeBook.Config.ClientSettings;
import ru.example.RecipeBook.ui.pages.FavoritePage;
import ru.example.RecipeBook.ui.pages.SettingsPage;
import ru.example.RecipeBook.ui.pages.categori.CategoriePage;
import ru.example.RecipeBook.ui.pages.recipe.ReceptsPage;
import ru.example.RecipeBook.ui.reports.ReportsPage;

import java.awt.*;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class NavigationPanel extends Page {

	private final Map<String, JButton> buttonsByName = new LinkedHashMap<>();

	@Override
	public void onCreate() {
		getPanel().setLayout(new BoxLayout(getPanel(), BoxLayout.Y_AXIS));
		getPanel().setOpaque(false); // Прозрачная панель

		addLogo();
		getPanel().add(Box.createVerticalStrut(20)); // Отступ 20 пикселей вниз
		addButtonRow();
	}

	private void addLogo() {
		// Загружаем оригинальное изображение
		ImageIcon originalIcon = new ImageIcon(getClass()
				.getResource("/images/logo_" + ClientSettings.getSettings().getThemeEnum().toString() + ".png"));

		Image scaledImage = originalIcon.getImage().getScaledInstance(219, 102, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		// Создаём JLabel с масштабированным изображением
		JLabel logoLabel = new JLabel(scaledIcon);
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Центрирование по оси X
		logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// Центрируем через wrapper-панель
		JPanel wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.X_AXIS));
		wrapper.setOpaque(false);
		wrapper.add(Box.createHorizontalGlue());
		wrapper.add(logoLabel);
		wrapper.add(Box.createHorizontalGlue());

		// Добавляем в основной layout
		getPanel().add(wrapper);
	}

	private void addButtonRow() {
		JPanel buttonRow = new JPanel(new GridLayout(1, 5));
		buttonRow.setOpaque(false); // Прозрачный фон под кнопками

		addNavButton("Рецепты", buttonRow, "/images/icon_butten/recepts.png", () -> {
			Page page = app.getPage(ReceptsPage.class);
			page.repaint();
			page.onCreate();
			navigateTo(ReceptsPage.class);
		});
		addNavButton("Избранное", buttonRow, "/images/icon_butten/izbr.png", () -> {
			Page page = app.getPage(FavoritePage.class);
			page.repaint();
			page.onCreate();
			navigateTo(FavoritePage.class);
		});
		addNavButton("Категории", buttonRow, "/images/icon_butten/katalogs.png", () -> {
			Page page = app.getPage(CategoriePage.class);
			page.repaint();
			page.onCreate();
			navigateTo(CategoriePage.class);
		});
		addNavButton("Отчёты", buttonRow, "/images/icon_butten/Otch.png", () -> {
			navigateTo(ReportsPage.class);
		});
		addNavButton("Настройки", buttonRow, "/images/icon_butten/Settings.png", () -> {
			navigateTo(SettingsPage.class);
		});

		getPanel().add(buttonRow);
	}

	private void addNavButton(String name, JPanel container, String iconPath, Runnable action) {
		JButton button = new JButton(name);

		button.setFont(new Font(ClientSettings.getSettings().getFont(), Font.PLAIN, 13));
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setForeground(ClientSettings.getSettings().getThemeTextColor());
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.setHorizontalAlignment(SwingConstants.CENTER);

		// загружаем иконку
		if (iconPath != null && !iconPath.isEmpty()) {
			try {
				URL resource = getClass().getResource(iconPath);
				if (resource != null) {
					// Загружаем иконку как Image и масштабируем
					ImageIcon rawIcon = new ImageIcon(resource);
					Image scaledImage = rawIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // например
																											// 20x20
					ImageIcon scaledIcon = new ImageIcon(scaledImage);

					button.setIcon(scaledIcon);
					button.setHorizontalTextPosition(SwingConstants.LEFT); // текст слева, иконка справа
					button.setIconTextGap(10); // отступ между текстом и иконкой
				} else {
					System.err.println("Icon not found: " + iconPath);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		button.addActionListener(e -> action.run());

		buttonsByName.put(name, button);
		container.add(button);
	}

	public JButton getButton(String name) {
		return buttonsByName.get(name);
	}
}
