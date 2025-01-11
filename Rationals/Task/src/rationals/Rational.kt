package rationals
import rationals.Rational.Companion.toRational
import java.math.BigInteger


/* Your task is to implement a class Rational representing rational numbers (So Huu Ti) .

A rational number is a number expressed as a ratio n/d , where n (numerator-TuSo) and d (denominator-MauSo)

are integer numbers, except that d cannot be zero. If the denominator is zero, you can throw an exception

on creating a new instance of the class, e.g. IllegalArgumentException.

---------------------

Examples of rational numbers are 1/2, 2/3, 117/1098, and 2/1 (which can alternatively be written simply as 2).

Rational numbers are represented exactly, without rounding LamTron or approximation XapSi, which gives them

the advantage compared to floating-point numbers-DauPhayDong .


Your task it to model the behavior of rational numbers,
including allowing them to be :
- added,
- subtracted,
- multiplied,
- divided and compared.

All arithmetic and comparison operations PhepToanSoHoc&SoSanh must be available for
rationals: +, -, *, /, ==, <, >= etc. Check whether a number belongs to a range should also be possible:
1/2 in 1/3..2/3 should return true.


The Rational class should contain a numerator and denominator which might be unlimited numbers,
so use java.math.BigInteger class for storing them.

The rational numbers must be compared to their "normalized" forms: for example, 1/2 should be equal to 2/4,
or 117/1098 to 13/122. The string representation of a rational must be also given in the normalized form.
được đưa ra ở dạng chuẩn hóa
Note that the denominator 1 must be omitted, so 2/1 must be printed as "2". The denominator must be always
positive in the normalized form. If the negative rational is normalized, then only the numerator can be
negative, e.g. the normalized form of 1/-2 should be -1/2.

Note that you can use BigInteger.gcd to find the greatest common divisor (ước chung lớn nhất) used in the
normalized form calculation.

You need to support two ways to create rationals.
The first one is to convert a string representation to a
rational directly, like in "1/2".toRational().
Converting an integer number should also be possible, and 1 should be used as denominator by default: "23".
toRational() is the same as "23/1".toRational().

The alternative way to create a rational is to use divBy infix function like in 1 divBy 2. The receiver and the argument might be of types Int, Long, or BigInteger.

Examples
All the following equality checks must be evaluated to true:

val half = 1 divBy 2
val third = 1 divBy 3

val sum: Rational = half + third
5 divBy 6 == sum

val difference: Rational = half - third
1 divBy 6 == difference

val product: Rational = half * third
1 divBy 6 == product

val quotient: Rational = half / third
3 divBy 2 == quotient

val negation: Rational = -half
-1 divBy 2 == negation

(2 divBy 1).toString() == "2"
(-2 divBy 4).toString() == "-1/2"
"117/1098".toRational().toString() == "13/122"

val twoThirds = 2 divBy 3
half < twoThirds

half in third..twoThirds

2000000000L divBy 4000000000L == 1 divBy 2

"912016490186296920119201192141970416029".toBigInteger() divBy
    "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2 */

/* việc khai báo các biến (hoặc thuộc tính) trực tiếp trong danh sách tham số của lớp là một tính năng đặc biệt,
được gọi là Primary Constructor with Property Declaration (Khởi tạo chính kèm theo khai báo thuộc tính) như khái báo bt*/
class Rational(val numerator: BigInteger, val denominator: BigInteger) : Comparable<Rational> {
/* Comparable<Rational>: Lớp này triển khai giao diện Comparable, cho phép các đối tượng của lớp Rational được so
sánh với nhau. (Chi tiết so sánh sẽ phụ thuộc vào việc bạn triển khai phương thức compareTo() sau này.) */

    /* Khối init là khối khởi tạo trong Kotlin, được thực thi ngay khi đối tượng của lớp được tạo.

    Dòng require(denominator != BigInteger.ZERO) kiểm tra rằng mẫu số (denominator) không được bằng 0.

    Nếu mẫu số bằng 0, chương trình sẽ ném ra một ngoại lệ IllegalArgumentException với thông báo

    "Denominator cannot be zero.". */
    init {
        require(denominator != BigInteger.ZERO) { "Denominator cannot be zero." }
    }

