package ru.example.RecipeBook.ui.reports;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import dev.aim_41tt.sling.core.Page;
import ru.example.RecipeBook.Config.ClientSettings;
import ru.example.RecipeBook.Config.SpringContext;
import ru.example.RecipeBook.models.Category;
import ru.example.RecipeBook.models.Recipe;
import ru.example.RecipeBook.models.DTO.FavoriteRecipeReportDTO;
import ru.example.RecipeBook.services.CategoryService;
import ru.example.RecipeBook.services.FavoriteService;
import ru.example.RecipeBook.ui.NavigationPanel;

public class ReportsPage  extends Page {
	
	private final FavoriteService favoriteService = SpringContext.getBean(FavoriteService.class);
	private final CategoryService categoryService = SpringContext.getBean(CategoryService.class);
	
	@Override
	public void onCreate() {
		setTitle(" - Отчёты");

		getPanel().setLayout(new BoxLayout(getPanel(), BoxLayout.Y_AXIS));
		getPanel().setBackground(ClientSettings.getSettings().getThemeColor());

		// Верхний отступ
		getPanel().add(Box.createVerticalStrut(20));

		// Навигационная панель
		Page navPanel = app.getPagePattern(NavigationPanel.class);
		navPanel.repaint();
		navPanel.onCreate();
		navPanel.getPanel().setMaximumSize(new Dimension(Integer.MAX_VALUE, 210));
		navPanel.getPanel().setAlignmentX(Component.CENTER_ALIGNMENT);
		getPanel().add(navPanel.getPanel());
		getPanel().add(Box.createVerticalStrut(20));

		// ===== Первая панель с заголовком =====
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
		settingsPanel.setBackground(Color.decode("#C6AA67"));
		settingsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		settingsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

		JLabel titleLabel = new JLabel("Отчёты");
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font(ClientSettings.getSettings().getFont(), Font.BOLD, 20));

		settingsPanel.add(Box.createVerticalStrut(30));
		settingsPanel.add(titleLabel);
		settingsPanel.add(Box.createVerticalStrut(20));

		getPanel().add(settingsPanel);
		getPanel().add(Box.createVerticalStrut(20));

		// ===== Вторая панель с кнопками (вертикально) =====
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
		buttonsPanel.setBackground(Color.decode("#C6AA67"));
		buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

		// Первая кнопка
		JButton categoryReportBtn = new JButton("Сформировать отчёт по категориям");
		categoryReportBtn.setFont(new Font(ClientSettings.getSettings().getFont(), Font.PLAIN, 14));
		categoryReportBtn.setFocusPainted(false);
		categoryReportBtn.setBackground(Color.WHITE);
		categoryReportBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		categoryReportBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		categoryReportBtn.setMaximumSize(new Dimension(330, 40));
		categoryReportBtn.addActionListener(e -> generateCategoryReport());

		// Вторая кнопка
		JButton favoriteReportBtn = new JButton("Сформировать отчёт по избранному");
		favoriteReportBtn.setFont(new Font(ClientSettings.getSettings().getFont(), Font.PLAIN, 14));
		favoriteReportBtn.setFocusPainted(false);
		favoriteReportBtn.setBackground(Color.WHITE);
		favoriteReportBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		favoriteReportBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		favoriteReportBtn.setMaximumSize(new Dimension(330, 40));
		favoriteReportBtn.addActionListener(e -> generateFavoriteReport());

		// Добавление кнопок с отступами
		buttonsPanel.add(Box.createVerticalStrut(10));
		buttonsPanel.add(categoryReportBtn);
		buttonsPanel.add(Box.createVerticalStrut(15));
		buttonsPanel.add(favoriteReportBtn);
		buttonsPanel.add(Box.createVerticalStrut(10));

