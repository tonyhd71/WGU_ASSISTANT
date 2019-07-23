package android.bignerdranch.wgu_assistant;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class CourseActivity extends SingleFragmentActivity{
    public static final String EXTRA_Course_ID = "com.bignerdranch.android.wgu_assistant.course_id";
    public static Intent newIntent(Context packageContext, UUID courseId) {
        Intent intent = new Intent(packageContext, CourseActivity.class);
        intent.putExtra(EXTRA_Course_ID, courseId);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        UUID termId = (UUID) getIntent().getSerializableExtra(EXTRA_Course_ID);
        return CourseFragment.newInstance(termId);
        //return new TermFragment();
    }
}
