package com.aakash.studentmanager;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "Student_Manager";
	public static final String COURSES_TABLE_NAME = "COURSES";
	public static final String COURSES_SEMESTER = "Semester";
	public static final String COURSES_CODE = "Course_Code";
	public static final String COURSES_NAME = "Course_Name";
	public static final String COURSES_DEPT = "Department";
	public static final String COURSES_INSTRUCTOR_NAME = "Instructor_Name";
	public static final String COURSES_INSTRUCTOR_EMAIL = "Instructor_Email";
	public static final String COURSES_TYPE = "Course_Type";
	public static final String COURSES_LECTURE_SLOT = "Lecture_Slot";
	public static final String COURSES_LECTURE_TIMINGS = "Lecture_Timings";
	public static final String COURSES_LECTURE_VENUE = "Lecture_Venue";
	public static final String COURSES_WEBPAGE = "Course_Webpage";

	public static final String LECTURES_TABLE_NAME = "LECTURES";
	public static final String LECTURES_NUMBER = "Lecture_Number";
	public static final String LECTURES_TITLE = "Lecture_Title";
	public static final String LECTURES_DESCRIPTION = "Lecture_Description";
	public static final String LECTURES_KEYWORDS = "Lecture_Keywords";

	public static final String ASSIGNMENTS_TABLE_NAME = "ASSIGNMENTS";
	public static final String ASSIGNMENTS_NUMBER = "Assignment_Number";
	public static final String ASSIGNMENTS_DESCRIPTION = "Assignment_Description";
	public static final String ASSIGNMENTS_DUE_DATE = "Assignment_Due_Date";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE " + COURSES_TABLE_NAME + "("
				+ COURSES_SEMESTER + " INTEGER, " + COURSES_CODE + " TEXT, "
				+ COURSES_NAME + " TEXT, " + COURSES_DEPT + " TEXT, "
				+ COURSES_INSTRUCTOR_NAME + " TEXT, "
				+ COURSES_INSTRUCTOR_EMAIL + " TEXT, " + COURSES_TYPE
				+ " TEXT, " + COURSES_LECTURE_SLOT + " INTEGER, "
				+ COURSES_LECTURE_TIMINGS + " TEXT, " + COURSES_LECTURE_VENUE
				+ " TEXT, " + COURSES_WEBPAGE + " TEXT" + ");");

		db.execSQL("CREATE TABLE " + LECTURES_TABLE_NAME + "("
				+ COURSES_SEMESTER + " INTEGER, " + COURSES_CODE + " TEXT, "
				+ LECTURES_NUMBER + " INTEGER, " + LECTURES_TITLE + " TEXT, "
				+ LECTURES_DESCRIPTION + " TEXT, " + LECTURES_KEYWORDS
				+ " TEXT" + ");");

		db.execSQL("CREATE TABLE " + ASSIGNMENTS_TABLE_NAME + "("
				+ COURSES_SEMESTER + " INTEGER, " + COURSES_CODE + " TEXT, "
				+ ASSIGNMENTS_NUMBER + " INTEGER, " + ASSIGNMENTS_DESCRIPTION
				+ " TEXT, " + ASSIGNMENTS_DUE_DATE + " TEXT" + ");");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + COURSES_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + LECTURES_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + ASSIGNMENTS_TABLE_NAME);
		onCreate(db);
	}

	// Courses

	public boolean insertCourse(int semester, String course_code,
			String course_name, String dept, String instructor_name,
			String instructor_email, String course_type, String lecture_slot,
			String lecture_timings, String lecture_venue, String course_webpage) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put(COURSES_SEMESTER, semester);
		contentValues.put(COURSES_CODE, course_code);
		contentValues.put(COURSES_NAME, course_name);
		contentValues.put(COURSES_DEPT, dept);
		contentValues.put(COURSES_INSTRUCTOR_NAME, instructor_name);
		contentValues.put(COURSES_INSTRUCTOR_EMAIL, instructor_email);
		contentValues.put(COURSES_TYPE, course_type);
		contentValues.put(COURSES_LECTURE_SLOT, lecture_slot);
		contentValues.put(COURSES_LECTURE_TIMINGS, lecture_timings);
		contentValues.put(COURSES_LECTURE_VENUE, lecture_venue);
		contentValues.put(COURSES_WEBPAGE, course_webpage);

		db.insert(COURSES_TABLE_NAME, null, contentValues);
		return true;
	}

	public Cursor getCourseData(int semester, String course_code) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("SELECT * FROM " + COURSES_TABLE_NAME
				+ " WHERE " + COURSES_SEMESTER + " = ? AND " + COURSES_CODE
				+ " = ?",
				new String[] { String.valueOf(semester), course_code });
		return res;
	}

	public int numberOfRowsInCourses() {
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db,
				COURSES_TABLE_NAME);
		return numRows;
	}

	public boolean updateCourse(int semester, String course_code,
			String course_name, String dept, String instructor_name,
			String instructor_email, String course_type, String lecture_slot,
			String lecture_timings, String lecture_venue, String course_webpage) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put(COURSES_CODE, course_code);
		contentValues.put(COURSES_NAME, course_name);
		contentValues.put(COURSES_DEPT, dept);
		contentValues.put(COURSES_INSTRUCTOR_NAME, instructor_name);
		contentValues.put(COURSES_INSTRUCTOR_EMAIL, instructor_email);
		contentValues.put(COURSES_TYPE, course_type);
		contentValues.put(COURSES_LECTURE_SLOT, lecture_slot);
		contentValues.put(COURSES_LECTURE_TIMINGS, lecture_timings);
		contentValues.put(COURSES_LECTURE_VENUE, lecture_venue);
		contentValues.put(COURSES_WEBPAGE, course_webpage);

		db.update(COURSES_TABLE_NAME, contentValues, COURSES_SEMESTER
				+ " = ? AND " + COURSES_CODE + " = ?",
				new String[] { Integer.toString(semester), course_code });
		return true;
	}

	public void deleteCourse(int semester, String course_code) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(COURSES_TABLE_NAME, COURSES_SEMESTER + " = ? AND "
				+ COURSES_CODE + " = ?",
				new String[] { Integer.toString(semester), course_code });
		db.delete(LECTURES_TABLE_NAME, COURSES_SEMESTER + " = ? AND "
				+ COURSES_CODE + " = ?",
				new String[] { Integer.toString(semester), course_code });
		db.delete(ASSIGNMENTS_TABLE_NAME, COURSES_SEMESTER + " = ? AND "
				+ COURSES_CODE + " = ?",
				new String[] { Integer.toString(semester), course_code });
	}

	public ArrayList<String> getAllCourses(int semester) {
		ArrayList<String> array_list = new ArrayList<String>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("SELECT * FROM " + COURSES_TABLE_NAME
				+ " WHERE " + COURSES_SEMESTER + " = ?",
				new String[] { Integer.toString(semester) });
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			array_list.add(res.getString(res.getColumnIndex(COURSES_CODE))
					+ ": " + res.getString(res.getColumnIndex(COURSES_NAME)));
			res.moveToNext();
		}
		return array_list;
	}

	// Lectures

	public boolean insertLecture(int semester, String course_code,
			int lecture_number, String lecture_title,
			String lecture_description, String lecture_keywords) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put(COURSES_SEMESTER, semester);
		contentValues.put(COURSES_CODE, course_code);
		contentValues.put(LECTURES_NUMBER, lecture_number);
		contentValues.put(LECTURES_TITLE, lecture_title);
		contentValues.put(LECTURES_DESCRIPTION, lecture_description);
		contentValues.put(LECTURES_KEYWORDS, lecture_keywords);

		db.insert(LECTURES_TABLE_NAME, null, contentValues);
		return true;
	}

	public Cursor getLectureData(int semester, String course_code, int lecture) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery(
				"SELECT * FROM " + LECTURES_TABLE_NAME + " WHERE "
						+ COURSES_SEMESTER + " = ? AND " + COURSES_CODE
						+ " = ? AND " + LECTURES_NUMBER + " = ?",
				new String[] { String.valueOf(semester), course_code,
						String.valueOf(lecture) });
		return res;
	}

	public int numberOfRowsInLectures() {
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db,
				LECTURES_TABLE_NAME);
		return numRows;
	}

	public boolean updateLecture(int semester, String course_code,
			int lecture_number, String lecture_title,
			String lecture_description, String lecture_keywords) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put(LECTURES_NUMBER, lecture_number);
		contentValues.put(LECTURES_TITLE, lecture_title);
		contentValues.put(LECTURES_DESCRIPTION, lecture_description);
		contentValues.put(LECTURES_KEYWORDS, lecture_keywords);

		db.update(LECTURES_TABLE_NAME, contentValues, COURSES_SEMESTER
				+ " = ? AND " + COURSES_CODE + " = ? AND " + LECTURES_NUMBER
				+ " = ?", new String[] { Integer.toString(semester),
				course_code, Integer.toString(lecture_number) });
		return true;
	}

	public void deleteLecture(int semester, String course_code,
			int lecture_number) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(
				LECTURES_TABLE_NAME,
				COURSES_SEMESTER + " = ? AND " + COURSES_CODE + " = ? AND "
						+ LECTURES_NUMBER + " = ?",
				new String[] { Integer.toString(semester), course_code,
						Integer.toString(lecture_number) });
	}

	public ArrayList<String> getAllLectures(int semester, String course_code) {
		ArrayList<String> array_list = new ArrayList<String>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("SELECT * FROM " + LECTURES_TABLE_NAME
				+ " WHERE " + COURSES_SEMESTER + " = ? AND " + COURSES_CODE
				+ " = ?", new String[] { Integer.toString(semester),
				course_code });
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			array_list.add("Lecture "
					+ res.getInt(res.getColumnIndex(LECTURES_NUMBER)) + ": "
					+ res.getString(res.getColumnIndex(LECTURES_TITLE)));
			res.moveToNext();
		}
		return array_list;
	}

	public int getLastAddedLecture(int semester, String course_code) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("SELECT * FROM " + LECTURES_TABLE_NAME
				+ " WHERE " + COURSES_SEMESTER + " = ? AND " + COURSES_CODE
				+ " = ?", new String[] { Integer.toString(semester),
				course_code });

		if (res.isAfterLast()) {
			return 0;
		}
		res.moveToLast();
		return res.getInt(res.getColumnIndex(LECTURES_NUMBER));
	}

	// Keywords

	public boolean updateKeywords(int semester, String course_code,
			int lecture_number, String lecture_keywords) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put(COURSES_SEMESTER, semester);
		contentValues.put(COURSES_CODE, course_code);
		contentValues.put(LECTURES_NUMBER, lecture_number);
		contentValues.put(LECTURES_KEYWORDS, lecture_keywords);

		/*
		 * db.update(LECTURES_TABLE_NAME, contentValues, COURSES_SEMESTER +
		 * " = ? AND " + COURSES_CODE + " = ? AND " + LECTURES_NUMBER + " = ?",
		 * new String[] { Integer.toString(semester), course_code,
		 * Integer.toString(lecture_number) });
		 */
		db.execSQL("UPDATE " + LECTURES_TABLE_NAME + " SET "
				+ LECTURES_KEYWORDS + " = (" + LECTURES_KEYWORDS + " || \"\n"
				+ lecture_keywords + "\") WHERE " + COURSES_SEMESTER + " = "
				+ Integer.toString(semester) + " AND " + COURSES_CODE + " = \""
				+ course_code + "\" AND " + LECTURES_NUMBER + " = "
				+ Integer.toString(lecture_number));

		return true;

	}

	public ArrayList<String> getAllKeywords(int semester, String course_code,
			int lecture_number) {
		// TODO Auto-generated method stub
		ArrayList<String> array_list = null;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery(
				"SELECT * FROM " + LECTURES_TABLE_NAME + " WHERE "
						+ COURSES_SEMESTER + " = ? AND " + COURSES_CODE
						+ " = ? AND " + LECTURES_NUMBER + " = ?",
				new String[] { Integer.toString(semester), course_code,
						Integer.toString(lecture_number) });
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			String keywords = res.getString(res
					.getColumnIndex(LECTURES_KEYWORDS));
			if (keywords != null)
				array_list = new ArrayList<String>(Arrays.asList(keywords
						.split("\n")));
			else
				array_list = new ArrayList<String>();
			res.moveToNext();
		}
		return array_list;

	}

	// Assignments

	public boolean insertAssignment(int semester, String course_code,
			int assignment_number, String assignment_description,
			String assignment_due_date) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put(COURSES_SEMESTER, semester);
		contentValues.put(COURSES_CODE, course_code);
		contentValues.put(ASSIGNMENTS_NUMBER, assignment_number);
		contentValues.put(ASSIGNMENTS_DESCRIPTION, assignment_description);
		contentValues.put(ASSIGNMENTS_DUE_DATE, assignment_due_date);

		db.insert(ASSIGNMENTS_TABLE_NAME, null, contentValues);
		return true;
	}

	public Cursor getAssignmentData(int semester, String course_code,
			int assn_number) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery(
				"SELECT * FROM " + ASSIGNMENTS_TABLE_NAME + " WHERE "
						+ COURSES_SEMESTER + " = ? AND " + COURSES_CODE
						+ " = ? AND " + ASSIGNMENTS_NUMBER + " = ?",
				new String[] { String.valueOf(semester), course_code,
						String.valueOf(assn_number) });
		return res;
	}

	public int numberOfRowsInAssignments() {
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db,
				ASSIGNMENTS_TABLE_NAME);
		return numRows;
	}

	public boolean updateAssignment(int semester, String course_code,
			int assignment_number, String assignment_description,
			String assignment_due_date) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put(ASSIGNMENTS_NUMBER, assignment_number);
		contentValues.put(ASSIGNMENTS_DESCRIPTION, assignment_description);
		contentValues.put(ASSIGNMENTS_DUE_DATE, assignment_due_date);

		db.update(ASSIGNMENTS_TABLE_NAME, contentValues, COURSES_SEMESTER
				+ " = ? AND " + COURSES_CODE + " = ? AND " + ASSIGNMENTS_NUMBER
				+ " = ?", new String[] { Integer.toString(semester),
				course_code, Integer.toString(assignment_number) });
		return true;
	}

	public void deleteAssignment(int semester, String course_code,
			int assignment_number) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(
				ASSIGNMENTS_TABLE_NAME,
				COURSES_SEMESTER + " = ? AND " + COURSES_CODE + " = ? AND "
						+ ASSIGNMENTS_NUMBER + " = ?",
				new String[] { Integer.toString(semester), course_code,
						Integer.toString(assignment_number) });
	}

	public ArrayList<String> getAllAssignments(int semester, String course_code) {
		ArrayList<String> array_list = new ArrayList<String>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("SELECT * FROM " + ASSIGNMENTS_TABLE_NAME
				+ " WHERE " + COURSES_SEMESTER + " = ? AND " + COURSES_CODE
				+ " = ?", new String[] { Integer.toString(semester),
				course_code });
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			array_list.add("Assignment "
					+ res.getInt(res.getColumnIndex(ASSIGNMENTS_NUMBER)));
			res.moveToNext();
		}
		return array_list;
	}
}