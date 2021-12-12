package com.upt.cti.smartwallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var signInButton: Button
    lateinit var signUpButton: Button

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        emailEditText = findViewById(R.id.email_editText)
        passwordEditText = findViewById(R.id.password_editText)
        signInButton = findViewById(R.id.signin_button)
        signUpButton = findViewById(R.id.signup_button)

        mAuth = FirebaseAuth.getInstance()

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("user", emailEditText.text.toString())
        intent.putExtra("pass", passwordEditText.text.toString())

        signInButton.setOnClickListener {
            signInWithEmailAndPassword(
                email = emailEditText.text.toString(),
                password = passwordEditText.text.toString()
            )
        }

        signUpButton.setOnClickListener {
            createUserWithEmailAndPassword(
                email = emailEditText.text.toString(),
                password = passwordEditText.text.toString()
            )
        }


    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { result ->
            run {
                if (result.isSuccessful) {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.putExtra("user", email)
                    intent.putExtra("pass", password)
                }
            }
        }
    }


    private fun createUserWithEmailAndPassword(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { result ->
            run {
                if (result.isSuccessful) {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.putExtra("user", email)
                    intent.putExtra("pass", password)
                }
            }
        }
    }
}