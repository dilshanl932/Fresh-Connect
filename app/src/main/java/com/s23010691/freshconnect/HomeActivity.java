package com.s23010691.freshconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        ProductAdapter adapter = new ProductAdapter(getProducts());
        recyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setItemIconTintList(getResources().getColorStateList(R.color.bottom_nav_icon_color, getTheme()));

        // Add FAB for posting new listing
        FloatingActionButton fab = findViewById(R.id.fab_add_listing);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddListingActivity.class);
            startActivity(intent);
        });
    }

    private List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) { // Add 10 dummy products
            products.add(new Product("Organic Banana 1kg", "Rs 200.00"));
        }
        return products;
    }

    // Product Model
    private static class Product {
        String name;
        String price;

        Product(String name, String price) {
            this.name = name;
            this.price = price;
        }
    }

    // RecyclerView Adapter
    private static class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
        private List<Product> productList;

        ProductAdapter(List<Product> productList) {
            this.productList = productList;
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            Product product = productList.get(position);
            holder.name.setText(product.name);
            holder.price.setText(product.price);
            // In a real app, you would load the image with Glide or Picasso
            // holder.image.setImageResource(R.drawable.banana);
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

        static class ProductViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView name;
            TextView price;

            ProductViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.product_image);
                name = itemView.findViewById(R.id.product_name);
                price = itemView.findViewById(R.id.product_price);
            }
        }
    }
} 