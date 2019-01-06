import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DfDate extends Value {
    private LocalDate date = null;
    private LocalDateTime dateTime = null;
    private boolean time = false;


    public DfDate(int ... date){
        if (date.length == 3){
            this.date = LocalDate.of(date[0],date[1],date[2]);
        }
        else if(date.length == 6){
            this.dateTime = LocalDateTime.of(date[0],date[1],date[2],date[3],date[4],date[5]);
            time = true;
        }
        else{
            throw new IllegalArgumentException("Invalid date format. " +
                    "Expected [year,month,day] or [year,month,day,hour,minute,second].");
        }
    }

    public DfDate(String date){
        if (date.length() == 10){
            this.date = LocalDate.parse(date);
        }
        else if (date.length() == 19){
            this.dateTime = LocalDateTime.parse(date);
            this.time = true;
        }
        else{
            throw new IllegalArgumentException("Invalid date format. Expected yyyy-mm-dd or yyyy-mm-ddThh:mm:ss");
        }
    }



    //2019-08-15
    //2020-06-30T15:06:45

    @Override
    public String toString() {
        if (time){
            return dateTime.toString();
        }
        else{
            return date.toString();
        }
    }

    @Override
    public Value add(Value value) {
        if (value instanceof DfDate){
            if (!time){
                if (((DfDate) value).isTime()){
                    throw new IllegalArgumentException("Only two dates with the same precision can be added");
                }
                else {
                    this.date.plus(((DfDate) value).getYear(), ChronoUnit.YEARS);
                    this.date.plus(((DfDate) value).getMonth(), ChronoUnit.MONTHS);
                    this.date.plus(((DfDate) value).getDay(), ChronoUnit.DAYS);
                }
            }
            else {
                if (! ((DfDate) value).isTime()){
                    throw new IllegalArgumentException("Only two dates with the same precision can be added");
                }
                else {
                    this.dateTime.plus(((DfDate) value).getYear(), ChronoUnit.YEARS);
                    this.dateTime.plus(((DfDate) value).getMonth(), ChronoUnit.MONTHS);
                    this.dateTime.plus(((DfDate) value).getDay(), ChronoUnit.DAYS);
                    this.dateTime.plus(((DfDate) value).getHour(), ChronoUnit.HOURS);
                    this.dateTime.plus(((DfDate) value).getMinute(), ChronoUnit.MINUTES);
                    this.dateTime.plus(((DfDate) value).getSecond(), ChronoUnit.SECONDS);
                }
            }
        }
        else {
            throw new IllegalArgumentException("Only two dates can be added");
        }
        return null;
    }

    @Override
    public Value sub(Value value) {
        if (value instanceof DfDate){
            if (!time){
                if (((DfDate) value).isTime()){
                    throw new IllegalArgumentException("Only two dates with the same precision can be added");
                }
                else {
                    this.date.minus(((DfDate) value).getYear(), ChronoUnit.YEARS);
                    this.date.minus(((DfDate) value).getMonth(), ChronoUnit.MONTHS);
                    this.date.minus(((DfDate) value).getDay(), ChronoUnit.DAYS);
                }
            }
            else {
                if (! ((DfDate) value).isTime()){
                    throw new IllegalArgumentException("Only two dates with the same precision can be added");
                }
                else {
                    this.dateTime.minus(((DfDate) value).getYear(), ChronoUnit.YEARS);
                    this.dateTime.minus(((DfDate) value).getMonth(), ChronoUnit.MONTHS);
                    this.dateTime.minus(((DfDate) value).getDay(), ChronoUnit.DAYS);
                    this.dateTime.minus(((DfDate) value).getHour(), ChronoUnit.HOURS);
                    this.dateTime.minus(((DfDate) value).getMinute(), ChronoUnit.MINUTES);
                    this.dateTime.minus(((DfDate) value).getSecond(), ChronoUnit.SECONDS);
                }
            }
        }
        else {
            throw new IllegalArgumentException("Only two dates can be added");
        }
        return null;
    }

    @Override
    public Value mul(Value value) {
        return null;
    }

    @Override
    public Value div(Value value) {
        return new DfDate("0000-01-01");
    }

    @Override
    public Value pow(Value value) {
        return null;
    }

    @Override
    public boolean eq(Value value) {
        if (value instanceof DfDate) {
            if (this.time && ((DfDate) value).time) {
                return this.dateTime.isEqual(((DfDate) value).dateTime);
            }
            else if (!(this.time && ((DfDate) value).time)){
                return this.date.isEqual(((DfDate) value).date);
            }
            else {
                throw new IllegalArgumentException("Only two Dates with the same precision can be compared");
            }
        }
        else {
            throw new IllegalArgumentException("Only two Dates can be compared");
        }
    }

//    DfDate > '2018-03-27' == "data jest STARSZA niz 2018-03-27

    @Override
    public boolean gte(Value value) {
        if (value instanceof DfDate) {
            if (this.time && ((DfDate) value).time) {
                if (this.dateTime.isAfter(((DfDate) value).dateTime)) {
                    return true;
                }
                else {
                    return this.dateTime.isEqual(((DfDate) value).dateTime);
                }
            }
            else if (!(this.time && ((DfDate) value).time)) {
                if (this.date.isAfter(((DfDate) value).date)) {
                    return true;
                }
                else {
                    return this.date.isEqual(((DfDate) value).date);
                }
            }
            else {
                throw new IllegalArgumentException("Only two Dates with the same precision can be compared");
            }
        }
        else {
            throw new IllegalArgumentException("Only two Dates can be compared");
        }
    }

    @Override
    public boolean lte(Value value) {
        if (value instanceof DfDate) {
            if (this.time && ((DfDate) value).time) {
                if (this.dateTime.isBefore(((DfDate) value).dateTime)) {
                    return true;
                }
                else {
                    return this.dateTime.isEqual(((DfDate) value).dateTime);
                }
            }
            else if (!(this.time && ((DfDate) value).time)) {
                if (this.date.isBefore(((DfDate) value).date)) {
                    return true;
                }
                else {
                    return this.date.isEqual(((DfDate) value).date);
                }
            }
            else {
                throw new IllegalArgumentException("Only two Dates with the same precision can be compared");
            }
        }
        else {
            throw new IllegalArgumentException("Only two Dates can be compared");
        }
    }

    @Override
    public boolean lt(Value value) {
        if (value instanceof DfDate){
            return !this.gte(value);
        }
        else {
            throw new IllegalArgumentException("Only two Dates can be compared");
        }
    }

    @Override
    public boolean gt(Value value) {
        if (value instanceof DfDate){
            return !this.lte(value);
        }
        else {
            throw new IllegalArgumentException("Only two Dates can be compared");
        }
    }

    @Override
    public boolean neq(Value value) {
        return !this.eq(value);
    }

    @Override
    public boolean equals(Object other) {
        return this.eq((Value)other);
    }

    @Override
    public int hashCode() {
        if (time){
            return java.lang.Integer.valueOf(this.getDay() * 79 + this.getMonth() * 7 + this.getYear() * 3 +
                    this.getHour() * 17 + this.getMinute() * 13 + this.getSecond() * 19).hashCode();
        }
        else{
            return java.lang.Integer.valueOf(this.getDay() * 79 + this.getMonth() * 7 + this.getYear() * 3).hashCode();
        }
    }

    @Override
    public Value create(String s) {
        return new DfDate(s);
    }

    private int getDay() {
        if (time){
            return dateTime.getDayOfMonth();
        }
        else{
            return date.getDayOfMonth();
        }
    }

    private int getMonth() {
        if (time){
            return dateTime.getMonthValue();
        }
        else {
            return date.getMonthValue();
        }
    }

    private int getYear() {
        if (time){
            return dateTime.getYear();
        }
        else {
            return date.getYear();
        }
    }

    private int getHour() {
        if (time){
            return dateTime.getHour();
        }
        else{
            return 0;
        }
    }

    private int getMinute() {
        if (time){
            return dateTime.getMinute();
        }
        else{
            return 0;
        }
    }

    private int getSecond() {
        if (time){
            return dateTime.getSecond();
        }
        else{
            return 0;
        }
    }

    public boolean isTime() {
        return time;
    }

    public void setTime(boolean time) {
        this.time = time;
    }

    public Value clone(Value value) {
        return new DfDate(this.toString());
    }

}
