package com.ally.shop

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.ally.shop.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    val TAG = SignInActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSignup.setOnClickListener { view ->
            signUp()
        }
        binding.buttonLogin.setOnClickListener { view ->
            login()
        }




    }

    private fun login() {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(
                binding.edEmail.text.toString(),
                binding.edPassword.text.toString()
            )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    AlertDialog.Builder(this)
                        .setTitle("Login")
                        .setMessage(task.exception?.message)
                        .setPositiveButton("OK", null)
                        .show()
                }
            }
    }

    private fun signUp() {
        val sEmail = binding.edEmail.text.toString()
        val sPassword = binding.edPassword.text.toString()
        Log.d(TAG, "Email:$sEmail, Password:$sPassword")
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(sEmail, sPassword)
            .addOnCompleteListener { task ->
                // 非同步，程式不會停在這裡等待建立完成再下一步，會等待Firebase執行完成後，再回來呼叫OnCompleteListener
                if (task.isSuccessful) {
                    AlertDialog.Builder(this)
                        .setTitle("Sign up Account")
                        .setMessage("Account created")
                        .setPositiveButton("OK") { dialog, which ->
                            setResult(Activity.RESULT_OK)
                            finish()
                        }.show()
                } else {
                    AlertDialog.Builder(this)
                        .setTitle("Sign up Account")
                        .setMessage(task.exception?.message)
                        .setPositiveButton("OK", null)
                        .show()
                }
            }
    }
}