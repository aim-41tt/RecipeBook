package ru.example.RecipeBook.ui.auth;

import dev.aim_41tt.sling.core.Page;
import dev.aim_41tt.sling.tools.dialogs.MessageDialog;
import ru.example.RecipeBook.Config.SpringContext;
import ru.example.RecipeBook.models.DTO.RegUserDTO;
import ru.example.RecipeBook.services.AuthService;
import ru.example.RecipeBook.ui.pages.MainMenuPage;

import javax.swing.*;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RegPage extends Page {

	private AuthService authServ = SpringContext.getBean(AuthService.class);
	private final Map<String, JComponent> inputFields = new HashMap<>();

	@Override
	@SuppressWarnings("serial")
	public void onCreate() {
		Image bgImage = new ImageIcon(getClass().getResource("/images/RegistrationPage.png")).getImage();

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
		formPanel.setBorder(BorderFactory.createEmptyBorder(220, 120, 40, 120));
		formPanel.setOpaque(false);

		String[] labels = { "Имя", "Логин", "Электронная почта", "Пароль" };

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

		JButton registerButton = new JButton("Зарегистрироваться") {
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
		registerButton.setBackground(Color.WHITE);
		registerButton.setForeground(Color.BLACK);
		registerButton.setFont(new Font("Inter", Font.PLAIN, 25));
		registerButton.setFocusPainted(false);
		registerButton.setContentAreaFilled(false);
		registerButton.setOpaque(false);
		registerButton.setPreferredSize(new Dimension(300, 30));
		registerButton.setMaximumSize(new Dimension(300, 30));
		registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Добавляем обработчик клика по кнопке регистрации
		registerButton.addActionListener(e -> {
			if(reg()) {
				navigateTo(MainMenuPage.class);
			}
		});

		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.X_AXIS));
		loginPanel.setOpaque(false);
		loginPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel label = new JLabel("Уже есть аккаунт?");
		label.setFont(new Font("Inter", Font.PLAIN, 20));

		JButton loginButton = new JButton("Войти");
		loginButton.setFont(new Font("Inter", Font.PLAIN, 20));
		loginButton.setForeground(new Color(0xC6AA67));
		loginButton.setFocusPainted(false);
		loginButton.setContentAreaFilled(false);
		loginButton.setBorderPainted(false);
		loginButton.setOpaque(false);

		// 👉 Обработка нажатия "Войти"
		loginButton.addActionListener(e -> app.navigateTo(LoginPage.class));

		loginPanel.add(label);
		loginPanel.add(Box.createHorizontalStrut(6));
		loginPanel.add(loginButton);

		bottomPanel.add(registerButton);
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

	public boolean reg() {
		String[] keysArray = inputFields.keySet().toArray(new String[0]);
		try {
			System.out.println(Arrays.toString(keysArray));
			authServ.regUser(new RegUserDTO(getFieldValue(keysArray[0]), getFieldValue(keysArray[3]),
					getFieldValue(keysArray[1]), getFieldValue(keysArray[2])));
		} catch (IllegalArgumentException e) {
			MessageDialog.error(e.getMessage(), "Ошибка");
			return false;
		} catch (Exception e) {
			MessageDialog.error(e.getMessage(), "Фатальная ошибка");
			return false;
		}
		return true;
	}
}
