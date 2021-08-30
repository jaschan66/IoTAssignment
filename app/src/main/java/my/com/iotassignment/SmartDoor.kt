package my.com.iotassignment

import android.app.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.com.iotassignment.databinding.ActivitySmartDoorBinding
import java.util.*


class SmartDoor : AppCompatActivity(),TimePickerDialog.OnTimeSetListener {


    var hour = 0
    var minute = 0

    var savedHour = 0
    var savedMinute = 0

    val database = Firebase.database
    val ref = database.getReference("smartDoor")
    private lateinit var binding: ActivitySmartDoorBinding






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySmartDoorBinding.inflate(layoutInflater)
        setContentView(binding.root)

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


        binding.swAuto.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.swManual.isEnabled = !binding.swAuto.isChecked
            binding.btnTime.isEnabled = isChecked
        }
        binding.btnTime.setOnClickListener { pickDate() }
    }




    private fun getTime(){
        val cal = Calendar.getInstance()
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun pickDate() {
        binding.btnTime.setOnClickListener {
            getTime()

            TimePickerDialog(this, this, hour, minute, true).show()
        }
    }

    private fun toast(s: String) {
        Toast.makeText(this,s, Toast.LENGTH_SHORT).show()
    }


    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute


    }
}