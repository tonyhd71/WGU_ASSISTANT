package android.bignerdranch.wgu_assistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

public class AssessmentListFragment extends Fragment {
    private RecyclerView mAssessmentRecyclerView;
    private AssessmentAdapter mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    private class AssessmentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mAssessTypeTextView;
        private TextView mDueDateTextView;
        private TextView mGoalDateTextView;
        private Assessment mAssessment;
        public void bind(Assessment assessment) {
            mAssessment = assessment;
            mTitleTextView.setText("Assessment Title: " + mAssessment.getTitle());
            mAssessTypeTextView.setText("Assessment Type: " + mAssessment.getAssessType());
            mDueDateTextView.setText("Assessment Due Date: " + mAssessment.getDueDate().toString());
            mGoalDateTextView.setText("Assessment Goal Date: " + mAssessment.getGoalDate().toString());

        }
        public AssessmentHolder (LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_assessment, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.assessment_title);
            mAssessTypeTextView = (TextView) itemView.findViewById(R.id.assessmentType);
            mDueDateTextView = (TextView) itemView.findViewById(R.id.assessmentDueDate);
            mGoalDateTextView = (TextView) itemView.findViewById(R.id.assessmentGoalDate);
        }
        @Override
        public void onClick(View v) {
            Intent intent = AssessmentActivity.newIntent(getActivity(), mAssessment.getID());
            startActivity(intent);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assessment_list, container, false);
        mAssessmentRecyclerView = (RecyclerView) view.findViewById(R.id.assessment_recycler_view);
        mAssessmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_assessment_list, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.new_assessment:
                Assessment assessment = new Assessment();
                AssessmentLab.get(getActivity()).addAssessment(assessment);
                Intent intent = AssessmentActivity.newIntent(getActivity(), assessment.getID());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void updateUI() {
        AssessmentLab assessmentLab = AssessmentLab.get(getActivity());
        List<Assessment> assessments = assessmentLab.getAssessments();
        if (mAdapter == null) {
            mAdapter = new AssessmentAdapter(assessments);
            mAssessmentRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setAssessments(assessments);
            mAdapter.notifyDataSetChanged();
        }
    }
    private class AssessmentAdapter extends RecyclerView.Adapter<AssessmentHolder> {
        private List<Assessment> mAssessments;
        public AssessmentAdapter(List<Assessment> assessments) {
            mAssessments = assessments;
        }

        @Override
        public AssessmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new AssessmentHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(AssessmentHolder assessmentHolder, int position) {
            Assessment assessment = mAssessments.get(position);
            assessmentHolder.bind(assessment);
        }

        @Override
        public int getItemCount() {
            return mAssessments.size();
        }
        public void setAssessments(List<Assessment> assessments) {
            mAssessments = assessments;
        }
    }
}
