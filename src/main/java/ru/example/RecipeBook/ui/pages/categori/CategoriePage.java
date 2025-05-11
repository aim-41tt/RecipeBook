package ru.example.RecipeBook.ui.pages.categori;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import dev.aim_41tt.sling.core.Page;
import ru.example.RecipeBook.Config.ClientSettings;
import ru.example.RecipeBook.Config.SpringContext;
import ru.example.RecipeBook.models.Category;
import ru.example.RecipeBook.services.CategoryService;
import ru.example.RecipeBook.ui.NavigationPanel;

public class CategoriePage extends Page {

	private final CategoryService categoryService = SpringContext.getBean(CategoryService.class);
	private final Color accentColor = Color.decode("#C6AA67");

	@Override
	public void onCreate() {
		setTitle(" - Категории");

		getPanel().setLayout(new BorderLayout());
		getPanel().setBackground(ClientSettings.getSettings().getThemeColor());

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.setBackground(ClientSettings.getSettings().getThemeColor());

		Page navPanel = app.getPagePattern(NavigationPanel.class);
		navPanel.repaint();
		navPanel.onCreate();
		navPanel.getPanel().setMaximumSize(new Dimension(Integer.MAX_VALUE, 210));

		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(accentColor);
		headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JButton addCategoryButton = new JButton("Добавить категорию");
		addCategoryButton.setFont(new Font(ClientSettings.getSettings().getFont(), Font.BOLD, 16));
		addCategoryButton.setBackground(accentColor);
		addCategoryButton.setForeground(ClientSettings.getSettings().getThemeTextColor());
		addCategoryButton.setFocusPainted(false);
		addCategoryButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		addCategoryButton.addActionListener(e -> openPageCreateCategory());
		headerPanel.add(addCategoryButton);
		topPanel.add(Box.createRigidArea(new Dimension(0, 20))); // ⬅️ добавляем отступ сверху
		topPanel.add(navPanel.getPanel());
		topPanel.add(Box.createRigidArea(new Dimension(0, 20))); // ⬅️ можешь оставить или убрать
		topPanel.add(headerPanel);
		
		getPanel().add(topPanel, BorderLayout.NORTH);

		// ===== Сетка категорий =====
		JPanel categoryGrid = new JPanel();
		categoryGrid.setLayout(new BoxLayout(categoryGrid, BoxLayout.Y_AXIS));
		categoryGrid.setBackground(ClientSettings.getSettings().getThemeColor());
		categoryGrid.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));


		List<Category> categories = categoryService.getAll();
		for (Category category : categories) {
			categoryGrid.add(createCategoryPanel(category));
		}

		JScrollPane scrollPane = new JScrollPane(categoryGrid);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setBorder(null);

		getPanel().add(scrollPane, BorderLayout.CENTER);
	}

	private JPanel createCategoryPanel(Category category) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBackground(accentColor);
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK, 1),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)
		));

		// Название категории
		JLabel nameLabel = new JLabel(category.getCategoryName());
		nameLabel.setFont(new Font(ClientSettings.getSettings().getFont(), Font.BOLD, 16));
		nameLabel.setForeground(ClientSettings.getSettings().getThemeTextColor());
		nameLabel.setPreferredSize(new Dimension(180, 30));

		// Описание категории
		JTextArea descArea = new JTextArea(category.getDescription());
		descArea.setFont(new Font(ClientSettings.getSettings().getFont(), Font.PLAIN, 14));
		descArea.setForeground(ClientSettings.getSettings().getThemeTextColor());
		descArea.setBackground(accentColor);
		descArea.setLineWrap(true);
		descArea.setWrapStyleWord(true);
		descArea.setEditable(false);
		descArea.setFocusable(false);
		descArea.setOpaque(false);
		descArea.setBorder(null);

		// Вычислим высоту в зависимости от длины описания
		int lineCount = (category.getDescription().length() / 60) + 1;
		int lineCountSmalLength = lineCount > 1?lineCount:2;
		int descHeight = Math.min(100, lineCountSmalLength * 20); // максимум 100px
		descArea.setMaximumSize(new Dimension(400, descHeight));
		descArea.setPreferredSize(new Dimension(400, descHeight));

		// Кнопка удалить
		JButton deleteButton = new JButton("Удалить");
		deleteButton.setFocusPainted(false);
		deleteButton.setBackground(Color.RED);
		deleteButton.setForeground(Color.WHITE);
		deleteButton.setFont(new Font(ClientSettings.getSettings().getFont(), Font.BOLD, 14));
		deleteButton.addActionListener(e -> confirmDelete(category));
		deleteButton.setPreferredSize(new Dimension(100, 30));
		deleteButton.setMaximumSize(new Dimension(120, 40));

		// Контейнер для текста
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
		textPanel.setBackground(accentColor);
		textPanel.add(nameLabel);
		textPanel.add(Box.createVerticalStrut(5));
		textPanel.add(descArea);

		panel.add(textPanel);
		panel.add(Box.createHorizontalGlue());
		panel.add(deleteButton);

		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, descHeight + 40));

		return panel;
	}




	private void confirmDelete(Category category) {
		int result = JOptionPane.showConfirmDialog(
			null,
			"Вы уверены, что хотите удалить категорию \"" + category.getCategoryName() + "\"?",
			"Подтверждение удаления",
			JOptionPane.YES_NO_OPTION
		);

		if (result == JOptionPane.YES_OPTION) {
			categoryService.deleteCategory(category.getCategoryId());
			repaint();
			onCreate();
		}
	}

	private void openPageCreateCategory() {
		navigateTo(CreateCategoriePage.class);
	}
}
