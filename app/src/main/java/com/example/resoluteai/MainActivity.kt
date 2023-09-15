package com.example.resoluteai

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.resoluteai.Googlemaps.MapsActivity
import com.example.resoluteai.databinding.ActivityMainBinding
import com.example.resoluteai.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var uid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        uid = auth.currentUser?.uid.toString()
        database = FirebaseDatabase.getInstance().getReference("User")

        if(uid.isNotBlank()){
            getUserdata()
        }

        binding.getlocation.setOnClickListener(){
            val intent = Intent(this,MapsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getUserdata() {
        database.child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var user = snapshot.getValue(User::class.java)
                binding.nameTextview.setText(user?.name)
                binding.emailTextview.setText(user?.email)
                binding.phoneTextview.setText(user?.phone)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity,"Failed", Toast.LENGTH_LONG).show()
            }
        })
    }
}