package ru.example.RecipeBook.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import dev.aim_41tt.sling.core.Page;
import ru.example.RecipeBook.ui.auth.LoginPage;
import ru.example.RecipeBook.ui.auth.RegPage;

public class MainPage extends Page {

	@Override
	@SuppressWarnings("serial")
	public void onCreate() {
		try {
			Image bgImage = new ImageIcon(getClass().getResource("/images/background.png")).getImage();

			JPanel backgroundPanel = new JPanel() {
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
				}
			};

			backgroundPanel.setLayout(new BorderLayout());
			backgroundPanel.setOpaque(false);

			// Очищаем всё из getPanel() и кладем туда backgroundPanel
			JPanel rootPanel = getPanel();
			rootPanel.setLayout(new BorderLayout());
			rootPanel.removeAll(); // очистка, если есть
			rootPanel.add(backgroundPanel, BorderLayout.CENTER);

			// Далее обычная нижняя панель
			JPanel bottomWrapper = new JPanel();
			bottomWrapper.setLayout(new BoxLayout(bottomWrapper, BoxLayout.Y_AXIS));
			bottomWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 140, 0));
			bottomWrapper.setOpaque(false);

			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
			buttonPanel.setOpaque(false);

			JButton registerButton = createRoundedButton("Авторизация", () -> app.navigateTo(LoginPage.class));
			JButton loginButton = createRoundedButton("Регистрация", () -> app.navigateTo(RegPage.class));

			buttonPanel.add(loginButton);
			buttonPanel.add(Box.createVerticalStrut(8));
			buttonPanel.add(registerButton);

			buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
			bottomWrapper.add(buttonPanel);

			backgroundPanel.add(bottomWrapper, BorderLayout.SOUTH);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Создание округлой кнопки с действием
	@SuppressWarnings("serial")
	private JButton createRoundedButton(String text, Runnable action) {
		JButton button = new JButton(text) {
			@Override
			protected void paintComponent(java.awt.Graphics g) {
				java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
				g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
						java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(getBackground());
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
				super.paintComponent(g);
				g2.dispose();
			}

			@Override
			protected void paintBorder(java.awt.Graphics g) {
				// убираем стандартную границу
			}
		};
		button.setBackground(Color.WHITE);
		button.setForeground(Color.BLACK);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		button.setOpaque(false);
		button.setPreferredSize(new Dimension(240, 40));
		button.setMaximumSize(new Dimension(240, 40));
		
		button.setFont(new Font("Arial", Font.BOLD, 20));
		
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.addActionListener(e -> action.run());
		return button;
	}
}
