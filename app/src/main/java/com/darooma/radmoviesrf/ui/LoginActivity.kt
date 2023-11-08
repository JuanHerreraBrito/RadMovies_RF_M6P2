package com.darooma.radmoviesrf.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.darooma.radmoviesrf.R
import com.darooma.radmoviesrf.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    //Para Firebase
    private lateinit var firebaseAuth: FirebaseAuth

    //Para cajas de texto
    private var email = ""
    private var contrasenia = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //Esto si para firebase
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            if (!validaCampos()) return@setOnClickListener
            binding.progressBar.visibility = View.VISIBLE

            //Autenticacion al usuario
            autenticaUsuario(email,contrasenia)
        }
        binding.btnRegistrarse.setOnClickListener {
            if(!validaCampos()) return@setOnClickListener

            binding.progressBar.visibility = View.VISIBLE

            //Registrando al usuario
            firebaseAuth.createUserWithEmailAndPassword(email, contrasenia).addOnCompleteListener { authResult->
                if(authResult.isSuccessful){
                    //Enviar correo para verificación de email
                    var user_fb = firebaseAuth.currentUser
                    user_fb?.sendEmailVerification()?.addOnSuccessListener {
                        Toast.makeText(this, getString(R.string.mail_send), Toast.LENGTH_SHORT).show()
                    }?.addOnFailureListener {
                        Toast.makeText(this, getString(R.string.error_sendind), Toast.LENGTH_SHORT).show()
                    }

                    Toast.makeText(this, getString(R.string.user_created), Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("psw", contrasenia)
                    startActivity(intent)
                    finish()


                }else{
                    binding.progressBar.visibility = View.GONE
                    manejaErrores(authResult)
                }
            }
        }
        binding.tvRestablecerPassword.setOnClickListener {
            val resetMail = EditText(it.context)
            resetMail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

            val passwordResetDialog = AlertDialog.Builder(it.context)
                .setTitle(getString(R.string.set_new_pass))
                .setMessage(getString(R.string.set_mail_to_new_pass))
                .setView(resetMail)
                .setPositiveButton(getString(R.string.send)) { _, _ ->
                    val mail = resetMail.text.toString()
                    if (mail.isNotEmpty()) {
                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener {
                            Toast.makeText(
                                this,
                                getString(R.string.link_sended),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }.addOnFailureListener {
                            Toast.makeText(
                                this,
                                "${getString(R.string.error_send_link)}  ${it.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show() //it tiene la excepción
                        }
                    } else {
                        Toast.makeText(
                            this,
                            getString(R.string.insert_mail),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()

        }
    }

    private fun validaCampos(): Boolean{
        email = binding.tietEmail.text.toString().trim() //para que quite espacios en blanco
        contrasenia = binding.tietContrasenia.text.toString().trim()

        if(email.isEmpty()){
            binding.tietEmail.error = getString(R.string.mail_required)
            binding.tietEmail.requestFocus()
            return false
        }

        if(contrasenia.isEmpty() || contrasenia.length < 6){
            binding.tietContrasenia.error = getString(R.string.bad_pass)
            binding.tietContrasenia.requestFocus()
            return false
        }

        return true
    }


    //Autenticar Firebase
    private fun autenticaUsuario(usr: String, psw: String){

        //tiene un activity del login
        firebaseAuth.signInWithEmailAndPassword(usr,psw).addOnCompleteListener {authResult->
            if (authResult.isSuccessful){
                Toast.makeText(this, getString(R.string.succes_logging), Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("psw", psw)
                startActivity(intent)
                finish()
            }else{
                binding.progressBar.visibility = View.GONE
                manejaErrores(authResult)
            }
        }
    }


    private fun manejaErrores(task: Task<AuthResult>){
        var errorCode = ""

        try{
            errorCode = (task.exception as FirebaseAuthException).errorCode
        }catch(e: Exception){
            e.printStackTrace()
        }

        when(errorCode){
            "ERROR_INVALID_EMAIL" -> {
                Toast.makeText(this, getString(R.string.error_bad_mail_format), Toast.LENGTH_SHORT).show()
                binding.tietEmail.error = getString(R.string.error_bad_mail_format)
                binding.tietEmail.requestFocus()
            }
            "ERROR_WRONG_PASSWORD" -> {
                Toast.makeText(this, getString(R.string.error_pass_invalid), Toast.LENGTH_SHORT).show()
                binding.tietContrasenia.error = getString(R.string.error_pass_invalid_ne)
                binding.tietContrasenia.requestFocus()
                binding.tietContrasenia.setText("")

            }
            "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> {
                //An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.
                Toast.makeText(this, getString(R.string.error_mail_exist), Toast.LENGTH_SHORT).show()
            }
            "ERROR_EMAIL_ALREADY_IN_USE" -> {
                Toast.makeText(this, getString(R.string.error_mail_in_use), Toast.LENGTH_LONG).show()
                binding.tietEmail.error = (getString(R.string.error_mail_in_use))
                binding.tietEmail.requestFocus()
            }
            "ERROR_USER_TOKEN_EXPIRED" -> {
                Toast.makeText(this, getString(R.string.error_expired_sesion), Toast.LENGTH_LONG).show()
            }
            "ERROR_USER_NOT_FOUND" -> {
                Toast.makeText(this, getString(R.string.error_user_not_found), Toast.LENGTH_LONG).show()
            }
            "ERROR_WEAK_PASSWORD" -> {
                Toast.makeText(this, getString(R.string.pass_invalid), Toast.LENGTH_LONG).show()
                binding.tietContrasenia.error = getString(R.string.pass_min_must_6)
                binding.tietContrasenia.requestFocus()
            }
            "NO_NETWORK" -> {
                Toast.makeText(this, getString(R.string.bad_network), Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(this, getString(R.string.error_bad_authentication), Toast.LENGTH_SHORT).show()
            }
        }

    }




}