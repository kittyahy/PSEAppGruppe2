/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/

package com.pseandroid2.dailydata.util

class SortedIntListUtil {

    companion object {

        /**
         * Finds the first missing Integer using binary Search
         *
         * @return The first Integer that is missing in the given list
         */
        public fun getFirstMissingInt(list: List<Int>): Int {
            //setup for binary search
            var start: Int = 0
            var end: Int = list.size - 1
            var middle: Int = (start + end) / 2

            if (list.isEmpty()) {
                return 1
            }

            if (list[start] == 1 && list[end] == list.size) {
                //There are no ids between 1 and list.size that aren't currently in use
                return list.size + 1
            }

            //binary Search
            while (start < end) {
                if ((list[middle] - list[start]) != (middle - start)) {
                    //There is an id that isn't currently in use in the first half
                    if ((middle - start) == 1 && (list[middle] - list[start]) > 1) {
                        return (list[middle] - 1)
                    }

                    end = middle
                } else if ((list[end] - list[end]) != (end - middle)) {
                    //There is an id that isn't currently in use in the second half
                    if ((end - middle) == 1 && (list[end] - list[middle]) > 1) {
                        return (list[middle] + 1)
                    }

                    start = middle
                }

                middle = (start + end) / 2
            }

            //List does not contain any elements
            return 1
        }

    }

}