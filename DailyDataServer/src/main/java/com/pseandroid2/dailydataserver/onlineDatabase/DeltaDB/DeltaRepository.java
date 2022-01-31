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
package com.pseandroid2.dailydataserver.onlineDatabase.DeltaDB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The repository for Deltas, to operate on the Table.
 */
@Repository
public interface DeltaRepository extends JpaRepository<Delta, DeltaID> {


    /**
     * Count all Deltas, which are added by a defined user, after a given time.
     *
     * @param user          which had uploaded the recommended Deltas
     * @param addedToServer after which point of time the Deltas should be count.
     * @return the amount of Deltas
     */
    long countByUserAndAddedToServerIsAfter(String user, LocalDateTime addedToServer);

    /**
     * Returns all Deltas, which are initially added to the server before a given point of time and requested by a
     * given participant.
     *
     * @param addedToServer the time, before the Deltas should be added.
     * @param requestedBy   the user, who requested them.
     * @return a list of the recommended Deltas.
     */
    List<Delta> findByAddedToServerIsBeforeAndRequestedBy(LocalDateTime addedToServer, String requestedBy);

    /**
     * Returns all Deltas, which a user requested in a specified project.
     *
     * @param user      the user, who requested the Deltas.
     * @param projectID the project, in which the Deltas were requested.
     * @return a list of the recommended Deltas.
     */
    List<Delta> findByRequestedByAndProject(String user, long projectID);


}
