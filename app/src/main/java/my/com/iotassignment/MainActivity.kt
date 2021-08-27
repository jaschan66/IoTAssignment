package my.com.iotassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.com.iotassignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val database = Firebase.database
    val ref = database.getReference("smartSecurity")

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener{ sendToFirebasePls() }



    }

    private fun sendToFirebasePls() {
        var text = binding.edtName.text.toString().trim().uppercase()

        ref.child("tripWire")
            .setValue(text)

        ref.child("tripWire")
            .get()
            .addOnSuccessListener {
                binding.txtTest.text = it.value.toString()
            }
    }


}