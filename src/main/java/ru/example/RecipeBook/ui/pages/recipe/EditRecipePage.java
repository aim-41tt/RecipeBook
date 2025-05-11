package ru.example.RecipeBook.ui.pages.recipe;

import dev.aim_41tt.sling.core.Page;
import ru.example.RecipeBook.Config.ClientSettings;
import ru.example.RecipeBook.Config.SpringContext;
import ru.example.RecipeBook.models.Category;
import ru.example.RecipeBook.models.Recipe;
import ru.example.RecipeBook.services.CategoryService;
import ru.example.RecipeBook.services.RecipeService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class EditRecipePage extends Page {

	private final RecipeService recipeService = SpringContext.getBean(RecipeService.class);
	private final CategoryService categoryService = SpringContext.getBean(CategoryService.class);
	private final Color accentColor = Color.decode("#C6AA67");

	private JTextField nameField;
	private JTextArea ingredientsArea;
	private JTextArea instructionsArea;
	private JComboBox<Category> categoryBox;
	private byte[] imageData = null;

	private static Long recipeId = 1L;
	private Recipe loadedRecipe = new Recipe();

	public static void setRecipeId(Long recipeId) {
		EditRecipePage.recipeId = recipeId;
	}

	@Override
	public void onCreate() {
		setTitle(" - Редактирование рецепта");
		getPanel().setLayout(new BoxLayout(getPanel(), BoxLayout.Y_AXIS));
		getPanel().setBackground(ClientSettings.getSettings().getThemeColor());
		getPanel().setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		loadedRecipe = recipeService.getById(recipeId).orElse(null);
		if (loadedRecipe == null) {
//			JOptionPane.showMessageDialog(getPanel(), "Рецепт не найден");
			navigateTo(ReceptsPage.class);
			return;
		}

		JLabel titleLabel = new JLabel("Редактирование рецепта");
		titleLabel.setFont(new Font(ClientSettings.getSettings().getFont(), Font.BOLD, 20));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setForeground(accentColor);
		getPanel().add(titleLabel);
		getPanel().add(Box.createRigidArea(new Dimension(0, 15)));

		nameField = new JTextField(loadedRecipe.getRecipeName());
		nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		nameField.setBorder(BorderFactory.createTitledBorder("Название"));
		nameField.setBackground(accentColor);
		getPanel().add(nameField);

		ingredientsArea = new JTextArea(loadedRecipe.getIngredients(), 5, 30);
		ingredientsArea.setLineWrap(true);
		ingredientsArea.setBorder(BorderFactory.createTitledBorder("Ингредиенты"));
		ingredientsArea.setBackground(accentColor);
		getPanel().add(new JScrollPane(ingredientsArea));

		instructionsArea = new JTextArea(loadedRecipe.getInstructions(), 5, 30);
		instructionsArea.setLineWrap(true);
		instructionsArea.setBorder(BorderFactory.createTitledBorder("Инструкция"));
		instructionsArea.setBackground(accentColor);
		getPanel().add(new JScrollPane(instructionsArea));

		categoryBox = new JComboBox<>();
		List<Category> categories = categoryService.getAll();
		for (Category c : categories) {
			if (!loadedRecipe.getCategory().equals(c)) {
				categoryBox.addItem(c);
			}
		}
		categoryBox.setSelectedItem(loadedRecipe.getCategory());
		categoryBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		categoryBox.setBackground(accentColor);
		getPanel().add(Box.createRigidArea(new Dimension(0, 10)));
		getPanel().add(categoryBox);

		JButton uploadImageBtn = new JButton("Заменить изображение");
		uploadImageBtn.setBackground(accentColor);
		uploadImageBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		uploadImageBtn.addActionListener(this::uploadImage);
		getPanel().add(Box.createRigidArea(new Dimension(0, 10)));
		getPanel().add(uploadImageBtn);

		JPanel panelButton = new JPanel();
		panelButton.setBackground(ClientSettings.getSettings().getThemeColor());

		JButton saveBtn = new JButton("Сохранить изменения");
		saveBtn.setBackground(accentColor);
		saveBtn.setPreferredSize(new Dimension(180, 40));
		saveBtn.addActionListener(this::updateRecipe);

		JButton backBtn = new JButton("Назад");
		backBtn.setBackground(Color.GREEN);
		backBtn.setPreferredSize(new Dimension(120, 40));
		backBtn.addActionListener((e) -> navigateTo(ReceptsPage.class));

		JButton delBtn = new JButton("Удалить");
		delBtn.setBackground(Color.RED);
		delBtn.setPreferredSize(new Dimension(95, 40));
		delBtn.addActionListener((e) -> {
			delItem(recipeId);
			
		});

		panelButton.add(delBtn);
		panelButton.add(backBtn);
		panelButton.add(saveBtn);
		getPanel().add(Box.createRigidArea(new Dimension(0, 10)));
		getPanel().add(panelButton);
	}

	private void delItem(Long id) {
		recipeService.delId(id);
		JOptionPane.showMessageDialog(getPanel(), "Рецепт удалён!");
		navigateTo(ReceptsPage.class);
	}

	private void uploadImage(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(getPanel());
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try {
				imageData = Files.readAllBytes(selectedFile.toPath());
				JOptionPane.showMessageDialog(getPanel(), "Изображение загружено успешно!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(getPanel(), "Ошибка при загрузке изображения: " + ex.getMessage());
			}
		}
	}

	private void updateRecipe(ActionEvent e) {
		loadedRecipe.setRecipeName(nameField.getText().trim());
		loadedRecipe.setIngredients(ingredientsArea.getText().trim());
		loadedRecipe.setInstructions(instructionsArea.getText().trim());
		loadedRecipe.setCategory((Category) categoryBox.getSelectedItem());

		if (imageData != null) {
			loadedRecipe.setImage(imageData);
		}

		recipeService.create(loadedRecipe); // можно заменить на update при наличии метода
		JOptionPane.showMessageDialog(getPanel(), "Рецепт обновлён!");
		navigateTo(ReceptsPage.class);
	}
}
