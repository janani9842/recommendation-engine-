package com.codtech;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RecommenderEngine {

    private DataModel model;
    private UserBasedRecommender recommender;

    public RecommenderEngine() {
        try {
            // Load data model from CSV file
            File dataFile = new File("src/main/resources/user_preferences.csv");
            model = new FileDataModel(dataFile);

            // Create similarity metric
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Create neighborhood
            UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);

            // Create recommender
            recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

        } catch (IOException | TasteException e) {
            System.err.println("Error initializing recommender: " + e.getMessage());
        }
    }

    public void recommendForUser(long userId) {
        try {
            System.out.println("\nTop Recommendations for User " + userId + ":");

            // Get 3 recommendations
            List<RecommendedItem> recommendations = recommender.recommend(userId, 3);

            if (recommendations.isEmpty()) {
                System.out.println("No recommendations available. Try rating more items!");
                handleColdStart(userId);
            } else {
                for (RecommendedItem recommendation : recommendations) {
                    System.out.printf("Item %d (Score: %.2f)\n",
                            recommendation.getItemID(),
                            recommendation.getValue());
                }
            }
        } catch (TasteException e) {
            System.err.println("Error generating recommendations: " + e.getMessage());
        }
    }

    private void handleColdStart(long userId) {
        // Basic content-based fallback for new users
        System.out.println("\nPopular items you might like:");
        System.out.println("1. Item 101 (Popular Choice)");
        System.out.println("2. Item 104 (Trending Now)");
        System.out.println("3. Item 106 (New Arrival)");
    }

    public void addPreference(long userId, long itemId, float preference) {
        // In a real system, this would update the data model
        System.out.printf("Simulated: Added preference - User: %d, Item: %d, Rating: %.1f\n",
                userId, itemId, preference);
        // Note: For actual implementation, you'd need to update the CSV file
        // or connect to a database
    }
}
