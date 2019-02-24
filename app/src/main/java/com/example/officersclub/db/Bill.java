    package com.example.officersclub.db;

    import android.util.Log;

    import java.util.regex.Matcher;
    import java.util.regex.Pattern;

    public class Bill {
        private Integer billNo;
        private Double amount;
        private String date;
        private String time;
        private final String AMOUNT_REGEX="[\\d]+[.][\\d]+";
        private final String BILL_NO_REGEX="Bill number [\\d]{5,}";
        private final String DATE_REGEX="[A-Za-z]{3} [\\d]{2}, [\\d]{4}";
        private final String TIME_REGEX="[\\d][\\d]:[\\d][\\d]:[\\d][\\d] [AP]M";

        public  Bill()
        {
            setDefault();
        }
        private boolean setAmountFromRegex(String message)
        {

            Pattern pattern= Pattern.compile(AMOUNT_REGEX);
            if(pattern!=null) {
                Matcher matcher = pattern.matcher(message);
                if (matcher != null) {
                    while (matcher.find()) {
                        this.amount = Double.parseDouble(matcher.group());
//                        Log.d("kdkpdk",matcher.group());
                    }
                    if (this.amount != null)
                        return true;
                }
            }
            return false;
        }
        private boolean setBillNoFromRegex(String message)
        {

            Pattern pattern= Pattern.compile(BILL_NO_REGEX);
            if(pattern!=null) {
                Matcher matcher = pattern.matcher(message);
                if (matcher != null) {
                    String billNoString = null;
                    while (matcher.find())
                        billNoString = matcher.group();
                    if(billNoString!=null) {
                        String array[] = billNoString.split("\\s+");

                        if(array.length==3) {
                            this.billNo = Integer.parseInt(array[2]);
                            if (this.billNo != null)
                                return true;
                        }
                    }
                }
            }
            return false;
        }
        private boolean setDateFromRegex(String message)
        {

            Pattern pattern= Pattern.compile(DATE_REGEX);
            if(pattern!=null) {
                Matcher matcher = pattern.matcher(message);
             if(matcher!=null)
             {
                 while (matcher.find())
                     this.date = matcher.group();
                if (this.date != null)
                    return true;
             }
            }
            return false;
        }
        private boolean setTimeFromRegex(String message)
        {

            Pattern pattern= Pattern.compile(TIME_REGEX);
            if(pattern!=null) {
                Matcher matcher = pattern.matcher(message);
                if (matcher != null) {
                    while (matcher.find())
                        this.time = matcher.group();
                    if (this.time != null)
                        return true;
                }
            }
            return false;

        }

        public Bill(String message)
        {
            if(message==null||message=="")
                setDefault();
            else
            {

                 boolean var=setAmountFromRegex(message);
                 Log.d("var",""+var);
                 setBillNoFromRegex(message);
                 setDateFromRegex(message);
                 setTimeFromRegex(message);
            }

        }
        private void setDefault()
        {
            this.billNo=null;
            this.amount=null;
            this.date=null;
            this.time=null;

        }
        public Bill(int billNo,Double amount,String date,String time){
            this.billNo=billNo;
            this.amount=amount;
            this.date=date;
            this.time=time;
        }

        public String getAmount() {
            return amount.toString();
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public String getBillNo() {
            return billNo.toString();
        }

        public void setBillNo(int billNo) {
            this.billNo = billNo;
        }


    }
