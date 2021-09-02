package my.com.iotassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.com.iotassignment.databinding.ActivityMotVibSensorBinding
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MotVibSensor : AppCompatActivity() {

    val database = Firebase.database
    val ref = database.getReference("motionSensor")
    private lateinit var binding : ActivityMotVibSensorBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMotVibSensorBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var detectionRecords : MutableList<String> = mutableListOf()


        binding.btnBackMotion.setOnClickListener { backToSmartGuard() }

        var getData = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var detectorVal = snapshot.child("detection").value



                if(detectorVal.toString()=="ON"){
                    val currentDateTime = LocalDateTime.now()
                    val dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                    detectionRecords.add(dtf.format(currentDateTime))
                    while(detectionRecords.size > 5) detectionRecords.removeFirst()
                    binding.lblMotionStatus.text = "${detectionRecords.asReversed().joinToString("\n")}"
                }

                if(detectionRecords.size<=0){
                    binding.lblRecordFound.isVisible = false
                }
                else{
                    binding.lblRecordFound.text = "${detectionRecords.size} record(s) "
                    binding.lblRecordFound.isVisible = true
                }






                var vibrationVal = snapshot.child("vibration").value
                var buzzVal = snapshot.child("buzz").value
                var powerOnVal = snapshot.child("powerOn").value

                binding.btnOffBuzzer.isEnabled = buzzVal.toString() == "ON"
                binding.swMotion.isChecked = powerOnVal == "ON"
                binding.swBurglar.isChecked = vibrationVal == "ON"



            }

            override fun onCancelled(error: DatabaseError) {
            }

        }
        ref.addValueEventListener(getData)
        ref.addListenerForSingleValueEvent(getData)


        binding.swMotion.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                ref.child("powerOn").setValue("ON")
            }
            else
                ref.child("powerOn").setValue("OFF")
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