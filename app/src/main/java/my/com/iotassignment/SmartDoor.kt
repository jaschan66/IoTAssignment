package my.com.iotassignment

import android.app.*
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.com.iotassignment.databinding.ActivitySmartDoorBinding
import java.lang.StringBuilder
import java.util.*


class SmartDoor : AppCompatActivity(){





    val database = Firebase.database
    val ref = database.getReference("smartDoor")

    private lateinit var binding: ActivitySmartDoorBinding






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySmartDoorBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var getData = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var sb = StringBuilder()

                var doorLVal = snapshot.child("doorLock").value
                var iLockVal = snapshot.child("iLock").value
//                var trippedVal = snapshot.child("tripped").value
//                var lcdMessageVal = snapshot.child("lcdMessage").value

//                sb.append("$lcdMessageVal")
              //  binding.edtMessage.setText(sb)

                binding.swManual.isChecked = doorLVal == "LOCKED"
                binding.swAuto.isChecked = iLockVal == "ENABLE"

                if(iLockVal.toString() == "ENABLE"){
                    binding.swManual.isEnabled = false
                }
                else{
                    binding.swAuto.isEnabled = true
                }
                if (doorLVal.toString() == "UNLOCKED"){
                    binding.lblDoorStatus.text = "UNLOCKED"
                    binding.imgDoorStatus.setImageResource(R.drawable.unlocked)
                    binding.lblDoorStatus.setTextColor(Color.parseColor("#EC3F2C"))

                }
                else{
                    binding.lblDoorStatus.text = "LOCKED"
                    binding.imgDoorStatus.setImageResource(R.drawable.locked)
                    binding.lblDoorStatus.setTextColor(Color.parseColor("#FF03DAC5"))
                }



            }

            override fun onCancelled(error: DatabaseError) {
            }

        }
        ref.addValueEventListener(getData)
        ref.addListenerForSingleValueEvent(getData)

        binding.swManual.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.swAuto.isEnabled = !binding.swManual.isChecked
            if (isChecked){
                ref.child("doorLock")
                    .setValue("LOCKED")
                toast("Door Locked.")
            }
            else {
                ref.child("doorLock")
                    .setValue("UNLOCKED")
                toast("Door Unlocked.")
            }

        }


        binding.swAuto.setOnCheckedChangeListener { _, isChecked ->
            binding.swManual.isEnabled = !binding.swAuto.isChecked
            if (isChecked){
                ref.child("iLock")
                    .setValue("ENABLE")
                toast("I-Lock Enable.")
            }
            else {
                ref.child("iLock")
                    .setValue("DISABLE")
                toast("I-Lock Disable.")
            }
        }

    }






    private fun toast(s: String) {
        Toast.makeText(this,s, Toast.LENGTH_SHORT).show()
    }



}