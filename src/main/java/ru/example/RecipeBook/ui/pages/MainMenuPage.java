package ru.example.RecipeBook.ui.pages;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import dev.aim_41tt.sling.core.Page;
import ru.example.RecipeBook.Config.ClientSettings;
import ru.example.RecipeBook.ui.NavigationPanel;

public class MainMenuPage extends Page {

	@Override
	public void onCreate() {
		setTitle(" - Главное меню");
		
	    getPanel().setLayout(new BoxLayout(getPanel(), BoxLayout.Y_AXIS));
	    getPanel().setBackground(ClientSettings.getSettings().getThemeColor());

	    Component verticalSpacer = Box.createRigidArea(new Dimension(0, 20));

	    Page navPanel = app.getPagePattern(NavigationPanel.class);
	    navPanel.repaint();
	    navPanel.onCreate();
	    navPanel.getPanel().setMaximumSize(new Dimension(Integer.MAX_VALUE, 210));
	    navPanel.getPanel().setAlignmentX(Component.CENTER_ALIGNMENT);

	    getPanel().add(verticalSpacer);
	    getPanel().add(navPanel.getPanel());
	    getPanel().add(Box.createVerticalStrut(20));

	    // Панель
	    JPanel settingsPanel = new JPanel();
	    settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
	    settingsPanel.setBackground(Color.decode("#C6AA67"));
	    settingsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    settingsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

	    // Заголовок
	    JLabel titleLabel = new JLabel("Преимущества приложения");
	    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    titleLabel.setFont(new Font(ClientSettings.getSettings().getFont(), Font.BOLD, 20));
	    settingsPanel.add(Box.createVerticalStrut(30));
	    settingsPanel.add(titleLabel);
	    settingsPanel.add(Box.createVerticalStrut(20));

	    
	    
	    
	    
		ImageIcon originalIcon = new ImageIcon(getClass()
				.getResource("/images/MainMenuPage_" + ClientSettings.getSettings().getThemeEnum().toString() + ".png"));

		Image scaledImage = originalIcon.getImage().getScaledInstance(360, 380, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		// Создаём JLabel с масштабированным изображением
		JLabel logoLabel = new JLabel(scaledIcon);
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Центрирование по оси X
		logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		Component verticalSpacer1 = Box.createRigidArea(new Dimension(0, 20));
		settingsPanel.add(logoLabel);
		settingsPanel.add(verticalSpacer1);
		getPanel().add(settingsPanel);
	}

}