    // Normalize the rational number to its simplest form
    fun normalize(): Rational {
        /* Tìm ước chung lớn nhất (GCD) */
        val gcd = numerator.gcd(denominator)
        val normalizedNumerator = numerator / gcd
        val normalizedDenominator = denominator / gcd
        // Ensure denominator is positive
        // Trong toán học, mẫu số của phân số thường được quy ước là số dương.
        return if (normalizedDenominator < BigInteger.ZERO) {
            Rational(-normalizedNumerator, -normalizedDenominator)
        } else {
            Rational(normalizedNumerator, normalizedDenominator)
        }
    }

    /* từ khóa operator được sử dụng để overload (nạp chồng) một toán tử trong Kotlin.
     Nó cho phép bạn định nghĩa cách mà các toán tử mặc định (như +, -, *, ...) hoạt động trên
     các đối tượng của một lớp cụ thể. */
    // Addition of two Rational numbers
    operator fun plus(other: Rational): Rational {
        val newNumerator = numerator * other.denominator + denominator * other.numerator
        val newDenominator = denominator * other.denominator
        return Rational(newNumerator, newDenominator).normalize()
    }

    // Subtraction of two Rational numbers
    operator fun minus(other: Rational): Rational {
        val newNumerator = numerator * other.denominator - denominator * other.numerator
        val newDenominator = denominator * other.denominator
        return Rational(newNumerator, newDenominator).normalize()
    }

    // Multiplication of two Rational numbers
    operator fun times(other: Rational): Rational {
        val newNumerator = numerator * other.numerator
        val newDenominator = denominator * other.denominator
        return Rational(newNumerator, newDenominator).normalize()
    }

    // Division of two Rational numbers
    operator fun div(other: Rational): Rational {
        val newNumerator = numerator * other.denominator
        val newDenominator = denominator * other.numerator
        return Rational(newNumerator, newDenominator).normalize()
    }

    // Negation of a Rational number - toán tử - đơn ngôi
    // Make nagation of a rational number
    operator fun unaryMinus(): Rational {
        return Rational(-numerator, denominator).normalize()
    }

    // Comparison operators
    // Nhân chéo tử mẫu 2 số cho nhau và so sánh nếu tích tử số nào > hơn thì số đó lớn hơn
    override fun compareTo(other: Rational): Int {
        val lefths = numerator * other.denominator
        val righths = denominator * other.numerator
        return lefths.compareTo(righths)
    }

