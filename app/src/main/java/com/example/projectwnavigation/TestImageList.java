package com.example.projectwnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import models.MyDataProvider;
import models.TestEntity;

public class TestImageList extends AppCompatActivity {
    GridView gridView;
    MyDataProvider provider;
    ArrayList<TestEntity> list;
    Test_image_list_adapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image_list);


        gridView = findViewById(R.id.test_image_list_gridView);
        list = new ArrayList<>();
        adapter = new Test_image_list_adapter(this, R.layout.activity_test_image_items, list);
        gridView.setAdapter(adapter);
        provider = new MyDataProvider(this);

        updateList();

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(TestImageList.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                           /* Cursor c = provider.getData("SELECT _id FROM test_image");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }*/
                            ArrayList<Integer> arrID = provider.getIdArrayFromTestImage();
                            showDialogUpdate(TestImageList.this, arrID.get(position));

                        } else {
                            // delete
                           /* Cursor c = provider.getData("SELECT _id FROM test_image");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }*/
                            ArrayList<Integer> arrID = provider.getIdArrayFromTestImage();
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });

    }


    ImageView imageView;

    private void showDialogUpdate(Activity activity, final int position) {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.activity_test_image_dialog);
        dialog.setTitle("Update");

        imageView = dialog.findViewById(R.id.test_image_dialog_image);
        final EditText edtName = dialog.findViewById(R.id.test_image_dialog_etText);
        Button btnUpdate = dialog.findViewById(R.id.test_image_dialog_btn);

        ActivityCompat.requestPermissions(TestImageList.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                888 );
        dialog.show();

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    provider.updateData(
                            edtName.getText().toString().trim(),
                            provider.imageViewToByte(imageView),
                            position);
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update successfully!", Toast.LENGTH_SHORT).show();
                } catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                    Toast.makeText(getApplicationContext(), "update error", Toast.LENGTH_SHORT).show();
                }
                updateList();
            }
        });
    }

    private void showDialogDelete(final int idFood) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(TestImageList.this);

        dialogDelete.setTitle("Warning!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    provider.deleteData(idFood);
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


    private void updateList() {
        list.clear();
      list = provider.getAllDataFromTestImage();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 888) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 888 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}

//<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
//
