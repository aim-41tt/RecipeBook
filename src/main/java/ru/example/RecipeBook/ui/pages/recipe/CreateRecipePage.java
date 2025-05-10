package ru.example.RecipeBook.ui.pages.recipe;

import dev.aim_41tt.sling.core.Page;
import ru.example.RecipeBook.Config.ClientSettings;
import ru.example.RecipeBook.Config.SpringContext;
import ru.example.RecipeBook.models.Category;
import ru.example.RecipeBook.models.Recipe;
import ru.example.RecipeBook.models.User;
import ru.example.RecipeBook.services.AuthService;
import ru.example.RecipeBook.services.CategoryService;
import ru.example.RecipeBook.services.RecipeService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class CreateRecipePage extends Page {

    private final RecipeService recipeService = SpringContext.getBean(RecipeService.class);
    private final CategoryService categoryService = SpringContext.getBean(CategoryService.class);
    private final AuthService userService = SpringContext.getBean(AuthService.class);
   
    private final Color accentColor = Color.decode("#C6AA67");
    
    private JTextField nameField;
    private JTextArea ingredientsArea;
    private JTextArea instructionsArea;
    private JComboBox<Category> categoryBox;
    private byte[] imageData = null;

    @Override
    public void onCreate() {
        setTitle(" - Создание рецепта");
        getPanel().setLayout(new BoxLayout(getPanel(), BoxLayout.Y_AXIS));
        getPanel().setBackground(ClientSettings.getSettings().getThemeColor());
        getPanel().setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Создание рецепта");
        titleLabel.setFont(new Font(ClientSettings.getSettings().getFont(), Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(accentColor);
        getPanel().add(titleLabel);
        getPanel().add(Box.createRigidArea(new Dimension(0, 15)));

        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        nameField.setBorder(BorderFactory.createTitledBorder("Название"));
        nameField.setBackground(accentColor);
        getPanel().add(nameField);

        ingredientsArea = new JTextArea(5, 30);
        ingredientsArea.setLineWrap(true);
        ingredientsArea.setBorder(BorderFactory.createTitledBorder("Ингредиенты"));
        ingredientsArea.setBackground(accentColor);
        getPanel().add(new JScrollPane(ingredientsArea));

        instructionsArea = new JTextArea(5, 30);
        instructionsArea.setLineWrap(true);
        instructionsArea.setBorder(BorderFactory.createTitledBorder("Инструкция"));
        instructionsArea.setBackground(accentColor);
        getPanel().add(new JScrollPane(instructionsArea));

        categoryBox = new JComboBox<>();
        List<Category> categories = categoryService.getAll();
        for (Category c : categories) {
            categoryBox.addItem(c);
        }
        categoryBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        categoryBox.setBackground(accentColor);
        getPanel().add(Box.createRigidArea(new Dimension(0, 10)));
        getPanel().add(categoryBox);

        JButton uploadImageBtn = new JButton("Загрузить изображение");
        uploadImageBtn.setBackground(accentColor);
        uploadImageBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadImageBtn.addActionListener(this::uploadImage);
        getPanel().add(Box.createRigidArea(new Dimension(0, 10)));
        getPanel().add(uploadImageBtn);

        
        
        JPanel panelButton = new JPanel();
        panelButton.setBackground(ClientSettings.getSettings().getThemeColor());
        
        JButton saveBtn = new JButton("Сохранить");
        saveBtn.setBackground(accentColor);
        saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveBtn.addActionListener(this::saveRecipe);
        
        JButton exitBtn = new JButton("Отмена");
        exitBtn.setBackground(Color.RED);
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.addActionListener(this::exit);
        
        exitBtn.setPreferredSize(new Dimension(160, 40));
        saveBtn.setPreferredSize(new Dimension(160, 40));
        
        
        getPanel().add(Box.createRigidArea(new Dimension(0, 10)));
        
        panelButton.add(exitBtn);
        panelButton.add(saveBtn);
        getPanel().add(panelButton);
    }
    
    private void exit(ActionEvent e) {
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

    private void saveRecipe(ActionEvent e) {
        String name = nameField.getText().trim();
        String ingredients = ingredientsArea.getText().trim();
        String instructions = instructionsArea.getText().trim();
        Category category = (Category) categoryBox.getSelectedItem();
        User currentUser = userService.getUserAuth();

        if (name.isEmpty() || ingredients.isEmpty() || instructions.isEmpty() || category == null) {
            JOptionPane.showMessageDialog(getPanel(), "Все поля обязательны для заполнения!");
            return;
        }

        Recipe recipe = new Recipe();
        recipe.setRecipeName(name);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);
        recipe.setCategory(category);
        recipe.setUser(currentUser);
        recipe.setImage(imageData);

        recipeService.create(recipe);
        JOptionPane.showMessageDialog(getPanel(), "Рецепт успешно сохранён!");
        navigateTo(ReceptsPage.class);
    }
}
