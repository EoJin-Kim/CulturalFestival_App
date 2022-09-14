package com.ej.culturalfestival.util

import com.ej.culturalfestival.dto.LocalDateDto
import com.ej.culturalfestival.dto.StartEndDate
import com.ej.culturalfestival.dto.WeekInfoDto
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CalendarUtil {

    companion object{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        fun yearMonthFromDate(date : LocalDate):String{
            val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월")
            return date.format(formatter)
        }
        fun monthDayFromDate(date : LocalDate) : String{
            val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("MM월 dd일")
            return date.format(formatter)
        }

        fun dateListTodateDtoList(
            dateList: MutableList<LocalDate?>,
        ) : MutableList<LocalDateDto>{
            val dateDtoList = mutableListOf<LocalDateDto>()

            for (localDate in dateList) {
                dateDtoList.add(LocalDateDto(localDate))
            }
            return dateDtoList
        }

        fun setDayWeek(date : LocalDate) : WeekInfoDto {

            val yearMonth = YearMonth.from(date)

            // 해당 월 마지막 날짜 가져오기(예 28, 30, 31)
            val monthDayCnt : kotlin.Int = yearMonth.lengthOfMonth()

            // 해당 월의 첫 번째 날 가져오기 (예 4월1일)
            val firstDay : LocalDate =date.withDayOfMonth(1)

            //첫 번째 날 요일 가져오기(월:1, 일:7)
            var dayOfWeek : kotlin.Int = firstDay.dayOfWeek.value


            if (dayOfWeek == 7) {
                dayOfWeek = 0
            }
            val firstWeekCnt = 7-dayOfWeek
            val lastWeekCnt = (monthDayCnt-firstWeekCnt)%7
            val fullWeekDayCount = monthDayCnt-firstWeekCnt-lastWeekCnt

            val nowDay = date.dayOfMonth

            val startEndDateList :MutableList<StartEndDate> = mutableListOf()
            // 첫 주
            val firstStartEndDate= StartEndDate(
                LocalDate.of(date.year,date.month,1),
                LocalDate.of(date.year,date.month,firstWeekCnt)
            )
            startEndDateList.add(firstStartEndDate)

            // 두번째 주부터 7일 완전한 주 마지막까지
            for ( weekRow : kotlin.Int in 1 until fullWeekDayCount/7+1){
                val startDate = LocalDate.of(date.year,date.month,firstWeekCnt+(weekRow-1)*7 +1)
                val endDate = LocalDate.of(date.year,date.month,firstWeekCnt+weekRow*7)
                val startEndDate = StartEndDate(startDate,endDate)
                startEndDateList.add(startEndDate)
            }

            // 마지막 완전하지 않은 주
            if(lastWeekCnt!=0){
                val startDate = LocalDate.of(date.year,date.month,date.lengthOfMonth()-lastWeekCnt+1)
                val endDate = LocalDate.of(date.year,date.month,date.lengthOfMonth())
                val startEndDate = StartEndDate(startDate,endDate)
                startEndDateList.add(startEndDate)
            }

            var idx = 1
            for (startEndDate in startEndDateList) {
                if(
                    date.isEqual(startEndDate.startDate) ||
                    date.isEqual(startEndDate.endDate) ||
                    (date.isAfter(startEndDate.startDate) && date.isBefore(startEndDate.endDate))
                ){
                    break
                }
                idx++
            }
            val weekInfoDto = WeekInfoDto(idx,startEndDateList)
            return weekInfoDto


        }
        fun setWeekTitleText(nowWeek : WeekInfoDto) : String{
            val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("MM")
            val monthStr = nowWeek.startEndDateList[nowWeek.weekRow-1].startDate.format(formatter)
            return "${monthStr}월 ${nowWeek.weekRow}주"
        }

        fun moveNextOneWeek(weekInfoDto: WeekInfoDto) : WeekInfoDto {
            if (weekInfoDto.weekRow == weekInfoDto.startEndDateList.size) {
                val nextWeekInfoDto = setDayWeek(weekInfoDto.startEndDateList[0].startDate.plusMonths(1))
                nextWeekInfoDto.weekRow = 1
                return nextWeekInfoDto
            }
            else {
                weekInfoDto.weekRow++
                return weekInfoDto
            }

        }
        
        fun moveNextOneMonth(weekInfoDto: WeekInfoDto) : WeekInfoDto {
            val nextWeekInfoDto = setDayWeek(weekInfoDto.startEndDateList[0].startDate.plusMonths(1))
            nextWeekInfoDto.weekRow = 1
            return nextWeekInfoDto
        }

        fun movePreOneWeek(weekInfoDto: WeekInfoDto) : WeekInfoDto {
            if (weekInfoDto.weekRow == 1) {
                val nextWeekInfoDto = setDayWeek(weekInfoDto.startEndDateList[0].startDate.minusMonths(1))
                nextWeekInfoDto.weekRow = nextWeekInfoDto.startEndDateList.size
                return nextWeekInfoDto
            }
            else {
                weekInfoDto.weekRow--
                return weekInfoDto
            }

        }
        
        fun movePreOneMonth(weekInfoDto: WeekInfoDto) : WeekInfoDto {
            val nextWeekInfoDto = setDayWeek(weekInfoDto.startEndDateList[0].startDate.minusMonths(1))
            nextWeekInfoDto.weekRow = nextWeekInfoDto.startEndDateList.size
            return nextWeekInfoDto
        }

    }

}