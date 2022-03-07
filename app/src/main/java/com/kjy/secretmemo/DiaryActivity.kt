package com.kjy.secretmemo

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity: AppCompatActivity() {

    // MainLooper를 연결해주어 메인쓰레드에 연결된 핸들러가 하나 생성됌.
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val diaryEditText = findViewById<EditText>(R.id.diaryEditText)

        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        diaryEditText.setText(detailPreferences.getString("detail", ""))


        // Runnable을 통해 쓰레드에서 일어나는 상황 구현
        // editText 저장 구문
        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit {
                putString("detail", diaryEditText.text.toString())
            }

            Log.d("DiaryActivity", "SAVE!!!!  ${diaryEditText.text.toString()}" )
        }

        diaryEditText.addTextChangedListener {

            Log.d("diaryActivity", "TextChanged :: $it")


            // 이전에 있는 runnable 상황을 지워줌.   0.5초 후에 postDelayed 구문을 실행 시켜줌.
            handler.removeCallbacks(runnable)
            // 500밀리초에 한번씩 딜레이를 줌 (0.5초)
            handler.postDelayed(runnable, 500)







            }

        }

    }

