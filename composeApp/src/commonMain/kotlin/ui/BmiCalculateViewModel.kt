package ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ui.model.BmiMutableView
import ui.model.Gender
import ui.model.HeightIn
import util.isNumeric

class BmiCalculateViewModel : ViewModel() {

    private val _bmiCalculateMutableView = MutableStateFlow(
        BmiMutableView(
            gender = Gender.Man,
            age = 0,
            heightIn = HeightIn.Feet,
            heightFt = "",
            heightInch = "",
            heightInCm = "",
            weight = 0
        )
    )
    val bmiCalculateMutableView: StateFlow<BmiMutableView> = _bmiCalculateMutableView

    private val _bmiCalculatedValue = MutableStateFlow<Double?>(null)
    val bmiCalculatedValue: MutableStateFlow<Double?> = _bmiCalculatedValue

    private val _bmiCalculateError = MutableStateFlow<String?>(null)
    val bmiCalculateError: Flow<String?> = _bmiCalculateError

    fun updateGender(gender: Gender) {
        _bmiCalculateMutableView.value = _bmiCalculateMutableView.value.copy(gender = gender)
    }

    fun setAge(age: Int) {
        _bmiCalculateMutableView.value = _bmiCalculateMutableView.value.copy(age = age)
    }

    fun setHeightIn(height: HeightIn) {
        _bmiCalculateMutableView.value = _bmiCalculateMutableView.value.copy(heightIn = height)
        if (height == HeightIn.Feet) {
            _bmiCalculateMutableView.value = _bmiCalculateMutableView.value.copy(heightFt = "")
            _bmiCalculateMutableView.value = _bmiCalculateMutableView.value.copy(heightInch = "")
        } else {
            _bmiCalculateMutableView.value = _bmiCalculateMutableView.value.copy(heightInCm = "")
        }
    }

    fun setWeight(weight: Int) {
        _bmiCalculateMutableView.value = _bmiCalculateMutableView.value.copy(weight = weight)
    }

    fun heightFt(ft: String) {
        _bmiCalculateMutableView.value = _bmiCalculateMutableView.value.copy(heightFt = ft)

    }

    fun heightInch(inch: String) {
        _bmiCalculateMutableView.value = _bmiCalculateMutableView.value.copy(heightInch = inch)

    }

    fun heightCm(cm: String) {
        _bmiCalculateMutableView.value = _bmiCalculateMutableView.value.copy(heightInCm = cm)
    }

    fun calculateTheBmi() {
        val bmiView = _bmiCalculateMutableView.value
        when (bmiView.heightIn) {
            HeightIn.Centimeter -> {
                when {
                    bmiView.age <= 0 -> {
                        _bmiCalculateError.value = "Give valid age"
                    }

                    bmiView.weight <= 0 -> {
                        _bmiCalculateError.value = "Give valid weight"
                    }

                    isNumeric(bmiView.heightInCm) && bmiView.heightInCm.toDouble() > 0.0 -> {
                        _bmiCalculateError.value = null
                        val heightInMeter =
                            _bmiCalculateMutableView.value.heightInCm.toDouble() / 100
                        val bmi =
                            _bmiCalculateMutableView.value.weight / (heightInMeter * heightInMeter)

                        _bmiCalculatedValue.value = bmi
                    }

                    else -> {
                        _bmiCalculateError.value = "Give a valid height"
                    }
                }
            }

            HeightIn.Feet -> {
                when {
                    bmiView.age <= 0 -> {
                        _bmiCalculateError.value = "Give valid age"
                    }

                    bmiView.weight <= 0 -> {
                        _bmiCalculateError.value = "Give valid weight"
                    }

                    bmiView.heightInch.isEmpty() -> {
                        _bmiCalculateError.value = "Inch shouldn't be a empty"
                    }

                    isNumeric(bmiView.heightFt) && bmiView.heightFt.toInt() > 0 || isNumeric(
                        bmiView.heightInch
                    ) && bmiView.heightInch.toInt() > 0 -> {
                        _bmiCalculateError.value = null
                        val totalInches =
                            _bmiCalculateMutableView.value.heightFt.toDouble() * 12 + _bmiCalculateMutableView.value.heightInch.toDouble()
                        val heightInMeters = totalInches * 0.0254
                        val bmi =
                            _bmiCalculateMutableView.value.weight / (heightInMeters * heightInMeters)
                        _bmiCalculatedValue.value = bmi
                    }

                    else -> {
                        _bmiCalculateError.value = "Give a valid height"
                    }
                }
            }
        }
    }
}