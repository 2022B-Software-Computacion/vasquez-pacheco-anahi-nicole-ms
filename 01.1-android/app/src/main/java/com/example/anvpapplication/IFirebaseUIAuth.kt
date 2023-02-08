package com.example.anvpapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult

class IFirebaseUIAuth : AppCompatActivity() {

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ){
      res: FirebaseAuthUIAuthenticationResult ->
        if(res.resultCode === RESULT_OK){
            if(res.idpResponse != null){
                seLogeo(res.idpResponse!!)
            }
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ifirebase_uiauth)

        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        btnLogin.setOnClickListener {
            val providers = arrayListOf(
                //Arreglo de PROVIDERS para logearse
                //EJ: Correo, Facebook, Twitter, Google
                AuthUI.IdpConfig.EmailBuilder().build()
            )

            //Create and launch sign-in intent
            //Construimos el intent de login
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()

            //REspuesta del intetn de Login
            signInLauncher.launch(signInIntent)

        }
    }

    fun seLogeo(
        res: IdpResponse
    ){

        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        btnLogin.visibility = View.VISIBLE
        btnLogout.visibility = View.INVISIBLE
        if(res.isNewUser == true){
            registrarUsuarioPorPrimeraVez(res)
        }
    }

    fun registrarUsuarioPorPrimeraVez(
        usuario: IdpResponse
    ){
        //Firestore
    }

}