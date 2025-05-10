package ru.example.RecipeBook.ui.pages;

import dev.aim_41tt.sling.core.Page;
import ru.example.RecipeBook.Config.ClientSettings;
import ru.example.RecipeBook.Config.SpringContext;
import ru.example.RecipeBook.models.enums.Themes;
import ru.example.RecipeBook.ui.NavigationPanel;

import javax.swing.*;
import java.awt.*;

public class SettingsPage extends Page {

	@Override
	public void onCreate() {
		setTitle(" - Настройки");
		
	    getPanel().setLayout(new BoxLayout(getPanel(), BoxLayout.Y_AXIS));
	    getPanel().setBackground(ClientSettings.getSettings().getThemeColor());

	    Component verticalSpacer = Box.createRigidArea(new Dimension(0, 20));

	    Page navPanel = app.getPagePattern(NavigationPanel.class);
	    navPanel.getPanel().setMaximumSize(new Dimension(Integer.MAX_VALUE, 210));
	    navPanel.getPanel().setAlignmentX(Component.CENTER_ALIGNMENT);

	    getPanel().add(verticalSpacer);
	    getPanel().add(navPanel.getPanel());
	    getPanel().add(Box.createVerticalStrut(20));

	    // Панель настроек
	    JPanel settingsPanel = new JPanel();
	    settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
	    settingsPanel.setBackground(Color.decode("#C6AA67"));
	    settingsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    settingsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

	    // Заголовок
	    JLabel titleLabel = new JLabel("Настройки приложения");
	    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    titleLabel.setFont(new Font(ClientSettings.getSettings().getFont(), Font.BOLD, 20));
	    settingsPanel.add(Box.createVerticalStrut(30));
	    settingsPanel.add(titleLabel);
	    settingsPanel.add(Box.createVerticalStrut(20));

	    // Панель выбора шрифта
	    JPanel fontPanel = new JPanel();
	    fontPanel.setLayout(new BoxLayout(fontPanel, BoxLayout.X_AXIS));
	    fontPanel.setOpaque(false);

	    JLabel fontLabel = new JLabel("Сменить шрифт:");
	    fontLabel.setFont(new Font(ClientSettings.getSettings().getFont(), Font.PLAIN, 16));
	    fontLabel.setPreferredSize(new Dimension(130, 30));

	    String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	    
	    JComboBox<String> fontComboBox = new JComboBox<>(fontNames);
	    fontComboBox.setSelectedItem(ClientSettings.getSettings().getFont());
	    fontComboBox.setMaximumSize(new Dimension(250, 30));
	    fontComboBox.setPreferredSize(new Dimension(250, 30));

	    fontComboBox.addActionListener(e -> onFontSelected((String) fontComboBox.getSelectedItem()));

	    fontPanel.add(fontLabel);
	    fontPanel.add(Box.createHorizontalStrut(10));
	    fontPanel.add(fontComboBox);

	    settingsPanel.add(fontPanel);
	    settingsPanel.add(Box.createVerticalStrut(20));

	    // Панель смены темы
	    JPanel themePanel = new JPanel();
	    themePanel.setLayout(new BoxLayout(themePanel, BoxLayout.X_AXIS));
	    themePanel.setOpaque(false);

	    JLabel themeLabel = new JLabel("Сменить тему:");
	    themeLabel.setFont(new Font(ClientSettings.getSettings().getFont(), Font.PLAIN, 16));
	    themeLabel.setPreferredSize(new Dimension(130, 30));

	    JButton themeToggleButton = new JButton("Сменить на "+ClientSettings.getSettings().getThemeInver());
	    themeToggleButton.setPreferredSize(new Dimension(250, 30));
	    themeToggleButton.setMaximumSize(new Dimension(250, 30));

	    themeToggleButton.addActionListener(e -> onThemeToggle());

	    themePanel.add(themeLabel);
	    themePanel.add(Box.createHorizontalStrut(10));
	    themePanel.add(themeToggleButton);

	    settingsPanel.add(themePanel);
	    settingsPanel.add(Box.createVerticalGlue());

	    JPanel wrapper = new JPanel();
	    wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
	    wrapper.setOpaque(false);
	    wrapper.add(Box.createVerticalGlue());
	    wrapper.add(settingsPanel);

	    getPanel().add(wrapper);
	}


	/**
	 * Вызывается при выборе нового шрифта.
	 *
	 * @param fontName имя выбранного шрифта
	 *
	 */
	private void onFontSelected(String fontName) {
	    ClientSettings.getSettings().setFont(fontName);
	    SpringContext.getBean(ClientSettings.class).saveFile();
	    app.repaintPages();
	}

	/**
	 * Вызывается при нажатии на кнопку смены темы.
	 */
	private void onThemeToggle() {
		if (ClientSettings.getSettings().getThemeEnum().equals(Themes.LIGHT)) {
			ClientSettings.getSettings().setThemeEnum(Themes.DARK);
		} else {

			ClientSettings.getSettings().setThemeEnum(Themes.LIGHT);
		}
		SpringContext.getBean(ClientSettings.class).saveFile();
		app.repaintPages();
	}

}
