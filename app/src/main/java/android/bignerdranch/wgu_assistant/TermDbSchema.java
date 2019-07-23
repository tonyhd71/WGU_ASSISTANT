package android.bignerdranch.wgu_assistant;

public class TermDbSchema {
    public static final class TermTable {
        public static final String NAME = "terms";
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String START_DATE = "startdate";
            public static final String END_DATE = "enddate";

        }
    }
    public static final class CourseTable {
        public static final String NAME = "courses";
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String START_DATE = "startdate";
            public static final String END_DATE = "enddate";
            public static final String CHOSEN_START_DATE = "chosenstartdate";
            public static final String CHOSEN_END_DATE = "chosenenddate";
            public static final String COURSE_STATUS = "coursestatus";
            public static final String OPTIONAL_NOTE = "optionalnote";
            public static final String MENTOR_NAME = "mentorname";
            public static final String MENTOR_PHONE = "mentorphone";
            public static final String MENTOR_EMAIL = "mentoremail";
            public static final String ALERT_SET = "alertset";
            public static final String COL_COURSE_TERM_REFERENCE = "term_reference";
        }
    }
    public static final class AssessmentTable {
        public static final String NAME = "assessments";
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String ASSESS_TYPE = "assesstype";

            public static final String DUE_DATE = "duedate";
            public static final String GOAL_DATE = "goaldate";
            //
            public static final String ALERT_SET = "alertset";

            //
           public static final String COL_ASSESS_COURSE_REFERENCE = "course_reference";
        }
    }
}
