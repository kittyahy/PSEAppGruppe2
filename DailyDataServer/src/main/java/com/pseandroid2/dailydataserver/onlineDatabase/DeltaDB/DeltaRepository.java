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

@Repository
public interface DeltaRepository extends JpaRepository<Delta, DeltaID> {

    List<Delta> findByProjectAndRequestedByIs(long project, String requestedBy);

    long countByUserAndAddedToServerIsAfter(String user, LocalDateTime addedToServer);

    List<Delta> findByAddedToServerIsBeforeAndRequestedBy(LocalDateTime addedToServer, String requestedBy);

    List<Delta> findByRequestedByAndProject(String user, long projectID);






}
