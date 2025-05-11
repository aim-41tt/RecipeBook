package ru.example.RecipeBook.ui.pages;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;

import dev.aim_41tt.sling.core.Page;
import ru.example.RecipeBook.Config.ClientSettings;
import ru.example.RecipeBook.Config.SpringContext;
import ru.example.RecipeBook.models.Recipe;
import ru.example.RecipeBook.models.User;
import ru.example.RecipeBook.services.AuthService;
import ru.example.RecipeBook.services.FavoriteService;
import ru.example.RecipeBook.services.RecipeService;
import ru.example.RecipeBook.ui.NavigationPanel;
import ru.example.RecipeBook.ui.pages.recipe.EditRecipePage;
//import ru.example.RecipeBook.ui.pages.details.RecipeDetailPage;

public class FavoritePage extends Page {

	private final RecipeService recipeService = SpringContext.getBean(RecipeService.class);
	private final FavoriteService favoriteService = SpringContext.getBean(FavoriteService.class);
	private final Color accentColor = Color.decode("#C6AA67");
	private static User user = AuthService.getUserAuthStat();

	@Override
	public void onCreate() {
		setTitle(" - Избранное");

		getPanel().setLayout(new BorderLayout());
		getPanel().setBackground(ClientSettings.getSettings().getThemeColor());

		// ===== Верхняя панель с заголовком и навигацией =====
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.setBackground(ClientSettings.getSettings().getThemeColor());

		Page navPanel = app.getPagePattern(NavigationPanel.class);
		navPanel.repaint();
		navPanel.onCreate();
		navPanel.getPanel().setMaximumSize(new Dimension(Integer.MAX_VALUE, 210));
		navPanel.getPanel().setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(accentColor);
		headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
		headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
		headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		topPanel.add(navPanel.getPanel());
		topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		topPanel.add(headerPanel);

		getPanel().add(topPanel, BorderLayout.NORTH);

		// ===== Сетка рецептов со скроллом =====
		JPanel recipeGrid = new JPanel(new GridLayout(0, 3, 15, 15));
		recipeGrid.setBackground(ClientSettings.getSettings().getThemeColor());
		recipeGrid.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		List<Recipe> recipes = recipeService.getLatestRecipes(100); // или сделать getAllRecipes()

		for (Recipe recipe : recipes) {
			switch ((user == null) ? 1 : 0) {
			case 1: {
				user = AuthService.getUserAuthStat();
				if (user == null) {
					return;
				}
			}
			case 0: {
				if (favoriteService.isFavorite(user.getUserId(), recipe.getRecipeId())) {
					JButton recipeBtn = createRecipeButton(recipe);
					recipeGrid.add(recipeBtn);
				}
			}
			}

		}

		JScrollPane scrollPane = new JScrollPane(recipeGrid);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setBorder(null);

		getPanel().add(scrollPane, BorderLayout.CENTER);
	}

	private JButton createRecipeButton(Recipe recipe) {
		JButton button = new JButton();
		button.setLayout(new BoxLayout(button, BoxLayout.Y_AXIS));
		button.setPreferredSize(new Dimension(200, 250));
		button.setBackground(accentColor);
		button.setOpaque(true);
		button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // рамка для всех кнопок
		button.setFocusPainted(false);
		button.setForeground(Color.WHITE);
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// 1. Картинка рецепта
		byte[] imageBytes = recipe.getImage();
		if (imageBytes != null && imageBytes.length > 0) {
			ImageIcon icon = new ImageIcon(imageBytes);
			Image scaled = icon.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
			JLabel imageLabel = new JLabel(new ImageIcon(scaled));
			imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			imageLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			button.add(imageLabel);
		}

		// 2. Название рецепта
		JLabel nameLabel = new JLabel(recipe.getRecipeName(), SwingConstants.CENTER);
		nameLabel.setFont(new Font(ClientSettings.getSettings().getFont(), Font.BOLD, 14));
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		button.add(nameLabel);

		// 3. Категория
		JLabel categoryLabel = new JLabel(recipe.getCategory().getCategoryName(), SwingConstants.CENTER);
		categoryLabel.setFont(new Font(ClientSettings.getSettings().getFont(), Font.PLAIN, 12));
		categoryLabel.setOpaque(true);
		categoryLabel.setBackground(new Color(100, 50, 150)); // можно кастомизировать по типу
		categoryLabel.setForeground(Color.WHITE);
		categoryLabel.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
		categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		categoryLabel.setMaximumSize(new Dimension(180, 20));
		categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		button.add(Box.createVerticalStrut(5));
		button.add(categoryLabel);

		button.addActionListener((ActionEvent e) -> openRecipeDetail(recipe.getRecipeId()));

		// ===== Кнопка "Избранное" =====
		JButton favButton = new JButton("★");
		favButton.setBounds(0, 0, 40, 40);
		favButton.setFont(new Font("SansSerif", Font.PLAIN, 25));
		favButton.setBorderPainted(false);
		favButton.setFocusPainted(false);
		favButton.setContentAreaFilled(false);
		favButton.setForeground(Color.BLACK);
		favButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		user = AuthService.getUserAuthStat();

		switch ((user == null) ? 1 : 0) {
		case 1: {
			user = AuthService.getUserAuthStat();
			if (user == null) {
				break;
			}
		}
		case 0: {
			boolean isFavorite = favoriteService.isFavorite(user.getUserId(), recipe.getRecipeId());
			favButton.setForeground(isFavorite ? Color.decode("#ffd700") : Color.BLACK);

			favButton.addActionListener(e -> {
				boolean isNowFavorite = favoriteService.toggleFavorite(user.getUserId(), recipe.getRecipeId());
				favButton.setForeground(isNowFavorite ? Color.decode("#ffd700") : Color.BLACK);
			});

			button.add(Box.createVerticalStrut(5));
			button.add(favButton);
		}

		}

		return button;
	}

	private void openRecipeDetail(Long recipeId) {
		EditRecipePage.setRecipeId(recipeId);
		Page page = app.getPage(EditRecipePage.class);
		page.repaint();
		page.onCreate();
		navigateTo(EditRecipePage.class);
	}

}
