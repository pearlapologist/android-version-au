package com.example.projectwnavigation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class TestActivity extends AppCompatActivity {
    Button test_json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        test_json = findViewById(R.id.test_json);



        test_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent json = new Intent(TestActivity.this, TestJsonActivity.class);
                startActivity(json);
            }
        });

    }
    /*
      private void showDialogDelete(final int id) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(TestImageList.this);

        dialogDelete.setTitle("Warning!");
        dialogDelete.setMessage("Are you sure you want to delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    provider.deleteData(id);
                    Toast.makeText(getApplicationContext(), "Delete successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                updateList();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }
    /*
     */
}
