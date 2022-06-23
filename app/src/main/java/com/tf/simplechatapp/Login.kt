package com.tf.simplechatapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var loginBtn: Button
    private lateinit var signupTv: TextView

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        emailEt = findViewById(R.id.email_et_id)
        passwordEt = findViewById(R.id.password_et_id)
        loginBtn = findViewById(R.id.login_button_id)

        loginBtn.setOnClickListener {
            val email = emailEt.text.toString()
            val password = passwordEt.text.toString()
            login(email, password)
        }

        signupTv = findViewById(R.id.signup_tv_id)
        val txt =
            "<font color=\"#000000\">Don't have an account?</font> <font color=\"#02B815\">SIGNUP</font>"
        signupTv.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(txt, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(txt)
        }
        signupTv.setOnClickListener {
            startActivity(Intent(this@Login, SignUp::class.java))
        }
    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    finish()
                    startActivity(Intent(this@Login, MainActivity::class.java))
                } else {
                    Toast.makeText(this, "User can't exists.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}