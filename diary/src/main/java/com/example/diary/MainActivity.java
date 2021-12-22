package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
    EditText edtDiary;
    Button btnWrite;
    String filename1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatePicker dp = (DatePicker) findViewById(R.id.datePicker1);
        edtDiary = (EditText) findViewById(R.id.edtDiary);
        btnWrite = (Button) findViewById(R.id.btnWrite);

        Calendar cal1 = Calendar.getInstance();
        int cYear = cal1.get(Calendar.YEAR);
        int cMonth = cal1.get(Calendar.MONTH);
        int cDay = cal1.get(Calendar.DAY_OF_MONTH);

        //실행과 동시에 일기 로드(readDiary메소드 호출)
        filename1 = cYear+ "_" + cMonth + "_" + cDay + ".txt";
        String str2 = readDiary(filename1);
        edtDiary.setText(str2);
        btnWrite.setEnabled(true);

       dp.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day)
            {
                //선택날짜로 파일명을 만든다. 예) 2021_12_22.txt
                filename1 = year+ "_" + month + "_" + day + ".txt";
                String str1 = readDiary(filename1);
                edtDiary.setText(str1);
                btnWrite.setEnabled(true);

            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream outFs = openFileOutput(filename1, Context.MODE_PRIVATE);
                    String str = edtDiary.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                    Toast.makeText(getApplicationContext(), filename1+" 이 저장됨", Toast.LENGTH_SHORT).show();
                }
                catch (IOException e)
                {

                }


            }
        });


    }

    private String readDiary(String filename1)
    {
        String str1 = "";
        FileInputStream inFs;

        try
        {
            inFs = openFileInput(filename1);
            byte[] txt = new byte[300];
            inFs.read(txt);
            inFs.close();
            str1 = (new String(txt)).trim();
            btnWrite.setText("수정하기");

        } catch (Exception e) {
            e.printStackTrace();
            edtDiary.setHint("일기없음");
            btnWrite.setText("새로저장");
        }

        return str1;
    }
}