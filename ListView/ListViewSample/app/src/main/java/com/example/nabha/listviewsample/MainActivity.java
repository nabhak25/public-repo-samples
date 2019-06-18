package com.example.nabha.listviewsample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static android.support.v7.appcompat.R.styleable.View;
import static com.example.nabha.listviewsample.R.id.parent;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.recipe_list_view);
        final ArrayList<Recipe> recipeArrayList = Recipe.getRecipesFromFile("recipes.json", this);
        RecipeAdapter adapter = new RecipeAdapter(getApplicationContext(), recipeArrayList);
        mListView.setAdapter(adapter);

        final Context context = this;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 1
                Recipe selectedRecipe = recipeArrayList.get(position);

                // 2
                Intent detailIntent = new Intent(context, RecipeDetailActivity.class);

                // 3
                detailIntent.putExtra("title", selectedRecipe.title);
                detailIntent.putExtra("url", selectedRecipe.instructionUrl);

                // 4
                startActivity(detailIntent);
            }

        });
    }
}
