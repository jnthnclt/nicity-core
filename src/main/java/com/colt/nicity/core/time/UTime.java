/*
 * UTime.java.java
 *
 * Created on 12-29-2009 07:48:00 PM
 *
 * Copyright 2009 Jonathan Colt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.colt.nicity.core.time;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class UTime {

    
    
    public static long currentGMT() {
        return new Date().getTime();
    }
   
    
    public static String elapseHMS(long _millis) {
        
        long seconds = _millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        
        seconds -= minutes * 60;
        minutes -= hours * 60;
        
        String time = ((seconds < 10) ? "0" + String.valueOf(seconds) : String.valueOf(seconds));
        time = ((minutes < 10) ? "0" + String.valueOf(minutes) : String.valueOf(minutes)) + "." + time;
        if (hours > 0) {
            time = ((hours < 10) ? "0" + String.valueOf(hours) : String.valueOf(hours)) + ":" + time;
        
        }
        return time;
    }
    
    public static String elapseInDays(long _millis, String _extension) {
        long days = _millis / millisInADay;
        String time = days + " " + _extension;
        return time;
    }
    
    public static String elapse(long _millis) {
        
        long years = _millis / millisInAYear;
        _millis -= years * millisInAYear;
        
        long days = _millis / millisInADay;
        _millis -= days * millisInADay;
        
        long hours = _millis / millisInAHour;
        _millis -= hours * millisInAHour;
        
        long minutes = _millis / millisInAMinute;
        _millis -= minutes * millisInAMinute;
        
        long seconds = _millis / millisInASecond;
        _millis -= seconds * millisInASecond;
        
        String time =
                ((years > 0) ? years + "y " : "") +
                ((days > 0) ? days + "d " : "") +
                ((hours > 0) ? hours + "h " : "") +
                ((minutes > 0) ? minutes + "m " : "") +
                ((seconds > 0) ? seconds + "s " : "");
        return time;
    }
    
    public static String fixedWidthElapse(long _millis) {
        
        long years = _millis / millisInAYear;
        _millis -= years * millisInAYear;
        
        long days = _millis / millisInADay;
        _millis -= days * millisInADay;
        
        long hours = _millis / millisInAHour;
        _millis -= hours * millisInAHour;
        
        long minutes = _millis / millisInAMinute;
        _millis -= minutes * millisInAMinute;
        
        long seconds = _millis / millisInASecond;
        _millis -= seconds * millisInASecond;
        
        long months = days / 32;
        days -= months * 32;
        //long weeks = days/7;
        //days -= weeks*7;
        
        String time =
                months + "-" +
                days + "-" +
                years;
        return time;
    }
    
    public static String elapseYMWDHMS(long _millis) {
        
        long years = _millis / millisInAYear;
        _millis -= years * millisInAYear;
        
        long days = _millis / millisInADay;
        _millis -= days * millisInADay;
        
        long hours = _millis / millisInAHour;
        _millis -= hours * millisInAHour;
        
        long minutes = _millis / millisInAMinute;
        _millis -= minutes * millisInAMinute;
        
        long seconds = _millis / millisInASecond;
        _millis -= seconds * millisInASecond;
        
        long months = days / 32;
        days -= months * 32;
        long weeks = days / 7;
        days -= weeks * 7;
        
        String time =
                ((years > 0) ? (years + ((years == 1) ? " years " : " year ")) : "") +
                ((months > 0) ? (months + ((months == 1) ? " months " : " month ")) : "") +
                ((weeks > 0) ? (weeks + ((weeks == 1) ? " weeks " : " week ")) : "") +
                ((days > 0) ? (days + ((days == 1) ? " days " : " day ")) : "") +
                ((hours > 0) ? (hours + ((hours == 1) ? " hours " : " hour ")) : "") +
                ((minutes > 0) ? (minutes + ((minutes == 1) ? " minutes " : " minute ")) : "") +
                ((seconds > 0) ? (seconds + ((seconds == 1) ? " seconds " : " second ")) : "");
        return time;
    }
    
    public static final long millisInASecond = 1000;
    public static final long millisInAMinute = (long) (millisInASecond * 60);
    public static final long millisInAHour = (long) (millisInAMinute * 60);
    public static final long millisInADay = (long) (millisInAHour * 24);
    public static final long millisInAWeek = (long) (millisInADay * 7);
    public static final long millisInAMonth = (long) (millisInADay * 30.437);
    public static final long millisInAYear = (long) (millisInADay * 365.25);
    
    public static long ydhms(long years, long days, long hours, long minutes, long seconds) {
        long millis = seconds * millisInASecond;
        millis += minutes * millisInAMinute;
        millis += hours * millisInAHour;
        millis += days * millisInADay;
        millis += years * millisInAYear;
        return millis;
    }    
    static DateFormat yearFormatter = null;

    public static String year(long _time) {
        if (yearFormatter == null) {
            yearFormatter = new SimpleDateFormat("yyyy", Locale.getDefault());
            yearFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        return yearFormatter.format(new Date(_time));
    }
    
    static DateFormat monthFormatter = null;

    public static String month(long _time) {
        if (monthFormatter == null) {
            monthFormatter = new SimpleDateFormat("MM", Locale.getDefault());
            monthFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        return monthFormatter.format(new Date(_time));
    }
    
    static DateFormat monthNameFormatter = null;

    public static String monthName(long _time) {
        if (monthNameFormatter == null) {
            monthNameFormatter = new SimpleDateFormat("MMM", Locale.getDefault());
            monthNameFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        return monthNameFormatter.format(new Date(_time));
    }
    
    static DateFormat dayFormatter = null;

    public static String day(long _time) {
        if (dayFormatter == null) {
            dayFormatter = new SimpleDateFormat("dd", Locale.getDefault());
            dayFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        return dayFormatter.format(new Date(_time));
    }
    
    static DateFormat weekDayFormatter = null;

    public static String weekDay(long _time) {
        if (weekDayFormatter == null) {
            weekDayFormatter = new SimpleDateFormat("E", Locale.getDefault());
            weekDayFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        return weekDayFormatter.format(new Date(_time));
    }
    
    static DateFormat dayOfYearFormatter = null;

    public static String dayOfYear(long _time) {
        if (dayOfYearFormatter == null) {
            dayOfYearFormatter = new SimpleDateFormat("D", Locale.getDefault());
            dayOfYearFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        return dayOfYearFormatter.format(new Date(_time));
    }
    
    static DateFormat weekOfYearFormatter = null;

    public static String weekOfYear(long _time) {
        if (weekOfYearFormatter == null) {
            weekOfYearFormatter = new SimpleDateFormat("w", Locale.getDefault());
            weekOfYearFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        return weekOfYearFormatter.format(new Date(_time));
    }
    
    static DateFormat weekOfMonthFormatter = null;

    public static String weekOfMonth(long _time) {
        if (weekOfMonthFormatter == null) {
            weekOfMonthFormatter = new SimpleDateFormat("W", Locale.getDefault());
            weekOfMonthFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        return weekOfMonthFormatter.format(new Date(_time));
    }    
    static DateFormat hourFormatter = null;

    public static String hour(long _time) {
        if (hourFormatter == null) {
            hourFormatter = new SimpleDateFormat("HH", Locale.getDefault());
            hourFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        return hourFormatter.format(new Date(_time));
    }    
    static DateFormat minuteFormatter = null;

    public static String minute(long _time) {
        if (minuteFormatter == null) {
            minuteFormatter = new SimpleDateFormat("mm", Locale.getDefault());
            minuteFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        return minuteFormatter.format(new Date(_time));
    }    
    static DateFormat secondFormatter = null;

    public static String second(long _time) {
        if (secondFormatter == null) {
            secondFormatter = new SimpleDateFormat("ss", Locale.getDefault());
            secondFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        return secondFormatter.format(new Date(_time));
    }    
    static DateFormat timeOfDayFormatter = null;

    public static String timeOfDay(long _time) {
        if (timeOfDayFormatter == null) {
            timeOfDayFormatter = new SimpleDateFormat("aa", Locale.getDefault());
            timeOfDayFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
        return timeOfDayFormatter.format(new Date(_time));
    }    
    static DateFormat fileTimeFormatter = null;
    
    public static String fileNameTime(long _time) {
        if (fileTimeFormatter == null) {
            fileTimeFormatter = new SimpleDateFormat("MM-dd-yyyy_hh-mm-ss_aa_", Locale.getDefault());
            fileTimeFormatter.setTimeZone(TimeZone.getDefault());
        }
        return fileTimeFormatter.format(new Date(_time));
    }    
    static DateFormat fixedWidthFormatter = null;
    static DateFormat formatter = null;
    
    public static String basicTime(long _time) {
        if (formatter == null) {
            formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss aa", Locale.getDefault());
            formatter.setTimeZone(TimeZone.getDefault());
        }
        return formatter.format(new Date(_time));
    }
    
    public static String basicTime(long _time,TimeZone _tz) {
        if (formatter == null) {
            formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss aa z", Locale.getDefault());
            formatter.setTimeZone(_tz);
        }
        return formatter.format(new Date(_time));
    }
    static DateFormat frameFormatter = null;
    
    public static String frameTime(long _time, int _fps) {
        if (frameFormatter == null) {
            frameFormatter = new SimpleDateFormat("SSS", Locale.getDefault());
            frameFormatter.setTimeZone(TimeZone.getDefault());
        }
        try {
            String millis = frameFormatter.format(new Date(_time));
            int m = Integer.valueOf(millis);
            return "" + (int) (m / (1000 / _fps));
        } catch (Exception x) {
            return "";
        }
    }
    
    static DateFormat writtenFormatter = null;

    public static String writtenTime(long _time) {
        if (writtenFormatter == null) {
            writtenFormatter = new SimpleDateFormat("MMMM d yyyy h:m:s", Locale.getDefault());
            writtenFormatter.setTimeZone(TimeZone.getDefault());
        }
        return writtenFormatter.format(new Date(_time));
    }
    
    public static String fixedWidthTime(long _time) {
        if (fixedWidthFormatter == null) {
            fixedWidthFormatter = new SimpleDateFormat("hh:mm:ss aa", Locale.getDefault());
            fixedWidthFormatter.setTimeZone(TimeZone.getDefault());
        }
        return fixedWidthFormatter.format(new Date(_time));
    }
    
    static DateFormat fixedWidthDate = null;

    public static String fixedWidthDate(long _time) {
        if (fixedWidthDate == null) {
            fixedWidthDate = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
            fixedWidthDate.setTimeZone(TimeZone.getDefault());
        }
        return fixedWidthDate.format(new Date(_time));
    }
    
    static DateFormat yyyyFormatter = null;

    public static String yyyy(long _time) {
        if (yyyyFormatter == null) {
            yyyyFormatter = new SimpleDateFormat("yyyy", Locale.getDefault());
            yyyyFormatter.setTimeZone(TimeZone.getDefault());
        }
        return yyyyFormatter.format(new Date(_time));
    }
    
    static DateFormat dateFormat = DateFormat.getDateInstance();
    public static String date(long _time) {
        return dateFormat.format(new Date(_time));
    }
    
    static DateFormat mmddyy = null;

    public static Date parse_mmddyy(String _s) {
        if (mmddyy == null) {
            mmddyy = new SimpleDateFormat("MMddyy");
//!!		locale & time zone options may be desired for some applications
//!!		mmddyy = new SimpleDateFormat("mmddyy",Locale.getDefault());
//!!		mmddyy.setTimeZone(TimeZone.getDefault());
        }
        Date date = null;
        try {
            date = mmddyy.parse(_s, new ParsePosition(0));
        } catch (Exception x) {
            System.out.println("UTime parse_mmddyy exception = " + x);
        }
        return date;
    }
    
    static DateFormat mmddyyyy = null;

    public static Date parse_mmddyyyy(String _s) {
        if (mmddyyyy == null) {
            mmddyyyy = new SimpleDateFormat("MMddyyyy");
//!!		locale & time zone options may be desired for some applications
//!!		mmddyy = new SimpleDateFormat("mmddyy",Locale.getDefault());
//!!		mmddyy.setTimeZone(TimeZone.getDefault());
        }
        Date date = null;
        try {
            date = mmddyyyy.parse(_s, new ParsePosition(0));
        } catch (Exception x) {
            System.out.println("UTime parse_mmddyy exception = " + x);
        }
        return date;
    }
    
    public static String interval(long _millis) {
        
        
        if (_millis < millisInAMinute) {
            return _millis / millisInASecond + "s";
        } else if (_millis < millisInAHour) {
            return _millis / millisInAMinute + "m";
        } else if (_millis < millisInADay) {
            return _millis / millisInAHour + "h";
        } else if (_millis < millisInAYear) {
            return _millis / millisInADay + "d";
        } else {
            return _millis / millisInAYear + "y";
        }
    }
    
    
    static public long yearLong(long _time, int _direction) {
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        GregorianCalendar out = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(_time);
        int year = calendar.get(Calendar.YEAR);
        if (_direction > 0) {
            out.set(year + 1, 0, 1,0,0,0);return out.getTimeInMillis();
        } else if (_direction < 0) {
            out.set(year - 1, 0, 1,0,0,0);return out.getTimeInMillis();
        } else {
            out.set(year, 0, 1,0,0,0);return out.getTimeInMillis();
        }
    }
    
    static public long monthLong(long _time, int _direction) {
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        GregorianCalendar out = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(_time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        if (_direction > 0) {
            if (month == 11) {
                out.set(year + 1, 0, 1,0,0,0);return out.getTimeInMillis();
            } else {
                out.set(year, month + 1, 1,0,0,0);return out.getTimeInMillis();
            }
        } else if (_direction < 0) {
            if (month == 0) {
                out.set(year - 1, 0, 1,0,0,0);return out.getTimeInMillis();
            } else {
                out.set(year, month - 1, 1,0,0,0);return out.getTimeInMillis();
            }
        } else {
            out.set(year, month, 1,0,0,0);return out.getTimeInMillis();
        }
    }
    
    static public long dayLong(long _time, int _direction) {
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        GregorianCalendar out = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(_time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (_direction > 0) {
            int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int minDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
            if (day == maxDay) {
                int maxMonth = calendar.getActualMaximum(Calendar.MONTH);
                int minMonth = calendar.getActualMinimum(Calendar.MONTH);
                if (month == maxMonth) {
                    out.set(year + 1, minMonth, minDay,0,0,0);return out.getTimeInMillis();
                } else {
                    out.set(year, month + 1, minDay,0,0,0);return out.getTimeInMillis();
                }
            } else {
                out.set(year, month, day + 1,0,0,0);return out.getTimeInMillis();
            }
        } else if (_direction < 0) {
            int minDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
            if (day == minDay) {
                if (month == 0) {
                    out.set(year - 1, 0, minDay,0,0,0);return out.getTimeInMillis();
                } else {
                    out.set(year, month - 1, minDay,0,0,0);return out.getTimeInMillis();
                }
            } else {
                out.set(year, month, day - 1,0,0,0);return out.getTimeInMillis();
            }
        } else {
            out.set(year, month, day,0,0,0);return out.getTimeInMillis();
        }
    }
   
    static public long hourLong(long _time, int _direction) {
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        GregorianCalendar out = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(_time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

       

        if (_direction > 0) {
            int max = calendar.getActualMaximum(Calendar.HOUR_OF_DAY);
            if (hour == max) {
                int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                if (day == maxDay) {
                    int maxMonth = calendar.getActualMaximum(Calendar.MONTH);
                    if (month == maxMonth) {
                        out.set(year + 1, 0, 1, 0, 0, 0);return out.getTimeInMillis();
                    } else {
                        out.set(year, month + 1, 1, 0, 0, 0);return out.getTimeInMillis();
                    }
                } else {
                    out.set(year, month, day + 1, 0, 0, 0);return out.getTimeInMillis();
                }
            } else {
                out.set(year, month, day, hour + 1, 0, 0);return out.getTimeInMillis();
            }
        } else if (_direction < 0) {
            if (hour == 1) {
                if (day == 1) {
                    if (month == 11) {
                        out.set(year - 1, 0, 1, 0, 0, 0);return out.getTimeInMillis();
                    } else {
                        out.set(year, month - 1, 1, 0, 0, 0);return out.getTimeInMillis();
                    }
                } else {
                    out.set(year, month, day - 1, 0, 0, 0);return out.getTimeInMillis();
                }
            } else {
                out.set(year, month, day, hour - 1, 0, 0);return out.getTimeInMillis();
            }
        } else {
            out.set(year, month, day, hour, 0, 0);return out.getTimeInMillis();
        }
    }
    
    static public long minuteLong(long _time, int _direction) {
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        GregorianCalendar out = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(_time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if (_direction > 0) {
            int maxMinute = calendar.getActualMaximum(Calendar.MINUTE);
            if (maxMinute == minute) {
                int maxHour = calendar.getActualMaximum(Calendar.HOUR_OF_DAY);
                if (maxHour == hour) {
                    int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    if (day == maxDay) {
                        if (month == 11) {
                            out.set(year + 1, 0, 1, 0, 0, 0);return out.getTimeInMillis();
                        } else {
                            out.set(year, month + 1, 1, 0, 0, 0);return out.getTimeInMillis();
                        }
                    } else {
                        out.set(year, month, day + 1, 0, 0, 0);return out.getTimeInMillis();
                    }
                } else {
                    out.set(year, month, day, hour + 1, 0, 0);return out.getTimeInMillis();
                }
            } else {
                out.set(year, month, day, hour, minute + 1, 0);return out.getTimeInMillis();
            }
        } else if (_direction > 0) {
            if (minute == 0) {
                if (hour == 1) {
                    if (day == 1) {
                        if (month == 0) {
                            out.set(year - 1, 0, 1, 0, 0, 0);return out.getTimeInMillis();
                        } else {
                            out.set(year, month - 1, 1, 0, 0, 0);return out.getTimeInMillis();
                        }
                    } else {
                        out.set(year, month, day - 1, 0, 0, 0);return out.getTimeInMillis();
                    }
                } else {
                    out.set(year, month, day, hour - 1, 0, 0);return out.getTimeInMillis();
                }
            } else {
                out.set(year, month, day, hour, minute - 1, 0);return out.getTimeInMillis();
            }
        } else {
            out.set(year, month, day, hour, 0, 0);return out.getTimeInMillis();
        }
    }
    
    static public long secondLong(long _time, int _direction) {
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        GregorianCalendar out = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(_time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        if (_direction > 0) {
            int maxSecond = calendar.getActualMaximum(Calendar.SECOND);
            if (maxSecond == second) {
                int maxMinute = calendar.getActualMaximum(Calendar.MINUTE);
                if (maxMinute == minute) {
                    int maxHour = calendar.getActualMaximum(Calendar.HOUR_OF_DAY);
                    if (maxHour == hour) {
                        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                        if (day == maxDay) {
                            if (month == 11) {
                                out.set(year + 1, 0, 1, 0, 0, 0);return out.getTimeInMillis();
                            } else {
                                out.set(year, month + 1, 1, 0, 0, 0);return out.getTimeInMillis();
                            }
                        } else {
                            out.set(year, month, day + 1, 0, 0, 0);return out.getTimeInMillis();
                        }
                    } else {
                        out.set(year, month, day, hour + 1, 0, 0);return out.getTimeInMillis();
                    }
                } else {
                    out.set(year, month, day, hour, minute + 1, 0);return out.getTimeInMillis();
                }
            } else {
                out.set(year, month, day, hour, minute, second + 1);return out.getTimeInMillis();
            }
        } else if (_direction < 0) {
            if (second == 0) {
                if (minute == 0) {
                    if (hour == 0) {
                        if (day == 1) {
                            if (month == 0) {
                                out.set(year - 1, 0, 1, 0, 0, 0);return out.getTimeInMillis();
                            } else {
                                out.set(year, month - 1, 1, 0, 0, 0);return out.getTimeInMillis();
                            }
                        } else {
                            out.set(year, month, day - 1, 0, 0, 0);return out.getTimeInMillis();
                        }
                    } else {
                        out.set(year, month, day, hour - 1, 0, 0);return out.getTimeInMillis();
                    }
                } else {
                    out.set(year, month, day, hour, minute - 1, 0);return out.getTimeInMillis();
                }
            } else {
                out.set(year, month, day, hour, minute, second - 1);return out.getTimeInMillis();
            }
        } else {
            out.set(year, month, day, hour, minute, 0);return out.getTimeInMillis();
        }
    }

    static public int gmtYear(long _time) {
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(_time);
        int year = calendar.get(Calendar.YEAR);
        return year;
    }
     static public int gmtMonth(long _time) {
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(_time);
        int month = calendar.get(Calendar.MONTH);
        return month;
    }
      static public int gmtDay(long _time) {
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(_time);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return day;
    }
       static public int gmtHour(long _time) {
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(_time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour;
    }
    static public int gmtMinute(long _time) {
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(_time);
        int minute = calendar.get(Calendar.MINUTE);
        return minute;
    }

    static private long oneSecond = 1000000000L;
    public static String nanoTime(long timeNanos) {
        
        long nano = timeNanos;
        int seconds = (int) (nano /oneSecond);
        int hours = seconds / 3600;
        int minutes = (seconds - hours * 3600) / 60;
        seconds = seconds - hours * 3600 - minutes * 60;
        nano = (long) ((nano %oneSecond) / (oneSecond / 100));

        int hours10 = hours / 10;
        hours = hours % 10;
        int minutes10 = minutes / 10;
        minutes = minutes % 10;
        int seconds10 = seconds / 10;
        seconds = seconds % 10;
        long nano10 = nano / 10;
        nano = nano % 10;

        return new String("" + hours10 + hours + ":" + minutes10 +
                minutes + ":" + seconds10 + seconds + "." + nano10 + nano);
    }

    
	/*
    static public long time(long _time,int _y,int _m,int _d,int _h,int _min,int _s) {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeInMillis(_time);
    int year = calendar.get(Calendar.YEAR);		
    int month = calendar.get(Calendar.MONTH);		
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);
    int second = calendar.get(Calendar.SECOND);
    
    second += _s;
    if (second > 59) {
    second = 0;
    minute++;
    if (minute > 59) {
    minute = 0;
    hour++;
    }
    }
    
    }*/
}
