package com.kjy.secretmemo

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {


    // lazy로 초기화 하는이유: MainActivity가 초기화될 시점에는 view가 아직 그려지지 않았기 때문임.
    // view가 다그려졌다고 알려질 시점이 onCreate함수부터
    private val numberPicker1: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker1)
                // apply를 통해 numberpicker에 접근
            .apply {
                minValue = 0
                maxValue = 9

            }
    }

    private val numberPicker2: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker2)

            .apply {
                minValue = 0
                maxValue = 9

            }
    }

    private val numberPicker3: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker3)

            .apply {
                minValue = 0
                maxValue = 9

            }
    }

    private val openButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.openButton)
    }

    private val changePassWordButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.changePasswordButton)
    }

    // 비밀번호 변경모드에 들어갔을때 예외 처리를 해주기 위해서 선언한 전역변수
    private var changePasswordMode = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker1
        numberPicker2
        numberPicker3


        // 값을 저장하는 sharedPreference 사용
        // name과 mode를 인자로 받는데 name = sharedPreference의 저장 파일 이름을 나타냄.
        // mode를 여기서는 다른앱과 공유하지 않고 본인만 사용할 수 있게 MODE_PRIVATE로 나타냄.
        openButton.setOnClickListener {


            // 비밀번호 변경모드에 들어갔을때 메세지를 띄어주고 setOnClickListener 구문 초기로 돌아감.
            if (changePasswordMode) {
                Toast.makeText(this, "비밀번호 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)


            // 띄어쓰기 없이 각 numberPicker의 값을 가져옴.
            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            // key = "password", default value = 000으로 설정, 빈값 설정시 매치가 되지 않음.
            if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                // 패스워드 성공 == 일치
                // TODO 다이어리 페이지 작성 후에 넘겨주어야 함.
                //startActivity()
                // diaryActivity로 넘겨주는 인텐트 구문 작성 == 패스워드가 정상적으로 일치할 시에 다음 화면으로 넘겨주는 역할.
                startActivity(Intent(this, DiaryActivity::class.java))

                }else {
                    showErrorAlertDialog()
            }

        }

        changePassWordButton.setOnClickListener {


                val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
                val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            // 패스워드 변경 구문
            if (changePasswordMode) {
                passwordPreferences.edit(true) {
                    putString("password", passwordFromUser)

                }

                // 패스워드 변경완료후 패스워드 변경모드 false
                // 버튼 컽러 원상태로 복귀

                changePasswordMode = false
                changePassWordButton.setBackgroundColor(Color.BLACK)

            }else {
                // changePasswordMode가 활성화 :: 비밀번호가 맞는지를 체크


                if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {

                    // 올바른 패스워드가 인식되었으므로 패스워드 변경모드 활성화와 동시에 변경할 패스워드를 요구하는 메시지 출력
                    changePasswordMode = true
                    Toast.makeText(this, "변경할 패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show()

                    // 패스워드 변경 버튼 클릭시 기존의 색상에서 빨간색으로 변경.

                    changePassWordButton.setBackgroundColor(Color.RED)

                }else {
                    showErrorAlertDialog()
                }




            }

        }
    }


    // 에러메시지 함수를 따로 설정하여 코드의 불필요한 중복을 줄임.
    private fun showErrorAlertDialog() {

        AlertDialog.Builder(this)
            .setTitle("실패!!")
            .setMessage("비밀번호가 잘못되었습니다.")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
        // 실패

    }
}