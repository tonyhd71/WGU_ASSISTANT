package android.bignerdranch.wgu_assistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomeScreen extends AppCompatActivity {
    Button viewTermsButton;
    Button viewCoursesButton;
    private TermBaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        myDb = new TermBaseHelper(getApplicationContext());

    }
    public void goToViewTerms(View view) {
        Intent myIntent = new Intent(this, TermListActivity.class);
        startActivity(myIntent);
    }
    public void goToViewCourses(View view) {
       // Cursor res = myDb.getTerms();
       // if(res.getCount() == 0) {
         //   Toast.makeText(getApplicationContext(), "You must create a term before adding a Course", Toast.LENGTH_SHORT).show();
        //} else {
            Intent myIntent = new Intent(this, CourseListActivity.class);
            startActivity(myIntent);
        //}

    }
    public void goToViewAssessments(View view) {
        Intent myIntent = new Intent(this, AssessmentListActivity.class);
        startActivity(myIntent);
    }

    /*public void goToNotifications(View view) {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }*/

}
