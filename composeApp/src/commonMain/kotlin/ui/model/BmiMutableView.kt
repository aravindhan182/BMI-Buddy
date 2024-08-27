package ui.model

data class BmiMutableView(
    var gender: Gender = Gender.Man,
    var age: Int = 0,
    var heightIn: HeightIn = HeightIn.Centimeter,
    var heightFt: String ,
    var heightInch: String,
    var heightInCm: String,
    var weight: Int = 0
)

enum class Gender(val id: Int, val value: String) {
    Man(1, "Man"),
    Women(2, "Women")
}

enum class HeightIn(val id: Int, val value: String) {
    Centimeter(1, "cm"),
    Feet(2, "ft")
}