    // Check if the rational number is within a range
    /* Lỗi "operator modifier is inapplicable on this function: must return Int" xảy ra trong
    Kotlin do quy tắc của từ khóa operator.

    Nguyên nhân lỗi
````Trong Kotlin, từ khóa operator được sử dụng để nạp chồng toán tử. Khi bạn nạp chồng toán tử
````so sánh (<, >, <=, >=), bạn cần tuân thủ các quy định sau:

    Hàm phải có tên compareTo.
    Hàm phải trả về một giá trị kiểu Int

    range: ClosedRange<Rational> là một đối tượng đại diện cho một phạm vi đóng (ClosedRange) với
    kiểu dữ liệu là Rational. Phạm vi này bao gồm cả giá trị bắt đầu (start) và giá trị kết thúc
    (endInclusive).

    Đúng vậy, trong Kotlin, ClosedRange<T> là một interface được sử dụng để đại diện cho một phạm vi
    (range) mà giá trị bắt đầu (start) và kết thúc (endInclusive) đều được bao gồm.

    interface ClosedRange<T : Comparable<T>> {
    val start: T
    val endInclusive: T

    operator fun contains(value: T): Boolean
    fun isEmpty(): Boolean
    }
    */
    fun compareTo(range: ClosedRange<Rational>): Boolean {
        return this >= range.start && this <= range.endInclusive
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Rational) return false
        return this.numerator == other.numerator && this.denominator == other.denominator
    }

    override fun hashCode(): Int {
        return 31 * numerator.hashCode() + denominator.hashCode()
    }

    // String representation in normalized form
    override fun toString(): String {
        return if (denominator == BigInteger.ONE) {
            numerator.toString() // Just the numerator if the denominator is 1
        } else {
            "$numerator/$denominator" // Otherwise, show the full fraction
        }
    }

    /*
    * Companion Object:

    Một companion object trong Kotlin giống như một singleton liên kết với một lớp. Nó thường được
    * sử dụng để chứa các phương thức hoặc thuộc tính tĩnh.
    Ở đây, companion object được sử dụng để định nghĩa một hàm mở rộng cho kiểu String,
    * giúp chuyển đổi chuỗi sang kiểu Rational.
    *
    Hàm mở rộng toRational:
    Hàm này được định nghĩa để làm việc với các chuỗi định dạng kiểu phân số, ví dụ: "3/4" hoặc "5".
    Phương thức sẽ chuyển đổi chuỗi này thành một đối tượng Rational.
    *  */
    // Companion object to support string-to-Rational conversion
    companion object {
        fun String.toRational(): Rational {
            /* Hàm split("/") chia chuỗi thành các phần dựa trên ký tự /.
            Kết quả split là một danh sách (List<String>):
            Nếu chuỗi chỉ là một số (vd: "5"), danh sách sẽ chứa một phần tử duy nhất (["5"]).
            Nếu chuỗi là phân số (vd: "3/4"), danh sách sẽ chứa hai phần tử (["3", "4"]). */
            val parts = this.split("/")
            return if (parts.size == 1) {
                val numerator = BigInteger(parts[0])
                Rational(numerator, BigInteger.ONE).normalize()
            } else {
                val numerator = BigInteger(parts[0])
                val denominator = BigInteger(parts[1])
                Rational(numerator, denominator).normalize()
            }
        }
    }
}

/* Đoạn code này định nghĩa các hàm infix trong Kotlin để hỗ trợ tạo các đối tượng Rational từ các
kiểu số nguyên khác nhau (Int, Long, BigInteger). Hãy cùng phân tích chi tiết:

Hàm infix là gì?
Hàm infix là một hàm được gọi mà không cần sử dụng dấu chấm (.) hoặc dấu ngoặc đơn.
Nó cho phép viết code ngắn gọn và dễ đọc hơn khi biểu diễn các phép toán hoặc tạo đối tượng. */
// Infix function to create Rational numbers from Int, Long, or BigInteger
// Tạo một đối tượng Rational từ hai số nguyên Int.
/* Tham số:
this: Giá trị số nguyên đầu tiên (được gọi trên một đối tượng Int).
other: Số nguyên thứ hai (mẫu số của phân số).
Hoạt động:
this.toLong() và other.toLong(): Chuyển đổi các giá trị Int sang Long để tránh tràn số.
-> Do phạm vi của Long 64bit lớn hơn Int 32bit, việc chuyển đổi sang Long giúp tăng không gian số
để tránh tràn số khi thực hiện các phép toán lớn.

BigInteger.valueOf(...): Chuyển các giá trị Long sang BigInteger để sử dụng trong đối tượng Rational.
Tạo đối tượng Rational với tử số (this) và mẫu số (other).
Gọi .normalize() để chuẩn hóa phân số (giản ước nếu cần).
*/
infix fun Int.divBy(other: Int): Rational = Rational(
    BigInteger.valueOf(this.toLong()),
    BigInteger.valueOf(other.toLong())
).normalize()

infix fun Long.divBy(other: Long): Rational = Rational(
    BigInteger.valueOf(this),
    BigInteger.valueOf(other)
).normalize()

infix fun BigInteger.divBy(other: BigInteger): Rational = Rational(
    this, other).normalize()

// 5 divBy 2 // Thay vì viết 5.divBy(2)
// Testing the Rational class with examples

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}
