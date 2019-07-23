package android.bignerdranch.wgu_assistant;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class AssessmentActivity extends SingleFragmentActivity {
    public static final String EXTRA_ASSESSMENT_ID = "com.bignerdranch.android.wgu_assistant.assessment_id";
    public static Intent newIntent(Context packageContext, UUID assessmentId) {
        Intent intent = new Intent(packageContext, AssessmentActivity.class);
        intent.putExtra(EXTRA_ASSESSMENT_ID, assessmentId);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        UUID assessmentId = (UUID) getIntent().getSerializableExtra(EXTRA_ASSESSMENT_ID);
        return AssessmentFragment.newInstance(assessmentId);
        //return new TermFragment();
    }
}
