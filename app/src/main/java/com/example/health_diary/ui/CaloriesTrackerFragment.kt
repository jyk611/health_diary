package com.example.health_diary.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.health_diary.R
import com.example.health_diary.databinding.AddTotalCaloriesIntakeBinding
import com.example.health_diary.databinding.FragmentCaloriesTrackerBinding
import com.example.health_diary.model.TotalCalIntakeList
import com.example.health_diary.utils.toast
import com.example.health_diary.view.TotalCalIntakeAdapter
import com.example.health_diary.viewmodel.TotalCalIntakeViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CaloriesTrackerFragment : Fragment(R.layout.fragment_calories_tracker) {

  private lateinit var binding: FragmentCaloriesTrackerBinding
  private val viewModel by viewModels<TotalCalIntakeViewModel>()
  private lateinit var totalCalIntakeAdapter: TotalCalIntakeAdapter
  private val foodList = listOf<TotalCalIntakeList>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding = FragmentCaloriesTrackerBinding.bind(view)

    (activity as AppCompatActivity).supportActionBar?.title = "Calories Tracker"

    binding.backFoodplanButton.setOnClickListener {
      val action = CaloriesTrackerFragmentDirections.actionCaloriesTrackerFragmentToMainFragment()
      findNavController().navigate(action)
    }

    var textView: TextView = binding.caloriestrackerDate
    val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy")
    val currentDateAndTime: String = simpleDateFormat.format(Date())
    textView.text = currentDateAndTime

    totalCalIntakeAdapter = TotalCalIntakeAdapter(foodList) { food, v ->
      val popupMenus = PopupMenu(requireContext(), v)
      popupMenus.apply {
        inflate(R.menu.show_menu)
        setOnMenuItemClickListener {
          when (it.itemId) {
            R.id.edit_cal_record -> {
              val dialogBinding =  AddTotalCaloriesIntakeBinding.inflate(LayoutInflater.from(requireContext()))

              with(dialogBinding) {
                foodName.setText(food.foodName)
                foodType.setText(food.foodType)
                foodCal.setText(food.foodCal)
              }

              AlertDialog.Builder(requireContext())
                .setView(dialogBinding.root)
                .setPositiveButton("Ok") { dialog, _ ->
                  viewModel.editFood(
                    food.id,
                    dialogBinding.foodName.text.toString(),
                    dialogBinding.foodType.text.toString(),
                    dialogBinding.foodCal.text.toString(),
                  )
                  requireContext().toast("Calories record is edited")
                  dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                  dialog.dismiss()

                }
                .create()
                .show()
              true
            }
            R.id.delete_cal_record -> {
              /**set delete*/
              AlertDialog.Builder(requireContext())
                .setTitle("Delete")
                .setIcon(R.drawable.cal_record_warng)
                .setMessage("Are you sure delete this record ?")
                .setPositiveButton("Yes") { dialog, _ ->
                  viewModel.deleteFood(food)
                }
                .setNegativeButton("No") { dialog, _ ->
                  dialog.dismiss()
                }
                .create()
                .show()

              true
            }
            else -> true
          }
        }
        show()
      }

      val popup = PopupMenu::class.java.getDeclaredField("mPopup")
      popup.isAccessible = true
      val menu = popup.get(popupMenus)
      menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
        .invoke(menu, true)
    }

    binding.caloriesList.apply {
      layoutManager = LinearLayoutManager(requireContext())
      adapter = totalCalIntakeAdapter
    }

    binding.caloriesAdd.setOnClickListener {
      val dialogBinding = AddTotalCaloriesIntakeBinding.inflate(LayoutInflater.from(requireContext()))

      val addDialog = AlertDialog.Builder(requireContext())
      addDialog.apply {
        setView(dialogBinding.root)
        setPositiveButton("OK") { dialog, _ ->
          viewModel.addFood(
            dialogBinding.foodName.text.toString(),
            dialogBinding.foodType.text.toString(),
            dialogBinding.foodCal.text.toString(),
          )
          requireContext().toast("Record Added Successfully")

          dialog.dismiss()
        }
        setNegativeButton("Cancel") { dialog, _ ->
          dialog.dismiss()
          requireContext().toast("Cancel")
        }
        create()
        show()
      }
    }

    viewModel.allFood.observe(viewLifecycleOwner) {
      totalCalIntakeAdapter.insertData(it)
    }

    viewModel.allCalories.observe(viewLifecycleOwner) { calList ->
      var totalCal = 0
      for(cal in calList) {
        totalCal += cal.toInt()
      }
      binding.totalCalIntakeResult.text = totalCal.toString()
    }
  }

}