package ru.example.RecipeBook.ui.pages.categori;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

import dev.aim_41tt.sling.core.Page;
import ru.example.RecipeBook.Config.ClientSettings;
import ru.example.RecipeBook.Config.SpringContext;
import ru.example.RecipeBook.models.Category;
import ru.example.RecipeBook.services.CategoryService;

public class CreateCategoriePage extends Page {

	private final CategoryService categoryService = SpringContext.getBean(CategoryService.class);
	private final Color accentColor = Color.decode("#C6AA67");

	private JTextField nameField;
	private JTextArea descriptionArea;

	@Override
	public void onCreate() {
		setTitle(" - Новая категория");

		getPanel().setLayout(new BoxLayout(getPanel(), BoxLayout.Y_AXIS));
		getPanel().setBackground(ClientSettings.getSettings().getThemeColor());
		getPanel().setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel titleLabel = new JLabel("Создание категории");
		titleLabel.setFont(new Font(ClientSettings.getSettings().getFont(), Font.BOLD, 20));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setForeground(accentColor);
		getPanel().add(titleLabel);
		getPanel().add(Box.createRigidArea(new Dimension(0, 15)));

		// ===== Название категории =====
		nameField = new JTextField();
		nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		nameField.setFont(new Font(ClientSettings.getSettings().getFont(), Font.PLAIN, 16));
		nameField.setBorder(BorderFactory.createTitledBorder("Название категории"));
		nameField.setBackground(accentColor);
		getPanel().add(nameField);
		getPanel().add(Box.createRigidArea(new Dimension(0, 10)));

		// ===== Описание категории =====
		descriptionArea = new JTextArea(5, 30);
		descriptionArea.setFont(new Font(ClientSettings.getSettings().getFont(), Font.PLAIN, 15));
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);
		descriptionArea.setBorder(BorderFactory.createTitledBorder("Описание"));
		descriptionArea.setBackground(accentColor);

		JScrollPane scrollPane = new JScrollPane(descriptionArea);
		scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
		getPanel().add(scrollPane);
		getPanel().add(Box.createRigidArea(new Dimension(0, 20)));

		// ===== Кнопки =====
		JPanel panelButton = new JPanel();
		panelButton.setBackground(ClientSettings.getSettings().getThemeColor());
		panelButton.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

		JButton saveBtn = new JButton("Сохранить");
		saveBtn.setBackground(accentColor);
		saveBtn.setForeground(Color.BLACK);
		saveBtn.setPreferredSize(new Dimension(160, 40));
		saveBtn.setFont(new Font(ClientSettings.getSettings().getFont(), Font.BOLD, 16));
		saveBtn.addActionListener(this::saveCategory);

		JButton cancelBtn = new JButton("Отмена");
		cancelBtn.setBackground(Color.RED);
		cancelBtn.setForeground(Color.WHITE);
		cancelBtn.setPreferredSize(new Dimension(160, 40));
		cancelBtn.setFont(new Font(ClientSettings.getSettings().getFont(), Font.BOLD, 16));
		cancelBtn.addActionListener(e -> navigateTo(CategoriePage.class));

		panelButton.add(cancelBtn);
		panelButton.add(saveBtn);

		getPanel().add(panelButton);
	}

	private void saveCategory(ActionEvent e) {
		String name = nameField.getText().trim();
		String desc = descriptionArea.getText().trim();

		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(getPanel(), "Название не может быть пустым!", "Ошибка", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Category category = new Category();
		category.setCategoryName(name);
		category.setDescription(desc);

		categoryService.createCategory(category);
		JOptionPane.showMessageDialog(getPanel(), "Категория успешно сохранена!");
		navigateTo(CategoriePage.class);
	}
}
