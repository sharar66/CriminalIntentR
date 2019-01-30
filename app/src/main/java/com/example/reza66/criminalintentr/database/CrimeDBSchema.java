package com.example.reza66.criminalintentr.database;

/**
 * Created by www.NooR26.com on 1/18/2019.
 */

public class CrimeDBSchema {
    public static final String NAME="crimes.db";
    public static final int VERSION =1;
    public static final class CrimeTable{
        public static final String NAME = "crime";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }
}
