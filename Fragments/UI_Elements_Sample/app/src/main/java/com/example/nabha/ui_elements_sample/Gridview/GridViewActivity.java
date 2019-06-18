package com.example.nabha.ui_elements_sample.Gridview;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.nabha.ui_elements_sample.Gridview.DialogActivity;
import com.example.nabha.ui_elements_sample.R;

import java.util.ArrayList;

public class GridViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        GridView myGrid = (GridView) findViewById(R.id.gridView);
        myGrid.setAdapter(new GridViewAdapter(this));
        myGrid.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent dialogIntent = new Intent(this, DialogActivity.class);
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        Food temp = (Food) viewHolder.foodItemImage.getTag();
        dialogIntent.putExtra("foodItemImage",temp.imageId);
        dialogIntent.putExtra("dishName", temp.dishName);
        startActivity(dialogIntent);
    }

    class Food {
        int imageId;
        String dishName;

        Food(int id, String name) {
            this.imageId = id;
            this.dishName = name;
        }
    }

    class ViewHolder {
        ImageView foodItemImage;
        ViewHolder(View view) {
            foodItemImage = (ImageView) view.findViewById(R.id.foodItemImageView);
        }
    }


    class GridViewAdapter extends BaseAdapter {

        ArrayList<Food> list;
        Context context;
        GridViewAdapter(Context context) {
            this.context = context;
            list = new ArrayList<Food>();
            Resources res = context.getResources();
            String[] tempDishNames = res.getStringArray(R.array.food_items);
            int[] foodImages = {R.drawable.barfi, R.drawable.crispy_prawns,
                    R.drawable.dahipuri, R.drawable.jamun, R.drawable.lava_cake,
                    R.drawable.paneer, R.drawable.panipuri, R.drawable.rasmalai,
                    R.drawable.samosa, R.drawable.seekh_kebab};
            for(int i=0; i<10; i++) {
                Food tempFood = new Food(foodImages[i], tempDishNames[i]);
                list.add(tempFood);
            }

        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder viewHolder = null;
            if (row == null) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = layoutInflater.inflate(R.layout.single_item, parent, false);
                viewHolder = new ViewHolder(row);
                row.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) row.getTag();
            }
            Food foodItem = list.get(position);
            viewHolder.foodItemImage.setImageResource(foodItem.imageId);
            viewHolder.foodItemImage.setTag(foodItem);
            return row;
        }
    }
}