		getPanel().add(buttonsPanel);

	}

	
    // Заглушки:
	private void generateCategoryReport() {
		JDialog reportDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(getPanel()), "Отчёт по категориям", true, null);
		reportDialog.setSize(800, 500);
		reportDialog.setLocationRelativeTo(getPanel());
		reportDialog.setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.setBackground(Color.decode("#C6AA67"));

		JLabel label = new JLabel("Выберите категорию:");
		label.setFont(new Font(ClientSettings.getSettings().getFont(), Font.PLAIN, 14));
		topPanel.add(label);

		// Выпадающий список
		List<Category> categories = categoryService.getAll();
		JComboBox<Category> categoryComboBox = new JComboBox<>(categories.toArray(new Category[0]));
		categoryComboBox.setPreferredSize(new Dimension(250, 25));
		categoryComboBox.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			JLabel renderer = new JLabel(value != null ? value.getCategoryName() : "", JLabel.LEFT);
			if (isSelected) {
				renderer.setBackground(list.getSelectionBackground());
				renderer.setForeground(list.getSelectionForeground());
				renderer.setOpaque(true);
			}
			return renderer;
		});
		topPanel.add(categoryComboBox);

		reportDialog.add(topPanel, BorderLayout.NORTH);

		// Таблица
		String[] columnNames = { "Имя пользователя", "Название рецепта", "Ингредиенты", "Инструкции" };
		@SuppressWarnings("serial")
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable table = new JTable(tableModel);
		table.setAutoCreateRowSorter(true);
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		reportDialog.add(scrollPane, BorderLayout.CENTER);

		// Обработка выбора категории
		categoryComboBox.addActionListener(e -> {
			Category selectedCategory = (Category) categoryComboBox.getSelectedItem();
			if (selectedCategory == null) return;

			// Очистить таблицу
			tableModel.setRowCount(0);

			// Заполнить рецепты
			List<Recipe> recipes = selectedCategory.getRecipes();
			for (Recipe r : recipes) {
				Object[] row = {
					r.getUser().getUserName(),
					r.getRecipeName(),
					r.getIngredients(),
					r.getInstructions()
				};
				tableModel.addRow(row);
			}
		});

		// Отобразить окно
		reportDialog.setVisible(true);
	}

	private void generateFavoriteReport() {
	    JPanel inputPanel = new JPanel(new BorderLayout());
	    JTextField userField = new JTextField();
	    inputPanel.add(new JLabel("Введите логин или email пользователя:"), BorderLayout.NORTH);
	    inputPanel.add(userField, BorderLayout.CENTER);

	    int result = JOptionPane.showConfirmDialog(getPanel(), inputPanel,
	            "Формирование отчёта по избранному", JOptionPane.OK_CANCEL_OPTION);

	    if (result == JOptionPane.OK_OPTION) {
	        String input = userField.getText().trim();
	        if (input.isEmpty()) {
	            JOptionPane.showMessageDialog(getPanel(), "Введите логин или email пользователя", "Ошибка", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        try {
	            List<FavoriteRecipeReportDTO> report = favoriteService.getFavoriteReportByUser(input);
	            if (report.isEmpty()) {
	                JOptionPane.showMessageDialog(getPanel(), "Нет избранных рецептов у пользователя " + input);
	                return;
	            }

	            Object[][] data = report.stream()
	                .map(dto -> new Object[]{
	                    dto.getUserName(),
	                    dto.getRecipeName(),
	                    dto.getIngredients(),
	                    dto.getInstructions()
	                })
	                .toArray(Object[][]::new);

	            String[] columns = {"Пользователь", "Название рецепта", "Ингредиенты", "Инструкции"};

	            JTable table = new JTable(data, columns);
	            table.setAutoCreateRowSorter(true);

	            JScrollPane scrollPane = new JScrollPane(table);
	            scrollPane.setPreferredSize(new Dimension(800, 400));

	            JOptionPane.showMessageDialog(getPanel(), scrollPane, "Отчёт по избранному", JOptionPane.INFORMATION_MESSAGE);
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(getPanel(), "Ошибка при формировании отчёта: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
	            e.printStackTrace();
	        }
	    }
	}

}
