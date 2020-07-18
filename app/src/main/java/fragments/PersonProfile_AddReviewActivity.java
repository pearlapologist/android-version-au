package fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.projectwnavigation.MyProfile_reviews_activity;
import com.example.projectwnavigation.R;

import models.ApiProvider;
import models.MyDataProvider;
import models.MyUtils;
import models.Notify;
import models.Persons;
import models.Review;

public class PersonProfile_AddReviewActivity extends AppCompatActivity {
Button cancel, save;
EditText assessment, text;
MyDataProvider provider;
ApiProvider apiProvider;

private NotificationManager nManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_profile_add_review);
        cancel = findViewById(R.id.person_profile_add_review_btnCancel);
        save = findViewById(R.id.person_profile_add_review_btnSave);
        assessment = findViewById(R.id.person_profile_add_review_etAssessment);
        text = findViewById(R.id.person_profile_add_review_etText);
        provider = new MyDataProvider(this);
        apiProvider = new ApiProvider();
        nManager =(NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              setResult(RESULT_CANCELED);
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                int executorId = intent.getIntExtra("addReview", -1);
                int curPersonId= provider.getLoggedInPerson().getId();
                Review review  = new Review(executorId, curPersonId , text.getText().toString().trim(), Integer.parseInt(assessment.getText().toString()));

                try {
                    AddREviewTask addREviewTask = new AddREviewTask();
                    addREviewTask.execute(review);
                  //provider.addReview(review);
                  //  apiProvider.updatePersonRatingById(executorId);
                   // provider.updatePersonRatingById(executorId);

                    Notify notify = new Notify(executorId, "У вас новый отзыв", MyUtils.getCurentDateInLong(), 2, curPersonId, 0);
                    CreateNotifyTask task = new CreateNotifyTask();
                    task.execute(notify);
                   // provider.createNotify(notify);
                }catch(Exception e){
                    Log.e("add review", e.getMessage());
                }


          setResult(RESULT_OK);
          finish();
            }
        });

    }

    private class CreateNotifyTask extends AsyncTask<Notify, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Notify... params) {
            try {
                apiProvider.createNotify(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
        }
    }

    private class AddREviewTask extends AsyncTask<Review, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Review... params) {
            try {
                apiProvider.addReview(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
        }
    }
}
