package com.jsalek.pw.virtualclinic.security.user;

public enum Role {
    // [WARNING] Values defined in 'Ordinals' must be consistent with actual ordinal of Role enum values.
    PATIENT, DOCTOR, STAFF;

    // this solution is necessary for proper config @DiscriminatorValue in 'User' class descendants
    // (annotation attributes must be a constant expression + @DiscriminatorValue annotation accepts only String)
    public static class Ordinals {
        public static final String PATIENT = "0";
        public static final String DOCTOR = "1";
        public static final String STAFF = "2";
    }
}
