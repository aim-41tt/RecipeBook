package ru.example.RecipeBook.ui.reports;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dev.aim_41tt.sling.core.Page;
import ru.example.RecipeBook.Config.ClientSettings;
import ru.example.RecipeBook.ui.NavigationPanel;

public class ReportsPage  extends Page {
	@Override
	public void onCreate() {
		setTitle(" - Отчёты");

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
		JLabel titleLabel = new JLabel("Отчёты");
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font(ClientSettings.getSettings().getFont(), Font.BOLD, 20));
		settingsPanel.add(Box.createVerticalStrut(30));
		settingsPanel.add(titleLabel);
		settingsPanel.add(Box.createVerticalStrut(20));

		getPanel().add(settingsPanel);
	}
}
