package android.bignerdranch.wgu_assistant;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class TermActivity extends SingleFragmentActivity {
    public static final String EXTRA_Term_ID = "com.bignerdranch.android.wgu_assistant.term_id";
    public static Intent newIntent(Context packageContext, UUID termId) {
        Intent intent = new Intent(packageContext, TermActivity.class);
        intent.putExtra(EXTRA_Term_ID, termId);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        UUID termId = (UUID) getIntent().getSerializableExtra(EXTRA_Term_ID);
        return TermFragment.newInstance(termId);
        //return new TermFragment();
    }
}
