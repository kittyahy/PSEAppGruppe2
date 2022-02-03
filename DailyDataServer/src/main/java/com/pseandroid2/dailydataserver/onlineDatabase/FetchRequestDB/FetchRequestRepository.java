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
package com.pseandroid2.dailydataserver.onlineDatabase.FetchRequestDB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The repository for FetchRequests.
 * <p>
 * It defines methods to operate on the table FetchRequests_FetchReqTable.
 */
@Repository
public interface FetchRequestRepository extends JpaRepository<FetchRequest, Integer> {
    /**
     * Returns all FetchRequests, which belongs to one specified Project.
     *
     * @param project the project, to which the FetchRequests belongs
     * @return all recommended FetchRequests.
     */
    List<FetchRequest> findByProject(long project);

    /**
     * Returns all FetchRequests, which belongs to a user in a project.
     *
     * @param user    the user, which had created the FetchRequest.
     * @param project the project, to which the FetchRequest belongs.
     * @return all recommended FetchRequests.
     */
    List<FetchRequest> findByUserAndProject(String user, long project);


}
