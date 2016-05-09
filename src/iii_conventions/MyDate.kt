package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.plus(interval: TimeInterval) : MyDate = addTimeIntervals(interval, 1)

class RepeatedTimeInterval(val interval: TimeInterval, val times: Int)

operator fun TimeInterval.times(times: Int) : RepeatedTimeInterval = RepeatedTimeInterval(this, times)

operator fun MyDate.plus(repeated: RepeatedTimeInterval) : MyDate = addTimeIntervals(repeated.interval, repeated.times)

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = DateIterator(this)

    class DateIterator(val dateRange: DateRange) : Iterator<MyDate> {
        var current: MyDate = dateRange.start

        override fun next(): MyDate {
            val result = current
            current = current.nextDay()
            return result
        }

        override fun hasNext(): Boolean = current <= dateRange.endInclusive
    }

    operator fun contains(value: MyDate): Boolean = start < value && value < endInclusive
}
