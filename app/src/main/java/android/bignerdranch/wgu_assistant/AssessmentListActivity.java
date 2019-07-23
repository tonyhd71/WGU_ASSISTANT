package android.bignerdranch.wgu_assistant;

import android.support.v4.app.Fragment;

public class AssessmentListActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new AssessmentListFragment();
    }
}
