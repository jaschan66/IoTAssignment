package my.com.iotassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.com.iotassignment.databinding.ActivityMotVibSensorBinding
import java.lang.StringBuilder

class MotVibSensor : AppCompatActivity() {

    val database = Firebase.database
    val ref = database.getReference("motionSensor")
    private lateinit var binding : ActivityMotVibSensorBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMotVibSensorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackMotion.setOnClickListener { backToSmartGuard() }

        var getData = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {



                var detectorVal = snapshot.child("detection").value
                var vibrationVal = snapshot.child("vibration").value
                var buzzVal = snapshot.child("buzz").value

                binding.btnOffBuzzer.isEnabled = buzzVal.toString() == "ON"
                binding.swMotion.isChecked = detectorVal == "ON"
                binding.swBurglar.isChecked = vibrationVal == "ON"



            }

            override fun onCancelled(error: DatabaseError) {
            }

        }
        ref.addValueEventListener(getData)
        ref.addListenerForSingleValueEvent(getData)


        binding.swMotion.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                ref.child("detection").setValue("ON")
            }
            else
                ref.child("detection").setValue("OFF")
        }
        binding.swBurglar.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                ref.child("vibration").setValue("ON")
            }
            else
                ref.child("vibration").setValue("OFF")
        }

        binding.btnOffBuzzer.setOnClickListener {
            ref.child("buzz").setValue("OFF")
        }

    }

    private fun backToSmartGuard() {
        val intent = Intent (this, SmartGuard::class.java)
        startActivity(intent)
    }

    private fun toast(s: String) {
        Toast.makeText(this,s, Toast.LENGTH_SHORT).show()
    }
}