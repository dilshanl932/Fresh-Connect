package com.s23010691.freshconnect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class AddListingActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 1001;
    private EditText productTitleEditText;
    private EditText productPriceEditText;
    private EditText productDescriptionEditText;
    private EditText productQuantityEditText;
    private LinearLayout addLocationLayout;
    private Button addItemButton;
    private Button addImagesButton;
    private RecyclerView imagesRecyclerView;
    private List<Uri> imageUris = new ArrayList<>();
    private ImagesAdapter imagesAdapter;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);

        productTitleEditText = findViewById(R.id.product_title);
        productPriceEditText = findViewById(R.id.product_price);
        productDescriptionEditText = findViewById(R.id.product_description);
        productQuantityEditText = findViewById(R.id.product_quantity);
        addLocationLayout = findViewById(R.id.add_location_layout);
        addItemButton = findViewById(R.id.btn_add_item);
        addImagesButton = findViewById(R.id.btn_add_images);
        imagesRecyclerView = findViewById(R.id.images_recycler_view);

        imagesAdapter = new ImagesAdapter(imageUris);
        imagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imagesRecyclerView.setAdapter(imagesAdapter);

        imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    if (result.getData().getClipData() != null) {
                        int count = result.getData().getClipData().getItemCount();
                        for (int i = 0; i < count; i++) {
                            Uri imageUri = result.getData().getClipData().getItemAt(i).getUri();
                            imageUris.add(imageUri);
                        }
                    } else if (result.getData().getData() != null) {
                        Uri imageUri = result.getData().getData();
                        imageUris.add(imageUri);
                    }
                    imagesAdapter.notifyDataSetChanged();
                }
            }
        );

        addImagesButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            imagePickerLauncher.launch(intent);
        });

        addLocationLayout.setOnClickListener(v -> {
            // TODO: Implement location picker
            Toast.makeText(this, "Location picker not implemented", Toast.LENGTH_SHORT).show();
        });

        addItemButton.setOnClickListener(v -> {
            String title = productTitleEditText.getText().toString().trim();
            String price = productPriceEditText.getText().toString().trim();
            String description = productDescriptionEditText.getText().toString().trim();
            String quantity = productQuantityEditText.getText().toString().trim();

            if (title.isEmpty() || price.isEmpty() || description.isEmpty() || quantity.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            // TODO: Implement post logic
            Toast.makeText(this, "Listing posted (stub)", Toast.LENGTH_SHORT).show();
        });
    }

    // Adapter for displaying images
    private static class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {
        private final List<Uri> imageUris;
        ImagesAdapter(List<Uri> imageUris) { this.imageUris = imageUris; }
        @Override
        public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView imageView = new ImageView(parent.getContext());
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(120, 120);
            params.setMargins(8, 8, 8, 8);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return new ImageViewHolder(imageView);
        }
        @Override
        public void onBindViewHolder(ImageViewHolder holder, int position) {
            holder.imageView.setImageURI(imageUris.get(position));
        }
        @Override
        public int getItemCount() { return imageUris.size(); }
        static class ImageViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            ImageViewHolder(ImageView itemView) {
                super(itemView);
                imageView = itemView;
            }
        }
    }
} 