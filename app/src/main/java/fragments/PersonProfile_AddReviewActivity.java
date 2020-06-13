package fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.projectwnavigation.R;

import models.MyDataProvider;
import models.MyUtils;
import models.Notify;
import models.Review;

public class PersonProfile_AddReviewActivity extends AppCompatActivity {
Button cancel, save;
EditText assessment, text;
MyDataProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_profile_add_review);
        cancel = findViewById(R.id.person_profile_add_review_btnCancel);
        save = findViewById(R.id.person_profile_add_review_btnSave);
        assessment = findViewById(R.id.person_profile_add_review_etAssessment);
        text = findViewById(R.id.person_profile_add_review_etText);
        provider = new MyDataProvider(this);

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
                    provider.addReview(review);
                    provider.updatePersonRatingById(executorId);

                    //TODO: передавать айди юзера или спеца? :(
                    Notify notify = new Notify(provider.getPersonIdByExecutorId(executorId), "У вас новый отзыв", MyUtils.getCurentDateInLong(), 2, curPersonId);
                    provider.createNotify(notify);
                }catch(Exception e){
                    Log.e("add review", e.getMessage());
                }



          setResult(RESULT_OK);
          finish();
            }
        });

    }
}
