package ru.example.RecipeBook.ui.auth;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dev.aim_41tt.sling.core.Page;
import dev.aim_41tt.sling.tools.dialogs.MessageDialog;
import ru.example.RecipeBook.Config.SpringContext;
import ru.example.RecipeBook.models.DTO.LoginUserDTO;
import ru.example.RecipeBook.services.AuthService;
import ru.example.RecipeBook.ui.pages.MainMenuPage;

public class LoginPage extends Page {

	private AuthService authServ = SpringContext.getBean(AuthService.class);
	private final Map<String, JComponent> inputFields = new HashMap<>();

	@Override
	@SuppressWarnings("serial")
	public void onCreate() {
		Image bgImage = new ImageIcon(getClass().getResource("/images/AuthorizationPage.png")).getImage();

		JPanel backgroundPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
			}
		};
		backgroundPanel.setLayout(new BorderLayout());

		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		formPanel.setBorder(BorderFactory.createEmptyBorder(320, 120, 40, 120));
		formPanel.setOpaque(false);

		String[] labels = { "Логин", "Пароль" };

		for (String labelText : labels) {
			JLabel label = new JLabel(labelText);
			label.setFont(new Font("Inter", Font.PLAIN, 18));
			label.setAlignmentX(Component.LEFT_ALIGNMENT);

			JComponent inputField;

			if (labelText.equals("Пароль")) {
				JPasswordField passwordField = new JPasswordField();
				passwordField.setFont(new Font("Inter", Font.PLAIN, 17));
				passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
				passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
				passwordField.setMargin(new Insets(5, 10, 5, 10));
				inputField = passwordField;
			} else {
				JTextField textField = new JTextField();
				textField.setFont(new Font("Inter", Font.PLAIN, 17));
				textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
				textField.setAlignmentX(Component.LEFT_ALIGNMENT);
				textField.setMargin(new Insets(5, 10, 5, 10));
				inputField = textField;
			}

			inputFields.put(labelText, inputField);

			formPanel.add(label);
			formPanel.add(Box.createVerticalStrut(4));
			formPanel.add(inputField);
			formPanel.add(Box.createVerticalStrut(15));
		}

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
		bottomPanel.setOpaque(false);

		JButton loginButton = new JButton("Войти") {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(getBackground());
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
				super.paintComponent(g);
				g2.dispose();
			}

			@Override
			protected void paintBorder(Graphics g) {
			}
		};
		loginButton.setBackground(Color.WHITE);
		loginButton.setForeground(Color.BLACK);
		loginButton.setFont(new Font("Inter", Font.PLAIN, 25));
		loginButton.setFocusPainted(false);
		loginButton.setContentAreaFilled(false);
		loginButton.setOpaque(false);
		loginButton.setPreferredSize(new Dimension(300, 30));
		loginButton.setMaximumSize(new Dimension(300, 30));
		loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Добавляем обработчик клика по кнопке регистрации
		loginButton.addActionListener(e -> {
			if(login()) {
				navigateTo(MainMenuPage.class);
			}
		});

		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.X_AXIS));
		loginPanel.setOpaque(false);
		loginPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel label = new JLabel("Нет аккаунта?");
		label.setFont(new Font("Inter", Font.PLAIN, 20));

		JButton regButton = new JButton("Зарегистрироваться");
		regButton.setFont(new Font("Inter", Font.PLAIN, 20));
		regButton.setForeground(new Color(0xC6AA67));
		regButton.setFocusPainted(false);
		regButton.setContentAreaFilled(false);
		regButton.setBorderPainted(false);
		regButton.setOpaque(false);

		regButton.addActionListener(e -> app.navigateTo(RegPage.class));

		loginPanel.add(label);
		loginPanel.add(Box.createHorizontalStrut(6));
		loginPanel.add(regButton);

		bottomPanel.add(loginButton);
		bottomPanel.add(Box.createVerticalStrut(16));
		bottomPanel.add(loginPanel);

		backgroundPanel.add(formPanel, BorderLayout.CENTER);
		backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

		getPanel().setLayout(new BorderLayout());
		getPanel().add(backgroundPanel, BorderLayout.CENTER);
	}

	public String getFieldValue(String label) {
		JComponent comp = inputFields.get(label);
		if (comp instanceof JTextField) {
			return ((JTextField) comp).getText();
		}
		if (comp instanceof JPasswordField) {
			return new String(((JPasswordField) comp).getPassword());
		}
		return "";
	}

	public boolean login() {
		String[] keysArray = inputFields.keySet().toArray(new String[0]);
		try {
			authServ.loginUser(new LoginUserDTO(getFieldValue(keysArray[1]), getFieldValue(keysArray[0])));
		} catch (IllegalArgumentException e) {
			MessageDialog.error(e.getMessage(), "Ошибка");
			return false;
		} catch (Exception e) {
			MessageDialog.error(e.getMessage(), "Фатальная ошибка");
			return false;
		}
		System.out.println(authServ.getUserAuth().toString());
		return true;
	}

}
