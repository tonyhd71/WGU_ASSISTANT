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

public class CourseListFragment extends Fragment {
    private RecyclerView mCourseRecyclerView;
    private CourseAdapter mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    private class  CourseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mCourseTitleTextView;
        private TextView mCourseStartDateTextView;
        private TextView mCourseEndDateTextView;
        private TextView mCourseStatusTextView;
        private TextView mCourseNoteTextView;
        private TextView mCourseMentorNameTextView;
        private TextView mCourseMentorPhoneTextView;
        private TextView mCourseMentorEmailTextView;

        private Course mCourse;

        public void bind(Course course) {
            mCourse = course;
            mCourseTitleTextView.setText("Course Title: " + mCourse.getTitle());
            mCourseStartDateTextView.setText("Course Start Date: " + mCourse.getStartDate().toString());
            mCourseEndDateTextView.setText("Course End Date: " + mCourse.getEndDate().toString());
            /*mCourseStartDateTextView.setText("Course Start Date: " + mCourse.getChosenStartDate());
            mCourseEndDateTextView.setText("Course End Date: " + mCourse.getChosenEndDate());*/
            mCourseStatusTextView.setText("Course Status: " + mCourse.getCourseStatus());
            mCourseNoteTextView.setText("Course Note: " + mCourse.getCourseNote());
            mCourseMentorNameTextView.setText("Mentor Name: " + mCourse.getCourseMentorName());
            mCourseMentorPhoneTextView.setText("Mentor Phone: " + mCourse.getCourseMentorPhone());
            mCourseMentorEmailTextView.setText("Mentor E-mail: " + mCourse.getCourseMentorEmail());
        }

        public CourseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_course, parent, false));
            itemView.setOnClickListener(this);
            mCourseTitleTextView = (TextView) itemView.findViewById(R.id.course_title);
            mCourseStartDateTextView = (TextView) itemView.findViewById(R.id.course_start_date);
            mCourseEndDateTextView = (TextView) itemView.findViewById(R.id.course_end_date);
            mCourseStatusTextView = (TextView) itemView.findViewById(R.id.course_status);
            mCourseNoteTextView = (TextView) itemView.findViewById(R.id.course_note);
            mCourseMentorNameTextView = (TextView) itemView.findViewById(R.id.mentor_name);
            mCourseMentorPhoneTextView = (TextView) itemView.findViewById(R.id.mentor_phone);
            mCourseMentorEmailTextView = (TextView) itemView.findViewById(R.id.mentor_email);

            /*mTitleTextView = (TextView) itemView.findViewById(R.id.term_title);
            mStartDateTextView = (TextView) itemView.findViewById(R.id.termStartDate);
            mEndDateTextView = (TextView) itemView.findViewById(R.id.termEndDate);*/
        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(getActivity(), mTerm.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(getActivity(), TermActivity.class);
            Intent intent = CourseActivity.newIntent(getActivity(), mCourse.getID());
            startActivity(intent);
            }
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_course_list, container, false);
            mCourseRecyclerView = (RecyclerView) view.findViewById(R.id.course_recycler_view);
            mCourseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
            inflater.inflate(R.menu.fragment_course_list, menu);
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch(item.getItemId()){
                case R.id.new_course:
                    Course course = new Course();
                    CourseLab.get(getActivity()).addCourse(course);
                    Intent intent = CourseActivity.newIntent(getActivity(), course.getID());
                    startActivity(intent);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
        private void updateUI() {
        /*TermLab termLab = TermLab.get(getActivity());
        List<Term> terms = termLab.getTerms();
        if (mAdapter == null) {
            mAdapter = new TermAdapter(terms);
            mTermRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setTerms(terms);
            mAdapter.notifyDataSetChanged();
        }*/
            CourseLab courseLab = CourseLab.get(getActivity());
            List<Course> courses = courseLab.getCourses();
            if (mAdapter == null) {
                mAdapter = new CourseAdapter(courses);
                mCourseRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.setCourses(courses);
                mAdapter.notifyDataSetChanged();
            }
        }
        private class CourseAdapter extends RecyclerView.Adapter<CourseHolder> {
            private List<Course> mCourses;
            public CourseAdapter(List<Course> courses) {
                mCourses = courses;
            }

            @Override
            public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                return new CourseHolder(layoutInflater, parent);
            }

            @Override
            public void onBindViewHolder(CourseHolder courseHolder, int position) {
                Course course = mCourses.get(position);
                courseHolder.bind(course);
            }

            @Override
            public int getItemCount() {
                return mCourses.size();
            }
            public void setCourses(List<Course> courses) {
                mCourses = courses;
            }
        }


}
