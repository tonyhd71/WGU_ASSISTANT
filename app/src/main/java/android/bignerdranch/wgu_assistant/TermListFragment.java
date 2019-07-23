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

public class TermListFragment extends Fragment {
    private RecyclerView mTermRecyclerView;
    private TermAdapter mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    private class  TermHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mStartDateTextView;
        private TextView mEndDateTextView;
        private Term mTerm;
        public void bind(Term term) {
            mTerm = term;
            mTitleTextView.setText("Term Title: " + mTerm.getTitle());
            mStartDateTextView.setText("Term Start Date: " + mTerm.getStartDate().toString());
            mEndDateTextView.setText("Term End Date: " + mTerm.getEndDate().toString());


        }
        public TermHolder (LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_term, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.term_title);
            mStartDateTextView = (TextView) itemView.findViewById(R.id.term_start_date);
            mEndDateTextView = (TextView) itemView.findViewById(R.id.term_end_date);
        }
        @Override
        public void onClick(View v) {
            Intent intent = TermActivity.newIntent(getActivity(), mTerm.getID());
            startActivity(intent);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_term_list, container, false);
        mTermRecyclerView = (RecyclerView) view.findViewById(R.id.term_recycler_view);
        mTermRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        inflater.inflate(R.menu.fragment_term_list, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.new_term:
                Term term = new Term();
                TermLab.get(getActivity()).addTerm(term);
                Intent intent = TermActivity.newIntent(getActivity(), term.getID());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //

    //
    private void updateUI() {
        TermLab termLab = TermLab.get(getActivity());
        List<Term> terms = termLab.getTerms();
        if (mAdapter == null) {
            mAdapter = new TermAdapter(terms);
            mTermRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setTerms(terms);
            mAdapter.notifyDataSetChanged();
        }
    }
    private class TermAdapter extends RecyclerView.Adapter<TermHolder> {
        private List<Term> mTerms;
        public TermAdapter(List<Term> terms) {
            mTerms = terms;
        }

        @Override
        public TermHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TermHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TermHolder termHolder, int position) {
            Term term = mTerms.get(position);
            termHolder.bind(term);
        }

        @Override
        public int getItemCount() {
            return mTerms.size();
        }
        public void setTerms(List<Term> terms) {
            mTerms = terms;
        }
    }
 }
