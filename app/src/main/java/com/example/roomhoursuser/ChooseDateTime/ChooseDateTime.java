package com.example.roomhoursuser.ChooseDateTime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.roomhoursuser.CheckInScreen.CheckInActivity;
import com.example.roomhoursuser.PaymentMwthod.PaymentMethodActivity;
import com.example.roomhoursuser.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ChooseDateTime extends AppCompatActivity {

    private RelativeLayout RR_next;
    private RelativeLayout RR_one_hour;
    private RelativeLayout RR_two_hour;
    private RelativeLayout RR_three_hour;
    private RelativeLayout RR_four_sex_hour;

    private RelativeLayout RR_date_checkIn;
    private RelativeLayout RR_date_checkOut;

    private TextView txt_one;
    private TextView txt_two;
    private TextView txt_three;
    private TextView txt_four;

    private TextView txt_date_in;
    private TextView txt_chck_out;
    private CheckBox check_for_hour;
    private RelativeLayout RR_select_check;
    private RelativeLayout RR_selected_hour;
    private LinearLayout RR_chekcout;

    String RentForHour ="";

    private int mYear, mMonth,mDay;
    private int mYear1, mMonth1,mDay1;
    String DateIn="";
    String DateInNew="";
    String DateOut="";
    String NewDate;
    String  DateInnew;
    Calendar c1;
    DatePickerDialog datePickerDialog;

    int Check_In_Year;
    int Check_In_month;
    int Check_In_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date_time);

        RR_next=findViewById(R.id.RR_next);
        RR_one_hour=findViewById(R.id.RR_one_hour);
        RR_two_hour=findViewById(R.id.RR_two_hour);
        RR_three_hour=findViewById(R.id.RR_three_hour);
        RR_four_sex_hour=findViewById(R.id.RR_four_sex_hour);
        txt_one=findViewById(R.id.txt_one);
        txt_two=findViewById(R.id.txt_two);
        txt_three=findViewById(R.id.txt_three);
        txt_four=findViewById(R.id.txt_four);
        RR_date_checkIn=findViewById(R.id.RR_date_checkIn);
        RR_date_checkOut=findViewById(R.id.RR_date_checkOut);
        txt_date_in=findViewById(R.id.txt_date_in);
        txt_chck_out=findViewById(R.id.txt_chck_out);
        check_for_hour=findViewById(R.id.check_for_hour);
        RR_select_check=findViewById(R.id.RR_select_check);
        RR_selected_hour=findViewById(R.id.RR_selected_hour);
        RR_chekcout=findViewById(R.id.RR_chekcout);
        c1 = Calendar.getInstance();
        check_for_hour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked )
                {
                    RR_selected_hour.setVisibility(View.VISIBLE);
                    RR_chekcout.setVisibility(View.INVISIBLE);


                }else
                {
                    RR_selected_hour.setVisibility(View.GONE);
                    RR_chekcout.setVisibility(View.VISIBLE);
                }
            }
        });

       // check_for_hour.setEnabled(false);
        RR_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(DateIn.equalsIgnoreCase(""))
                {
                    Toast.makeText(ChooseDateTime.this, "Please Select Check In Date", Toast.LENGTH_SHORT).show();

                }else if(check_for_hour.isChecked())
                {
                        Intent intent = new Intent(ChooseDateTime.this, PaymentMethodActivity.class);
                        startActivity(intent);

                }else if(DateOut.equalsIgnoreCase(""))
                {
                    Toast.makeText(ChooseDateTime.this, "Please Select Check Out Date", Toast.LENGTH_SHORT).show();

                }else
                {
                    Intent intent = new Intent(ChooseDateTime.this, PaymentMethodActivity.class);
                    startActivity(intent);
                }
            }
        });

        RR_select_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(check_for_hour.isChecked())
                {
                    check_for_hour.setChecked(false);

                    RR_selected_hour.setVisibility(View.GONE);
                    RR_chekcout.setVisibility(View.VISIBLE);

                }else
                {
                    check_for_hour.setChecked(true);
                    RR_selected_hour.setVisibility(View.VISIBLE);
                    RR_chekcout.setVisibility(View.INVISIBLE);

                }

            }
        });

        RR_date_checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                  mYear = c1.get(Calendar.YEAR);
                  mMonth = c1.get(Calendar.MONTH);
                  mDay = c1.get(Calendar.DAY_OF_MONTH);

                  datePickerDialog = new DatePickerDialog(ChooseDateTime.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                //  RR_booking_Date.setVisibility( View.VISIBLE );
                                //  txt_time.setVisibility(View.VISIBLE);
                                view.setVisibility(View.VISIBLE);
                             //String   Date = (dayOfMonth+"-"+(monthOfYear+1)+"-"+year);
                                DateIn = (year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                                DateInNew = (year+""+(monthOfYear+1)+""+dayOfMonth);

                                Check_In_Year = year;
                                Check_In_month = monthOfYear;
                                Check_In_day = dayOfMonth;
                                DateInnew= String.valueOf(year+monthOfYear+dayOfMonth);
                                String NewDate = getDate(DateIn);

                                txt_date_in.setText(NewDate);

                            }
                        }, mYear, mMonth, mDay);

                 datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

                 datePickerDialog.show();

            }
        });

        RR_date_checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!DateIn.equalsIgnoreCase(""))
                {
                    DatePickerDialog   datePickerDialog = new DatePickerDialog(ChooseDateTime.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    DateOut = (year+"-"+(monthOfYear+1)+"-"+dayOfMonth);

                                     NewDate = getDate(DateOut);
                                    txt_chck_out.setText(NewDate);

                                }
                            }, Check_In_Year, Check_In_month, Check_In_day);

                    datePickerDialog.getDatePicker().setMinDate(c1.getTimeInMillis()-1000);

                    datePickerDialog.show();

                }else
                {
                    Toast.makeText(ChooseDateTime.this, "Please Select Check in Date..", Toast.LENGTH_SHORT).show();
                }
            }
        });


        RR_one_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RentForHour ="1hr";

                txt_one.setTextColor(getResources().getColor(R.color.white));
                txt_two.setTextColor(getResources().getColor(R.color.black));
                txt_three.setTextColor(getResources().getColor(R.color.black));
                txt_four.setTextColor(getResources().getColor(R.color.black));

                RR_one_hour.setBackgroundResource(R.drawable.roundbttn_white_one_yellow);
                RR_two_hour.setBackgroundResource(R.drawable.roundbttn_white_one);
                RR_three_hour.setBackgroundResource(R.drawable.roundbttn_white_one);
                RR_four_sex_hour.setBackgroundResource(R.drawable.roundbttn_white_one);

            }
        });
        RR_two_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RentForHour ="2hr";

                txt_one.setTextColor(getResources().getColor(R.color.black));
                txt_two.setTextColor(getResources().getColor(R.color.white));
                txt_three.setTextColor(getResources().getColor(R.color.black));
                txt_four.setTextColor(getResources().getColor(R.color.black));

                RR_one_hour.setBackgroundResource(R.drawable.roundbttn_white_one);
                RR_two_hour.setBackgroundResource(R.drawable.roundbttn_white_one_yellow);
                RR_three_hour.setBackgroundResource(R.drawable.roundbttn_white_one);
                RR_four_sex_hour.setBackgroundResource(R.drawable.roundbttn_white_one);

            }
        });

        RR_three_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RentForHour ="3hr";

                txt_one.setTextColor(getResources().getColor(R.color.black));
                txt_two.setTextColor(getResources().getColor(R.color.black));
                txt_three.setTextColor(getResources().getColor(R.color.white));
                txt_four.setTextColor(getResources().getColor(R.color.black));

                RR_one_hour.setBackgroundResource(R.drawable.roundbttn_white_one);
                RR_two_hour.setBackgroundResource(R.drawable.roundbttn_white_one);
                RR_three_hour.setBackgroundResource(R.drawable.roundbttn_white_one_yellow);
                RR_four_sex_hour.setBackgroundResource(R.drawable.roundbttn_white_one);

            }
        });

        RR_four_sex_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RentForHour ="4hr";

                txt_one.setTextColor(getResources().getColor(R.color.black));
                txt_two.setTextColor(getResources().getColor(R.color.black));
                txt_three.setTextColor(getResources().getColor(R.color.black));
                txt_four.setTextColor(getResources().getColor(R.color.white));

                RR_one_hour.setBackgroundResource(R.drawable.roundbttn_white_one);
                RR_two_hour.setBackgroundResource(R.drawable.roundbttn_white_one);
                RR_three_hour.setBackgroundResource(R.drawable.roundbttn_white_one);
                RR_four_sex_hour.setBackgroundResource(R.drawable.roundbttn_white_one_yellow);

            }
        });



    }

    private String getDate(String Date)
    {
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dateObj = null;
        try {
            dateObj = curFormater.parse(Date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("dd MMM yyyy");
        String newDateStr = postFormater.format(dateObj);
        return newDateStr;
    }

    public long getLongAsDate(int year, int month, int date) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, date);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        return calendar.getTimeInMillis();
    }

}
