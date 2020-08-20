package com.example.escbaiscapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.LocaleList;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.IllegalFormatCodePointException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ImageButton addContact;
    private ImageButton contact;
    private TextView phoneNum;
    private TextView[] dials = new TextView[10];
    private TextView star;
    private TextView sharp;
    private ImageButton message;
    private ImageButton call;
    private ImageButton backspace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetUpUI();
    }

    private void SetUpUI() {
        addContact = findViewById(R.id.main_ibtn_add);
        contact = findViewById(R.id.main_ibtn_contact);
        phoneNum = findViewById(R.id.main_tv_phone);

        for (int i = 0; i < dials.length; i++) {
            dials[i] = findViewById(getResourceID("main_tv_" + i, "id", this));
        }

        star = findViewById(R.id.main_tv_star);
        sharp = findViewById(R.id.main_tv_sharp);
        message = findViewById(R.id.main_ibtn_message);
        call = findViewById(R.id.main_ibtn_call);
        backspace = findViewById(R.id.main_ibtn_backspace);

        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: 연락처 추가
                Toast.makeText(MainActivity.this, "test", Toast.LENGTH_SHORT).show();
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: 연락처
            }
        });

        setOnClickDial(star, "*");
        setOnClickDial(sharp, "#");

        for (int i = 0; i < 10; i++) {
            setOnClickDial(dials[i], String.valueOf(i));
        }
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:메시지
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: 전화
            }
        });

        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneNum.getText().length() > 0) {
                    String formatPhoneNum = PhoneNumberUtils.formatNumber(
                            phoneNum.getText().subSequence(0, phoneNum.getText().length() - 1).toString(),
                            Locale.getDefault().getCountry()
                    );
                    phoneNum.setText(formatPhoneNum);
                }
            }
        });
    }

    private void setOnClickDial(View view, final String input) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum.setText(changeToDial(phoneNum.getText() + input));
            }
        });
    }

    private int getResourceID(final String resName, final String resType, final Context ctx) {
        final int ResourceID =
                ctx.getResources().getIdentifier(resName, resType, ctx.getApplicationInfo().packageName);
        if (ResourceID == 0) {
            throw new IllegalArgumentException("No resource string found with name " + resName);
        } else {
            return ResourceID;
        }
    }

    private String changeToDial(String phoneNum) {
        // 전화번호 기준 010-6371-8230
        String simplePhoneNum = phoneNum.replace("-", "");
        if (phoneNum.contains("#") || phoneNum.contains("*") || simplePhoneNum.length() > 11) {
            phoneNum = simplePhoneNum;
        }
        else if (simplePhoneNum.length() >= 4 && simplePhoneNum.length() <= 11) {
            if (simplePhoneNum.length() <= 7) {
                if (phoneNum.indexOf("-") == 3) {
                } else {
                    phoneNum = simplePhoneNum.substring(0, 3) + "-" + simplePhoneNum.substring(3);
                }
            } else {
                if (phoneNum.indexOf("-") == 8) {
                } else {
                    phoneNum = simplePhoneNum.substring(0, 3) + "-" + simplePhoneNum.substring(3, 7) + "-" + simplePhoneNum.substring(7);
                }
            }
            return phoneNum;
        }
        return phoneNum;
        // 12글자 이상이면 - 전부 제거
        // 특수문자 있으면 - 전부 제거

        // 4글자 이상일때 3번째 숫자 다음에 - (010-3)
        // 8글자 이상일때 3번째 글자 다음이랑 7번째 글짜 다음에 - (010-6371-8)
    }
}
